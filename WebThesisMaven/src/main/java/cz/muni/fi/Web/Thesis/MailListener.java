package cz.muni.fi.Web.Thesis;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 * Starts tread for sending e-mail messages.
 *
 * @author Matus Makovy
 */
public class MailListener implements ServletContextListener {
    
    private MailThread mailThread = new MailThread();

    public void contextInitialized(ServletContextEvent sce) {        
        mailThread.setName("Mail-Sender-Thread");
        mailThread.setServletContext(sce.getServletContext());
        mailThread.start();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        mailThread.setStop(true);    
    }
}
