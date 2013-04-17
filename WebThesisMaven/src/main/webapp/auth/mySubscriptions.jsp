<%-- 
    Document   : mySubscriptions
    Author     : Matus Makovy
--%>

<%@page import="cz.muni.fi.thesis.Offer"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
                    <div id="registration-content">
                        <div id="registration-content-top">
                        </div>
                        <div id="registration-content-middle">
                            <% if (request.getAttribute("message") != null) {
                            %>
                            <div class="success">${message}</div>
                            <%                                }%>
                            <table class="offers-table">
                                <th>Offer</th>
                                <%
                                    List<Offer> offers = (List) request.getAttribute("offers");
                                    for (Offer offer : offers) {
                                %>
                                <tr>
                                    <td style='border: 1px solid black;'><a href='/auth/ShowOffer?id=<%out.println(offer.getId());%>'> <%out.println(offer.getName());%> </a></td>
                                    <td><a href='/auth/removeMySubscription?id=<% out.println(offer.getId());%>' onclick="return confirm('Do you really want to remove this subscription?')">Remove</a></td>
                                </tr>
                                <%
                                    }
                                %>
                            </table>
                            <a href='/auth/removeAllMySubscriptions' onclick="return confirm('Do you really want to remove all your subscription?')">Remove all my Subscriptions</a>
                        </div>
                        <div id="registration-content-bottom">
                        </div>
                    </div>
                </div>

                <jsp:include page="../footer.jsp"/>
            </div>
    </body>
</html>

