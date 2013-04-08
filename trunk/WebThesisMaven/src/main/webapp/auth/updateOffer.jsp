<%-- 
    Document   : updateOffer
    Author     : Matus Makovy
--%>

<%@page import="net.tanesha.recaptcha.ReCaptchaFactory"%>
<%@page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@page import="java.util.Map"%>
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
                            <%
                                ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LdWet4SAAAAAOlPY6u3FoRS10OPJxRoE5ow7mbW", "6LdWet4SAAAAALOkcI8Auoub7_pM__sNyQUZbpdr", false);
                                Map<String, String> offerData = (Map) request.getAttribute("offerData");
                                if (request.getAttribute("message") != null) {
                            %>
                            <div class="error">${message}</div>
                            <%                                }%>
                            <form method='post' name='update_offer' ENCTYPE='multipart/form-data' onsubmit='return submit_offer()' action='/WebThesisMaven/auth/updateOfferProcess?id=${offerData.get("id")}'>
                                Name:
                                <input type='text' name='name'  value='${offerData.get("name")}'><br/>
                                Price:
                                <input type='text' name='price'  value='${offerData.get("price")}'><br/>
                                Quantity:
                                <input type='text' name='quantity' value='${offerData.get("quantity")}'><br/>
                                Minimal Buy Quantity:
                                <input type='text' name='minimal_buy' value='${offerData.get("minimalQuantity")}'><br/>
                                Category:
                                <select name='category'>
                                    <% if (offerData.get("category").equals("BUILDING")) {%>
                                    <option selected>BUILDING</option>
                                    <%} else {%>
                                    <option>BUILDING</option>
                                    <%}%>
                                    <% if (offerData.get("category").equals("FURNITURE")) {%>
                                    <option selected>FURNITURE</option>
                                    <%} else {%>
                                    <option>FURNITURE</option>
                                    <%}%>
                                    <% if (offerData.get("category").equals("ELECTROTECHNICS")) {%>
                                    <option selected>ELECTROTECHNICS</option>
                                    <%} else {%>
                                    <option>ELECTROTECHNICS</option>
                                    <%}%>
                                    <% if (offerData.get("category").equals("CARS")) {%>
                                    <option selected>CARS</option>
                                    <%} else {%>
                                    <option>CARS</option>
                                    <%}%>
                                    <% if (offerData.get("category").equals("COMPUTER")) {%>
                                    <option selected>COMPUTER</option>
                                    <%} else {%>
                                    <option>COMPUTER</option>
                                    <%}%>
                                    <% if (offerData.get("category").equals("COSMETICS")) {%>
                                    <option selected>COSMETICS</option>
                                    <%} else {%>
                                    <option>COSMETICS</option>
                                    <%}%>
                                    <% if (offerData.get("category").equals("SPORTS")) {%>
                                    <option selected>SPORTS</option>
                                    <%} else {%>
                                    <option>SPORTS</option>
                                    <%}%>
                                </select>    <br/>

                                Purchase Date:<br/>
                                Day:
                                <input type='text' name='dob_day' value='${offerData.get("purchaseDay")}'/>
                                Month:
                                <input type='text' name='dob_month' value='${offerData.get("purchaseMonth")}'/>
                                Year:
                                <input type='text' name='dob_year' value='${offerData.get("purchaseYear")}'/> <br/>

                                Description:
                                <input type='text' name='description' value='${offerData.get("description")}'><br/>

                                Image:<br/>
                                <img width='300' src='/WebThesisMaven/uploads/${offerData.get("photoUrl")}'><br/>
                                Update image:<br/>
                                <input type='file' name='image'><br/>

                                <input type='submit' name='submit' value='Update'>
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


calendar.setTime(offer.getPurchaseDate());
int day = calendar.get(Calendar.DAY_OF_MONTH);
int month = calendar.get(Calendar.MONTH) + 1;
int year = calendar.get(Calendar.YEAR);

