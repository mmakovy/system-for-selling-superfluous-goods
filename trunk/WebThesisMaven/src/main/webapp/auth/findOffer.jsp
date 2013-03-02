<%-- 
    Document   : findOffer
    Created on : 29.11.2012, 16:12:05
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
        <h1>Find offer</h1>
        <form name="fomr3" method="post" action="/WebThesisMaven/auth/FindOffer">
            <input type="text" name="expression">
            <input type="submit" value="Find">
        </form>    
         <a href='/WebThesisMaven/index.jsp'>Go to Home Page</a>
    </body>
</html>
