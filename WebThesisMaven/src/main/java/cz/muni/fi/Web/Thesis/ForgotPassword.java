package cz.muni.fi.Web.Thesis;

import cz.muni.fi.thesis.UserManager;
import cz.muni.fi.thesis.UserManagerImpl;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.slf4j.LoggerFactory;

/**
 * Servlet for recovering the lost password.
 * Servlet processes data from forgotPassword.jsp.
 * 
 * @author Matus Makovy
 */
public class ForgotPassword extends HttpServlet {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(ForgotPassword.class);

    /**
     * 
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

        String email = request.getParameter("email");
        String password = null;

        String remoteAddr = request.getRemoteAddr();
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey("6LdWet4SAAAAALOkcI8Auoub7_pM__sNyQUZbpdr");

        String challenge = request.getParameter("recaptcha_challenge_field");
        String uresponse = request.getParameter("recaptcha_response_field");
        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

        if (reCaptchaResponse.isValid()) {
            try {
                password = userManager.newPassword(email);
            } catch (Exception ex) {
                log.error(ex.getMessage());
                String message = ex.getMessage();
                request.setAttribute("message", message);
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }

            if (password == null) {
                String message = "E-mail wasnt found in our database";
                request.setAttribute("message", message);
                request.getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
            } else {

                BlockingQueue<Map<String, String>> messagesQueue = (BlockingQueue<Map<String, String>>) request.getSession().getServletContext().getAttribute("messagesQueue");
                Map<String, String> messageMap = new HashMap<String, String>();
                messageMap.put("to", email);
                messageMap.put("subject", "New password from SSSG");
                messageMap.put("text", "Your new password is: " + password);
                messagesQueue.add(messageMap);

                String message = "New password has been sent to your e-mail<br/> You can login now <br/>";
                request.setAttribute("message", message);
                request.getRequestDispatcher("/response.jsp").forward(request, response);
            }
        } else {
            String message = "Your reCAPTCHA answer wasn't correct";
            request.setAttribute("message", message);
            request.getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
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
