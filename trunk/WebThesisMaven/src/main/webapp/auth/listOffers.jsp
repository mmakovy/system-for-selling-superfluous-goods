<%-- 
    Document   : listOffers
    Author     : Matus Makovy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                                <th> MBQ* </th>
                                <th> Purchase Date </th>
                                <th> Category </th>
                                <%
                                    List<Offer> offers = (List) request.getAttribute("offers");  
                                %>
                                <c:forEach items='${offers}' var="offer">
                                <tr>

                                    <c:if test='${empty offer.photoUrl}'>
                                    <td>no-image</td>
                                    </c:if>
                                    
                                    <c:if test='${not empty offer.photoUrl}'>
                                    <td><img width='100' src='/static/<c:out value='${offer.photoUrl}'/>'/></td>
                                    </c:if>

                                    <td><a href='ShowOffer?id=<c:out value='${offer.id}'/>'> <c:out value='${offer.name}'/> </a></td>
                                    <td>  <c:out value='${offer.price}'/> &euro; </td>
                                    <td> <c:out value='${offer.quantity}'/> </td>
                                    
                                    <c:if test='${offer.minimalBuyQuantity == 0}'>
                                    <td> Not specified</td>
                                    </c:if>
                                    
                                    <c:if test='${offer.minimalBuyQuantity != 0}'>
                                    <td><c:out value='${offer.minimalBuyQuantity}'/></td>
                                    </c:if>

                                    
                                    <c:if test='${empty offer.purchaseDate}'>
                                    <td> Not specified</td>
                                    </c:if>
                                    
                                    <c:if test='${not empty offer.purchaseDate}'>
                                    <td><c:out value='${offer.purchaseDate}'/></td>
                                    </c:if>
                                    

                                    <td><a href="ListOffersFromCategory?category=<c:out value='${offer.category}'/>"><c:out value='${offer.category}'/></a></td>
                                 

                                </tr>
                                </c:forEach>
                                
                            </table>
                                *-Minimal Buy Quantity
                        </div>
                        <div id="registration-content-bottom">
                        </div>
                    </div>
                </div>

                <jsp:include page="../footer.jsp"/>
            </div>
    </body>


</html>
