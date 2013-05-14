package cz.muni.fi.Web.Thesis;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import org.slf4j.LoggerFactory;

/**
 * Thread for sending e-mail messages.
 *
 * @author Matus Makovy
 */
public class MailThread extends Thread {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(MailThread.class);
    ServletContext sc = null;
    private boolean stop = false;
    private static String pass;
    private static String from;
    private static String host;

    public static void loadProperties() {
        Properties config = new Properties();
        try {
            config.load(new FileInputStream("/var/lib/openshift/516daf70e0b8cd59c4000169/app-root/data/config.properties"));
            host = config.getProperty("smtp");
            from = config.getProperty("emailUser");
            pass = config.getProperty("emailPassword");
        } catch (Exception ex) {
            log.error("Loading properties failed " + ex.getMessage());
        }

    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    @Override
    public void run() {

        loadProperties();

        while (!stop) {

            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.user", from);
            props.put("mail.smtp.password", pass);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props, null);
            MimeMessage message = new MimeMessage(session);



            if (sc != null) {

                BlockingQueue<Map<String, String>> messagesQueue = new LinkedBlockingQueue<Map<String, String>>();
                sc.setAttribute("messagesQueue", messagesQueue);

                while (true) {

                    Transport transport = null;
                    try {
                        message.setFrom(new InternetAddress(from));
                        transport = session.getTransport("smtp");
                        transport.connect(host, from, pass);
                    } catch (Exception ex) {
                        log.error(ex.getMessage());
                    }
                    
                    try {
                        Map<String, String> messageMap = messagesQueue.take();
                        message.setRecipient(Message.RecipientType.TO, new InternetAddress(messageMap.get("to")));
                        message.setSubject(messageMap.get("subject"));
                        message.setText(messageMap.get("text"));
                        transport.sendMessage(message, message.getAllRecipients());
                    } catch (Exception ex) {
                        log.error(ex.getMessage());
                    }
                }

            }

        }
    }

    public void setServletContext(ServletContext sc) {
        this.sc = sc;
    }
}
