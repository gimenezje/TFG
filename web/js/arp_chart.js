/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Cargar la API de visualización y 
// el paquete de controles de Google Charts
google.charts.load('current', {packages: ['corechart', 'table', 'controls'],
    language: 'es'
});

// Lanzamiento construcción y 
// presentación del cuadro de mando
google.charts.setOnLoadCallback(cuadroMando);

// Construcción y presentación 
// del cuadro de mando            
function cuadroMando() {

    // Construir la tabla de datos    
    var data = google.visualization.arrayToDataTable(eval(arp_data));

    // Construir una vista de datos a partir de la tabla,
    // agregando un nuevo campo con rol de anotación
    // para dibujar una línea vertical resaltando a
    // cada artículo del eje x del gráfico de líneas
    var view = new google.visualization.DataView(data);
    view.setColumns([0, {calc: space, type: 'string', role: 'annotation'},
        1, 2]);

    // Construir el cuadro de mando
    var dashboard = new google.visualization.Dashboard(
            document.getElementById('dashboard_div'));

    // Construir y presentar un gráfico de líneas
    var lineChart = new google.visualization.ChartWrapper({
        chartType: 'LineChart',
        containerId: 'chart_div',
        options: {
            width: '1074',
            height: '600',
            isStacked: false,
            title: 'Artículos recibidos',
            legend: {position: 'top'},
            vAxis: {title: 'Uds.recibidas', textStyle: {fontSize: '10'}},
            hAxis: {title: 'Artículo', textStyle: {fontSize: '10'}},
            chartArea: {left: '150', bottom: '150', width: '900'},
            annotations: {style: 'line'}
        },
        dataTable: view
    });
    lineChart.draw();

    // Construir y presentar una tabla
    var table = new google.visualization.ChartWrapper({
        chartType: 'Table',
        containerId: 'table_div',
        view: {columns: [0, 2, 3]},
        options: {
            showRowNumber: true,
            width: '100%',
            height: '100%',
            sortColumn: 2,
            sortAscending: false
        },
        dataTable: view
    });
    table.draw();

    // Presentar el cuadro de mando
    dashboard.draw();
}

// Retorno de un espacio en blanco
function space() {
    return ' ';
}