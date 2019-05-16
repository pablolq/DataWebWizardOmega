<%-- 
    Document   : home
    Created on : 16/05/2019, 12:06:23 PM
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
        String username = session.getAttribute("username").toString();
    %>
    <body onload="loadUserTables('tablenames','http://localhost:8080/DataWebWizard/webresources/UserTables?username=<%=username%>')">
        <h1>Welcome to the all mighty data wizard's lair/h1>
            <br>
            Here you can see your tables structure
            <form>

                <select id="tablenames" onchange="loadTableStructure('table','http://localhost:8080/DataWebWizard/webresources/TableSchema')">
                    <option></option>                    
                </select>

                <input type="hidden" id="user" name="user" value="<%= username%>"> 
                
            </form>

            <div id ='table' >

            </div>

            <br>
            <a href="LogoutServlet"> Log out </a>


            <script>
                function loadUserTables(id, target) {
                    var ajaxRequest;
                    if (window.XMLHttpRequest) {
                        ajaxRequest = new XMLHttpRequest(); // IE7+, Firefox, Chrome, Opera, Safari
                    } else {
                        ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP"); // IE6, IE5
                    }
                    ajaxRequest.onreadystatechange = function () {
                        if (ajaxRequest.readyState == 4 && ajaxRequest.status == 200) {
                            xmlDoc = ajaxRequest.responseXML;
                            txt = "";
                            x = xmlDoc.getElementsByTagName("nombre");
                            for (i = 0; i < x.length; i++) {
                                nombreTabla = x[i].childNodes[0].nodeValue; 
                                desplegado = nombreTabla.replace("<%=username%>","");
                                txt += "<option value='" + nombreTabla + "'>" + desplegado + "</option>";
                            }                            
                            document.getElementById(id).innerHTML = txt;  
                        }
                    }
                    ajaxRequest.open("GET", target, true /*async*/);
                    ajaxRequest.send();
                }
                
                function loadTableStructure(id, target) {
                    var ajaxRequest;
                    var e = document.getElementById("tablenames");
                    var tablaN = e.options[e.selectedIndex].value;
                    if (window.XMLHttpRequest) {
                        ajaxRequest = new XMLHttpRequest(); // IE7+, Firefox, Chrome, Opera, Safari
                    } else {
                        ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP"); // IE6, IE5
                    }
                    ajaxRequest.onreadystatechange = function () {
                        if (ajaxRequest.readyState == 4 && ajaxRequest.status == 200) {
                            xmlDoc = ajaxRequest.responseXML;
                            txt = "<table>";
                            xnombres = xmlDoc.getElementsByTagName("nombre");
                            xtipos = xmlDoc.getElementsByTagName("tipo");
                            for (i = 0; i < x.length; i++) {
                                nombreCampo = xnombres[i].childNodes[0].nodeValue; 
                                tipoCampo = xtipos[i].childNodes[0].nodeValue;                                 
                                txt += "<tr>";
                                txt += "<td>" + nombreCampo + "</td>";                                
                                txt += "<td>" + tipoCampo + "</td>";
                                txt += "</tr>";
                            }                            
                            document.getElementById(id).innerHTML = txt;  
                        }
                    }
                    ajaxRequest.open("GET", target + "?table=" + tablaN, true /*async*/);
                    ajaxRequest.send();
                }
            </script>
    </body>
</html>

