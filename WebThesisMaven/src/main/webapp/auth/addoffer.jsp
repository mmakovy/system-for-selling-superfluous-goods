<%-- 
    Document   : addoffer
    Author     : Matus Makovy
--%>

<%@page import="cz.muni.fi.thesis.User"%>
<%@page import="cz.muni.fi.thesis.UserManager"%>
<%@page import="cz.muni.fi.thesis.UserManagerImpl"%>
<%@page import="cz.muni.fi.thesis.DatabaseConnection"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/style.css">
        <link href='https://fonts.googleapis.com/css?family=Days+One' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Istok+Web' rel='stylesheet' type='text/css'>
        <title>SSSG - Add offer</title>
    </head>
    <script src="../myjs.js">
    </script>   
    <body>
        <div id="wrapper">
            <div id ="page">
                <jsp:include page="../menu.jsp"/> 
                <div id="content">
                    <div id="page-content">
                        <div id="page-content-top">
                        </div>
                        <div id="page-content-middle">
                            <% if (request.getAttribute("message")!=null) {
                                %>
                                <div class="error">${message}</div>
                            <%
                                }%>
                            <h1>Add offer </h1>
                            <form ENCTYPE="multipart/form-data" name='add_offer' method='post' action='AddOffer'  onsubmit='return submit_offer()'>  
                                Name*:  
                                <input type='text' name='name'><br/>  
                                Price*: in &euro; 
                                <input type='text' name='price'><br/>  
                                Quantity*:  
                                <input type='text' name='quantity'><br/> 
                                MINIMAL Quantity to Buy*:  
                                <input type='text' name='minimal_buy' value="0"><br/>  
                                <div style="margin-left: 182px;" class="info">
                                0 is "not specified"
                                </div>
                                Description:  
                                <input type='text' name='description'><br/>  <br/> 

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

                                <u>Date of purchase:</u>  
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
                                <div class="info" style="margin-left: 0;top: -45px;">
                                    Image can have dimensions up to 500 x 500 px and size 1 MB.
                                </div>
                                *-required fields<br/>
                                <input type='submit' value='Add offer'><br/>  
                            </form>  
                        </div>
                        <div id="page-content-bottom">
                        </div>
                    </div>

                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
    </body>
</html>


