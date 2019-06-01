/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Cargar la API de visualización y 
// el paquete de controles de Google Charts
google.charts.load('current', {packages: ['corechart'],
    language: 'es'
});

// Lanzamiento construcción y presentación del 
// gráfico de columnas de ocupación almacén por tipo ubicación
google.charts.setOnLoadCallback(grafico1);

// Lanzamiento construcción y presentación del 
// gráfico de tarta de situación recepciones
google.charts.setOnLoadCallback(grafico2);

// Lanzamiento construcción y presentación del 
// gráfico de tarta de situación expediciones
google.charts.setOnLoadCallback(grafico3);

// Lanzamiento construcción y presentación de los 
// gráficos de calibres de expediciones por muelle
google.charts.setOnLoadCallback(grafico4);

// Lanzamiento construcción y presentación del 
// gráfico de barras de faltas expediciones
google.charts.setOnLoadCallback(grafico5);

// Lanzamiento construcción y presentación del 
// gráfico de barras de top 10 de operarios
google.charts.setOnLoadCallback(grafico6);

// Construcción y presentación del gráfico de columnas 
// de ocupación almacén por tipo ubicación            
function grafico1() {

    // Construir la tabla de datos    
    var oatuData = google.visualization.arrayToDataTable(eval(oatu_data));

    // Construir y presentar el gráfico de columnas
    var oatuColumnChart = new google.visualization.ChartWrapper({
        chartType: 'ColumnChart',
        containerId: 'oatu_chart_div',
        options: {
            width: '370',
            height: '300',
            title: 'Ocupación almacén por tipo ubic.',
            legend: {position: 'top'},
            isStacked: 'percent',
            hAxis: {title: 'Tipos ubicaciones', textStyle: {fontSize: '8'}},
            vAxis: {title: 'Ocupación', textStyle: {fontSize: '8'}},
            chartArea: {left: '75', width: '250'}
        },
        dataTable: oatuData
    });
    oatuColumnChart.draw();
}

// Construcción y presentación del gráfico de tarta 
// de situación recepciones          
function grafico2() {

    // Construir la tabla de datos     
    var srData = google.visualization.arrayToDataTable(eval(sr_data));

    // Construir y presentar el gráfico de tarta
    var srPieChart = new google.visualization.ChartWrapper({
        chartType: 'PieChart',
        containerId: 'sr_chart_div',
        options: {
            width: '363',
            height: '300',
            pieSliceText: 'percentage',
            legend: {position: 'top', maxLines: '2'},
            pieSliceTextStyle: {fontSize: '10'},
            title: 'Situación recepciones',
            is3D: true
        },
        dataTable: srData
    });
    srPieChart.draw();
}

// Construcción y presentación del gráfico de tarta 
// de situación expediciones            
function grafico3() {

    // Construir la tabla de datos      
    var seData = google.visualization.arrayToDataTable(eval(se_data));

    // Construir y presentar el gráfico de tarta
    var sePieChart = new google.visualization.ChartWrapper({
        chartType: 'PieChart',
        containerId: 'se_chart_div',
        options: {
            width: '359',
            height: '300',
            pieSliceText: 'percentage',
            legend: {position: 'top', maxLines: '2'},
            pieSliceTextStyle: {fontSize: '10'},
            title: 'Situación expediciones',
            is3D: true,
            chartArea: {top: '77'}
        },
        dataTable: seData
    });
    sePieChart.draw();
}

// Construcción y presentación de los gráficos de calibres 
// de expediciones por muelle           
function grafico4() {

    // Construir la tabla de datos    
    var mexData = google.visualization.arrayToDataTable(eval(mex_data));

    // Construir y presentar los gráficos de calibres
    var mexGauge = new google.visualization.ChartWrapper({
        chartType: 'Gauge',
        containerId: 'mex_chart_div',
        options: {
            width: '1175',
            height: '100',
            min: 0,
            max: 30000,
            minorTicks: 10
        },
        dataTable: mexData
    });
    mexGauge.draw();
}

// Construcción y presentación del gráfico de barras 
// de faltas expediciones            
function grafico5() {

    // Construir la tabla de datos    
    var feData = google.visualization.arrayToDataTable(eval(fe_data));

    // Construir y presentar del gráficos de barras
    var feBarChart = new google.visualization.ChartWrapper({
        chartType: 'BarChart',
        containerId: 'fe_chart_div',
        options: {
            width: '1100',
            height: '500',
            isStacked: false,
            title: 'Faltas expediciones',
            legend: {position: 'top'},
            hAxis: {title: 'Unidades', textStyle: {fontSize: '10'}},
            vAxis: {title: 'Artículos', textStyle: {fontSize: '10'}},
            chartArea: {top: '75', left: '300', bottom: '50'}
        },
        dataTable: feData
    });
    feBarChart.draw();
}

// Construcción y presentación del gráfico de barras 
// de top 10 de operarios            
function grafico6() {

    // Construir la tabla de datos     
    var tdoData = google.visualization.arrayToDataTable(eval(tdo_data));

    // Construir y presentar del gráficos de barras
    var tdoBarChart = new google.visualization.ChartWrapper({
        chartType: 'BarChart',
        containerId: 'tdo_chart_div',
        options: {
            width: '1100',
            height: '400',
            isStacked: true,
            title: 'Top 10 operarios',
            legend: {position: 'top'},
            hAxis: {title: 'Pallets', textStyle: {fontSize: '10'}},
            vAxis: {title: 'Operarios', textStyle: {fontSize: '10'}},
            chartArea: {top: '75', left: '300', bottom: '50', width: '750'}
        },
        dataTable: tdoData
    });
    tdoBarChart.draw();
}