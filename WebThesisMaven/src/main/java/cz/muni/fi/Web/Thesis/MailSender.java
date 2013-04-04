/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.Web.Thesis;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author matus
 */
public class MailSender {

    public void sendOneEmail(String to, String subject, String text) {

        String host = "smtp.gmail.com";
        String from = "no.reply.sssg@gmail.com";
        String pass = "epson123";
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(text);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ex) {
            Logger.getLogger(VerificationEmailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMoreEmails(List<String> recipients, String subject, String text) {

        String host = "smtp.gmail.com";
        String from = "no.reply.sssg@gmail.com";
        String pass = "epson123";
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            message.setText(text);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);

            for (String email : recipients) {
                String to = email;
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                transport.sendMessage(message, message.getAllRecipients());
            }

            transport.close();
        } catch (MessagingException ex) {
            Logger.getLogger(VerificationEmailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
