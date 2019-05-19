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
        String username ;
        if(session.getAttribute("valid-session")==null || session.getAttribute("valid-session").equals(""))
            response.sendRedirect("index.html");
        if(session.getAttribute("username")!=null)
           username = session.getAttribute("username").toString();
        else
            username = "";
    %>
    <body onload="loadUserTables('tablenames','http://localhost:8080/DataWebWizard/webresources/UserTables?username=<%=username%>')">
         <h1>Welcome to the all mighty data wizard's lair</h1><br>

        <h3>Create table</h3>
        Table name: <input type="text" id='tblName' /><br>
        Number of Fields: <input id='numFields' type="text" value="" /><br>
        <input id='username' type="hidden" value=<%=session.getAttribute("username")%> />

        <input type="submit" value="Add Fields" onclick="createFields('resFields', 'FieldServlet')" /><br>
        <div id="resFields"></div><br><br>

        <input type="submit" value="Create table" onclick="callRESTCreateTable('username', 'GET',
                        'http://localhost:8080/DataWebWizard/webresources/CreateTable',
                        '')" /><br><br>
        
        <input type="submit" value="Insert Data" onclick="callRESTInsertQuery('username', 'PUT',
                        'http://localhost:8080/DataWebWizard/webresources/CreateTable')" /><br>

        <h3>Here you can see your tables structure</h3>
        
        <select id="tablenames" onchange="loadTableStructure('table', 'http://localhost:8080/DataWebWizard/webresources/TableSchema')">
            <option></option>                    
        </select>
        <input type="submit" value="Cargar esquema con JSON" onclick="loadTableStructureJSON('table', 'http://localhost:8080/DataWebWizard/webresources/TableSchema')">
        <input type="submit" value="Ver registros" onclick="verRegistros('DataExtractor')">
        
        <input type="hidden" id="user" name="user" value="<%= username%>"> 
        <input type="submit" value="Delete table" onclick="callRESTDeleteTable('username', 'DELETE',
                    'http://localhost:8080/DataWebWizard/webresources/CreateTable')" />
        
        <br>
        <br>
        <input type="submit" value="Get Information" onclick=""/>
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
                                desplegado = nombreTabla.replace("<%=username%>".toUpperCase(),"");
                                txt += "<option value='" + nombreTabla + "'>" + desplegado + "</option>";
                            }                            
                            document.getElementById(id).innerHTML = txt;  
                        }
                        document.getElementById(id).innerHTML = txt;
                    }
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
                            txt = "<table><th>Campo</th><th>Tipo de dato</th>";
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
                            txt += "</table>";
                            document.getElementById(id).innerHTML = txt;  
                        }
                        document.getElementById(id).innerHTML = txt;
                    }
                }
                function loadTableStructureJSON(id, target) {
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
                            var myObj = JSON.parse(ajaxRequest.responseText);
                            txt = "<table><th>Campo</th><th>Tipo de dato</th>";
                            
                            for (i in myObj.columnas) {
                                txt += "<tr>";
                                txt += "<td>" + myObj.columnas[i].nombre  + "</td>";                                
                                txt += "<td>" + myObj.columnas[i].tipo  + "</td>";
                                txt += "</tr>";
                            }
                            
                            document.getElementById(id).innerHTML = txt;  
                        }
                    }
                    ajaxRequest.open("POST", target + "?table=" + tablaN, true /*async*/);
                    ajaxRequest.send();
                }
                
            function createFields(id, target) {
                var ajaxRequest;
                if (window.XMLHttpRequest) {
                    ajaxRequest = new XMLHttpRequest(); // IE7+, Firefox, Chrome, Opera, Safari
                } else {
                    ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP"); // IE6, IE5
                }
                ajaxRequest.onreadystatechange = function () {
                    if (ajaxRequest.readyState == 4 && ajaxRequest.status == 200) {
                        document.getElementById(id).innerHTML = ajaxRequest.responseText;
                        loadUserTables('tablenames','http://localhost:8080/DataWebWizard/webresources/UserTables?username=' + document.getElementById("user").value);
                    }
                }
                ajaxRequest.open("POST", target, true);
                ajaxRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                var numFields = document.getElementById("numFields").value;
                //alert(numFields);
                ajaxRequest.send("numFields=" + numFields);
            }
            
            function verRegistros(target) {
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
                        document.getElementById(id).innerHTML = ajaxRequest.responseText;
                    }
                }
                ajaxRequest.open("POST", target, true);
                ajaxRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                
                //alert(numFields);
                ajaxRequest.send("tabla=" + tablaN);
            }
            
            function callRESTCreateTable(id, method, target, msg) {
                var username = document.getElementById(id).value;
                var nomTable = document.getElementById("tblName").value;
                var numFields = document.getElementById("numFields").value;
                var numFieldsInt = parseInt(numFields);
                var QueryString = "create table "+ username + nomTable +"(";
                var stringKey;
                
                for (var i = 0; i < numFieldsInt; i++) {
                    var stringName = "fieldName" + i;
                    var stringType = "ddlType" + i;
                    var stringLength = "length" + i;
                    var stringPK = "PK" + i;

                    var elementName = document.getElementById(stringName).value;
                    var elementType = document.getElementById(stringType).value;

                    if (elementType == 'varchar') {
                        var elementLength = document.getElementById(stringLength).value;

                        QueryString = QueryString + elementName + " " + elementType + "(" + elementLength + "),";
                    } else {
                        QueryString = QueryString + elementName + " " + elementType + ",";
                    }

                    if (document.getElementById(stringPK).checked) {
                        stringKey = elementName;
                    }
                }

                QueryString = QueryString + "primary key (" + stringKey + "))";

                var resQuery = QueryString;

                var ajaxRequest;
                if (window.XMLHttpRequest) {
                    ajaxRequest = new XMLHttpRequest(); // IE7+, Firefox, Chrome, Opera, Safari
                } else {
                    ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP"); // IE6, IE5
                }
                ajaxRequest.onreadystatechange = function () {
                    if (ajaxRequest.readyState == 4 &&
                            (ajaxRequest.status == 200 || ajaxRequest.status == 204)) {
                        alert("Query Exitoso");
                        loadUserTables('tablenames','http://localhost:8080/DataWebWizard/webresources/UserTables?username='+document.getElementById("user").value);
                    }
                }

                var targets = target + "?" + "query=" + resQuery;
                ajaxRequest.open(method, targets, true /*async*/);
                ajaxRequest.setRequestHeader("Content-Type", "text/html");
                ajaxRequest.send(msg);

            }
            
            function callRESTDeleteTable(id, method, target, msg) {
                var username = document.getElementById(id).value;
                var e = document.getElementById("tablenames");
                var tablaN = e.options[e.selectedIndex].value;
                var QueryString = "drop table "+ tablaN  +"";
                
                var ajaxRequest;
                if (window.XMLHttpRequest) {
                    ajaxRequest = new XMLHttpRequest(); // IE7+, Firefox, Chrome, Opera, Safari
                } else {
                    ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP"); // IE6, IE5
                }
                
                ajaxRequest.onreadystatechange = function () {
                    if (ajaxRequest.readyState == 4 &&
                            (ajaxRequest.status == 200 || ajaxRequest.status == 204)) {
                        alert("Delete Exitoso");
                        loadUserTables('tablenames','http://localhost:8080/DataWebWizard/webresources/UserTables?username='+document.getElementById("user").value);
                    }
                }
                
                var targets = target + "?" + "query=" + QueryString;
                
                ajaxRequest.open(method, targets, true /*async*/);
                ajaxRequest.setRequestHeader("Content-Type", "text/html");   
                //ajaxRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                
                ajaxRequest.send();
                
            }
            
            function callRESTInsertQuery(user, method, target) {
                var username = document.getElementById(user).value;
                var nombreTabla = document.getElementById("tblName").value;
                var numFields = document.getElementById("numFields").value;
                var numFieldsInt = parseInt(numFields);
                var QueryString = "insert into ROOT." + username + nombreTabla + " values (";

                for (var i = 0; i < numFieldsInt; i++) {
                    var stringName = "fieldName" + i;
                    var stringType = "ddlType" + i;

                    var name = document.getElementById(stringName).value;
                    var type = document.getElementById(stringType).value;

                    if (type == 'varchar') {
                        QueryString = QueryString + "'" + name + "'";
                    } else {
                        QueryString = QueryString + name;
                    }

                    if ((numFieldsInt - i) != 1) {
                        QueryString = QueryString + ",";
                    }
                }

                QueryString = QueryString + ")";

                var resQuery = QueryString;

                var ajaxRequest;
                if (window.XMLHttpRequest) {
                    ajaxRequest = new XMLHttpRequest(); // IE7+, Firefox, Chrome, Opera, Safari
                } else {
                    ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP"); // IE6, IE5
                }
                ajaxRequest.onreadystatechange = function () {
                    if (ajaxRequest.readyState == 4 &&
                            (ajaxRequest.status == 200 || ajaxRequest.status == 204)) {
                        alert("Insert Exitoso");
                    }
                }
                var taux = target + "?" + "queryParam=" + resQuery;

                ajaxRequest.open(method, taux, true /*async*/);
                ajaxRequest.setRequestHeader("Content-Type", "text/html");
                ajaxRequest.send();

            }
            </script>
    </body>
</html>

