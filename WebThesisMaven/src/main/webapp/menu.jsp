<%-- 
    Document   : menu
    Author     : Matus Makovy
--%>

<%@page contentType="text/html" pageEncoding="utf-8"%>


<%
    session = request.getSession();
    Long id = (Long) session.getAttribute("userID");
%>

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
<div class="menu-item" style="width: 140px; margin-left: 5px;">
    <a href="MySubscriptions">My Subscriptions</a> 
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

