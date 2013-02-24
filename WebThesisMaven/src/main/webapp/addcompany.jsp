<%-- 
    Document   : addcompany
    Created on : 23.11.2012, 13:23:55
    Author     : matus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <script src="myjs.js">
    </script>    
    <body>
         <%
            session = request.getSession();
            Long id = (Long) session.getAttribute("userID");
            if (id != null) {
                out.println("You are logged in <a href='Logout'>Logout</a> first.");
            } else {
        %>
        <h1>Add company</h1>
        <form method="post" name='form2' action="/WebThesisMaven/AddCompany" onsubmit="return submit_company()">    
            Username: <input type="text" name="usrname"><br/>
            Password: <input type="text" name="pwd"><br/>
            Name: <input type="text" name="name"><br/>
            Email: <input type="text" name="email"><br/>
            PhoneNumber: <input type="text" name="phone"><br/>   
            <input type="submit" value="Add Company" name="option"/>
        </form>
        <a href="index.jsp"> Go to Home Page </a>
        <% }%>
    </body>
</html>
