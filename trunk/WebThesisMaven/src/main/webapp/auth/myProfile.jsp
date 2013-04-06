<%-- 
    Document   : index
    Created on : 23.11.2012, 12:16:10
    Author     : matus
--%>

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
                                Map companyMap = (Map) request.getAttribute("companyMap");
                                Long id = (Long) session.getAttribute("userID");
                                if (request.getAttribute("message") != null) {
                            %>
                            <div class="error">${message}</div>
                            <%                                }%>
                            <h1>Edit my profile info</h1>
                            <form method='post' name='update_company' onsubmit='return submit_company()' action='/WebThesisMaven/auth/updateCompanyProcess?id=<% out.println(id);%>'>
                                Name:
                                <input type='text' name='name' value='<% out.println(companyMap.get("name"));%>'><br/>
                                Email: <% out.println(companyMap.get("email"));%> <br/>
                                Phone Number:
                                <input type='text' name='phone' value='<% out.println(companyMap.get("phoneNumber"));%>'><br/><br/>
                                Other:
                                <input type='text' name='other' value='<% out.println(companyMap.get("other"));%>'><br/><br/>
                                Address: <br/>
                                Street:
                                <input type='text' name='street' value='<% out.println(companyMap.get("street"));%>'><br/>
                                City:
                                <input type='text' name='city' value='<% out.println(companyMap.get("city"));%>'><br/>
                                PSC:
                                <input type='text' name='psc' value='<% out.println(companyMap.get("psc"));%>'><br/>
                                Country:
                                <input type='text' name='country' value='<% out.println(companyMap.get("country"));%>'><br/>

                                <input type='submit' name='submit' value='Update'>
                            </form>
                            <br/>

                            <h1>Change password </h1>
                            <form method="post" name="change_password" action="ChangePassword" onsubmit='return change_password()'>

                                Old password:<br/>
                                <input type="password" name="old_password"/><br/>
                                New password:<br/>
                                <input type="password" name="new_password"/><br/>
                                New password (verification):<br/>
                                <input type="password" name="new_password_ver"/><br/>
                                <input type="submit" value="Change password">

                            </form>

                            <a href='removeCompany'>Remove my company from system</a><br/>

                        </div>
                        <div id="registration-content-bottom">
                        </div>
                    </div>

                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
    </body>


</html>
