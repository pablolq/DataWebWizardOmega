<%-- 
    Document   : registro
    Created on : 9/05/2019, 09:57:33 PM
    Author     : Pablo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            if(request.getParameter("error")!= null && !request.getParameter("error").equals(""))
            out.print("<h2 style='color:red;'>"+request.getParameter("error")+"</h2>");
        %>
        <div>
            Choose your username and password <br>
            <form action="RegisterUserServlet">
                <table border="1">
                    <tbody>
                        <tr>
                            <td>Username</td>
                            <td>
                                <input type="text" name="user" value="" />
                            </td>
                        </tr>
                        <tr>
                            <td>Password</td>
                            <td>
                                <input type="password" name="pwd1" value="" />
                            </td>
                        </tr>
                        <tr>
                            <td>Confirm Password</td>
                            <td> 
                                <input type="password" name="pwd2" value="" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="reset" value="Reset" />
                            </td>
                            <td> 
                                <input type="submit" value="Create user" />
                            </td>
                        </tr>
                    </tbody>
                </table>

                
            </form>            
        </div>
    </body>
</html>
