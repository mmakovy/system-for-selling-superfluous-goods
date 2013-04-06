package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.Company;
import cz.muni.fi.thesis.CompanyManager;
import cz.muni.fi.thesis.CompanyManagerImpl;
import cz.muni.fi.thesis.DatabaseException;
import java.io.IOException;
import java.io.PrintWriter;
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
public class updateCompany extends HttpServlet {

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
        CompanyManager manager = new CompanyManagerImpl();

        HttpSession session = request.getSession();
        Object userID = session.getAttribute("userID");

        Long id = (Long) userID;
        Company company = null;


        try {


            out.println("<script src='myjs.js'>");
            out.println("</script>");


            try {
                company = manager.getCompanyById(id);
                if (company == null) {
                    out.println("Company wasnt found in database");
                } else {

                    out.println("<form method='post' name='form2' onsubmit='return submit_company()' action='/WebThesisMaven/auth/updateCompanyProcess?id=" + id + "'>");
                    out.println("Name:");
                    out.println("<input type='text' name='name' value='" + company.getName() + "'><br/>");
                    out.println("Email:");
                    out.println(company.getEmail() + "<br/>");
                    out.println("Phone Number:");
                    out.println("<input type='text' name='phone' value='" + company.getPhoneNumber() + "'><br/><br/>");
                    out.println("Other:");
                    out.println("<input type='text' name='other' value='" + company.getOther() + "'><br/><br/>");
                    out.println("Address: <br/>");
                    out.println("Street:");
                    out.println("<input type='text' name='street' value='" + company.getStreet() + "'><br/>");
                    out.println("City:");
                    out.println("<input type='text' name='city' value='" + company.getCity() + "'><br/>");
                    out.println("PSC:");
                    out.println("<input type='text' name='psc' value='" + company.getPsc() + "'><br/>");
                    out.println("Country:");
                    out.println("<input type='text' name='country' value='" + company.getCountry() + "'><br/>");

                    out.println("<input type='submit' name='submit' value='Update'>");
                    out.println("</form>");
                }
            } catch (DatabaseException ex) {
                out.println(ex.getMessage());
                log.error(ex.getMessage());
            }
            out.println("<a href='removeCompany'>Remove my company from system</a><br/>");
            out.println("<a href='changePassword.jsp'>Change password</a><br/>");
            out.println("<a href='/WebThesisMaven/auth/menu.jsp'>Go to Home Page</a>");

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
