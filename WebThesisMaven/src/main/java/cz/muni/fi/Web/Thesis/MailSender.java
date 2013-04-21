package cz.muni.fi.Web.Thesis;

import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Matus Makovy
 */
public class MailSender {

    private static final String HOST = "smtp.gmail.com";
    private static final String FROM = "no.reply.sssg@gmail.com";
    private static final String PASS = "epson123";

    public void sendOneEmail(String to, String subject, String text) throws MessagingException {


        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.user", FROM);
        props.put("mail.smtp.password", PASS);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(FROM));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setText(text);
        Transport transport = session.getTransport("smtp");
        transport.connect(HOST, FROM, PASS);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

    }

    public void sendMoreEmails(List<String> recipients, String subject, String text) throws MessagingException {

        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.user", FROM);
        props.put("mail.smtp.password", PASS);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(FROM));
        message.setSubject(subject);
        message.setText(text);
        Transport transport = session.getTransport("smtp");
        transport.connect(HOST, FROM, PASS);

        for (String email : recipients) {
            String to = email;
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            transport.sendMessage(message, message.getAllRecipients());
        }

        transport.close();

    }
}
