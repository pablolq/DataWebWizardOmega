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
        String username = session.getAttribute("user").toString();
    %>
    <body>
        <h1>Welcome to the all mighty data wizard's lair/h1>
            <form>
                
                <select name="tablenames">
                    <option></option>                    
                </select>
                
                <input type="hidden" id="user" name="user" value="<%= username%>"> 
                <button onclick=''>
            </form>
            
            <div id ='table'>
                
            </div>
            
        <br>
        <a href="LogoutServlet"> Log out </a>
        
        
        
    </body>
</html>
