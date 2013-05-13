<%-- 
    Document   : forgotPassword
    Author     : Matus Makovy
--%>

<%@page import="java.util.UUID"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/style.css">
        <link href='https://fonts.googleapis.com/css?family=Days+One' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Istok+Web' rel='stylesheet' type='text/css'>
        <title>SSSG - Forgot Password</title>
    </head>
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
                            <%
                                }%>

                            <form name="forgot_pass" action="/ForgotPassword">
                                <h1> Did you forget your password ? </h1>
                                <p> Enter your e-mail address, and we will send
                                    you a new one </p>
                                E-mail address: <br/>
                                <input type="text" name="email"/>
                                <br/>
                                <br/>
                                <%
                                    ReCaptcha c = ReCaptchaFactory.newSecureReCaptcha("6LdWet4SAAAAAOlPY6u3FoRS10OPJxRoE5ow7mbW", "6LdWet4SAAAAALOkcI8Auoub7_pM__sNyQUZbpdr", false);
                                    out.println(c.createRecaptchaHtml(null, null));
                                %>
                                <br/>
                                <input type="submit" value="Send new password"/>
                            </form>
                        </div>
                        <div id="page-content-bottom">
                        </div>
                    </div>

                </div>
                <jsp:include page="footer.jsp"/>
            </div>
    </body>
</html>
