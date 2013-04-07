package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.Company;
import cz.muni.fi.thesis.CompanyManager;
import cz.muni.fi.thesis.CompanyManagerImpl;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matus Makovy
 */
public class updateCompany extends HttpServlet {

    final static Logger log = LoggerFactory.getLogger(CompanyManagerImpl.class);

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
        CompanyManager manager = new CompanyManagerImpl();

        HttpSession session = request.getSession();
        Object userID = session.getAttribute("userID");
        Map<String,String> companyMap = new HashMap<String,String>();

        Long id = (Long) userID;
        Company company;

        try {
            company = manager.getCompanyById(id);
            if (company == null) {
                log.error("getCompanyById() returned null");
                String message = "Your company wasn't found in database";
                request.setAttribute("message", message);
                request.getRequestDispatcher("../error.jsp").forward(request, response);
            } else {

                companyMap.put("name", company.getName());
                companyMap.put("email", company.getEmail());
                companyMap.put("phoneNumber", company.getPhoneNumber());
                companyMap.put("other", company.getOther());
                companyMap.put("street", company.getStreet());
                companyMap.put("city", company.getCity());
                companyMap.put("psc", company.getPsc());
                companyMap.put("country", company.getCountry());
                
                request.setAttribute("companyMap", companyMap);
                request.getRequestDispatcher("myProfile.jsp").forward(request, response);

            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            String message = "Sorry, we are experiencing some problems, please try again<br/>" + ex.getMessage();
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
