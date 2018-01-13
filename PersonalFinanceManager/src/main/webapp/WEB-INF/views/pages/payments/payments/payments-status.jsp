<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<spring:url var = "serviceUrl" value ="/getPayments"/>
<script src="<c:url value='/resources/js/Highcharts-5.0.12/code/highcharts.js' />"></script>
<script>
    $(document).ready(function () {
        function updateSeries(chart, series) {
            if (typeof (chart.series) !== 'undefined') {
                $(series).each(function (key, serie) {
                    chart.series[key].update(serie);
                });
            }
        }

        function generateAmountsArray(amounts) {
            var result = [];
            $.each(amounts, function (date, amount) {
                var amountArr = [];
                var dateNumber = parseInt(date);
                var dateObj = new Date(dateNumber);
                var date_utc = Date.UTC(dateObj.getFullYear(), dateObj.getMonth(), dateObj.getDate());
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
        
        function setCalendars(from, to) {
            var type = $(".btn-group label.active").attr("data-type");
            switch (type) {
                case "month":
                    from.setDate(moment().startOf('month').format('YYYY-MM-DD'));
                    to.setDate(moment().endOf('month').format('YYYY-MM-DD'));
                    break;
                case "week":
                    from.setDate(moment().subtract(6, 'days').format("YYYY-MM-DD"));
                    to.setDate(moment().format('YYYY-MM-DD'));
                    break;
                case "day":
                    from.setDate(moment().format('YYYY-MM-DD'));
                    to.setDate(moment().format('YYYY-MM-DD'));
                    break;
            }
        }
        
        var calendarFrom = $('#date-from').flatpickr({
            'locale': 'bg',
            'mode': 'single',
            'enableTime': false,
            'dateFormat': "Y-m-d",
            onChange: function (rawdate, altdate, FPOBJ) {
                FPOBJ.close();
            }
        });
        
        var calendarTo = $('#date-to').flatpickr({
            'locale': 'bg',
            'mode': 'single',
            'enableTime': false,
            'dateFormat': "Y-m-d",
            onChange: function (rawdate, altdate, FPOBJ) {
                FPOBJ.close();
            }
        });
        
        setCalendars(calendarFrom, calendarTo);

        function getSeries() {
            var series = [];
            var from = $("#date-from").val();
            var to = $("#date-to").val();
            var data = JSON.stringify({"from": from, "to": to});
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
                        dashStyle: dashStyle,
                        animation:1000
                    };
                    series.push(serie);
                    sumTotal = 0;
                });
            });
            return series;
        }
        var chart = new Highcharts.chart('container', {
            scaleOverride: true,
            chart: {
                type: 'area',
                zoomType: 'x'
            },
            title: {
                text: 'Приходи и разходи за месец'
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
            series: getSeries()
        });
        $("#filter-button").click(function () {
            var series = getSeries();
            updateSeries(chart, series);
        });
        $("label").change(function () {
            setCalendars(calendarFrom, calendarTo);
        });
    });
</script>
<div class="row">
    <div class="col-12 col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-5 col-md-5">
                        <span class="heading-centered">Приходи спрямо разходи</span>
                    </div>
                    <div class="col-7 col-md-7">
                        <div class="btn-group no-padding col-12 col-md-12" data-toggle="buttons">
                            <label class="btn btn-default col-2 col-md-2 " data-type="month">
                                <input name="options" id="option-month" checked="" type="radio"> Месец
                            </label>
                            <label class="btn btn-default col-2 col-md-2 active" data-type="week">
                                <input name="options" id="option-week" type="radio"> Седмично
                            </label>
                            <label class="btn btn-default col-2 col-md-2" data-type="day">
                                <input name="options" id="option-day" type="radio"> 24ч
                            </label>
                            <div class="form-group col-4 col-md-4 no-padding no-margin">
                                <div class="input-group">
                                    <input class="form-control" id ="date-from" placeholder="От" name="start" type="text">
                                    <span class="input-group-addon"><i class="fa fa-calendar-check-o" aria-hidden="true"></i>
                                    </span>
                                    <input class="form-control" id ="date-to" placeholder="До" name="end" type="text">
                                </div>
                            </div>
                            <button type="button" id="filter-button" class="btn btn-success col-2 col-md-2">
                                <i class="fa fa-check" aria-hidden="true"></i> Филтриране
                            </button>
                        </div>

                    </div>
                </div>
            </div>
            <div class="panel-body">
                <div id="container" style="min-width: 300px; height: 400px; margin: 0 auto"></div>
            </div>
        </div>
    </div>
</div>