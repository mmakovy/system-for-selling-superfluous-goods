/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matus
 */
public class updateCompanyProcess extends HttpServlet {
    
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
        
        CompanyManager manager = new CompanyManagerImpl();
        Company company = new Company();
        String name = request.getParameter("name");
        String phoneNumber = request.getParameter("phone");
        String street = request.getParameter("street");
        String city = request.getParameter("city");
        String country = request.getParameter("country");
        String psc = request.getParameter("psc");
        String other = request.getParameter("other");
        
        Long id = Long.parseLong(request.getParameter("id"));
        
        String email = null;
        
        try {
            email = manager.getCompanyById(id).getEmail();
        } catch (DatabaseException ex) {
            log.error(ex.getMessage());
            String message = ex.getMessage();
            request.setAttribute("message", message);
            request.getRequestDispatcher("../error.jsp").forward(request, response);
        }
        
        HttpSession session = request.getSession();
        Object userID = session.getAttribute("userID");
        
        Long userIdLong = (Long) userID;
        
        
        if (!id.equals(userIdLong)) {
            log.error("Access denied");
            String message = "You have not permission to do this";
            request.setAttribute("message", message);
            request.getRequestDispatcher("../error.jsp").forward(request, response);
        } else {
            
            if ((name != null && name.length() != 0)
                    && (phoneNumber != null && phoneNumber.length() != 0)) {
                company.setName(name);
                company.setEmail(email);
                company.setPhoneNumber(phoneNumber);
                company.setId(id);
                company.setPsc(psc);
                company.setCity(city);
                company.setCountry(country);
                company.setStreet(street);
                company.setOther(other);
                
                try {
                    manager.updateCompany(company);
                    String message = "Company was succesfully updated";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("../response.jsp").forward(request, response);
                } catch (DatabaseException ex) {
                    log.error(ex.getMessage());
                    String message = ex.getMessage();
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("../error.jsp").forward(request, response);
                } catch (CompanyException ex) {
                    log.error(ex.getMessage());
                    String message = ex.getMessage();
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("../error.jsp").forward(request, response);
                }                
                
            } else {
                String message = "Company wasnt updated, because one of fields was left blank<br/>";
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
