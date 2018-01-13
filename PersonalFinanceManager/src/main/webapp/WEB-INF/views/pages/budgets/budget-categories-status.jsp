<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.pfm.enums.PaymentTypes" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<spring:url var = "serviceUrl" value ="/budget/plannedVsSpentCategories"/>
<script src="<c:url value='/resources/js/Highcharts-5.0.12/code/highcharts.js' />"></script>
<script>
    $(document).ready(function () {
        var income = "${PaymentTypes.Income.getValue()}";
        var spendings = "${PaymentTypes.Spendings.getValue()}";
        var savings = "${PaymentTypes.Savings.getValue()}";
        var parent = $("#budgetMonthPicker").parent();
        var datepicker = $('#budgetMonthPicker').datepicker({
            inline: true,
            format: "yyyy/mm",
            container: parent,
            autoPick: true,
            language: "bg-BG"
        });

        function updateSeries(chart, series) {
            if (typeof (chart.series) !== 'undefined') {
                chart.series[0].setData(series[0], true);
                chart.series[1].setData(series[1], true);
                var date = $("#budgetMonthPicker").val();
                chart.setTitle(null, {text: 'За ' + date});
            }
        }
        function updateCategoryField(fieldInfo) {
            var percent = fieldInfo.percents;
            var catId = fieldInfo.categoryId;
            var widthPercent = 0;
            if (percent > 100) {
                widthPercent = 100;
            } else {
                widthPercent = percent;
            }
            percent = parseFloat(widthPercent.toFixed(2));
            $("#percent_" + catId).animate({
                width: widthPercent + '%'
            });
            $("#percent_" + catId).text(percent + "%");
        }

        function getSeries() {
            var series = [];
            var date = $("#budgetMonthPicker").val();
            var inputs = date.split("/");
            var year = inputs[0];
            var month = inputs[1];
            var data = JSON.stringify({"month": month, "year": year});
            var planned = [];
            var actual = [];
            var names = [];
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
                    updateCategoryField(val);
                    names.push(val.categoryName);
                    actual.push(val.actual);
                    planned.push(val.planned);
                });
                series.push(planned);
                series.push(actual);
                series.push(names);
            });
            return series;
        }

        var series = getSeries();
        var initSubtitle = 'За ' + $("#budgetMonthPicker").val();
        var chart = Highcharts.chart('container', {
            chart: {
                type: 'bar'
            },
            title: {
                text: 'Покритие по категории за бюджетен план'
            },
            subtitle: {
                text: initSubtitle
            },
            xAxis: {
                categories: series[2],
                title: {
                    text: null
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Сума',
                    align: 'high'
                },
                labels: {
                    overflow: 'justify'
                }
            },
            tooltip: {
                valueSuffix: '',
                shared: true
            },
            plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: true
                    }
                }
            },
            legend: {
                enabled: true
            },
            credits: {
                enabled: false
            },
            series: [{
                    name: 'Бюджет',
                    data: series[0],
                    color: "#7CB5EC"
                }, {
                    name: 'Действително',
                    data: series[1],
                    color: "#434348"
                }]
        });

        $('#budgetMonthPicker').change(function () {
            datepicker.datepicker("update");
            var series = getSeries();
            updateSeries(chart, series);
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
        <c:forEach items="${paymentTypes}" var="type">
            <c:if test="${type.getId() == 1}">
                <spring:url var = "panelType" value='success' />
                <spring:url var = "icon" value='<i class="fa fa-plus" aria-hidden="true"></i>' />
            </c:if>
            <c:if test="${type.getId() == 2}">
                <spring:url var = "panelType" value='warning' />
                <spring:url var = "icon" value='<i class="fa fa-minus" aria-hidden="true"></i>' />
            </c:if>
            <c:if test="${type.getId() == 3}">
                <spring:url var = "panelType" value='primary' />
                <spring:url var = "icon" value='<i class="fa fa-suitcase" aria-hidden="true"></i>' />
            </c:if>
            <div class="row">
                <div class="col-12 col-md-12">
                    <div class="panel panel-${panelType} budget-inputs">
                        <div class='panel-heading'>${type.getName()}</div>
                        <div class="panel-body">
                            <c:forEach items="${categories}" var="category">
                                <c:if test="${type.getId() == category.getType()}">
                                    <div class="form-group ">
                                        <label for="exampleFormControlInput1">${category.getName()}:</label>
                                        <div class="progress">
                                            <div class="progress-bar progress-bar-striped progress-bar-${panelType} bg-danger" id="percent_${category.getId()}" role="progressbar" style="width: 100%" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>