<%-- 
    Document   : error
    Author     : Matus Makovy
--%>

<%@page import="cz.muni.fi.thesis.User"%>
<%@page import="cz.muni.fi.thesis.UserManager"%>
<%@page import="cz.muni.fi.thesis.UserManagerImpl"%>
<%@page import="cz.muni.fi.thesis.DatabaseConnection"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/style.css">
        <link href='https://fonts.googleapis.com/css?family=Days+One' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Istok+Web' rel='stylesheet' type='text/css'>
        <title>SSSG - Error</title>
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
                            <div class="error">
                                ${message}
                            </div>
                        </div>
                        <div id="page-content-bottom">
                        </div>
                    </div>

                </div>
                <jsp:include page="footer.jsp"/>
            </div>
    </body>


</html>
