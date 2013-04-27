package cz.muni.fi.Web.Thesis;

import cz.muni.fi.thesis.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matus Makovy
 */
public class VerificationEmailSender extends HttpServlet {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(VerificationEmailSender.class);

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html;charset=UTF-8");

        CompanyManager companyManager = new CompanyManagerImpl();
        UserManager userManager = new UserManagerImpl();
        
        String userID = request.getParameter("id");
        Long id = Long.parseLong(userID);
        Company company = null;
        User user = null;

        try {
            company = companyManager.getCompanyById(id);
            user = userManager.getUser(id);
        } catch (DatabaseException ex) {
            log.error(ex.getMessage());
            String message = ex.getMessage();
            request.setAttribute("message", message);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }

        String to = company.getEmail();
        String text = "Verify your e-mail: https://sssg-sssg.rhcloud.com/VerifyEmail?code=" + user.getVerificationString() + "";

        try {
            BlockingQueue<Map<String, String>> messagesQueue = (BlockingQueue<Map<String, String>>)request.getSession().getServletContext().getAttribute("messagesQueue");
            Map<String, String> messageMap = new HashMap<String, String>();
            messageMap.put("to", to);
            messageMap.put("subject", "SSSG - E-mail Verification");
            messageMap.put("text",text);
            messagesQueue.add(messageMap);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            String message = ex.getMessage();
            request.setAttribute("message", message);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }

        String message = "Your registration is complete <br/>Please verify your e-mail address " + company.getEmail() + " , you should recieve an e-mail<br/>";
        request.setAttribute("message", message);
        request.getRequestDispatcher("/response.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}