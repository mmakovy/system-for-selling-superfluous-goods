<%-- 
    Document   : login
    Created on : 7.2.2013, 10:53:48
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
       
        <form method="post" name='login' action="/WebThesisMaven/Login" ><!--onsubmit="return submit_company()">-->
            Username: <input type="text" name="userName"/><br/>
            Password: <input type="password" name="pwd"/><br/>
            <input type="submit" value="Log-in"/>
            
        </form>    
        <a href="index.jsp"> Go to Home Page </a>
    </body>
</html>
