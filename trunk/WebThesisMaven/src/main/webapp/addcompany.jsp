<%-- 
    Document   : addcompany
    Created on : 23.11.2012, 13:23:55
    Author     : matus
--%>

<%@page import="javax.print.attribute.standard.OutputDeviceAssigned"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <script src="myjs.js">
    </script>    
    <body>

        <h1>Add company</h1>
        <form method="post" name='add_company' action="/WebThesisMaven/AddCompany" onsubmit="return submit_company()">    
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
            Name*: <input type="text" name="name"><br/>
            Email*: <input type="text" name="email"><br/>
            Email(verification)*: <input type="text" name="emailVer"><br/>
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
            * - required fields <br/>
            <input type="submit" value="Add Company" name="option"/>
        </form>
        <a href="index.jsp"> Go to Home Page </a>
    </body>
</html>
