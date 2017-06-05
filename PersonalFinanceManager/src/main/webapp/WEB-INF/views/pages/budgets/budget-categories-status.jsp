<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.pfm.enums.PaymentTypes" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="<c:url value='/resources/js/Highcharts-5.0.12/code/highcharts.js' />"></script>
<script>
    $(document).ready(function () {
        var income = "${PaymentTypes.Income.getValue()}";
        var spendings = "${PaymentTypes.Spendings.getValue()}";
        function getBudgetCategoryStatus(month, year, chart) {
            var data = JSON.stringify({"month": month, "year": year});
            var result = [];
            var planned = [];
            var actual = [];
            var names = [];
    <spring:url var = "serviceUrl" value ="/budget/plannedVsSpentCategories"/>
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
                    names.push(val.categoryName);
                    actual.push(val.actual);
                    planned.push(val.planned);
                    var type = val.catType;
                    var color = "#7CB5EC";
                    if (type == spendings) {
                        color = "#E74C3C";
                    }
                    var percent = val.percents;
                    var catId = val.categoryId;
                    var widthPercent = 0;
                    if (percent > 100) {
                        widthPercent = 100;
                    } else {
                        widthPercent = percent;
                    }
                    percent = parseFloat(widthPercent.toFixed(2));
                    $("#percent_" + catId).css("background-color", color).animate({
                        width: widthPercent + '%'
                    });
                    $("#percent_" + catId).text(percent + "%");
                });
                if (typeof (chart.series) !== 'undefined') {
                    chart.series[0].setData(planned, true);
                    chart.series[1].setData(actual, true);
                }
            });
            result.push(planned);
            result.push(actual);
            result.push(names);
            return result;
        }
        var parent = $("#budgetMonthPicker").parent();
        var datepicker = $('#budgetMonthPicker').datepicker({
            inline: true,
            format: "yyyy/mm",
            container: parent,
            autoPick: true,
            language: "bg-BG"
        });
        $('#budgetMonthPicker').change(function () {
            var date = $(this).val();
            var inputs = date.split("/");
            var year = inputs[0];
            var month = inputs[1];
            datepicker.datepicker("update");
            var data = getBudgetCategoryStatus(month, year, chart);
        });
        var date = $("#budgetMonthPicker").val();
        var inputs = date.split("/");
        var year = inputs[0];
        var month = inputs[1];
        var data = getBudgetCategoryStatus(month, year, this);
        var chart = Highcharts.chart('container', {
            chart: {
                type: 'bar'
            },
            title: {
                text: 'Покритие по категорики за бюджетен план'
            },
            subtitle: {
                //text: 'Source: <a href="https://en.wikipedia.org/wiki/World_population">Wikipedia.org</a>'
            },
            xAxis: {
                categories: data[2],
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
                    name: 'Планирано',
                    data: data[0],
                    color:"#7CB5EC"
                }, {
                    name: 'Действително',
                    data: data[1],
                    color:"#434348"
                }]
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
                                Планирано/изхарчено за месец
                            </span>
                        </div>
                        <div class="input-holder">
                            <input data-toggle="datepicker" type="hidden" name="budgetDate" id="budgetMonthPicker">
                        </div>
                    </div>
                    <div class="input-container size-1">
                        <div class="input-title-holder no-select">
                            <span> 
                            </span>
                        </div>
                        <div class="input-holder">
                            <div id="container" style="min-width: 300px; margin: 0 auto"></div>
                        </div>
                    </div>
                </div><div class="partial-contentainer size-2 side-padding">
                <c:forEach items="${paymentTypes}" var="type">
                    <div class = "input-group" id='group_${type.getId()}'>
                        <div class="input-group-title-holder">
                            <span> 
                                ${type.getName()}
                            </span>
                        </div>
                        <c:forEach items="${categories}" var="category"><c:if test="${type.getId() == category.getType()}"><div class="input-container size-1 side-padding">
                                    <div class="input-title-holder no-select">
                                        <span>${category.getName()}:</span>
                                    </div>
                                    <div class="input-holder">
                                        <div class="percentage" id="percent_${category.getId()}">&nbsp;</div>
                                    </div>
                                </div></c:if></c:forEach>
                            </div>
                </c:forEach>
            </div>
        </form>
    </div>
</div>