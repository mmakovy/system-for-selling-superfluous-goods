package cz.muni.fi.Web.Thesis;

import cz.muni.fi.thesis.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VerificationEmailSender extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html;charset=UTF-8");

        CompanyManager companyManager = new CompanyManagerImpl();
        UserManager userManager = new UserManagerImpl();
        PrintWriter out = response.getWriter();
        MailSender mailSender = new MailSender();

        String userID = request.getParameter("id");
        Long id = Long.parseLong(userID);
        Company company = null;
        User user = null;
        try {
            company = companyManager.getCompanyById(id);
            user = userManager.getUser(id);
        } catch (DatabaseException ex) {
            Logger.getLogger(VerificationEmailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String to = company.getEmail();
        String text = "Verify your e-mail: http://localhost:8090/WebThesisMaven/VerifyEmail?code=" + user.getHashVer()+"";
        
        mailSender.sendOneEmail(to, "SSSG - E-mail Verification", text);
        
        out.println("Your registration is complete <br/>");
        out.println("Please verify your e-mail address " + company.getEmail() + " , you should recieve an e-mail<br/>");
        out.println("<a href='index.jsp'>Home</a>");
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