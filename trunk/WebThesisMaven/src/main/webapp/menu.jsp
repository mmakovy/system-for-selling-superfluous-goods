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
                <a href="https://sssg-sssg.rhcloud.com/addcompany.jsp">SIGN UP </a>
            </div>
            <div class="menu-item">
                <a href="https://sssg-sssg.rhcloud.com/login.jsp">LOG IN</a>
            </div>
        </div>
        <div id="menu-right">
        </div>
    </div>
</div>
<%                    } else {
%>

<div class="menu-item">
    <a href="/auth/updateCompany">My profile</a>
</div>
<div class="menu-item">
    <a href="/auth/ListMyOffers">My Offers</a> 
</div>
<div class="menu-item" style="width: 140px; margin-left: 5px;">
    <a href="/auth/MySubscriptions">My Subscriptions</a> 
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
        <ul>
            <li>
                <div class="submenu-item browse">
                    <a href="/auth/ListOffers"> Browse offers</a>
                </div>
                <ul>
                    <li>
                        <a href="/auth/ListOffers">All</a>
                    </li>
                    <li>
                        <u>Categories:</u>
                    </li>
                    <li>
                        <a href="/auth/ListOffersFromCategory?category=BUILDING">Building</a>
                    </li>
                    <li>
                        <a href="/auth/ListOffersFromCategory?category=FURNITURE">Furniture</a>
                    </li>
                    <li>
                        <a href="/auth/ListOffersFromCategory?category=ELECTROTECHNICS">Electrotechnics</a>
                    </li>
                    <li>
                        <a href="/auth/ListOffersFromCategory?category=CARS">Cars</a>
                    </li>
                    <li>
                        <a href="/auth/ListOffersFromCategory?category=COMPUTER">Computer</a>
                    </li>
                    <li>
                        <a href="/auth/ListOffersFromCategory?category=COSMETICS">Cosmetics</a>
                    </li>
                    <li>
                        <a href="/auth/ListOffersFromCategory?category=SPORTS">Sports</a>
                    </li>
                    <li>
                        <a href="/auth/ListOffersFromCategory?category=OTHERS">Others</a>
                    </li>

                </ul>    
            </li>
            <li>
                <div class="submenu-item">
                    <a href="/auth/addoffer.jsp">Add offer</a>
                </div>
            </li>
            <li>
                <div class="submenu-item">
                    <a href="/auth/findOffer.jsp">Find offer</a>
                </div>
            </li>
        </ul>
    </div>
    <div id="submenu-right">
    </div>
</div>
</div>
<%                        }
%>

