/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.Web.Thesis;

import cz.muni.fi.thesis.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
public class AddOffer extends HttpServlet {

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

        CompanyManager CompanyManager = new CompanyManagerImpl();
        OfferManager OfferManager = new OfferManagerImpl();
        HttpSession session = request.getSession();

        try {

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddOffer</title>");
            out.println("</head>");
            out.println("<body>");

            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String quantityString = request.getParameter("quantity");
            String priceString = request.getParameter("price");
            Object idString = session.getAttribute("userID");

            if (name.length() != 0 && description.length() != 0
                    && idString != null && priceString.length() != 0
                    && quantityString.length() != 0) {

                Long id = (Long)idString;
                BigDecimal price = new BigDecimal(priceString);
                int quantity = Integer.parseInt(quantityString);

                Offer offer = new Offer();
                offer.setCompany_id(id);
                offer.setDescription(description);
                offer.setName(name);
                offer.setPrice(price);
                offer.setQuantity(quantity);

                try {

                    Company company = CompanyManager.getCompany(id);

                    if (company == null) {
                        out.println("Company wasnt found interface database");
                    } else {
                        Offer added = OfferManager.addOffer(company, offer);
                        if (added == null) {
                            out.println("Offer wasnt added");
                        } else {
                            out.println("Offer was sucessfuly added");
                        }
                    }

                } catch (DatabaseException ex) {
                    log.error(ex.getMessage());
                    out.println(ex.getMessage());
                }
            } else {
                out.println("Offer wasnt added because one of text fields was left blank or you didn choose company");
            }


            out.println("<a href='/WebThesisMaven/index.jsp'>Go to Home Page</a>");
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
