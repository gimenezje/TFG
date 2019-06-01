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
    var data = google.visualization.arrayToDataTable(eval(pmop_data));

    // Construir el cuadro de mando
    var dashboard = new google.visualization.Dashboard(
            document.getElementById('dashboard_div'));

    // Construir un filtro tipo drop down list para el año
    var dropDownList1 = new google.visualization.ControlWrapper({
        controlType: 'CategoryFilter',
        containerId: 'filter1_div',
        state: {selectedValues: ['2018']},
        options: {
            filterColumnLabel: 'Año',
            ui: {
                allowTyping: false,
                allowMultiple: false,
                allowNone: false,
                selectedValuesLayout: 'belowStacked'
            }
        }
    });

    // Construir un filtro tipo drop down list para el mes
    var dropDownList2 = new google.visualization.ControlWrapper({
        controlType: 'CategoryFilter',
        containerId: 'filter2_div',
        state: {selectedValues: ['12']},
        options: {
            filterColumnLabel: 'Mes',
            ui: {
                allowTyping: false,
                allowMultiple: false,
                allowNone: false,
                selectedValuesLayout: 'belowStacked'
            }
        }
    });

    // Construir un filtro tipo range slider para los pallets movidos
    var rangeSlider = new google.visualization.ControlWrapper({
        controlType: 'NumberRangeFilter',
        containerId: 'filter3_div',
        options: {
            filterColumnLabel: 'Pallets movidos'
        }
    });

    // Construir un gráfico de tarta 
    var pieChart = new google.visualization.ChartWrapper({
        chartType: 'PieChart',
        containerId: 'chart_div',
        view: {columns: [0, 3]},
        options: {
            width: '1074',
            height: '540',
            pieSliceText: 'value',
            legend: {position: 'right', textStyle: {fontSize: '10'}},
            pieSliceTextStyle: {fontSize: '10'},
            title: 'Pallets movidos por operarios',
            is3D: true
        }
    });

    // Construir una tabla
    var table = new google.visualization.ChartWrapper({
        chartType: 'Table',
        containerId: 'table_div',
        options: {
            showRowNumber: true,
            width: '100%',
            height: '100%',
            sortColumn: 3,
            sortAscending: false
        }
    });

    // Establecer las dependencias entre los filtros,
    // el gráfico de tarta y la tabla
    dashboard.bind([dropDownList1, dropDownList2], rangeSlider)
            .bind([dropDownList1, dropDownList2, rangeSlider], pieChart)
            .bind([dropDownList1, dropDownList2, rangeSlider], table);

    // Presentar el cuadro de mando
    dashboard.draw(data);
}