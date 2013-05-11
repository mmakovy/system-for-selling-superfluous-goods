package cz.muni.fi.Web.Thesis;

import cz.muni.fi.thesis.UserException;
import cz.muni.fi.thesis.UserManager;
import cz.muni.fi.thesis.UserManagerImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;

/**
 * Servlet for e-mail verification.
 * Links from verification messages lead here for e-mail verification.
 *
 * @author Matus Makovy
 */
public class VerifyEmail extends HttpServlet {
    
    final static org.slf4j.Logger log = LoggerFactory.getLogger(VerifyEmail.class);

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
        String code = request.getParameter("code");

        try {
            userManager.verifyEmail(code);
            String message = "Your account was successfully activated <br/> You can login now";
            request.setAttribute("message", message);
            request.getRequestDispatcher("/response.jsp").forward(request, response);
        } catch (UserException ex) {
            log.error("Account not verified - code not found interface DB");
            String message = "Account wasnt activated, because verification code wasnt found in DB<br/>" + ex.getMessage() ;
            request.setAttribute("message", message);
            request.getRequestDispatcher("/error.jsp").forward(request, response);                    
        } catch (Exception ex) {
            String message = ex.getMessage() ;
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
