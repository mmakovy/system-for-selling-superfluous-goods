<%-- 
    Document   : index
    Author     : Matus Makovy
--%>

<%@page import="java.util.UUID"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/style.css">
        <link href='https://fonts.googleapis.com/css?family=Days+One' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Istok+Web' rel='stylesheet' ;type='text/css'>
              <title>SSSG - System for selling superfluous goods</title>
    </head>

    <script src="myjs.js">
    </script>   
    <body>
        <div id="wrapper">
            <div id ="page">
                <jsp:include page="menu.jsp"/> 
                <div id="content">
                    <div id="page-content">
                        <div id="page-content-top">
                        </div>
                        <div id="page-content-middle">
                            <% if (request.getAttribute("message") != null) {
                            %>
                            <div class="error">${message}</div>
                            <%                                }%>
                            <h1> What is SSSG ? </h1>
                            <p> 
                                SSSG - System for Selling Superfluous Goods, is a system that serves companies. 
                                This system enables them to sell their redundant goods on the internet.
                                It is a place where all the companies from your field meet and try to sell their redundant
                                goods.
                            </p>
                            <p>
                                System gives you an oppurtunity to <b>offer your goods</b>, but also to <b>browse through
                                    other offers</b> and <b>find what you need</b>. You can also <b> follow an offer </b> and system
                                will send you an email when this offer changes or is removed.
                                Every offer has its <b>contact form</b>, from which you can send e-mail to the seller
                                of particular offer.
                            </p>
                            <p class="last-p">
                                (Login info for testing purposes - username: test1 pass: testsssg1)
                            </p>
                        </div>
                        <div id="page-content-bottom">
                        </div>
                    </div>

                </div>
                <jsp:include page="footer.jsp"/>
            </div>
    </body>
</html>
