package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import javax.imageio.ImageIO;
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
 * Servlet updated one offer in database.
 * Servlet obtains data from updateOffer.jsp
 * 
 * @author Matus Makovy
 */
public class updateOfferProcess extends HttpServlet {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(updateOfferProcess.class);

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
        Offer offer = null;
        Calendar calendar = Calendar.getInstance();
        MailingListManager mailingListManager = new MailingListManagerImpl();
        String newline = System.getProperty("line.separator");

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
        boolean fileIsCorrect = true;


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
                    File path = new File(System.getenv("OPENSHIFT_DATA_DIR") + "/images");
                    if (!path.exists()) {
                        boolean status = path.mkdirs();
                    }

                    photoUrl = UUID.randomUUID().toString().substring(0, 13) + ".jpg";
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
                            fileIsCorrect = false;
                            uploadedFile.delete();
                            log.error("File wasnt image");
                            String message = "File wasnt image";
                            request.setAttribute("message", message);
                            request.getRequestDispatcher("../error.jsp").forward(request, response);
                        } else {

                            BufferedImage bimg = ImageIO.read(uploadedFile);
                            int imageWidth = bimg.getWidth();
                            int imageHeight = bimg.getHeight();

                            long fileSizeInBytes = uploadedFile.length();
                            long fileSizeInKB = fileSizeInBytes / 1024;
                            long fileSizeInMB = fileSizeInKB / 1024;

                            if (imageWidth > 500 || imageHeight > 500 || fileSizeInMB > 1) {
                                fileIsCorrect = false;
                                uploadedFile.delete();
                                log.error("File doesn't meet the requirements");
                                String message = "File doesn't meet the requirements";
                                request.setAttribute("message", message);
                                request.getRequestDispatcher("../error.jsp").forward(request, response);
                            }
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

        Long id = Long.parseLong(request.getParameter("id"));

        HttpSession session = request.getSession();
        Object userID = session.getAttribute("userID");

        Long userIdLong = (Long) userID;

        try {
            offer = manager.getOffer(id);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            String message = ex.getMessage();
            request.setAttribute("message", message);
            request.getRequestDispatcher("../error.jsp").forward(request, response);
        }

        if (offer == null) {
            String message = "Offer wasnt found in database";
            log.error(message);
            request.setAttribute("message", message);
            request.getRequestDispatcher("../error.jsp").forward(request, response);
        } else {

            if (name.length() != 0 && priceString.length() != 0 && quantityString.length() != 0) {

                BigDecimal price = null;
                int minimalBuyQuantity = 0;
                int quantity = 0;
                Date date = null;

                try {
                    price = new BigDecimal(priceString.replace(",", "."));
                    minimalBuyQuantity = Integer.parseInt(minimalBuyString);
                    quantity = Integer.parseInt(quantityString);

                    if (yearString.length() == 0 || monthString.length() == 0 || dayString.length() == 0) {
                        date = null;
                    } else {
                        int YearInt = Integer.parseInt(yearString);
                        int MonthInt = Integer.parseInt(monthString) - 1;
                        int DayInt = Integer.parseInt(dayString);
                        calendar.set(YearInt, MonthInt, DayInt);
                        date = new Date(calendar.getTimeInMillis());
                    }
                } catch (Exception ex) {
                    String message = "Bad input - Some number field was not a number <br/> or quantity was not a whole number <br/> " + ex.toString();
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("../error.jsp").forward(request, response);
                }

                if (!userIdLong.equals(offer.getCompanyId())) {
                    log.error("Access denied");
                    String message = "You don't have permission to do this";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("../error.jsp").forward(request, response);
                } else {
                    Offer updatedOffer = new Offer();
                    updatedOffer.setDescription(description);
                    updatedOffer.setName(name);
                    updatedOffer.setPrice(price);
                    updatedOffer.setQuantity(quantity);
                    updatedOffer.setId(id);
                    updatedOffer.setCompanyId(offer.getCompanyId());
                    updatedOffer.setMinimalBuyQuantity(minimalBuyQuantity);
                    updatedOffer.setPurchaseDate(date);
                    updatedOffer.setCategory(Category.valueOf(categoryString));

                    if (photoUrl == null || photoUrl.length() == 0) {
                        photoUrl = offer.getPhotoUrl();
                    } else {
                        String imageUrl = offer.getPhotoUrl();
                        File path = new File(System.getenv("OPENSHIFT_DATA_DIR"));
                        File uploadedFile = new File(path + "/" + imageUrl);
                        uploadedFile.delete();
                    }

                    updatedOffer.setPhotoUrl(photoUrl);

                    String messageText = "Offer " + offer.getName() + " was updated" + newline;

                    messageText = messageText + "Before update: " + newline + offer.toString() + newline;

                    try {
                        manager.updateOffer(updatedOffer);
                    } catch (Exception ex) {
                        log.error(ex.getMessage());
                        String message = ex.getMessage();
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("../error.jsp").forward(request, response);
                    }

                    messageText = messageText + "After update: " + newline + updatedOffer.toString()
                            + newline + "You can disable this notifications in My Subscriptions section "
                            + newline + "https://sssg-sssg.rhcloud.com/auth/MySubscriptions";
                    String messageSubject = "Offer " + offer.getName() + " was updated";

                    List<String> recipients;

                    try {
                        recipients = mailingListManager.getEmails(offer);
                        if (!recipients.isEmpty()) {
                            
                            BlockingQueue<Map<String, String>> messagesQueue = (BlockingQueue<Map<String, String>>) request.getSession().getServletContext().getAttribute("messagesQueue");

                            for (String email : recipients) {
                                Map<String, String> messageMap = new HashMap<String, String>();
                                messageMap.put("to", email);
                                messageMap.put("subject", messageSubject);
                                messageMap.put("text", messageText);
                                messagesQueue.add(messageMap);
                            }
                        }
                    } catch (Exception ex) {
                        log.error(ex.getMessage());
                        String message = ex.getMessage();
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("../error.jsp").forward(request, response);
                    }

                    String message = "Offer was succesfully updated";
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("../response.jsp").forward(request, response);

                }
            } else {
                String message = "Company wasnt updated, because one of fields was left blank<br/>";
                log.error(message);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
