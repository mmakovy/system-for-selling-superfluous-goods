/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.*;
import java.io.IOException;
import java.io.PrintWriter;
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
public class ShowOffer extends HttpServlet {

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
        CompanyManager companyManager = new CompanyManagerImpl();

        Long id = Long.parseLong(request.getParameter("id"));
        Offer offer = null;
        Company company = null;

        HttpSession session = request.getSession();
        Object userID = session.getAttribute("userID");
        Long userIDLong = (Long) userID;

        try {

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ShowOffer</title>");
            out.println("</head>");
            out.println("<body>");

            try {
                offer = offerManager.getOffer(id);
                Long id_company = offer.getCompany_id();
                company = companyManager.getCompany(id_company);
            } catch (DatabaseException ex) {
                log.error(ex.getMessage());
                out.println(ex.getMessage());
            }

            if (offer == null || company == null) {
                out.println("Error when searching for company or offer in database");
            } else {
                out.println("<h1>OFFER</h1> <br/>");
                out.println("<u>Name:</u>");
                out.println(offer.getName() + "</br>");
                out.println("<u>Price:</u>");
                out.println(offer.getPrice() + "</br>");
                out.println("<u>Quantity:</u>");
                out.println(offer.getQuantity() + "</br>");
                out.println("<u>Minimal buy quantity:</u>");
                out.println(offer.getMinimalBuyQuantity() + "</br>");
                out.println("<u>Purchase Date:</u>");
                out.println(offer.getPurchaseDate() + "</br>");
                out.println("<u>Category:</u>");
                out.println(offer.getCategory() + "</br>");
                out.println("<u>Description:</u>");
                out.println(offer.getDescription() + "</br></br>");

                out.println("is offered by COMPANY<br/></br>");
                out.println("<u>Name:</u>");
                out.println(company.getName() + "</br>");
                out.println("<u>E-mail address:</u>");
                out.println(company.getEmail() + "</br>");
                out.println("<u>Phone number:</u>");
                out.println(company.getPhoneNumber() + "</br></br>");
                out.println("<u>Address:</u> <br/>");
                out.println(company.getStreet() + " " + company.getPsc() +"<br/>");
                out.println(company.getCity() + "<br/>");
                out.println(company.getCountry() + "<br/></br>");
                
                out.println("<form method='post' name='form2' action='/WebThesisMaven/auth/ContactFormEmailSender?offerId=" + offer.getId() + "'>");
                out.println("Send e-mail:<br/>");
                try {
                    out.println("Your e-mail address:" + companyManager.getCompany(userIDLong).getEmail() + "<br/>");
                } catch (DatabaseException ex) {
                    Logger.getLogger(ShowOffer.class.getName()).log(Level.SEVERE, null, ex);
                }
                out.println("Text of message:<br/>");
                out.println("<input type='text' name='text'>");
                out.println("<input type='submit' value='send'>");

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
