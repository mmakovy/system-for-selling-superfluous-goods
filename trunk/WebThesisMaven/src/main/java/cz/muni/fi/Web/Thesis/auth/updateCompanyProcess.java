/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.*;
import java.io.IOException;
import java.io.PrintWriter;
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
public class updateCompanyProcess extends HttpServlet {

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
        CompanyManager manager = new CompanyManagerImpl();

        Company company = new Company();
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone");

        Long id = Long.parseLong(request.getParameter("id"));

        HttpSession session = request.getSession();
        Object userID = session.getAttribute("userID");

        Long userIdLong = (Long) userID;


        if (!id.equals(userIdLong)) {
            response.sendRedirect("denied.jsp");
        } else {

            try {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet updateCompanyProcess</title>");
                out.println("</head>");
                out.println("<body>");

                if ((name != null && name.length() != 0)
                        && (email != null && email.length() != 0)
                        && (phoneNumber != null && phoneNumber.length() != 0)) {
                    company.setName(name);
                    company.setEmail(email);
                    company.setPhoneNumber(phoneNumber);
                    company.setId(id);

                    try {
                        manager.updateCompany(company);
                    } catch (DatabaseException ex) {
                        out.println(ex.getMessage());
                        log.error(ex.getMessage());
                    } catch (CompanyException ex) {
                        out.println(ex.getMessage());
                        log.error(ex.getMessage());
                    }

                    out.println("Company was succesfully updated");
                    out.println("<form method = 'POST' action = '/WebThesisMaven/auth/ListCompanies'> <input type = 'submit' value = 'List all companies' name = 'option' /> </form >");

                } else {
                    out.println("Company wasnt updated, because one of fields was left blank<br/>");
                }


                out.println("<a href='/WebThesisMaven/auth/menu.jsp'>Go to Home Page</a>");
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
