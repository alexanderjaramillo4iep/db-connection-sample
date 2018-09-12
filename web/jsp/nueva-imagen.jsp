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
        <h1>Formulario para nuevas imágenes de la galeria</h1>
        <form name="nueva-imagen" action="NuevaImagen" method="POST">
            <span>nombre</span><input type="text" name="nombre" />
            <span>ruta</span><input type="text" name="ruta" />
            <button type="submit">Guardar imagen</button>
        </form>
    </body>
</html>
