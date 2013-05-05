<%-- 
    Document   : mySubscriptions
    Author     : Matus Makovy
--%>

<%@page import="cz.muni.fi.thesis.Offer"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/style.css">
        <link href='https://fonts.googleapis.com/css?family=Days+One' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Istok+Web' rel='stylesheet' type='text/css'>
        <title>SSSG - My Subscriptions</title>
    </head>
    <body>
        <div id="wrapper">
            <div id ="page">
                <jsp:include page="../menu.jsp"/> 
                <div id="content">
                    <div id="page-content">
                        <div id="page-content-top">
                        </div>
                        <div id="page-content-middle">
                            <% if (request.getAttribute("message") != null) {
                            %>
                            <div class="success">${message}</div>
                            <%                                }%>
                            <table>
                                <th>Offer name</th>
                                <%
                                    List<Offer> offers = (List) request.getAttribute("offers");
                                %>
                                <c:forEach items='${offers}' var="offer">
                                <tr>
                                    <td><a href='ShowOffer?id=<c:out value='${offer.id}'/>'> <c:out value='${offer.name}'/></a></td>
                                    <td><a href='removeMySubscription?id=<c:out value='${offer.id}'/>' onclick="return confirm('Do you really want to remove this subscription?')">Remove</a></td>
                                </tr>
                                </c:forEach>
                            </table>
                                <br/>
                            <a href='/auth/removeAllMySubscriptions' onclick="return confirm('Do you really want to remove all your subscription?')">Remove all my Subscriptions</a>
                        </div>
                        <div id="page-content-bottom">
                        </div>
                    </div>
                </div>

                <jsp:include page="../footer.jsp"/>
            </div>
    </body>
</html>

