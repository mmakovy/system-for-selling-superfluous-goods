<%-- 
    Document   : index
    Created on : 23.11.2012, 12:16:10
    Author     : matus
--%>

<%@page import="java.util.List"%>
<%@page import="cz.muni.fi.thesis.Offer"%>
<%@page import="cz.muni.fi.thesis.User"%>
<%@page import="cz.muni.fi.thesis.UserManager"%>
<%@page import="cz.muni.fi.thesis.UserManagerImpl"%>
<%@page import="cz.muni.fi.thesis.DatabaseConnection"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>System for selling superflouos goods</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/style.css">
        <link href='http://fonts.googleapis.com/css?family=Days+One' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Istok+Web' rel='stylesheet' type='text/css'>
    </head>
    <body>
        <div id="wrapper">
            <div id ="page">
                <jsp:include page="../menu.jsp"/> 
                <div id="content">
                    <div id="registration-content">
                        <div id="registration-content-top">
                        </div>
                        <div id="registration-content-middle">
                            <table class="offers-table">
                                <th> Image </th>
                                <th> Name </th>
                                <th> Price </th>
                                <th> Quantity </th>
                                <th> Minimal Buy Quantity </th>
                                <th> Purchase Date </th>
                                <th> Category </th>
                                <% 
                                Long id = (Long) session.getAttribute("userId");
                                List<Offer> offers = (List) request.getAttribute("offers");
                                    for (Offer offer : offers) {
                                %>
                                <tr>

                                    <%if (offer.getPhotoUrl() == null || offer.getPhotoUrl().length() == 0) {%>
                                    <td style='border: 1px solid black;'>no-image</td>
                                    <%} else {%>
                                    <td style='border: 1px solid black;'><img width='100' src='/WebThesisMaven/uploads/<%out.println(offer.getPhotoUrl());%>'></td>
                                        <%}%>

                                    <td style='border: 1px solid black;'><a href='/WebThesisMaven/auth/ShowOffer?id=<%out.println(offer.getId()); %>'> <%out.println(offer.getName());%> </a></td>
                                    <td style='border: 1px solid black;'>  <% out.println(String.format("%.2f", offer.getPrice())); %>  </td>
                                    <td style='border: 1px solid black;'> <% out.println(offer.getQuantity());%> </td>

                                    <%if (offer.getMinimalBuyQuantity() == 0) {%>
                                    <td style='border: 1px solid black;'> Not specified</td>
                                    <%} else {%>
                                    <td style='border: 1px solid black;'><%out.println(offer.getMinimalBuyQuantity());%></td>
                                    <% }%>


                                    <%if (offer.getPurchaseDate() == null) {%>
                                    <td style='border: 1px solid black;'> Not specified</td>
                                    <%} else {%>
                                    <td style='border: 1px solid black;'><% out.println(offer.getPurchaseDate());%></td>
                                    <%}%>

                                    <td style='border: 1px solid black;'><%out.println(offer.getCategory());%></td>

                                    <% if (offer.getCompany_id().equals(id)) {%>
                                    <td><a href='/WebThesisMaven/auth/removeOffer?id=" + offersList.get(i).getId() + "'>Remove</a></td>
                                    <td><a href='/WebThesisMaven/auth/updateOffer?id=" + offersList.get(i).getId() + "'>Update</a></td>"



                                    <%                                    }                            
                                    %>
                                </tr>
                                <% } %>
                             </table>
                        </div>
                        <div id="registration-content-bottom">
                        </div>
                    </div>
</div>
                
                <jsp:include page="../footer.jsp"/>
            </div>
    </body>


</html>
