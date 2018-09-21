<%-- 
    Document   : nueva-imagen
    Created on : 12/09/2018, 08:21:27 AM
    Author     : USUARIO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <%@page import="java.util.List" %>
        <%@page import="modelos.Tipo" %>
        <%
        List<Tipo> listaTipos = (List<Tipo>)request.getAttribute("tipos");
        %>
        <h1>Formulario para nuevas imágenes de la galeria</h1>
        <form name="nueva-imagen" action="NuevaImagen" method="POST">
            <span>nombre</span><input type="text" name="nombre" />
            <span>ruta</span><input type="text" name="ruta" />
            <span>tipo</span>
            <select name="tipo">
                <%
                for(Tipo t : listaTipos) {%>
                <option value="<%= t.id%>"><%= t.nombre%></option>
                <%
                }%>
            </select>
            <button type="submit">Guardar imagen</button>
        </form>
    </body>
</html>
