<%-- 
    Document   : addoffer
    Created on : 23.11.2012, 12:16:10
    Author     : matus
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
    <body>
        <div id="wrapper">
            <div id ="page">
                <jsp:include page="../menu.jsp"/> 
                <div id="content">
                    <div id="registration-content">
                        <div id="registration-content-top">
                        </div>
                        <div id="registration-content-middle">
                            <% if (request.getAttribute("message")!=null) {
                                %>
                                <div class="error">${message}</div>
                            <%
                                }%>
                            <h1>Add offer </h1>
                            <form ENCTYPE="multipart/form-data" name='add_offer' method='post' action='/WebThesisMaven/auth/AddOffer'  onsubmit='return submit_offer()'>  
                                Name*:  
                                <input type='text' name='name'><br/>  
                                Price*:  
                                <input type='text' name='price'><br/>  
                                Quantity*:  
                                <input type='text' name='quantity'><br/> 
                                MINIMAL Quantity to Buy:  
                                <input type='text' name='minimal_buy' value="0"><br/>  
                                Description:  
                                <input type='text' name='description'><br/>  

                                Category:  
                                <select name="category">
                                    <option selected>OTHERS</option>
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
                                <input type="text" name="dob_day" /><br/>
                                Month:
                                <input type="text" name="dob_month" /><br/>
                                Year:
                                <input type="text" name="dob_year" /><br/>
                                <br/>
                                Image: 
                                <input type="file" name="image" /><br/> <br/>
                                *-required fields<br/>
                                <input type='submit' value='Add offer'><br/>  
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


