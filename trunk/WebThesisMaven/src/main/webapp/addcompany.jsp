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

        <h1>Add company</h1>
        <form method="post" name='form2' action="/WebThesisMaven/AddCompany" onsubmit="return submit_company()">    
            Username: <input type="text" name="usrname"><br/>
            <%
                session = request.getSession();
                if (session.getAttribute("password") != null) {
            %>
            Passwords dont match!
            Password: <input type="password" name="pwd" alt=":)"><br/>
            Password(verification): <input type="password" name="pwd-ver" alt=":)"><br/> 

            <%
                session.setAttribute("password", null);
            } else {
            %>
            Password: <input type="password" name="pwd"><br/>
            Password(verification): <input type="password" name="pwd-ver"><br/>
            <% }%>
            Name: <input type="text" name="name"><br/>
            Email: <input type="text" name="email"><br/>
            PhoneNumber: <input type="text" name="phone"><br/>   
            <input type="submit" value="Add Company" name="option"/>
        </form>
        <a href="index.jsp"> Go to Home Page </a>
    </body>
</html>
