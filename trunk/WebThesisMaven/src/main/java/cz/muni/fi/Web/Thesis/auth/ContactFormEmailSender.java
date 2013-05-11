package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.slf4j.LoggerFactory;

/**
 * Servlet that mediates sending e-mails from page with offer.
 * Servlet processes data obtained from showOffer.jsp
 * 
 * @author Matus Makovy
 */
public class ContactFormEmailSender extends HttpServlet {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(ContactFormEmailSender.class);

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
        OfferManager offerManager = new OfferManagerImpl();

        String text = request.getParameter("text");
        String offerId = request.getParameter("offerId");
        Long offerIdLong = Long.parseLong(offerId);

        String newline = System.getProperty("line.separator");

        HttpSession httpSession = request.getSession();
        Long userIdLong = (Long) httpSession.getAttribute("userID");

        String to = null;
        Offer offer = null;
        Company companyFrom = null;

        String remoteAddr = request.getRemoteAddr();
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey("6LdWet4SAAAAALOkcI8Auoub7_pM__sNyQUZbpdr");

        String challenge = request.getParameter("recaptcha_challenge_field");
        String uresponse = request.getParameter("recaptcha_response_field");
        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

        if (reCaptchaResponse.isValid()) {
            try {
                offer = offerManager.getOffer(offerIdLong);
                Long companyId = offer.getCompanyId();
                to = companyManager.getCompanyById(companyId).getEmail();
                companyFrom = companyManager.getCompanyById(userIdLong);
            } catch (Exception ex) {
                log.error(ex.getMessage());
                String message = "Sorry, we are experiencing some problems, please try again<br/>" + ex.getMessage();
                request.setAttribute("message", message);
                request.getRequestDispatcher("../error.jsp").forward(request, response);
            }

            String subject = "Reply to offer " + offer.getName();
            String messageText = companyFrom.toString() + newline + " in reply to offer "
                    + newline + offer.toString() + newline + " has sent you this message:" + text;

            try {
                BlockingQueue<Map<String, String>> messagesQueue = (BlockingQueue<Map<String, String>>) request.getSession().getServletContext().getAttribute("messagesQueue");
                Map<String, String> messageMap = new HashMap<String, String>();
                messageMap.put("to", to);
                messageMap.put("subject", subject);
                messageMap.put("text", messageText);
                messagesQueue.add(messageMap);

                String message = "Your email has been sent<br/>";
                request.setAttribute("message", message);
                request.getRequestDispatcher("../response.jsp").forward(request, response);
            } catch (Exception ex) {
                log.error(ex.getMessage());
                String message = "Sorry, we are experiencing some problems, please try again<br/>" + ex.getMessage();
                request.setAttribute("message", message);
                request.getRequestDispatcher("../error.jsp").forward(request, response);
            }
        } else {
            log.error("reCaptcha - wrong answer");
            String message = "Your reCaptcha answer was wrong";
            request.setAttribute("message", message);
            request.getRequestDispatcher("../error.jsp").forward(request, response);
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
