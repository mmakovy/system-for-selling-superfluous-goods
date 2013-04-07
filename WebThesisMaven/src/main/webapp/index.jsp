<%-- 
    Document   : index
    Author     : Matus Makovy
--%>

<%@page import="java.util.UUID"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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
                <jsp:include page="menu.jsp"/> 
                <div id="content">
                    <div id="log-in-form">
                        <div id="log-in-form-top">
                        </div>
                        <div id="log-in-form-middle">
                            <div class="log-in">LOGIN</div>
                            <form method="post" name='login' action="/WebThesisMaven/Login" ><!--onsubmit="return submit_company()">-->
                                Username: <input type="text" name="userName"/><br/>
                                Password: <input type="password" name="pwd"/><br/>
                                <input type="submit" value="Log-in"/>
                            </form>  
                            <a href="forgotPassword.jsp">Forgot your password?</a><br/>

                        </div>
                        <div id="log-in-form-bottom">
                        </div>
                    </div>
                    <div id="index-content">
                        <div id="index-content-top">
                        </div>
                        <div id="index-content-middle">
                            <% if (request.getAttribute("message") != null) {
                                %>
                                <div class="error">${message}</div>
                            <%
                                }%>
                            <h1> What is SSSG ? </h1>
                            <p>Lorem ipsum dolor sit amet, prima ubique corrumpit ei ius. Agam scriptorem intellegebat 
                                eum ea, velit labitur at quo, ei idque homero ceteros eum. Tale congue semper ea est. 
                                Aliquid delicata definitionem id ius. Numquam eruditi ius in. Ne euismod appareat 
                                contentiones eos.
                            </p>
                            <p>

                                Deserunt recteque contentiones eum in, mel doctus timeam iuvaret cu.
                                Ei quot splendide sea, ad sea omnium delicata. An usu error diceret. 
                                Usu et diceret explicari. Reformidans philosophia vis an, id indoctum salutatus his, 
                                ut vel oblique platonem theophrastus.
                            </p>
                            <p>
                                Cu nibh legere nec, te pericula aliquando disputationi eos. 
                                Cibo accusam signiferumque ei eam. Ius probo quaerendum ex. 
                                Mea admodum platonem eu, at usu cetero inermis. Eu laudem epicurei cum. 
                                Sed ad iracundia efficiendi, in per eius quaeque nominati.
                            </p>
                            <p>
                                His te aliquando incorrupte. Te quaestio laboramus eum, his vitae adipisci cu. 
                                Natum viris platonem at ius, sit eu dico alia consetetur, nulla meliore euripidis te sea. 
                                Nec omnes iuvaret cu, decore apeirian cotidieque ea qui. 
                                Pri in affert placerat deseruisse, ne diam mentitum delicata pri, prodesset inciderint pri ad.
                            </p>
                            <p class="last-p">
                                Error similique sea in, at sed modo veri dicta.
                                Latine salutatus principes ne sed, sit iisque dolorum delicata cu. 
                                Nisl illud conceptam ad pri. Sit ut noluisse salutandi, ut pri alia aliquip torquatos. 
                                His ea novum qualisque. Sed in mucius fabellas insolens, cum 
                                omnes iracundia similique at, rebum epicuri consequuntur nam te.</p>
                        </div>
                        <div id="index-content-bottom">
                        </div>
                    </div>

                </div>
                <jsp:include page="footer.jsp"/>
            </div>
    </body>
</html>
