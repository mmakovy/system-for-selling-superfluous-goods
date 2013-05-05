<%-- 
    Document   : showOffer
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
        <title>SSSG - Offer</title>
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
                                Long id = (Long) session.getAttribute("userID");
                                ReCaptcha c = ReCaptchaFactory.newSecureReCaptcha("6LdWet4SAAAAAOlPY6u3FoRS10OPJxRoE5ow7mbW", "6LdWet4SAAAAALOkcI8Auoub7_pM__sNyQUZbpdr", false);
                                Map<String, String> offerData = (Map) request.getAttribute("offerData");
                                if (request.getAttribute("message") != null) {
                            %>
                            <div class="error">${fn:escapeXml(message)}</div>
                            <%                                }%>

                            <h1>${fn:escapeXml(offerData.get("name"))}</h1> <br/>



                            <% if (id.toString().equals(offerData.get("idCompany"))) {%>
                            <div class="update">
                                <a href='updateOffer?id=${offerData.get("id")}'>Update</a>
                            </div>
                            <div class="remove">
                                <a href='removeOffer?id=${offerData.get("id")}' onclick="return confirm('Do you really want to remove this offer?')">Remove</a>
                            </div>
                            <%}%>
                            <div class="follow">
                                <a href='FollowOffer?id=${fn:escapeXml(offerData.get("id"))}'>Follow this offer</a>
                            </div>



                            <%
                                if (offerData.get("photoUrl") != null) {
                            %>
                            <img width='500' src='/static/${fn:escapeXml(offerData.get("photoUrl"))}'><br/>
                            <%                                }
                            %>

                            <u>Price:</u>
                            ${fn:escapeXml(offerData.get("price"))} &euro;</br>
                            <u>Quantity:</u>
                            ${fn:escapeXml(offerData.get("quantity"))}</br>
                            <u>Minimal buy quantity:</u>
                            ${fn:escapeXml(offerData.get("minimalQuantity"))}</br>
                            <u>Purchase Date:</u>
                            ${fn:escapeXml(offerData.get("purchaseDate"))}</br>
                            <u>Category:</u>
                            ${fn:escapeXml(offerData.get("category"))}</br>
                            <u>Description:</u>
                            ${fn:escapeXml(offerData.get("description"))}</br></br>

                            is offered by COMPANY<br/></br>
                            <u>Name:</u>
                            ${fn:escapeXml(offerData.get("companyName"))}</br>
                            <u>E-mail address:</u>
                            ${fn:escapeXml(offerData.get("companyEmail"))}</br>
                            <u>Phone number:</u>
                            ${fn:escapeXml(offerData.get("companyPhone"))}</br></br>

                            <% if (!offerData.get("companyStreet").isEmpty()
                                        || (!offerData.get("companyPsc").isEmpty())
                                        || (!offerData.get("companyCity").isEmpty()) 
                                        || (!offerData.get("companyCountry").isEmpty())) {%>
                            <u>Address:</u> <br/>
                            <% }%>

                            <% if (!offerData.get("companyStreet").isEmpty()) {%>
                            ${fn:escapeXml(offerData.get("companyStreet"))} 
                            <% }%>

                            <% if (!offerData.get("companyPsc").isEmpty()) {%>
                            ${fn:escapeXml(offerData.get("companyPsc"))} 
                            <% }%>

                            <% if (!offerData.get("companyCity").isEmpty()) {%>
                            ${fn:escapeXml(offerData.get("companyCity"))} 
                            <% }%>

                            <% if (!offerData.get("companyCountry").isEmpty()) {%>
                            ${fn:escapeXml(offerData.get("companyCountry"))} 
                            <% }%>


                            <form method='post' name='send_email_from_offer' action='/auth/ContactFormEmailSender?offerId=${fn:escapeXml(offerData.get("id"))}' >
                                <u>Send e-mail:</u><br/>
                                <br/>

                                Your e-mail address: ${fn:escapeXml(offerData.get("myEmail"))}<br/>
                                <br/>
                                Text of message:<br/>
                                <input type='text' name='text'><br/>
                                <% out.println(c.createRecaptchaHtml(null, null));%>
                                <input type='submit' value='send'>

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

