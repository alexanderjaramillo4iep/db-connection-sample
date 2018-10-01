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
        <style>
            td>img.edit {
                width: 30px;
            }
        </style>
    </head>
    <body>
        
        <%@page import="java.util.List" %>
        <%@page import="modelos.Tipo" %>
        <%@page import="modelos.Imagen" %>
        <%
        List<Tipo> listaTipos = (List<Tipo>)request.getAttribute("tipos");
        List<Imagen> listaImagenes = (List<Imagen>)request.getAttribute("imagenes");
        %>
        <h1>Formulario para nuevas imágenes de la galeria</h1>
        <form name="nueva-imagen" action="NuevaImagen" method="POST">
            <input id="idimagenes" type="hidden" name="idimagenes" />
            <span>nombre</span><input id="nombre" type="text" name="nombre" />
            <span>ruta</span><input id="ruta" type="text" name="ruta" />
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
            <table border="1">
                <thead>
                    <tr>
                        <th>id imagen</th>
                        <th>nombre</th>
                        <th>ruta</th>
                        <th>editar</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    for(Imagen i : listaImagenes) {%>
                    <tr>
                        <td><%= i.idimagenes%></td>
                        <td><%= i.nombre%></td>
                        <td><%= i.ruta%></td>
                        <td><image class="edit" onclick="editarImagen(<%= i.idimagenes%>, '<%= i.nombre%>', '<%= i.ruta%>')" src="https://icon-icons.com/icons2/906/PNG/512/pencil_icon-icons.com_69999.png" /></td>
                    </tr>
                    <%
                    }%>
                </tbody>
            </table>
            <script>
                function editarImagen(id, nombre, ruta){
                    //alert(id + " " + nombre + " " + ruta);
                    var inputId = document.getElementById("idimagenes");
                    var inputNombre = document.getElementById("nombre");
                    var inputRuta = document.getElementById("ruta");
                    
                    inputId.value = id;
                    inputNombre.value = nombre;
                    inputRuta.value = ruta;
                }
            </script>
    </body>
</html>
