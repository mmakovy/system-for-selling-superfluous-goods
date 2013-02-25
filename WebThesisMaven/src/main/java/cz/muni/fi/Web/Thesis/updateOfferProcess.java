/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.Web.Thesis;

import cz.muni.fi.thesis.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class updateOfferProcess extends HttpServlet {

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
        OfferManager manager = new OfferManagerImpl();
        Offer offer = null;

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String quantityString = request.getParameter("quantity");
        String priceString = request.getParameter("price");
        Long id = Long.parseLong(request.getParameter("id"));



        /**
         * testing log-in
         */
        HttpSession session = request.getSession();
        Object userID = session.getAttribute("userID");

        if (userID == null) {
            response.sendRedirect("index.jsp");
        } else {
            Long userIdLong = (Long) userID;

            /**
             * end of login testing
             */
            try {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet updateCompanyProcess</title>");
                out.println("</head>");
                out.println("<body>");

                try {
                    offer = manager.getOffer(id);
                } catch (DatabaseException ex) {
                    Logger.getLogger(updateOfferProcess.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (offer == null) {
                    out.println("Offer wasnt found in database");
                } else {

                    if (name.length() != 0 && description.length() != 0
                            && priceString.length() != 0
                            && quantityString.length() != 0) {

                        BigDecimal price = new BigDecimal(priceString);
                        int quantity = Integer.parseInt(quantityString);

                        if (!userIdLong.equals(offer.getCompany_id())) {
                            response.sendRedirect("denied.jsp");
                        } else {
                            Offer updatedOffer = new Offer();
                            updatedOffer.setDescription(description);
                            updatedOffer.setName(name);
                            updatedOffer.setPrice(price);
                            updatedOffer.setQuantity(quantity);
                            updatedOffer.setId(id);
                            updatedOffer.setCompany_id(offer.getCompany_id());

                            try {
                                manager.updateOffer(updatedOffer);
                            } catch (DatabaseException ex) {
                                out.println(ex.getMessage());
                                log.error(ex.getMessage());
                            } catch (OfferException ex) {
                                out.println(ex.getMessage());
                                log.error(ex.getMessage());
                            }

                            out.println("Offer was succesfully updated");
                            out.println("<form method = 'POST' action = '/WebThesisMaven/ListOffers'> <input type = 'submit' value = 'List all offers' name = 'option' /> </form >");
                        }
                    } else {
                        out.println("Company wasnt updated, because one of fields was left blank<br/>");
                    }
                }

                out.println("<a href='/WebThesisMaven/index.jsp'>Go to Home Page</a>");
                out.println("</body>");
                out.println("</html>");
            } finally {
                out.close();
            }
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
