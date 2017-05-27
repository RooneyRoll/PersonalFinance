<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="<c:url value='/resources/js/Highcharts-5.0.12/code/highcharts.js' />"></script>
<script>
    $(document).ready(function () {
        var parent = $("#paymentsMonthPicker").parent();
        var datepicker = $('#paymentsMonthPicker').datepicker({
            inline: true,
            format: "yyyy/mm",
            container: parent,
            autoPick: true,
            language: "bg-BG"
        });
    <spring:url var = "serviceUrl" value ="/getPayments"/>
        function getPaymentsStatus(month, year, chart) {
            var series = [];
            var data = JSON.stringify({"month": month, "year": year});
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
                    var serie = {name: val.paymentType, data: val.amounts}
                    series.push(serie);
                    sumTotal = 0;
                    var color = "#7CB5EC";
                    if (key !== 0)
                        color = "#E74C3C";
                });
                if (typeof (chart.series) !== 'undefined') {
                    $(series).each(function(key,serie){
                        chart.series[key].setData(serie.data, true);
                    });
                }
            });
            console.log(series);
            return series;
        }
        $('#paymentsMonthPicker').change(function () {
            var date = $(this).val();
            var inputs = date.split("/");
            var year = inputs[0];
            var month = inputs[1];
            datepicker.datepicker("update");
            var data = getPaymentsStatus(month, year, chart);
        });
        var date = $("#paymentsMonthPicker").val();
        var inputs = date.split("/");
        var year = inputs[0];
        var month = inputs[1];
        var chart = new Highcharts.chart('container', {
            chart: {
                type: 'area'
            },
            title: {
                text: 'Приходи и разходи за месец'
            },
            subtitle: {
                //text: 'Source: <a href="http://thebulletin.metapress.com/content/c4120650912x74k7/fulltext.pdf">' +
                //        'thebulletin.metapress.com</a>'
            },
            xAxis: {
                allowDecimals: false,
                labels: {
                    formatter: function () {
                        return this.value; //
                    }
                }
            },
            yAxis: {
                title: {
                    text: 'Стойност'
                },
                labels: {
                    formatter: function () {
                        return this.value; //
                    }
                }
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.y:,.0f}</b><br/> за ден {point.x}'
            },
            plotOptions: {
                area: {
                    pointStart: 1,
                    marker: {
                        enabled: false,
                        symbol: 'circle',
                        radius: 1,
                        states: {
                            hover: {
                                enabled: true
                            }
                        }
                    }
                }
            },
            series: getPaymentsStatus(month, year, this)
        });
    })
</script>
<div class="form-container">
    <c:if test="${errorMessage != null}"><tiles:insertAttribute name="categoryAddError" /></c:if>
    <div class="form-content">
        <form id="budget-manage-form" method="post">
            <div class="partial-contentainer size-2 side-padding">
                <div class="input-container size-1">
                    <div class="input-title-holder no-select">
                        <span> 
                            приходи и разходи за месец:
                        </span>
                    </div>
                    <div class="input-holder">
                        <input data-toggle="datepicker" type="hidden" name="budgetDate" id="paymentsMonthPicker">
                    </div>
                </div>
            </div><div class="partial-contentainer size-2 side-padding">
                <div class="input-container size-1">
                    <div class="input-title-holder no-select">
                        <span> 
                        </span>
                    </div>
                    <div class="input-holder">
                        <div id="container" style="min-width: 300px; height: 400px; margin: 0 auto"></div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>