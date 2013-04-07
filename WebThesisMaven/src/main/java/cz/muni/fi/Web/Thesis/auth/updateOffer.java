package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.CompanyManagerImpl;
import cz.muni.fi.thesis.Offer;
import cz.muni.fi.thesis.OfferManager;
import cz.muni.fi.thesis.OfferManagerImpl;
import java.io.IOException;
import java.util.Calendar;
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
public class updateOffer extends HttpServlet {

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

        OfferManager manager = new OfferManagerImpl();
        Long id = Long.parseLong(request.getParameter("id"));
        Offer offer;

        HttpSession session = request.getSession();
        Long userID = (Long) session.getAttribute("userID");
        Calendar calendar = Calendar.getInstance();
        Map<String, String> offerData = new HashMap<String, String>();

        try {
            offer = manager.getOffer(id);

            if (offer == null) {
                String message = "Offer wasnt found in database";
                request.setAttribute("message", message);
                request.getRequestDispatcher("../error.jsp").forward(request, response);
            } else {
                if (!userID.equals(offer.getCompany_id())) {

                    log.error("Access denied");
                    String message = "You have not permission to do this";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("../error.jsp").forward(request, response);

                } else {
                    if (offer == null) {

                        String message = "Offer wasnt found in database";
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("../error.jsp").forward(request, response);

                    } else {

                        if (offer.getPurchaseDate() != null) {
                            calendar.setTime(offer.getPurchaseDate());
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            int month = calendar.get(Calendar.MONTH) + 1;
                            int year = calendar.get(Calendar.YEAR);
                            offerData.put("purchaseDay", Integer.toString(day));
                            offerData.put("purchaseMonth", Integer.toString(month));
                            offerData.put("purchaseYear", Integer.toString(year));
                        }

                        offerData.put("photoUrl", offer.getPhotoUrl());
                        offerData.put("name", offer.getName());
                        offerData.put("price", offer.getPrice().toString());
                        offerData.put("quantity", Integer.toString(offer.getQuantity()));
                        offerData.put("minimalQuantity", Integer.toString(offer.getMinimalBuyQuantity()));
                        offerData.put("category", offer.getCategory().toString());
                        offerData.put("description", offer.getDescription());
                        offerData.put("id", offer.getId().toString());

                        request.setAttribute("offerData", offerData);
                        request.getRequestDispatcher("updateOffer.jsp").forward(request, response);

                    }
                }
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
