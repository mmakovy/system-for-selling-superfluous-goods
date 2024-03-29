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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/style.css">
        <link href='https://fonts.googleapis.com/css?family=Days+One' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Istok+Web' rel='stylesheet' type='text/css'>
        <title>SSSG - Update offer</title>
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
                            <%
                                ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LdWet4SAAAAAOlPY6u3FoRS10OPJxRoE5ow7mbW", "6LdWet4SAAAAALOkcI8Auoub7_pM__sNyQUZbpdr", false);
                                Map<String, String> offerData = (Map) request.getAttribute("offerData");
                                if (request.getAttribute("message") != null) {
                            %>
                            <div class="error">${message}</div>
                            <%                                }%>
                            <form method='post' name='add_offer' ENCTYPE='multipart/form-data' onsubmit='return submit_offer()' action='updateOfferProcess?id=${fn:escapeXml(offerData.get("id"))}'>
                                Name*:
                                <input type='text' name='name'  value='${fn:escapeXml(offerData.get("name"))}'><br/>
                                Price*: in &euro;
                                <input type='text' name='price'  value='${fn:escapeXml(offerData.get("price"))}'><br/>
                                Quantity*:
                                <input type='text' name='quantity' value='${fn:escapeXml(offerData.get("quantity"))}'><br/>
                                Minimal Buy Quantity*:
                                <input type='text' name='minimal_buy' value='${fn:escapeXml(offerData.get("minimalQuantity"))}'><br/>
                                <div style="margin-left: 182px;" class="info">
                                0 is "not specified"
                                </div>
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
                                    <% if (offerData.get("category").equals("OTHERS")) {%>
                                    <option selected>OTHERS</option>
                                    <%} else {%>
                                    <option>OTHERS</option>
                                    <%}%>
                                </select>    <br/>

                                Purchase Date:<br/>
                                Day:
                                <input type='text' name='dob_day' value='${fn:escapeXml(offerData.get("purchaseDay"))}'/><br/>
                                Month:
                                <input type='text' name='dob_month' value='${fn:escapeXml(offerData.get("purchaseMonth"))}'/><br/>
                                Year:
                                <input type='text' name='dob_year' value='${fn:escapeXml(offerData.get("purchaseYear"))}'/> <br/>

                                Description:
                                <input type='text' name='description' value='${fn:escapeXml(offerData.get("description"))}'><br/>

                                <%
                                    if (offerData.get("photoUrl") != null) {
                                %>
                                <img width='500' src='/static/${fn:escapeXml(offerData.get("photoUrl"))}'><br/>
                                <% } else { %>
                                
                                no image<br/>
                                
                                <% } %>
                                Update image:<br/>
                                <input type='file' name='image'><br/>

                                <input type='submit' name='submit' value='Update'>
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
