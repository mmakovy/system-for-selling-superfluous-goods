<%-- 
    Document   : addcompany
    Author     : Matus Makovy
--%>

<%@page import="javax.print.attribute.standard.OutputDeviceAssigned"%>
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
        <title>SSSG - Register</title>
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
                            <h1>Log in</h1>
                            <div id="log-in-form">
                                    <form method="post" name="login" action="https://sssg-sssg.rhcloud.com/Login" onsubmit="return login()">
                                        Username: <input type="text" name="userName"><br>
                                        Password: <input type="password" name="pwd"><br>
                                        <input type="submit" value="Log-in">
                                    </form>  
                                    <a href="forgotPassword.jsp">Forgot your password?</a><br/>
                            </div>
                        </div>
                        <div id="page-content-bottom">
                        </div>
                    </div>

                </div>
                <jsp:include page="footer.jsp"/>

                </body>
                </html>
