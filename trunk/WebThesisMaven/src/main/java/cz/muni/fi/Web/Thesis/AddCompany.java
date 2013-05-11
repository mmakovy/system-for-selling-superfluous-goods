package cz.muni.fi.Web.Thesis;

import com.google.common.base.CharMatcher;
import cz.muni.fi.thesis.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.slf4j.LoggerFactory;

/**
 * Servlet for adding company and user to the system. 
 * This servlet processes the data obtianed from addcompany.jsp
 * 
 * @author Matus Makovy
 */
public class AddCompany extends HttpServlet {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(AddCompany.class);

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
        request.setCharacterEncoding("UTF-8");

        CompanyManager companyMng = new CompanyManagerImpl();
        UserManager userManager = new UserManagerImpl();

        String remoteAddr = request.getRemoteAddr();
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey("6LdWet4SAAAAALOkcI8Auoub7_pM__sNyQUZbpdr");

        String challenge = request.getParameter("recaptcha_challenge_field");
        String uresponse = request.getParameter("recaptcha_response_field");
        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);
        
        if (reCaptchaResponse.isValid()) {
            
            Company company = new Company();
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phone");
            String usrname = request.getParameter("usrname");
            String pwd = request.getParameter("pwd");
            String street = request.getParameter("street");
            String city = request.getParameter("city");
            String country = request.getParameter("country");
            String psc = request.getParameter("psc");
            String other = request.getParameter("other");

            try {

                if (!CharMatcher.ASCII.matchesAllOf(usrname)) {
                    log.info("Username is not ASCII");
                    String message = "Username  - only characters from (a-z, A-z, 0-9). ";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("/addcompany.jsp").forward(request, response);
                } else {
                    if (userManager.isUsernameInDatabase(usrname)) {
                        String message = "Username is already in database";
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("/addcompany.jsp").forward(request, response);
                    } else {
                        if (companyMng.isEmailInDatabase(email)) {
                            String message = "Email is already in database";
                            request.setAttribute("message", message);
                            request.getRequestDispatcher("/addcompany.jsp").forward(request, response);
                        } else {

                            if ((name != null && name.length() != 0) && (email
                                    != null && email.length() != 0) && (phoneNumber != null
                                    && phoneNumber.length() != 0) && (pwd
                                    != null && pwd.length() != 0)) {
                                company.setName(name);
                                company.setEmail(email);
                                company.setPhoneNumber(phoneNumber);
                                company.setStreet(street);
                                company.setCountry(country);
                                company.setCity(city);
                                company.setOther(other);
                                company.setPsc(psc);

                                Company added;
                                added = companyMng.addCompany(company, usrname, pwd);
                                if (added != null) {
                                    log.info("Company was added");
                                    response.sendRedirect("VerificationEmailSender?id=" + added.getId() + "");
                                } else {
                                    log.error("addCompany() returned null");
                                    String message = "Sorry, we are experiencing some problems, please try again";
                                    request.setAttribute("message", message);
                                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                                }
                            } else {
                                log.info("Company wasnt added, because one of required fields was left blank");
                                String message = "Company wasnt added, because one required of fields was left blank<br/>";
                                request.setAttribute("message", message);
                                request.getRequestDispatcher("/addcompany.jsp").forward(request, response);
                            }
                        }

                    }
                }

            } catch (Exception ex) {
                log.error(ex.getMessage());
                String message = ex.getMessage();
                request.setAttribute("message", message);
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }

        } else {
            String message = "Your reCaptcha answer wasn't correct";
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
