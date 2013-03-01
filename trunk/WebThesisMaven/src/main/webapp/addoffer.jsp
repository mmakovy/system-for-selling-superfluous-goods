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
    <html>  
        <head>  
            <title>Servlet addOffer</title>  
        </head>  
        <script src='myjs.js'>  
        </script>  
        <body>  
            <%
                session = request.getSession();
                Long id = (Long) session.getAttribute("userID");
                if (id == null) {
                    response.sendRedirect("index.jsp");
                } else {
            %>
            <form name='form1' method='post' action='/WebThesisMaven/AddOffer'  onsubmit='return submit_offer()'>  
                Name:  
                <input type='text' name='name'><br/>  
                Price:  
                <input type='text' name='price'><br/>  
                Quantity:  
                <input type='text' name='quantity'><br/>  
                MINIMAL Quantity to Buy:  
                <input type='text' name='minimal_buy'><br/>  
                Description:  
                <input type='text' name='description'><br/>  

                Category:  
                <select name="category">
                    <option>BUILDING</option>
                    <option>FURNITURE</option>
                    <option>ELECTROTECHNICS</option>
                    <option>CARS</option>
                    <option>COMPUTER</option>
                    <option>COSMETICS</option>
                    <option>SPORTS</option>
                </select>    <br/>

                Date of purchase:  
                <br/>
                Day:
                <input type="text" name="dob_day"/>
                Month:
                <input type="text" name="dob_month"/>
                Year:
                <input type="text" name="dob_year"/>
                    <br/>

                <input type='submit' value='Add offer'><br/>  
            </form>  

            <a href='/WebThesisMaven/index.jsp'>Go to Home Page</a>  
            <% }%>
        </body>  
    </html>  

