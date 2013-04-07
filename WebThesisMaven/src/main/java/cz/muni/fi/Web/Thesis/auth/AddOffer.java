package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.Normalizer;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matus Makovy
 */
public class AddOffer extends HttpServlet {

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

        CompanyManager companyManager = new CompanyManagerImpl();
        OfferManager offerManager = new OfferManagerImpl();
        Calendar calendar = Calendar.getInstance();

        HttpSession session = request.getSession();
        Object userIdObject = session.getAttribute("userID");
        String photoUrl = null;

        String name = null;
        String description = null;
        String quantityString = null;
        String priceString = null;
        String minimalBuyString = null;
        String categoryString = null;
        String dayString = null;
        String monthString = null;
        String yearString = null;
        boolean fileIsImage = true;

        if (ServletFileUpload.isMultipartContent(request)) {

            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = null;

            try {
                items = upload.parseRequest(request);
            } catch (FileUploadException ex) {
                log.error(ex.getMessage());
                String message = "Sorry, we are experiencing some problems, please try again<br/>" + ex.getMessage();
                request.setAttribute("message", message);
                request.getRequestDispatcher("../error.jsp").forward(request, response);
            } 

            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (!item.isFormField() && item.getName().length() != 0) {

                    String fileName = item.getName();
                    String root = getServletContext().getRealPath("/");
                    File path = new File(root + "/uploads");
                    if (!path.exists()) {
                        boolean status = path.mkdirs();
                    }

                    photoUrl = Normalizer.normalize(fileName, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                    File uploadedFile = new File(path + "/" + photoUrl);


                    try {
                        item.write(uploadedFile);
                    } catch (Exception ex) {
                        log.error(ex.getMessage());
                        String message = "Sorry, we are experiencing some problems, please try again<br/>" + ex.getMessage();
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("../error.jsp").forward(request, response);
                    }

                    Magic parser = new Magic();
                    MagicMatch match = null;
                    try {
                        match = parser.getMagicMatch(uploadedFile, false);
                    } catch (Exception ex) {
                        log.error(ex.getMessage());
                        String message = "Sorry, we are experiencing some problems, please try again<br/>" + ex.getMessage();
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("../error.jsp").forward(request, response);
                    } 

                    if (match != null) {
                        String mimeType = match.getMimeType();
                        if (!mimeType.startsWith("image")) {
                            fileIsImage = false;
                            uploadedFile.delete();
                            log.error("File wasnt image");
                            String message = "File wasnt image";
                            request.setAttribute("message", message);
                            request.getRequestDispatcher("../error.jsp").forward(request, response);
                        }
                    }
                } else {

                    if ("name".equals(item.getFieldName())) {
                        name = item.getString();
                    } else if ("description".equals(item.getFieldName())) {
                        description = item.getString();
                    } else if ("quantity".equals(item.getFieldName())) {
                        quantityString = item.getString();
                    } else if ("minimal_buy".equals(item.getFieldName())) {
                        minimalBuyString = item.getString();
                    } else if ("category".equals(item.getFieldName())) {
                        categoryString = item.getString();
                    } else if ("price".equals(item.getFieldName())) {
                        priceString = item.getString();
                    } else if ("dob_day".equals(item.getFieldName())) {
                        dayString = item.getString();
                    } else if ("dob_month".equals(item.getFieldName())) {
                        monthString = item.getString();
                    } else if ("dob_year".equals(item.getFieldName())) {
                        yearString = item.getString();
                    }
                }
            }
        }

        /**
         * converting String to integer
         */
        int minimalBuyQuantity = Integer.parseInt(minimalBuyString);
        int quantity = Integer.parseInt(quantityString);

        /**
         * end
         */
        Date date;
        if (yearString.length() == 0 || monthString.length() == 0 || dayString.length() == 0) {
            date = null;
        } else {
            int YearInt = Integer.parseInt(yearString);
            int MonthInt = Integer.parseInt(monthString) - 1;
            int DayInt = Integer.parseInt(dayString);
            calendar.set(YearInt, MonthInt, DayInt);
            date = new Date(calendar.getTimeInMillis());
        }


        if (name.length() != 0 && priceString.length() != 0 && quantityString.length() != 0) {

            Long userId = (Long) userIdObject;
            BigDecimal price = new BigDecimal(priceString);

            Offer offer = new Offer();
            offer.setCompany_id(userId);
            offer.setDescription(description);
            offer.setName(name);
            offer.setPrice(price);
            offer.setQuantity(quantity);
            offer.setPurchaseDate(date);
            offer.setCategory(Category.valueOf(categoryString));
            offer.setMinimalBuyQuantity(minimalBuyQuantity);
            offer.setPhotoUrl(photoUrl);

            try {

                Company company = companyManager.getCompanyById(userId);

                if (company == null) {
                    String message = "Your company wasn't found in database";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("addoffer.jsp").forward(request, response);
                } else if (fileIsImage) {
                    Offer added = offerManager.addOffer(company, offer);
                    if (added == null) {
                        log.error("addOffer() returned null");
                        String message = "Offer wasnt added because of some problems on our side, sorry. Please try again later.";
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("../error.jsp").forward(request, response);
                    } else {
                        String message = "Offer was successfully added";
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("../response.jsp").forward(request, response);
                    }
                } else {
                    String message = "Offer wasn't added because of file (File wasn't image)";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("../error.jsp").forward(request, response);
                }


            } catch (Exception ex) {
                log.error(ex.getMessage());
                String message = ex.getMessage();
                request.setAttribute("message", message);
                request.getRequestDispatcher("../error.jsp").forward(request, response);
            }
        } else {
            log.error("Offer wasnt added - blank field/s");
            String message = "Your offer wasn't added because one of required fields was left blank";
            request.setAttribute("message", message);
            request.getRequestDispatcher("addoffer.jsp").forward(request, response);
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
