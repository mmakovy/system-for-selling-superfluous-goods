<%-- 
    Document   : changePassword
    Created on : 24.3.2013, 11:37:58
    Author     : matus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <script src='../myjs.js'>  
    </script>
    <body>
        <form method="post" name="change_password" action="ChangePassword" onsubmit='return change_password()'>

            Old password:<br/>
            <input type="password" name="old_password"/><br/>
            New password:<br/>
            <input type="password" name="new_password"/><br/>
            New password (verification):<br/>
            <input type="password" name="new_password_ver"/><br/>
            <input type="submit" value="DO!!">

        </form>
    </body>
</html>
