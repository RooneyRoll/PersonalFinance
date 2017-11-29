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
        function generateAmountsArray(amounts) {
            var result = [];
            
            $.each(amounts, function (date, amount) {
                var amountArr = [];
                var dateNumber = parseInt(date);
                var dateObj = new Date(dateNumber);
                var date_utc = Date.UTC(dateObj.getFullYear(),dateObj.getMonth(),dateObj.getDate());
                console.log(date_utc);
                console.log(parseInt(date));
                console.log("----")
                amountArr.push(date_utc);
                amountArr.push(amount);
                result.push(amountArr);
            });
            return result;
        }
        function determineColor(paymentTypeId) {
            color = "";
            switch (paymentTypeId) {
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
            return color;
        }
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
                    var seriesData = generateAmountsArray(val.amounts);
                    var color = determineColor(val.paymentTypeId);
                    var name = val.paymentType;
                    var dashStyle = "Solid";
                    opacity = 0.3;
                    var serie = {
                        name: name,
                        pointInterval: 24 * 3600 * 1000,
                        data: seriesData,
                        fillOpacity: opacity,
                        color: color,
                        dashStyle: dashStyle
                    };
                    series.push(serie);
                    sumTotal = 0;
                });
                if (typeof (chart.series) !== 'undefined') {
                    $(series).each(function (key, serie) {
                        chart.series[key].setData(serie.data, true);
                    });
                }
            });
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
            scaleOverride: true,
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
                type: "datetime",
                dateTimeLabelFormats: {
                    millisecond: '%H:%M:%S.%L',
                    second: '%H:%M:%S',
                    minute: '%H:%M',
                    hour: '%H:%M',
                    day: '%e. %b',
                    week: '%e. %b',
                    month: '%b \'%y',
                    year: '%Y'
                },
                labels: {
                    formatter: function () {
                        return Highcharts.dateFormat('%d %b', this.value);
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
                pointFormat: '{series.name}: <b>{point.y:,.0f}</b><br/> за ден {point.x:%e. %b}',
                split: true
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
<div class="row">
    <div class="col-12 col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-7 col-md-7">
                        <span class="heading-centered">Приходи спрямо разходи</span>
                    </div>
                    <div class="col-5 col-md-5">
                        <div class="btn-group no-padding col-12 col-md-12" data-toggle="buttons">
                            <label class="btn btn-default col-2 col-md-2">
                                <input name="options" id="option1" checked="" type="radio"> Месец
                            </label>
                            <label class="btn btn-default col-3 col-md-3 active">
                                <input name="options" id="option2" type="radio"> Седмично
                            </label>
                            <label class="btn btn-default col-2 col-md-2">
                                <input name="options" id="option3" type="radio"> 24ч
                            </label>
                            <div class="form-group col-5 col-md-5 no-padding no-margin">
                                <div class="input-group">
                                    <input class="form-control" placeholder="От" name="start" type="text">
                                    <span class="input-group-addon"><i class="fa fa-calendar-check-o" aria-hidden="true"></i>
                                    </span>
                                    <input class="form-control" placeholder="До" name="end" type="text">
                                </div>
                            </div>
                        </div>


                    </div>
                </div>
            </div>
            <div class="panel-body">
                <div id="container" style="min-width: 300px; height: 400px; margin: 0 auto"></div>
            </div>
            <div class="panel-heading">
                <div class="btn-group">
                    <a href = "${add}"><button type="submit" class="btn btn-primary">Ново плащане</button></a>
                </div>
            </div>
        </div>
    </div>
</div>






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