<%-- 
    Document   : addoffer
    Created on : 22.2.2013, 11:37:00
    Author     : matus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html  charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
         <html>  
             <head>  
             <title>Servlet addOffer</title>  
             </head>  
             <script src='myjs.js'>  
             </script>  
             <body>  

             <form name='form1' method='post' action='/WebThesisMaven/AddOffer'  onsubmit='return submit_offer()'>  
             Name:  
             <input type='text' name='name'><br/>  
             Price:  
             <input type='text' name='price'><br/>  
             Quantity:  
             <input type='text' name='quantity'><br/>  
             Description:  
             <input type='text' name='description'><br/>  
             <input type='submit' value='Add offer'><br/>  
             </form>  

             <a href='/WebThesisMaven/index.jsp'>Go to Home Page</a>  
             </body>  
             </html>  
    </body>
</html>
