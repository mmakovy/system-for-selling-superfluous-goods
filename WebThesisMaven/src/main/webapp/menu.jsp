<%-- 
    Document   : menu
    Created on : 5.4.2013, 11:43:09
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
        <title>JSP Page</title>
    </head>
    <%
        session = request.getSession();
        Long id = (Long) session.getAttribute("userID");
    %>
    <body>
        <div id="header">
            <div id="logo">SSSG</div>   
            <div id="menu">
                <div id="menu-left">
                </div>
                <div id="menu-middle">
                    <div class="menu-item">
                        <a href="index.jsp">HOME </a>
                    </div>
            <%
                        if (id == null) {
                    %>
            
                    

                    <div class="menu-item">
                        <a href="addcompany.jsp">SIGN UP </a>
                    </div>
                </div>
                <div id="menu-right">
                </div>
            </div>
            </div>
            <%                    } else {
            %>
           
                    <div class="menu-item">
                        <a href="updateCompany">My profile</a>
                    </div>
                    <div class="menu-item">
                        <a href="ListMyOffers">My Offers</a> 
                    </div>
                    <div class="menu-item logout">
                        <a href="../Logout"> Logout </a>
                    </div>
            </div>
            <div id="menu-right">
            </div>
        </div>
        <div id="submenu">
            <div id="submenu-left">
            </div>
            <div id="submenu-middle">
                <div class="submenu-item">
                    <a href="ListOffers"> Browse offers</a>
                </div>
                <div class="submenu-item">
                    <a href="addoffer.jsp">Add offer</a>
                </div>
                <div class="submenu-item">
                    <a href="findOffer.jsp">Find offer</a>
                </div>
            </div>
            <div id="submenu-right">
            </div>
        </div>
            </div>
    <%                        }
    %>

</body>
</html>
