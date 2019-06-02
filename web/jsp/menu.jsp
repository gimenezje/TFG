<%-- 
    Document   : menu
    Created on : 11-mar-2018, 17:33:44
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
        <title>CML - Menú</title>
        <link href="images/warehouse-network.png" rel="icon">

        <!-- Hoja de estilos general Bootstrap 4.0 -->        
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">

        <!-- Hoja de estilos focalizada para el cuadro de mando -->
        <link href="https://getbootstrap.com/docs/4.0/examples/dashboard/dashboard.css" rel="stylesheet">
    <head/>

    <!-- Sección cuerpo de la página HTML -->    
    <body>   
        <!-- Verificar si la sesión ha expirado -->
        <%
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            if (session.getAttribute("usr") == null) {
                request.getRequestDispatcher("/Cs").forward(request, response);
                return;
            }
        %>

        <!-- Cabecera del menú -->
        <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">

            <!-- Título de la página HTML -->
            <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">Cuadro de Mando Logístico</a>

            <!-- Fecha clave datos -->
            <a class="navbar-brand mr-0" style="background-color: transparent; box-shadow: none" href="#">                
                <%="Fecha Clave:" + " "
                        + System.getProperty("dateKey").substring(6, 8) + "."
                        + System.getProperty("dateKey").substring(4, 6) + "."
                        + System.getProperty("dateKey").substring(0, 4)%>
            </a>

            <!-- Pulsador cierre de sesión -->            
            <ul class="navbar-nav px-3"> 
                <li class="nav-item text-nowrap">
                    <a class="nav-link" href="<%=request.getContextPath()%>/Cs">
                        <span data-feather="log-out"></span>
                        Cerrar sesión
                    </a>
                </li>
            </ul>
        </nav>

        <!-- Opciones del menú -->
        <div class="container-fluid">
            <div class="row">
                <nav class="col-md-2 d-none d-md-block bg-light sidebar">
                    <div class="sidebar-sticky">
                        <ul class="nav flex-column">

                            <!-- En tiempo real -->
                            <li class="nav-item">
                                <a class="nav-link <%=request.getAttribute("tr_act")%>" href="<%=request.getContextPath()%>/Tr">
                                    <span data-feather="activity"></span>
                                    En tiempo real   
                                </a> 
                            </li>       

                            <!-- Pallets movidos -->
                            <li class="nav-item">
                                <a class="nav-link <%=request.getAttribute("pmop_act")%>" href="<%=request.getContextPath()%>/Pmop">
                                    <span data-feather="users"></span>
                                    Pallets movidos                                     
                                </a>                                      
                            </li>

                            <!-- Artículos recibidos  -->
                            <li class="nav-item">
                                <a class="nav-link <%=request.getAttribute("arp_act")%>" href="<%=request.getContextPath()%>/Arp">
                                    <span data-feather="arrow-left-circle"></span>
                                    Artículos recibidos
                                </a>
                            </li>

                            <!-- Artículos expedidos -->
                            <li class="nav-item">
                                <a class="nav-link <%=request.getAttribute("aep_act")%>" href="<%=request.getContextPath()%>/Aep">
                                    <span data-feather="arrow-right-circle"></span>
                                    Artículos expedidos
                                </a>
                            </li>

                            <!-- Zonas expediciones -->
                            <li class="nav-item">
                                <a class="nav-link <%=request.getAttribute("zep_act")%>" href="<%=request.getContextPath()%>/Zep">
                                    <span data-feather="map"></span>
                                    Zonas expediciones
                                </a>
                            </li>                                                  
                        </ul>
                    </div>
                </nav>

                <!-- Lanzamiento presentación gráficos tiempo real -->                    
                <% if (request.getAttribute("tr_main") != null) { %>                    
                ${tr_main}

                <!-- Lanzamiento presentación gráficos pallets movidos -->
                <% } else if (request.getAttribute("pmop_main") != null) { %>
                ${pmop_main}

                <!-- Lanzamiento presentación gráficos artículos recibidos -->
                <% } else if (request.getAttribute("arp_main") != null) { %>
                ${arp_main}

                <!-- Lanzamiento presentación gráficos artículos expedidos -->
                <% } else if (request.getAttribute("aep_main") != null) { %>
                ${aep_main}

                <!-- Lanzamiento presentación gráficos zonas expediciones -->
                <% } else if (request.getAttribute("zep_main") != null) { %>
                ${zep_main}

                <!-- Lanzamiento presentación gráficos tiempo real (1ª carga) -->
                <% } else { %>
                <% request.getRequestDispatcher("/Tr").forward(request, response); %> 
                <% } %>
            </div>
        </div> 

        <!-- Javascripts Bootstrap -->
        <!-- ===================== -->

        <!-- Carga de las liberías Bootstrap 4.0 -->
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <script>window.jQuery || document.write('<script src="https://getbootstrap.com/assets/js/vendor/popper.min.js"><\/script>');</script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

        <!-- Carga de las librerías de los iconos que acompañan a las opciones -->
        <script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
        <script>feather.replace();</script>

        <!-- Javascripts Google Charts -->
        <!-- ========================= -->

        <!-- Carga de las librerías Google Charts -->
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

        <!-- Presentación gráficos tiempo real -->
        <% if (request.getAttribute("tr_main") != null) { %>
        <script type="text/javascript">
            var oatu_data = "${oatu_data}";
            var sr_data = "${sr_data}";
            var se_data = "${se_data}";
            var fe_data = "${fe_data}";
            var tdo_data = "${tdo_data}";
            var mex_data = "${mex_data}";
        </script>
        <script type="text/javascript" src="js/tr_chart.js"></script>        

        <!-- Presentación gráficos pallets movidos -->
        <% } else if (request.getAttribute("pmop_main") != null) { %>
        <script type="text/javascript">
            var pmop_data = "${pmop_data}";
        </script>
        <script type="text/javascript" src="js/pmop_chart.js"></script>

        <!-- Presentación gráficos artículos recibidos -->
        <% } else if (request.getAttribute("arp_main") != null) { %>
        <script type="text/javascript">
            var arp_data = "${arp_data}";
        </script>
        <script type="text/javascript" src="js/arp_chart.js"></script>

        <!-- Presentación gráficos artículos expedidos -->
        <% } else if (request.getAttribute("aep_main") != null) { %>
        <script type="text/javascript">
            var aep_data = "${aep_data}";
        </script>
        <script type="text/javascript" src="js/aep_chart.js"></script>

        <!-- Presentación gráficos zonas expediciones -->
        <% } else if (request.getAttribute("zep_main") != null) { %>
        <script type="text/javascript">
            var zep_data = "${zep_data}";
        </script>
        <script type="text/javascript" src="js/zep_chart.js"></script>
        <% }%>
    </body>
</html>
