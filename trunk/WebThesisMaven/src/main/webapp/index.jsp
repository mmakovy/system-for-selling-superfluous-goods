<%-- 
    Document   : index
    Created on : 11.2.2013, 10:54:17
    Author     : matus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            session = request.getSession();
            Object userId = session.getAttribute("userID");

            if (userId == null) {
        %> 

        <h1>Welcome to our system</h1>
        <p> You are not logged-in </p>
        <a href="login.jsp">Log-in</a> or <a href="addcompany.jsp">Register</a>

        <%            
            } else {
                response.sendRedirect("menu.jsp");
            }
        %>
    </body>
</html>
