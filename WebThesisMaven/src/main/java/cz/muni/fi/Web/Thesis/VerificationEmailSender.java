package cz.muni.fi.Web.Thesis;

import cz.muni.fi.thesis.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VerificationEmailSender extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html;charset=UTF-8");

        CompanyManager companyManager = new CompanyManagerImpl();
        UserManager userManager = new UserManagerImpl();
        PrintWriter out = response.getWriter();

        String userID = request.getParameter("id");
        Long id = Long.parseLong(userID);
        Company company = null;
        User user = null;
        try {
            company = companyManager.getCompanyById(id);
            user = userManager.getUser(id);
        } catch (DatabaseException ex) {
            Logger.getLogger(VerificationEmailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        String to = company.getEmail();

        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);
        
        try {
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("SSSG - E-mail Verification");
            message.setText("Verify your e-mail: http://localhost:8090/WebThesisMaven/VerifyEmail?code=" + user.getHashVer()+"");
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ex) {
            Logger.getLogger(VerificationEmailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        out.println("Your registration is complete <br/>");
        out.println("Please verify your e-mail address " + company.getEmail() + " , you should recieve an e-mail<br/>");
        out.println("<a href='index.jsp'>Home</a>");
    }   

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}