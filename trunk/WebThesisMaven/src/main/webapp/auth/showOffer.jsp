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
                            <h1>${offerData.get("name")}</h1> <br/>
                            <u>Image:</u>
                            <img width='500' src='/WebThesisMaven/uploads/${offerData.get("photoUrl")}'><br/>
                            <u>Price:</u>
                            ${offerData.get("price")}</br>
                            <u>Quantity:</u>
                            ${offerData.get("quantity")}</br>
                            <u>Minimal buy quantity:</u>
                            ${offerData.get("minimalQuantity")}</br>
                            <u>Purchase Date:</u>
                            ${offerData.get("purchaseDate")}</br>
                            <u>Category:</u>
                            ${offerData.get("category")}</br>
                            <u>Description:</u>
                            ${offerData.get("description")}</br></br>

                            is offered by COMPANY<br/></br>
                            <u>Name:</u>
                            ${offerData.get("companyName")}</br>
                            <u>E-mail address:</u>
                            ${offerData.get("companyEmail")}</br>
                            <u>Phone number:</u>
                            ${offerData.get("companyPhone")}</br></br>
                            <u>Address:</u> <br/>
                            ${offerData.get("companyStreet")}  ${offerData.get("companyPsc")}<br/>
                            ${offerData.get("companyCity")}<br/>
                            ${offerData.get("companyCountry")}<br/></br>
                            <a href='FollowOffer?id=${offerData.get("id")}'>Follow this offer</a>

                            <form method='post' name='send_email_from_offer' action='/WebThesisMaven/auth/ContactFormEmailSender?offerId=${offerData.get("id")}' >
                                Send e-mail:<br/>

                                Your e-mail address:${offerData.get("myEmail")}<br/>
                                Text of message:<br/>
                                <input type='text' name='text'>
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

