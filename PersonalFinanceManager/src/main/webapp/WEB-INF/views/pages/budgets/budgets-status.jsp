<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<spring:url var = "serviceUrl" value ="/budget/plannedVsSpent"/>
<script src="<c:url value='/resources/js/Highcharts-5.0.12/code/highcharts.js' />"></script>
<script>
    $(document).ready(function () {
        var parent = $("#budgetMonthPicker").parent();
        var datepicker = $('#budgetMonthPicker').datepicker({
            inline: true,
            format: "yyyy/mm",
            container: parent,
            autoPick: true,
            language: "bg-BG"
        });

        function updateSeries(chart, seriesResult) {
            var subTitleText = seriesResult.subTitle;
            var series = seriesResult.series;
            if (typeof (chart.series) !== 'undefined') {
                chart.series[0].setData(series, true);
                chart.setTitle(null, {text: subTitleText});
            }
        }

        function getSeries() {
            var date = $("#budgetMonthPicker").val();
            var inputs = date.split("/");
            var year = inputs[0];
            var month = inputs[1];
            var data = JSON.stringify({"month": month, "year": year});
            var series = [];
            var subTitle = "";
            var result = {};
            $.ajax({
                type: "POST",
                url: "${serviceUrl}",
                dataType: "json",
                data: data,
                async: false,
                contentType: 'application/json',
                beforeSend: function (xhr, settings) {
                    var token = $('meta[name="_csrf"]').attr('content');
                    xhr.setRequestHeader('X-CSRF-TOKEN', token);
                }
            }).done(function (data) {
                $(data).each(function (key, val) {
                    var sumTotal = parseInt(val.planned) - (val.actual);
                    var color;
                    switch (val.paymentType) {
                        case 1:
                            color = "#43AC6A";
                            break;
                        case 2:
                            color = "#E99002";
                            break;
                        case 3:
                            color = "#008CBA";
                            break;
                    }
                    series.push({y: val.planned, color: color, name: "Планирани " + val.paymentTypeName});
                    series.push({y: val.actual, color: color, name: "Действителни " + val.paymentTypeName});
                    subTitle += "Оставащи " + val.paymentTypeName + " до план: " + sumTotal + "<br>";
                });
            });
            result.series = series;
            result.subTitle = subTitle;
            return result;
        }

        var series = getSeries().series;
        var subTitle = getSeries().subTitle;
        var chart = new Highcharts.Chart({
            chart: {
                renderTo: 'container',
                type: 'column'
            },
            title: {
                text: 'Планирани и действителни приходи/разходи'
            },
            subtitle: {
                text: subTitle
            },
            xAxis: {
                type: 'category',
                labels: {
                    style: {
                        fontSize: '14px',
                        fontFamily: 'Roboto,Aral, sans-serif'
                    }
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Стойност'
                }
            },
            tooltip: {
                pointFormat: '<b>{point.y:.1f}</b>'
            },
            legend: {
                enabled: false
            },
            series: [{
                    animation: false,
                    name: "",
                    data: series,
                    dataLabels: {
                        enabled: true,
                        rotation: 0,
                        color: '#FFFFFF',
                        align: 'center',
                        y: 35,
                        style: {
                            fontSize: '14px',
                            fontFamily: 'Roboto,Arial, sans-serif'
                        }
                    }
                }]
        });
        $('#budgetMonthPicker').change(function () {
            datepicker.datepicker("update");
            var data = getSeries();
            updateSeries(chart, data);
        });
    });
</script>
<div class="row">
    <div class="col-8 col-md-8">
        <div class="row">
            <div class="col-12 col-md-12">
                <div class="panel panel-primary">
                    <div class='panel-heading'>
                        Данни
                    </div>
                    <div class="panel-body">
                        <div id="container" style="min-width: 300px; height: 700px; margin: 0 auto"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-4 col-md-4">
        <div class="row">
            <div class="col-12 col-md-12">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        Бюджет за месец
                    </div>
                    <div class="panel-body">
                        <input data-toggle="datepicker" type="hidden" name="budgetDate" id="budgetMonthPicker">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>