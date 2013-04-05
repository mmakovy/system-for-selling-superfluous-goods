/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.Web.Thesis.auth;

import cz.muni.fi.thesis.Offer;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author matus
 */
public class OffersLister {
    
    public void OffersToTable(List<Offer> offersList, PrintWriter out, Long id){
        if (offersList.isEmpty()) {
                out.println("No offers in database");
            } else {
                out.println("<table>");
                out.println("<th> Image </th>");
                out.println("<th> Name </th>");
                out.println("<th> Price </th>");
                out.println("<th> Quantity </th>");
                out.println("<th> Minimal Buy Quantity </th>");
                out.println("<th> Purchase Date </th>");
                out.println("<th> Category </th>");


                for (int i = 0; i < offersList.size(); i++) {
                    out.println("<tr>");
                    if (offersList.get(i).getPhotoUrl() == null || offersList.get(i).getPhotoUrl().length() == 0) {
                        out.println("<td style='border: 1px solid black;'>no-image</td>");
                    } else {
                        out.println("<td style='border: 1px solid black;'><img width='100' src='/WebThesisMaven/uploads/" + offersList.get(i).getPhotoUrl() + "'></td>");
                    }
                    out.println("<td style='border: 1px solid black;'><a href='/WebThesisMaven/auth/ShowOffer?id=" + offersList.get(i).getId() + "'>" + offersList.get(i).getName() + "</a></td>");
                    out.println("<td style='border: 1px solid black;'>" + offersList.get(i).getPrice() + "</td>");
                    out.println("<td style='border: 1px solid black;'>" + offersList.get(i).getQuantity() + "</td>");

                    if (offersList.get(i).getMinimalBuyQuantity() == 0) {
                        out.println("<td style='border: 1px solid black;'> Not specified</td>");
                    } else {
                        out.println("<td style='border: 1px solid black;'>" + offersList.get(i).getMinimalBuyQuantity() + "</td>");
                    }


                    if (offersList.get(i).getPurchaseDate() == null) {
                        out.println("<td style='border: 1px solid black;'> Not specified</td>");
                    } else {
                        out.println("<td style='border: 1px solid black;'>" + offersList.get(i).getPurchaseDate() + "</td>");
                    }

                    out.println("<td style='border: 1px solid black;'>" + offersList.get(i).getCategory() + "</td>");

                    if (offersList.get(i).getCompany_id().equals(id)) {
                        out.println("<td><a href='/WebThesisMaven/auth/removeOffer?id=" + offersList.get(i).getId() + "'>Remove</a></td>");
                        out.println("<td><a href='/WebThesisMaven/auth/updateOffer?id=" + offersList.get(i).getId() + "'>Update</a></td>");
                    }

                    out.println("</tr>");
                }

                out.println("</table>");
    }
    
}
}
