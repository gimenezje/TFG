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
    var data = google.visualization.arrayToDataTable(eval(aep_data));

    // Construir el cuadro de mando
    var dashboard = new google.visualization.Dashboard(
            document.getElementById('dashboard_div'));

    // Construir y presentar un gráfico de barras 
    var barChart = new google.visualization.ChartWrapper({
        chartType: 'BarChart',
        containerId: 'chart_div',
        options: {
            width: '1074',
            height: '600',
            isStacked: false,
            title: 'Artículos expedidos',
            legend: {position: 'top'},
            hAxis: {title: 'Uds.servidas', textStyle: {fontSize: '10'}},
            vAxis: {title: 'Artículos', textStyle: {fontSize: '10'}},
            chartArea: {top: '100', left: '300', bottom: '50', width: '750'}
        },
        dataTable: data
    });
    barChart.draw();

    // Construir y presentar una tabla
    var table = new google.visualization.ChartWrapper({
        chartType: 'Table',
        containerId: 'table_div',
        options: {
            showRowNumber: true,
            width: '100%',
            height: '100%',
            sortColumn: 2,
            sortAscending: false
        },
        dataTable: data
    });
    table.draw();

    // Presentar el cuadro de mando
    dashboard.draw();
}