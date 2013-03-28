<%-- 
    Document   : forgotPassword
    Created on : 23.3.2013, 12:10:48
    Author     : matus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form name="forgot_pass" action="ForgotPassword">
            E-mail address: <br/>
            <input type="text" name="email"/>
            <br/>
            <%
                ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LdWet4SAAAAAOlPY6u3FoRS10OPJxRoE5ow7mbW", "6LdWet4SAAAAALOkcI8Auoub7_pM__sNyQUZbpdr", false);
                out.println(c.createRecaptchaHtml(null, null));
            %>
            <input type="submit" value="Send new password"/>
        </form>

    </body>
</html>
