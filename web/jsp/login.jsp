<%-- 
    Document   : login
    Created on : 10-mar-2018, 20:33:24
    Author     : egimenez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>    
    <!-- Sección cabecera de la página HTML -->
    <head>
        <!-- Tipos de contenido de la página HTML -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">   

        <!-- Diseño adaptativo -->
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Icono y título en la pestaña del navegador -->
        <title>CML - Acceso</title>
        <link href="images/warehouse-network.png" rel="icon">

        <!-- Hoja de estilos general Bootstrap 4.0 -->
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">        

        <!-- Hoja de estilos focalizada para la autenticación -->
        <link href="https://getbootstrap.com/docs/4.0/examples/sign-in/signin.css" rel="stylesheet">  
    </head>

    <!-- Sección cuerpo de la página HTML -->
    <body class="text-center">
        <!-- Formulario credenciales -->
        <form class="form-signin" action="<%=request.getContextPath()%>/Login" method="POST">
            <!-- Imagen almacén -->
            <img class="mb-4" src="images/warehouse-network.png" alt="" width="333.33" height="183.33">

            <!-- Título -->
            <h1 class="h3 mb-3 font-weight-normal">Acceso a CML</h1>

            <!-- Usuario -->
            <label for="inputUser" class="sr-only">Usuario</label>
            <input type="text" name="inputUser" id="inputUser" class="form-control" placeholder="Usuario" required autofocus>

            <!-- Contraseña -->
            <label for="inputPassword" class="sr-only">Contraseña</label>
            <input type="password" name="inputPassword" id="inputPassword" class="form-control" placeholder="Contraseña" required>

            <!-- Pulsador confirmación credenciales -->
            <button class="btn btn-lg btn-primary btn-block" type="submit">Identificarse</button>

            <!-- Mensaje -->
            <p>${txmsj}</p>
        </form>
    </body>
</html>
