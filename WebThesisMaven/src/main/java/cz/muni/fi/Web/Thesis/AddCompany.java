package cz.muni.fi.Web.Thesis;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
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
import org.slf4j.LoggerFactory;

/**
 *
 * @author matus
 */
public class AddCompany extends HttpServlet {

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
        CompanyManager companyMng = new CompanyManagerImpl();

        Company company = new Company();
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone");
        String usrname = request.getParameter("usrname");
        String pwd = request.getParameter("pwd");
        String pwdVer = request.getParameter("pwd-ver");

        if (!pwd.equals(pwdVer)) {
            request.getSession().setAttribute("password", "error");
            response.sendRedirect("addcompany.jsp");
        } else {


            try {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet AddCompany</title>");
                out.println("</head>");
                out.println("<body>");

                if ((name != null && name.length() != 0)
                        && (email != null && email.length() != 0)
                        && (phoneNumber != null && phoneNumber.length() != 0)) {
                    company.setName(name);
                    company.setEmail(email);
                    company.setPhoneNumber(phoneNumber);

                    Company added = null;

                    try {
                        added = companyMng.addCompany(company, usrname, pwd);
                    } catch (DatabaseException ex) {
                        out.println(ex.getMessage());
                        log.error(ex.getMessage());
                    }


                    if (added != null) {
                        out.println("Company was succesfuly added");
                        out.println("You can <a href='login.jsp'>Log-in</a> now");
                        out.println("<form method = 'POST' action = '/WebThesisMaven/ListCompanies'> <input type = 'submit' value = 'List all companies' name = 'option' /> </form >");
                    } else {
                        out.println("Company wasnt added");
                    }
                } else {
                    out.println("Company wasnt added, because one of fields was left blank<br/>");
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
