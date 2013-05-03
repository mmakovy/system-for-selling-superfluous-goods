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
        <link href='https://fonts.googleapis.com/css?family=Istok+Web' rel='stylesheet' type='text/css'>
        <title>SSSG - System for selling superfluous goods</title>
    </head>
    
    <script src="myjs.js">
    </script>   
    
    <body>
        <div id="wrapper">
            <div id ="page">
                <jsp:include page="menu.jsp"/> 
                <div id="content">
                    <div id="log-in-form">
                        <div id="log-in-form-top">
                        </div>
                        <div id="log-in-form-middle">
                            <div class="log-in">LOGIN</div>
                            <form method="post" name='login' action="Login" onsubmit="return login()">
                                Username: <input type="text" name="userName"/><br/>
                                Password: <input type="password" name="pwd"/><br/>
                                <input type="submit" value="Log-in"/>
                            </form>  
                            <a href="forgotPassword.jsp">Forgot your password?</a><br/>
                        </div>
                        <div id="log-in-form-bottom">
                        </div>
                    </div>
                    <div id="index-content">
                        <div id="index-content-top">
                        </div>
                        <div id="index-content-middle">
                            <% if (request.getAttribute("message") != null) {
                                %>
                                <div class="error">${fn:escapeXml(message)}</div>
                            <%
                                }%>
                            <h1> What is SSSG ? </h1>
                            <p> 
                                SSSG - System for Selling Superfluous Goods, is a system that serves companies. 
                                This system enables them to sell their redundant goods on the internet.
                                It is a place where all the companies from your field meet and try to sell their redundant
                                goods.
                            </p>
                            <p class="last-p">
                                System gives you an oppurtunity to <b>offer your goods</b>, but also to <b>browse through
                                other offers</b> and <b>find what you need</b>. You can also <b> follow an offer </b> and system
                                will send you an email when this offer changes or is removed.
                                Every offer has its <b>contact form</b>, from which you can send e-mail to the seller
                                of particular offer.
                            </p>
                        </div>
                        <div id="index-content-bottom">
                        </div>
                    </div>

                </div>
                <jsp:include page="footer.jsp"/>
            </div>
    </body>
</html>
