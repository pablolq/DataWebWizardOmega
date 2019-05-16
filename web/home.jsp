<%-- 
    Document   : home
    Created on : 9/05/2019, 09:27:00 PM
    Author     : Pablo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    
    <%
        
        if(session.getAttribute("valid-session")==null || session.getAttribute("valid-session").equals(""))
            response.sendRedirect("index.html");
        
    %>
    <body>
        <h1>Hello World!</h1>
        
        <br>
        <a href="LogoutServlet"> Log out </a>
    </body>
</html>
