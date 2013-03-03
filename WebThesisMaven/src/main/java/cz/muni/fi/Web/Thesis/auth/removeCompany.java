package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.*;
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
public class removeCompany extends HttpServlet {

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
        Company company = null;

        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("userID");

            try {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet removeCompany</title>");
                out.println("</head>");
                out.println("<body>");

                try {
                    company = manager.getCompany(id);

                    if (company == null) {
                        out.println("Company wasnt found in database");
                    } else {
                        manager.removeCompany(company);
                        out.println(company.toString() + " was deleted<br/>");
                    }

                } catch (DatabaseException ex) {
                    out.println(ex.getMessage());
                    log.error(ex.getMessage());
                } catch (CompanyException ex) {
                    out.println(ex.getMessage());
                    log.error(ex.getMessage());
                } catch (OfferException ex) {
                    out.println(ex.getMessage());
                    log.error(ex.getMessage());
                } catch (Exception ex) {
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
