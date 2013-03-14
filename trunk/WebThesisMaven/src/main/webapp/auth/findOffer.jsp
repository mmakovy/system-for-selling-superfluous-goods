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
            Search: <input type="text" name="expression"> (searching in name and description)<br/>
            Quantity: from <input type="text" name="min-quantity"> to <input type="text" name="max-quantity"><br/>
            Minimal quantity to buy: from <input type="text" name="min-quantity-to-buy"> to <input type="text" name="max-quantity-to-buy"><br/>
            Price: from <input type="text" name="min-price"> to <input type="text" name="max-price"><br/>
            Category: <select name="category">
                    <option selected>----</option>
                    <option>OTHER</option>
                    <option>BUILDING</option>
                    <option>FURNITURE</option>
                    <option>ELECTROTECHNICS</option>
                    <option>CARS</option>
                    <option>COMPUTER</option>
                    <option>COSMETICS</option>
                    <option>SPORTS</option>
                </select>    <br/>
            <input type="submit" value="Find">
        </form>    
         <a href='/WebThesisMaven/auth/menu.jsp'>Go to Home Page</a>
    </body>
</html>
