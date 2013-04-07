<%-- 
    Document   : error
    Author     : Matus Makovy
--%>

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
    </head>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/style.css">
        <link href='http://fonts.googleapis.com/css?family=Days+One' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Istok+Web' rel='stylesheet' type='text/css'>
        <title>JSP Page</title>
    </head>
    <body>
        <div id="wrapper">
            <div id ="page">
                <jsp:include page="menu.jsp"/> 
                <div id="content">
                    <div id="registration-content">
                        <div id="registration-content-top">
                        </div>
                        <div id="registration-content-middle">
                            <div class="error">
                                ${message}
                            </div>
                        </div>
                        <div id="registration-content-bottom">
                        </div>
                    </div>

                </div>
                <jsp:include page="footer.jsp"/>
            </div>
    </body>


</html>
