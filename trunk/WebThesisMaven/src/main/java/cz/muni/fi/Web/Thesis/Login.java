package cz.muni.fi.Web.Thesis;

import cz.muni.fi.thesis.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;

/**
 *  Servlet for authentication.
 *  Servlet processes data from login.jsp
 * 
 * @author Matus Makovy
 */
public class Login extends HttpServlet {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(Login.class);

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

        CompanyManager companyManager = new CompanyManagerImpl();
        UserManager userManager = new UserManagerImpl();
        User user;
        HttpSession session;

        String userName = request.getParameter("userName");
        String pwd = request.getParameter("pwd");

        try {
            user = userManager.findUser(userName, pwd);

            if (user == null) {
                String message = "Wrong username or password";
                request.setAttribute("message", message);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else if (!userManager.isVerified(user)) {
                String message = "Your email address hasn't been verified yet, look in your mailbox for email from our system";
                request.setAttribute("message", message);
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            } else {
                session = request.getSession(true);
                session.setAttribute("userID", user.getIdCompany());
                response.sendRedirect("https://sssg-sssg.rhcloud.com/auth/index.jsp");
            }

        } catch (Exception ex) {
            log.error(ex.getMessage());
            String message = ex.getMessage();
            request.setAttribute("message", message);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
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
