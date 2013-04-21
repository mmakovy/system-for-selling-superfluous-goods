package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.*;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matus
 */
public class MySubscriptions extends HttpServlet {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(MySubscriptions.class);

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

        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("userID");

        MailingListManager mailingListManager = new MailingListManagerImpl();
        CompanyManager companyManager = new CompanyManagerImpl();

        Company company;
        List<Offer> offers;

        try {
            company = companyManager.getCompanyById(id);

            if (company != null) {
                offers = mailingListManager.getOffers(company);

                if (offers == null) {
                    log.error("getOffers() returned null");
                    String message = "Sorry, we are experiencing some problems, please try again<br/>";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("../error.jsp").forward(request, response);
                } else if (offers.isEmpty()) {
                    String message = "You have no subscriptions";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("../error.jsp").forward(request, response);
                } else {
                    request.setAttribute("offers", offers);
                    request.getRequestDispatcher("mySubscriptions.jsp").forward(request, response);
                }

            } else {
                log.error("getCompanyById() returned null");
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
