/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matus
 */
public class updateOffer extends HttpServlet {

    final static Logger log = LoggerFactory.getLogger(CompanyManagerImpl.class);

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
        Long id = Long.parseLong(request.getParameter("id"));
        Offer offer = null;

        HttpSession session = request.getSession();
        Long userID = (Long) session.getAttribute("userID");
        Calendar calendar = Calendar.getInstance();

            try {

                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet updateOffer</title>");
                out.println("</head>");
                out.println("<script src='myjs.js'>");
                out.println("</script>");
                out.println("<body>");

                try {
                    offer = manager.getOffer(id);

                    if (offer == null) {
                        out.println("Offer wasnt found in database");
                    } else {
                        /**
                         * testing if user has permission for this action
                         */
                        if (!userID.equals(offer.getCompany_id())) {
                            response.sendRedirect("denied.jsp");

                        } else {
                            /**
                             * end
                             */
                            if (offer == null) {
                                out.println("Offer wasnt found in database");
                            } else {
                                calendar.setTime(offer.getPurchaseDate());
                                int day = calendar.get(Calendar.DAY_OF_MONTH);
                                int month = calendar.get(Calendar.MONTH) + 1;
                                int year = calendar.get(Calendar.YEAR);

                                out.println("<form method='post' name='form1' onsubmit='return submit_offer()' action='/WebThesisMaven/updateOfferProcess?id=" + id + "&id_company=" + offer.getCompany_id() + "'>");
                                out.println("Name:");
                                out.println("<input type='text' name='name'  value='" + offer.getName() + "'><br/>");
                                out.println("Price:");
                                out.println("<input type='text' name='price'  value='" + offer.getPrice() + "'><br/>");
                                out.println("Quantity:");
                                out.println("<input type='text' name='quantity' value='" + offer.getQuantity() + "'><br/>");
                                out.println("Minimal Buy Quantity:");
                                out.println("<input type='text' name='minimal_buy' value='" + offer.getMinimalBuyQuantity() + "'><br/>");
                                out.println("Category:");
                                out.println("<select name='category'>");
                                if (offer.getCategory().toString().equals("BUILDING")) {
                                    out.println("<option selected>BUILDING</option>");
                                } else {
                                    out.println("<option>BUILDING</option>");
                                }
                                if (offer.getCategory().toString().equals("FURNITURE")) {
                                    out.println("<option selected>FURNITURE</option>");
                                } else {
                                    out.println("<option>FURNITURE</option>");
                                }
                                if (offer.getCategory().toString().equals("ELECTROTECHNICS")) {
                                    out.println("<option selected>ELECTROTECHNICS</option>");
                                } else {
                                    out.println("<option>ELECTROTECHNICS</option>");
                                }
                                if (offer.getCategory().toString().equals("CARS")) {
                                    out.println("<option selected>CARS</option>");
                                } else {
                                    out.println("<option>CARS</option>");
                                }
                                if (offer.getCategory().toString().equals("COMPUTER")) {
                                    out.println("<option selected>COMPUTER</option>");
                                } else {
                                    out.println("<option>COMPUTER</option>");
                                }
                                if (offer.getCategory().toString().equals("COSMETICS")) {
                                    out.println("<option selected>COSMETICS</option>");
                                } else {
                                    out.println("<option>COSMETICS</option>");
                                }
                                if (offer.getCategory().toString().equals("SPORTS")) {
                                    out.println("<option selected>SPORTS</option>");
                                } else {
                                    out.println("<option>SPORTS</option>");
                                }
                                out.println("</select>    <br/>");

                                out.println("Purchase Date:<br/>");
                                out.println("Day:");
                                out.println("<input type='text' name='dob_day' value='" + day + "'/>");
                                out.println("Month:");
                                out.println("<input type='text' name='dob_month' value='" + month  + "'/>");
                                out.println("Year:");
                                out.println("<input type='text' name='dob_year' value='" + year + "'/> <br/>");
                                
                                out.println("Description:");
                                out.println("<input type='text' name='description' value='" + offer.getDescription() + "'><br/>");

                                out.println("<input type='submit' name='submit' value='Update'>");
                                out.println("</form>");
                            }
                        }
                    }
                } catch (DatabaseException ex) {
                    out.println(ex.getMessage());
                    log.error(ex.getMessage());
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
