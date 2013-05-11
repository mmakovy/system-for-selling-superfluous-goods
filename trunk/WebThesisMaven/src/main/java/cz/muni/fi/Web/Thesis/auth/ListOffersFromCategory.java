package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.Category;
import cz.muni.fi.thesis.Offer;
import cz.muni.fi.thesis.OfferManager;
import cz.muni.fi.thesis.OfferManagerImpl;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;

/**
 * Servlet obtianes data from database assigned to selected category.
 * Servlet passes data to listOffers.jsp
 *
 * @author Matus Makovy
 */
public class ListOffersFromCategory extends HttpServlet {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(ListOffersFromCategory.class);

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
        List<Offer> offers;
        HttpSession session = request.getSession();
        Object userId = session.getAttribute("userID");
        Long id = (Long) userId;

        String categoryString = request.getParameter("category");
        Category category = null;

        try {
            category = Category.valueOf(categoryString.toUpperCase());
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage());
            String message = "System doesn't have category " + categoryString;
            request.setAttribute("message", message);
            request.getRequestDispatcher("../error.jsp").forward(request, response);
        }

        try {
            offers = offerManager.getOffersFromCategory(category);

            if (offers == null) {
                log.error("getOffersFromCategory() returned null");
                String message = "We have some internal problems. Please, try again later";
                request.setAttribute("message", message);
                request.getRequestDispatcher("../error.jsp").forward(request, response);
            } else if (offers.isEmpty()) {
                String message = "No offers from category " + categoryString + " in database";
                request.setAttribute("message", message);
                request.getRequestDispatcher("../error.jsp").forward(request, response);
            } else {
                request.setAttribute("offers", offers);
                request.getRequestDispatcher("listOffers.jsp").forward(request, response);
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
