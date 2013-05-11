package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.User;
import cz.muni.fi.thesis.UserManager;
import cz.muni.fi.thesis.UserManagerImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;

/**
 * Servlet for changing password.
 * Servlet processes data obtained from myProfile.jsp
 * 
 * @author Matus Makovy
 */
public class ChangePassword extends HttpServlet {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(ChangePassword.class);

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
        UserManager userManager = new UserManagerImpl();

        HttpSession session = request.getSession();
        Long id = (Long) session.getAttribute("userID");
        User user = null;

        String oldPassword = request.getParameter("old_password");
        String newPassword = request.getParameter("new_password");

        try {
            user = userManager.getUser(id);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            String message = ex.getMessage();
            request.setAttribute("message", message);
            request.getRequestDispatcher("../error.jsp").forward(request, response);
        }

        if (user == null) {
            log.error("getUser() returned null");
            String message = "We have some internal problems. Please, try again later";
            request.setAttribute("message", message);
            request.getRequestDispatcher("../error.jsp").forward(request, response);
        } else {
            try {
                userManager.changePassword(user, oldPassword, newPassword);
                String message = "Your password was succesfully changed";
                request.setAttribute("message", message);
                request.getRequestDispatcher("../response.jsp").forward(request, response);
            } catch (Exception ex) {
                log.error(ex.getMessage());
                String message = ex.getMessage();
                request.setAttribute("message", message);
                request.getRequestDispatcher("../error.jsp").forward(request, response);
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
