package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.Web.Thesis.MailSender;
import cz.muni.fi.thesis.*;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matus Makovy
 */
public class removeOffer extends HttpServlet {

    final static Logger log = LoggerFactory.getLogger(removeOffer.class);

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

        OfferManager offerManager = new OfferManagerImpl();
        Long id = Long.parseLong(request.getParameter("id"));
        Offer offer;
        MailSender mailSender = new MailSender();
        MailingListManager mailingListManager = new MailingListManagerImpl();
        HttpSession session = request.getSession();
        Long userID = (Long) session.getAttribute("userID");
        String newline = System.getProperty("line.separator");


        try {
            offer = offerManager.getOffer(id);

            if (offer == null) {
                log.error("getOffer() returned null");
                String message = "Offer wasnt found in database";
                request.setAttribute("message", message);
                request.getRequestDispatcher("../error.jsp").forward(request, response);
            } else if (!userID.equals(offer.getCompany_id())) {
                log.error("Access denied");
                String message = "You don't have permission to do this";
                request.setAttribute("message", message);
                request.getRequestDispatcher("../error.jsp").forward(request, response);
            } else {
                
                List<String> emails = mailingListManager.getEmails(offer.getId());
                offerManager.removeOffer(offer);            

                if (!emails.isEmpty()) {
                    String messageText = "Offer " + offer.toString() + "was deleted from system" 
                            + newline + "You can disable this notifications in My Subscriptions section " 
                            + newline + "https://sssg-sssg.rhcloud.com/auth/MySubscriptions" ;
                    mailSender.sendMoreEmails(emails, "Offer" + offer.getName() + " was deleted", messageText);
                }

                String message = "Offer was deleted from database";
                request.setAttribute("message", message);
                request.getRequestDispatcher("../response.jsp").forward(request, response);
            }

        } catch (Exception ex) {
            log.error(ex.getMessage());
            String message = "Sorry, we are experiencing some problems, please try again<br/>" + ex.getMessage();
            request.setAttribute("message", message);
            request.getRequestDispatcher("../error.jsp").forward(request, response);
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
