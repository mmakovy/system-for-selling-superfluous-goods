package cz.muni.fi.Web.Thesis;

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
        Long id = (Long)session.getAttribute("userID");
        Company company = null;


        try {

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet updateCompany</title>");
            out.println("</head>");
            out.println("<script src='myjs.js'>");
            out.println("</script>");
            out.println("<body>");

            try {
                company = manager.getCompany(id);
                if (company == null) {
                    out.println("Company wasnt found in database");
                } else {
                    
                    out.println("<form method='post' name='form2' onsubmit='return submit_company()' action='/WebThesisMaven/updateCompanyProcess?id=" + id + "'>");
                    out.println("Name:");
                    out.println("<input type='text' name='name' value='" + company.getName() + "'><br/>");
                    out.println("Email:");
                    out.println("<input type='text' name='email' value='" + company.getEmail() + "'><br/>");
                    out.println("Phone Number:");
                    out.println("<input type='text' name='phone' value='" + company.getPhoneNumber() + "'><br/>");

                    out.println("<input type='submit' name='submit' value='Update'>");
                    out.println("</form>");
                }
            } catch (DatabaseException ex) {
                out.println(ex.getMessage());
                log.error(ex.getMessage());
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
