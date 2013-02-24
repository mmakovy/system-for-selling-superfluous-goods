<%-- 
    Document   : index
    Created on : 23.11.2012, 12:16:10
    Author     : matus
--%>

<%@page import="cz.muni.fi.thesis.User"%>
<%@page import="cz.muni.fi.thesis.UserManager"%>
<%@page import="cz.muni.fi.thesis.UserManagerImpl"%>
<%@page import="cz.muni.fi.thesis.DatabaseConnection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>System for selling superflouos goods</title>
    </head>
    <body>
        <h1>System for selling superflouos goods</h1>
        <%
            UserManager userManager = new UserManagerImpl();
            Long id = (Long) session.getAttribute("userID");
            User user = userManager.getUser(id);
            if (user == null) {
                out.println("Not logged in");
            } else {
                out.println("Logged in as " + user.getUserName() + "("+ user.getId()+")");
            }
        %>
        <table>

            <tr>
                <td>
                    <form method="POST" action="/WebThesisMaven/ListCompanies">
                        <input type="submit" value="List all companies" name="option"/>
                    </form>
                </td>
                <td>
                    <form method="POST" action="/WebThesisMaven/ListOffers">
                        <input type="submit" value="List all offers" name="option"/>
                    </form>
                </td>  



                <td>
                    <form method="POST" action="addoffer.jsp">
                        <input type="submit" value="Add Offer" name="option"/>
                    </form>
                </td>



                <td>
                </td>
                <td>
                    <form method="POST" action="/WebThesisMaven/ListMyOffers">
                        <input type="submit" value="List my offers" name="option"/>
                    </form>
                </td>
                <td>
                    <form method="POST" action="/WebThesisMaven/updateCompany">
                        <input type="submit" value="Update my profile" name="option"/>
                    </form>
                </td>

            </tr> 
            <tr>
                <td>
                    <form method="POST" action="findOffer.jsp">
                        <input type="submit" value="Find offer" name="option"/>
                    </form>
                </td>
            </tr>
            <tr>
                <td>
                    <form method="POST" action="/WebThesisMaven/Logout">
                        <input type="submit" value="Logout" name="option"/>
                    </form> 
                </td>    
            <tr> 

        </table>
    </body>
</html>
