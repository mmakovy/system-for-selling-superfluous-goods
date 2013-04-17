<%-- 
    Document   : myProfile
    Author     : Matus Makovy
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
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/style.css">
        <link href='https://fonts.googleapis.com/css?family=Days+One' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Istok+Web' rel='stylesheet' type='text/css'>
        <title>SSSG - My Profile</title>
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
                            <%
                                Map<String,String> companyMap = (Map) request.getAttribute("companyMap");
                                Long id = (Long) session.getAttribute("userID");
                                if (request.getAttribute("message") != null) {
                            %>
                            <div class="error">${message}</div>
                            <%                                }%>
                            <h1>Edit my profile info</h1>
                            <form method='post' name='updateCompany' onsubmit="return update_company()" action='/auth/updateCompanyProcess?id=<% out.println(id);%>'>
                                Company name:
                                <input type='text' name='name' value='<% out.println(companyMap.get("name"));%>'><br/>
                                Email: <span id="email"><% out.println(companyMap.get("email"));%> <br/></span>
                                Phone Number:
                                <input type='text' name='phone' value='<% out.println(companyMap.get("phoneNumber"));%>'><br/>
                                
                                <br/>
                                <u>Address:</u> <br/>
                                Street:
                                <input type='text' name='street' value='<% out.println(companyMap.get("street"));%>'><br/>
                                City:
                                <input type='text' name='city' value='<% out.println(companyMap.get("city"));%>'><br/>
                                PSC:
                                <input type='text' name='psc' value='<% out.println(companyMap.get("psc"));%>'><br/>
                                Country:
                                <input type='text' name='country' value='<% out.println(companyMap.get("country"));%>'><br/><br/>
                                Other information:
                                <input type='text' name='other' value='<% out.println(companyMap.get("other"));%>'><br/>
                                

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

                            <a href='removeCompany' onclick="return confirm('Do you really want to remove your company from system?')">Remove my company from system</a><br/>

                        </div>
                        <div id="registration-content-bottom">
                        </div>
                    </div>

                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
    </body>


</html>
