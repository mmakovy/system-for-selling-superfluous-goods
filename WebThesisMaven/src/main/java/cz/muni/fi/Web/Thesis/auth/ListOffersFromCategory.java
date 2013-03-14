/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author matus
 */
public class ListOffersFromCategory extends HttpServlet {

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
        List<Offer> offers = new ArrayList<Offer>();
        HttpSession session = request.getSession();
        Object userId = session.getAttribute("userID");
        Long id = (Long) userId;

        try {
            String categoryString = request.getParameter("category");
            Category category = null;

            try {
                category = Category.valueOf(categoryString.toUpperCase());
            } catch (IllegalArgumentException ex) {
                out.println("System doesnt have " + categoryString + " category");
            }

            try {
                offers = offerManager.getOffersFromCategory(category);

                if (offers.isEmpty()) {
                    out.println("No offers from this category in database");
                } else {
                    out.println("OFFERS");
                    out.println("<table>");
                    out.println("<th> ID </th>");
                    out.println("<th> Company ID </th>");
                    out.println("<th> Name </th>");
                    out.println("<th> Description </th>");
                    out.println("<th> Price </th>");
                    out.println("<th> Quantity </th>");
                    out.println("<th> Minimal Buy Quantity </th>");
                    out.println("<th> Purchase Date </th>");
                    out.println("<th> Category </th>");

                    for (int i = 0; i < offers.size(); i++) {
                        out.println("<tr>");
                        out.println("<td style='border: 1px solid black;'>" + offers.get(i).getId() + "</td>");
                        out.println("<td style='border: 1px solid black;'>" + offers.get(i).getCompany_id() + "</td>");
                        out.println("<td style='border: 1px solid black;'>" + offers.get(i).getName() + "</td>");
                        out.println("<td style='border: 1px solid black;'>" + offers.get(i).getDescription() + "</td>");
                        out.println("<td style='border: 1px solid black;'>" + offers.get(i).getPrice() + "</td>");
                        out.println("<td style='border: 1px solid black;'>" + offers.get(i).getQuantity() + "</td>");

                        if (offers.get(i).getMinimalBuyQuantity() == 0) {
                            out.println("<td style='border: 1px solid black;'> Not specified</td>");
                        } else {
                            out.println("<td style='border: 1px solid black;'>" + offers.get(i).getMinimalBuyQuantity() + "</td>");
                        }


                        if (offers.get(i).getPurchaseDate() == null) {
                            out.println("<td style='border: 1px solid black;'> Not specified</td>");
                        } else {
                            out.println("<td style='border: 1px solid black;'>" + offers.get(i).getPurchaseDate() + "</td>");
                        }

                        out.println("<td style='border: 1px solid black;'>" + offers.get(i).getCategory() + "</td>");
                        if (offers.get(i).getCompany_id().equals(id)) {
                            out.println("<td><a href='/WebThesisMaven/auth/removeOffer?id=" + offers.get(i).getId() + "'>Remove</a></td>");
                            out.println("<td><a href='/WebThesisMaven/auth/updateOffer?id=" + offers.get(i).getId() + "'>Update</a></td>");
                        }
                        out.println("</tr>");
                    }
                    out.println("</table>");

                }
            } catch (DatabaseException ex) {
                Logger.getLogger(ListOffersFromCategory.class.getName()).log(Level.SEVERE, null, ex);
            }

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListOffersFromCategory</title>");
            out.println("</head>");
            out.println("<body>");

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
