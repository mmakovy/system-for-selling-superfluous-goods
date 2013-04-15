
<%-- 
    Document   : findOffer
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
    <script src="../myjs.js">
    </script>  
    <body>
        <div id="wrapper">
            <div id ="page">
                <jsp:include page="../menu.jsp"/> 
                <div id="content">
                    <div id="registration-content">
                        <div id="registration-content-top">
                        </div>
                        <div id="registration-content-middle">
                            <% if (request.getAttribute("message") != null) {
                            %>
                            <div class="error">${message}</div>
                            <%                                }%>
                            <h1>Find offer</h1>
                            <form name="findOffer" method="post" action="/auth/FindOffer" onsubmit="return findOffer()">
                                Search: <input type="text" name="expression"> (searching in name and description)<br/>
                                Quantity: from <input type="text" name="minQuantity"> to <input type="text" name="maxQuantity"><br/>
                                Minimal quantity to buy: from <input type="text" name="minQuantityToBuy"> to <input type="text" name="maxQuantityToBuy"><br/>
                                Price: from <input type="text" name="minPrice"> to <input type="text" name="maxPrice"><br/>
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
                        </div>
                        <div id="registration-content-bottom">
                        </div>
                    </div>

                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
    </body>
</html>
