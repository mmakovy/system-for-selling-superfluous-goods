<%-- 
    Document   : listOffers
    Author     : Matus Makovy
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
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/style.css">
        <link href='https://fonts.googleapis.com/css?family=Days+One' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Istok+Web' rel='stylesheet' type='text/css'>
        <title>SSSG - Browse all offers</title>
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
                                    Long id = (Long) session.getAttribute("userID");
                                    List<Offer> offers = (List) request.getAttribute("offers");
                                    for (Offer offer : offers) {    
                                %>
                                <tr>

                                    <%if (offer.getPhotoUrl() == null || offer.getPhotoUrl().length() == 0) {%>
                                    <td style='border: 1px solid black;'>no-image</td>
                                    <%} else {%>
                                    <td style='border: 1px solid black;'><img width='100' src='<%out.println("/static/" + offer.getPhotoUrl());%>'></td>
                                        <%}%>

                                    <td style='border: 1px solid black;'><a href='/auth/ShowOffer?id=<%out.println(offer.getId());%>'> <%out.println(offer.getName());%> </a></td>
                                    <td style='border: 1px solid black;'>  <% out.println(String.format("%.2f", offer.getPrice()).replace(",", "."));%> &euro; </td>
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

                                    <% if (offer.getCompanyId().equals(id)) {%>
                                    <td><a href='/auth/removeOffer?id=<% out.println(offer.getId());%>' onclick="return confirm('Do you really want to remove this offer?')">Remove</a></td>
                                    <td><a href='updateOffer?id=<% out.println(offer.getId());%>'>Update</a></td>
                                    <%
                                        }
                                    %>

                                </tr>
                                <% }%>
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