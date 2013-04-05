/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.Web.Thesis.MailSender;
import cz.muni.fi.Web.Thesis.VerificationEmailSender;
import cz.muni.fi.thesis.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

/**
 *
 * @author matus
 */
public class ContactFormEmailSender extends HttpServlet {

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

        CompanyManager companyManager = new CompanyManagerImpl();
        OfferManager offerManager = new OfferManagerImpl();
        PrintWriter out = response.getWriter();
        MailSender mailSender = new MailSender();

        String text = request.getParameter("text");
        String offerId = request.getParameter("offerId");
        Long offerIdLong = Long.parseLong(offerId);

        HttpSession httpSession = request.getSession();
        Long userIdLong = (Long) httpSession.getAttribute("userID");

        String to = null;
        Offer offer = null;
        Company companyFrom = null;

        String remoteAddr = request.getRemoteAddr();
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey("6LdWet4SAAAAALOkcI8Auoub7_pM__sNyQUZbpdr");

        String challenge = request.getParameter("recaptcha_challenge_field");
        String uresponse = request.getParameter("recaptcha_response_field");
        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

        if (reCaptchaResponse.isValid()) {
            try {
                offer = offerManager.getOffer(offerIdLong);
                Long companyId = offer.getCompany_id();
                to = companyManager.getCompanyById(companyId).getEmail();
                companyFrom = companyManager.getCompanyById(userIdLong);
            } catch (DatabaseException ex) {
                Logger.getLogger(VerificationEmailSender.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String subject = "Reply to offer " + offer.getName();
            String messageText = "Company " + companyFrom.toString() + " has sent you this message:" + text;
            
            mailSender.sendOneEmail(to, subject, messageText);

            out.println("Your email has been sent<br/>");
            out.println("<a href='/WebThesisMaven/auth/menu.jsp'>Home</a>");

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