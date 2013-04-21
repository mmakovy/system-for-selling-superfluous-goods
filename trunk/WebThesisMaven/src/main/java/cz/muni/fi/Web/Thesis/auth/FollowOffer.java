package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matus Makovy
 */
public class FollowOffer extends HttpServlet {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(FollowOffer.class);

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
        MailingListManager mailingListManager = new MailingListManagerImpl();

        Long idOffer = Long.parseLong(request.getParameter("id"));
        Long idUser = (Long) request.getSession().getAttribute("userID");

        try {
            Company company = companyManager.getCompanyById(idUser);
            Offer offer = offerManager.getOffer(idOffer);

            if (company != null && offer != null) {
                    
                    if (mailingListManager.isFollowingThisOffer(company, offer)) {
                        String message = "You are already following this offer";
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("../error.jsp").forward(request, response);
                    } else {
                        mailingListManager.addEntry(company, offer);
                        String message = "Your email was added to mailing list, you will be notified on change of offer";
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("../response.jsp").forward(request, response);
                    }                               

            } else {
                log.error("company or offer isnt in database");
                String message = "Sorry, we are experiencing some problems, please try again<br/>";
                request.setAttribute("message", message);
                request.getRequestDispatcher("../error.jsp").forward(request, response);
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
