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
 * Servlet obtains all "my" offers from database.
 * Servlet passes data to listOffers.jsp.
 * 
 * @author Matus Makovy
 */
public class ListMyOffers extends HttpServlet {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(ListMyOffers.class);

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
        CompanyManager companyManager = new CompanyManagerImpl();
        List<Offer> offers;
        Company company;

        HttpSession session = request.getSession();
        Object userID = session.getAttribute("userID");

        Long id = (Long) userID;

        try {
            company = companyManager.getCompanyById(id);
            if (company == null) {
                String message = "Your company wasn't found in database";
                request.setAttribute("message", message);
                request.getRequestDispatcher("../error.jsp").forward(request, response);
            } else {
                offers = offerManager.getOffersByCompany(company);

                if (offers == null) {
                    log.error("getOffersByCompany() returned null");
                    String message = "We have some internal problems. Please, try again later";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("../error.jsp").forward(request, response);
                } else if (offers.isEmpty()) {
                    String message = "You have no offers in database";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("../error.jsp").forward(request, response);
                } else {
                    request.setAttribute("offers", offers);
                    request.getRequestDispatcher("listOffers.jsp").forward(request, response);
                }
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
