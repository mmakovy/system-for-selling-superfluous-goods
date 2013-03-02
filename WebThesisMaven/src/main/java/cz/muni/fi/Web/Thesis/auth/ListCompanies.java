package cz.muni.fi.Web.Thesis.auth;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import cz.muni.fi.thesis.Company;
import cz.muni.fi.thesis.CompanyManager;
import cz.muni.fi.thesis.CompanyManagerImpl;
import cz.muni.fi.thesis.DatabaseException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
public class ListCompanies extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        CompanyManager companyMng = new CompanyManagerImpl();

        List<Company> companies = null;

        /** testing log-in */
        HttpSession session = request.getSession();
        Object userID = session.getAttribute("userID");

        if (userID == null) {
            response.sendRedirect("index.jsp");
        } else {
        /** end of login testing */ 

            try {
                companies = companyMng.getAllCompanies();
            } catch (DatabaseException ex) {
                out.println(ex.getMessage());
                log.error(ex.getMessage());
            }

            try {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet servletTest</title>");
                out.println("</head>");
                out.println("<body>");


                if (companies.isEmpty()) {
                    out.println("No companies in database");
                } else {
                    out.println("COMPANIES");
                    out.println("<table>");
                    out.println("<th> ID </th>");
                    out.println("<th> Name </th>");
                    out.println("<th> E-mail </th>");
                    out.println("<th> Phone Number </th>");
                    out.println("<th> Street </th>");
                    out.println("<th> City </th>");
                    out.println("<th> Country </th>");
                    out.println("<th> PSC </th>");
                    out.println("<th> other info </th>");

                    for (int i = 0; i < companies.size(); i++) {
                        out.println("<tr>");
                        out.println("<td style='border: 1px solid black;'>" + companies.get(i).getId() + "</td>");
                        out.println("<td style='border: 1px solid black;'>" + companies.get(i).getName() + "</td>");
                        out.println("<td style='border: 1px solid black;'>" + companies.get(i).getEmail() + "</td>");
                        out.println("<td style='border: 1px solid black;'>" + companies.get(i).getPhoneNumber() + "</td>");
                        out.println("<td style='border: 1px solid black;'>" + companies.get(i).getStreet() + "</td>");
                        out.println("<td style='border: 1px solid black;'>" + companies.get(i).getCity() + "</td>");
                        out.println("<td style='border: 1px solid black;'>" + companies.get(i).getCountry() + "</td>");
                        out.println("<td style='border: 1px solid black;'>" + companies.get(i).getPsc() + "</td>");
                        out.println("<td style='border: 1px solid black;'>" + companies.get(i).getOther() + "</td>");

                        out.println("</tr>");

                    }
                    out.println("</table>");
                }
                out.println("<a href='/WebThesisMaven/index.jsp'>Go to Home Page</a>");
                out.println("</body>");
                out.println("</html>");
            } finally {
                out.close();
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
