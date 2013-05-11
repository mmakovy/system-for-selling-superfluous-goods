package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.Category;
import cz.muni.fi.thesis.Offer;
import cz.muni.fi.thesis.OfferManager;
import cz.muni.fi.thesis.OfferManagerImpl;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;

/**
 *  Servlet for searching for offer in the database.
 *  Servlet processes data from findOffer.jsp.
 * 
 * @author Matus Makovy
 */
public class FindOffer extends HttpServlet {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(FindOffer.class);

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

        String expression = request.getParameter("expression");
        String minQuantity = request.getParameter("minQuantity");
        String maxQuantity = request.getParameter("maxQuantity");
        String minQuantityToBuy = request.getParameter("minQuantityToBuy");
        String maxQuantityToBuy = request.getParameter("maxQuantityToBuy");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String categoryString = request.getParameter("category");
        int minQuantityInt;
        int maxQuantityInt;
        int minQuantityToBuyInt;
        int maxQuantityToBuyInt;
        BigDecimal minPriceInt;
        BigDecimal maxPriceInt;


        HttpSession session = request.getSession();
        Object userID = session.getAttribute("userID");
        Long id = (Long) userID;

        try {
            offers = offerManager.findOffer(expression);

            if (minQuantity.length() > 0 && maxQuantity.length() > 0) {
                minQuantityInt = Integer.parseInt(minQuantity);
                maxQuantityInt = Integer.parseInt(maxQuantity);
                offers = offerManager.filterQuantity(offers, minQuantityInt, maxQuantityInt);
            } else if (minQuantity.length() > 0 && maxQuantity.length() <= 0) {
                minQuantityInt = Integer.parseInt(minQuantity);
                maxQuantityInt = Integer.MAX_VALUE;
                offers = offerManager.filterQuantity(offers, minQuantityInt, maxQuantityInt);
            } else if (minQuantity.length() <= 0 && maxQuantity.length() > 0) {
                minQuantityInt = 0;
                maxQuantityInt = Integer.parseInt(maxQuantity);
                offers = offerManager.filterQuantity(offers, minQuantityInt, maxQuantityInt);
            }

            if (minQuantityToBuy.length() > 0 && maxQuantityToBuy.length() > 0) {
                minQuantityToBuyInt = Integer.parseInt(minQuantityToBuy);
                maxQuantityToBuyInt = Integer.parseInt(maxQuantityToBuy);
                offers = offerManager.filterQuantityToBuy(offers, minQuantityToBuyInt, maxQuantityToBuyInt);
            } else if (minQuantityToBuy.length() > 0 && maxQuantityToBuy.length() <= 0) {
                minQuantityToBuyInt = Integer.parseInt(minQuantityToBuy);
                maxQuantityToBuyInt = Integer.MAX_VALUE;
                offers = offerManager.filterQuantityToBuy(offers, minQuantityToBuyInt, maxQuantityToBuyInt);
            } else if (minQuantityToBuy.length() <= 0 && maxQuantityToBuy.length() > 0) {
                minQuantityToBuyInt = 0;
                maxQuantityToBuyInt = Integer.parseInt(maxQuantityToBuy);
                offers = offerManager.filterQuantityToBuy(offers, minQuantityToBuyInt, maxQuantityToBuyInt);
            }

            if (minPrice.length() > 0 && maxPrice.length() > 0) {
                minPriceInt = new BigDecimal(minPrice);
                maxPriceInt = new BigDecimal(maxPrice);
                offers = offerManager.filterPrice(offers, minPriceInt, maxPriceInt);
            } else if (minPrice.length() > 0 && maxPrice.length() <= 0) {
                minPriceInt = new BigDecimal(minPrice);
                maxPriceInt = new BigDecimal("1000000");
                offers = offerManager.filterPrice(offers, minPriceInt, maxPriceInt);
            } else if (minPrice.length() <= 0 && maxPrice.length() > 0) {
                minPriceInt = BigDecimal.ZERO;
                maxPriceInt = new BigDecimal(maxPrice);
                offers = offerManager.filterPrice(offers, minPriceInt, maxPriceInt);
            }

            if (!categoryString.equals("----")) {
                Category category = Category.valueOf(categoryString.toUpperCase());
                offers = offerManager.filterCategory(offers, category);
            }

            if (offers == null) {
                log.error("findOffer() or one of filters returned null");
                String message = "We have some internal problems, please try again later";
                request.setAttribute("message", message);
                request.getRequestDispatcher("../error.jsp").forward(request, response);
            } else if (offers.isEmpty()){
                String message = "No results for your querry";
                request.setAttribute("message", message);
                request.getRequestDispatcher("findOffer.jsp").forward(request, response);
            } else {
                request.setAttribute("offers", offers);
                request.getRequestDispatcher("listOffers.jsp").forward(request, response);
            }

        } catch (Exception ex) {
            log.error(ex.getMessage());
            String message = ex.getMessage();
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
