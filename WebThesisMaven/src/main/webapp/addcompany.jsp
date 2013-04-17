<%-- 
    Document   : addcompany
    Author     : Matus Makovy
--%>

<%@page import="javax.print.attribute.standard.OutputDeviceAssigned"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/style.css">
        <link href='https://fonts.googleapis.com/css?family=Days+One' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Istok+Web' rel='stylesheet' type='text/css'>
        <title>SSSG - Register</title>
    </head>
    <script src="myjs.js">
    </script>    
    <body>
        <div id="wrapper">
            <div id ="page">
                <jsp:include page="menu.jsp"/>               
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
                            <h1>New user registration</h1>
                            <form method="post" name='add_company' action="/AddCompany" onsubmit="return submit_company()">    
                                <%
                                    session = request.getSession();

                                    if (session.getAttribute("error") != null) {
                                        String error = (String) session.getAttribute("error");

                                        if (error.equals("email")) {
                                            out.println("!!! Email already in database !!!<br/>");
                                            session.removeAttribute("error");
                                        } else if (error.equals("username")) {
                                            out.println("!!! Username already in database !!!<br/>");
                                            session.removeAttribute("error");
                                        }

                                    }
                                %>
                                Username*: <input type="text" name="usrname"><br/>
                                Password*: <input type="password" name="pwd" alt=":)"><br/>
                                Password(verification)*: <input type="password" name="pwdVer" alt=":)"><br/> 
                                Company name*: <input type="text" name="name"><br/>
                                Email*: <input type="text" name="email"><br/>
                                Email(verification)*: <input type="text" name="emailVer"><br/>
                                <div class="info">(System will send you a verification e-mail)</div>
                                PhoneNumber*: <input type="text" name="phone"><br/>   <br/>
                                Address: <br/>
                                Street:
                                <input type="text" name="street"/><br/>
                                City:
                                <input type="text" name="city"/><br/>
                                Country:
                                <input type="text" name="country"/><br/>
                                PSC:
                                <input type="text" name="psc"/><br/>
                                Other information:
                                <input type="text" name="other"/><br/>
                                <div class="info">
                                (for example Skype alias, ICQ number,...)
                                </div>
                                * - required fields <br/>
                                <input type="submit" value="Add Company" name="option"/>
                            </form>
                        </div>
                        <div id="registration-content-bottom">
                        </div>
                    </div>

                </div>
                <jsp:include page="footer.jsp"/>

                </body>
                </html>
