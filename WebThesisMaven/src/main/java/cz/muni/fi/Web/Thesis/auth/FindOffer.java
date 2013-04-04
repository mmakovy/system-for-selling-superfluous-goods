/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
public class FindOffer extends HttpServlet {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(CompanyManagerImpl.class);

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
        OfferManager offerManager = new OfferManagerImpl();
        List<Offer> offers = null;
        OffersLister offersLister = new OffersLister();

        String expression = request.getParameter("expression");
        String minQuantity = request.getParameter("min-quantity");
        String maxQuantity = request.getParameter("max-quantity");
        String minQuantityToBuy = request.getParameter("min-quantity-to-buy");
        String maxQuantityToBuy = request.getParameter("max-quantity-to-buy");
        String minPrice = request.getParameter("min-price");
        String maxPrice = request.getParameter("max-price");
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

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FindOffer</title>");
            out.println("</head>");
            out.println("<body>");

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

                offersLister.OffersToTable(offers, out, id);
                
            } catch (DatabaseException ex) {
                log.error(ex.getMessage());
                out.println(ex.getMessage());
            }

            out.println("<a href='/WebThesisMaven/auth/menu.jsp'>Go to Home Page</a>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
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
