package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.*;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matus Makovy
 */
public class ShowOffer extends HttpServlet {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(ShowOffer.class);

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

        OfferManager offerManager = new OfferManagerImpl();
        CompanyManager companyManager = new CompanyManagerImpl();
        ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LdWet4SAAAAAOlPY6u3FoRS10OPJxRoE5ow7mbW", "6LdWet4SAAAAALOkcI8Auoub7_pM__sNyQUZbpdr", false);

        Long id = Long.parseLong(request.getParameter("id"));
        Offer offer;
        Company company;

        HttpSession session = request.getSession();
        Object userID = session.getAttribute("userID");
        Long userIDLong = (Long) userID;
        Map<String, String> offerData = new HashMap<String, String>();


        try {
            offer = offerManager.getOffer(id);
            
            if (offer != null) {
                Long id_company = offer.getCompany_id();

                if (id_company != null) {
                    company = companyManager.getCompanyById(id_company);

                    if (company == null) {
                        String message = "Coresponding company for this offer wasn't found in database";
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("../error.jsp").forward(request, response);
                    } else {
                        offerData.put("photoUrl", offer.getPhotoUrl());
                        offerData.put("name", offer.getName());
                        offerData.put("price", String.format("%.2f", offer.getPrice()).replace(",", "."));
                        offerData.put("quantity", Integer.toString(offer.getQuantity()));
                        offerData.put("minimalQuantity", Integer.toString(offer.getMinimalBuyQuantity()));
                        Date purchaseDate = offer.getPurchaseDate();
                        if (purchaseDate != null) {
                            offerData.put("purchaseDate", purchaseDate.toString());
                        }
                        offerData.put("category", offer.getCategory().toString());
                        offerData.put("description", offer.getDescription());
                        offerData.put("id", offer.getId().toString());

                        offerData.put("companyName", company.getName());
                        offerData.put("companyEmail", company.getEmail());
                        offerData.put("companyPhone", company.getPhoneNumber());
                        offerData.put("companyStreet", company.getStreet());
                        offerData.put("companyPsc", company.getPsc());
                        offerData.put("companyCity", company.getCity());
                        offerData.put("companyCountry", company.getCountry());

                        String myEmail = companyManager.getCompanyById(userIDLong).getEmail();

                        offerData.put("myEmail", myEmail);

                        request.setAttribute("offerData", offerData);
                        request.getRequestDispatcher("showOffer.jsp").forward(request, response);

                    }
                } else {
                    log.error("Company id in offer " + offer.getId() + " is null");
                    String message = "Internal error, please try again later";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("../error.jsp").forward(request, response);
                }

            } else {
                String message = "Offer wasn't found in database";
                request.setAttribute("message", message);
                request.getRequestDispatcher("../error.jsp").forward(request, response);
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
