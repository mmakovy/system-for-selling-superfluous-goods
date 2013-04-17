<%-- 
    Document   : index
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
        <link href='https://fonts.googleapis.com/css?family=Days+One' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Istok+Web' rel='stylesheet' type='text/css'>
        <title>SSSG - System for selling superfluous goods</title>
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
                            <h1>Welcome to our system</h1>
                            <p> 
                                You can <a href="ListOffers">browse all the offers</a> and try
                                to find what you need or you can <a href="addoffer.jsp">add offer</a> 
                                and wait for the customer to come. Or try to <a href="findOffer.jsp">find what you need</a>.
                            </p>
                            <p>
                                You can also follow the offer, if you want to have information about it.
                                We will send you e-mail on every change or remove of the offer.
                            </p>
                            <p>
                                Every offer has its contact form, from which you can send e-mail to the seller
                                of particular offer.
                            </p>
                            <p class="last-p">
                                Information about your company can be edited in section <a href="updateCompany">My profile</a> 
                                and your subscriptions in <a href="MySubscriptions">My Subscriptions</a> .

                            </p> 
                        </div>
                        <div id="registration-content-bottom">
                        </div>
                    </div>

                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
    </body>


</html>
