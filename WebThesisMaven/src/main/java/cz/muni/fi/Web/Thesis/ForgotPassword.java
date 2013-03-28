/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.Web.Thesis;

import cz.muni.fi.thesis.DatabaseException;
import cz.muni.fi.thesis.UserManager;
import cz.muni.fi.thesis.UserManagerImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

/**
 *
 * @author matus
 */
public class ForgotPassword extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        UserManager userManager = new UserManagerImpl();

        String email = request.getParameter("email");
        String password = null;

        String remoteAddr = request.getRemoteAddr();
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey("6LdWet4SAAAAALOkcI8Auoub7_pM__sNyQUZbpdr");

        String challenge = request.getParameter("recaptcha_challenge_field");
        String uresponse = request.getParameter("recaptcha_response_field");
        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

        if (reCaptchaResponse.isValid()) {
            try {
                password = userManager.forgotPassword(email);
            } catch (DatabaseException ex) {
                Logger.getLogger(ForgotPassword.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                /*
                 * TODO output your page here. You may use following sample
                 * code.
                 */
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet ForgotPassword</title>");
                out.println("</head>");
                out.println("<body>");

                if (password == null) {
                    out.println("E-mail wasnt found in our database");
                } else {

                    String host = "smtp.gmail.com";
                    String pass = "epson123";
                    String from = "no.reply.sssg@gmail.com";
                    Properties props = System.getProperties();
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host", host);
                    props.put("mail.smtp.user", "no.reply.sssg@gmail.com");
                    props.put("mail.smtp.password", pass);
                    props.put("mail.smtp.port", "587");
                    props.put("mail.smtp.auth", "true");

                    Session session = Session.getDefaultInstance(props, null);
                    MimeMessage message = new MimeMessage(session);

                    try {
                        message.setFrom(new InternetAddress(from));
                        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
                        message.setSubject("New password from SSSG");
                        String messageText = "Your new password is:" + password;
                        message.setText(messageText);
                        Transport transport = session.getTransport("smtp");
                        transport.connect(host, from, pass);
                        transport.sendMessage(message, message.getAllRecipients());
                        transport.close();
                    } catch (MessagingException ex) {
                        Logger.getLogger(VerificationEmailSender.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    out.println("New password has been sent to your e-mail<br/>");
                    out.println("You can <a href='login.jsp'>log-in</a> now <br/>");
                }

                out.println("</body>");
                out.println("</html>");
            } finally {
                out.close();
            }
        } else {
            out.print("Answer is wrong");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
