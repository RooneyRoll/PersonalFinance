<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="<c:url value='/resources/js/Highcharts-5.0.12/code/highcharts.js' />"></script>
<script>
    $(document).ready(function () {
        var sumTotal = 0;
        function getInitialSumOfInputVals(chart) {
            var sum = [];
            $(".budget-inputs").each(function (key, val) {
                var panelType = key;
                var value = 0;
                var name = $(val).find(".panel-heading").text();
                var inputs = $(val).find("input");
                $(inputs).each(function (k, v) {
                    if ($(v).val() !== "") {
                        value = value + parseInt($(v).val());
                    } else {
                        value = value + 0;
                    }
                });
                var color;
                if (panelType === 0)
                    color = "#43AC6A";
                if (panelType === 1)
                    color = "#E99002";
                if (panelType === 2)
                    color = "#008CBA";
                sum.push({y: value, color: color, name: name});
            });
            sumTotal = parseInt(sum[0].y) - parseInt(sum[1].y) - parseInt(sum[2].y);
            if (typeof (chart.series) !== 'undefined') {
                chart.series[0].setData(sum, true);
                chart.setTitle(null, {text: 'Крайно: ' + sumTotal});
            }
            return sum;
        }
        function getBudgetData(month, year, chart) {
            var data = JSON.stringify({"month": month, "year": year});
            $.ajax({
                type: "POST",
                url: "budget",
                dataType: "json",
                data: data,
                async: false,
                contentType: 'application/json',
                beforeSend: function (xhr, settings) {
                    var token = $('meta[name="_csrf"]').attr('content');
                    xhr.setRequestHeader('X-CSRF-TOKEN', token);
                }
            }).done(function (data) {
                if (data.length > 0) {
                    $(data).each(function (key, val) {
                        var id = val.categoryId;
                        var amount = val.amount;
                        var input = $("[name='category_" + id + "']");
                        input.val(amount);
                    });
                    $("input[type='text']").each(function (key, value) {
                        var val = $(value).val();
                        if (val === "") {
                            $(this).val(0);
                        }
                    });
                } else {
                    $("input[type='text']").each(function (key, value) {
                        $(value).val(0);
                    });
                }
                getInitialSumOfInputVals(chart);
            });
        }
        $('.tabs-container').pwstabs({
            effect: 'scale',
            defaultTab: 1,
            containerWidth: '100%',
            tabsPosition: 'horizontal',
            horizontalPosition: 'top',
            verticalPosition: 'left',
            responsive: true,
            theme: 'pws_theme_green',
            rtl: false,
            onBeforeFirstInit: function () {},
            onAfterFirstInit: function () {
                var parent = $("#budgetMonthPicker").parent();
                var parent1 = $("#copyBudgetMonthPicker").parent();
                var copyDatepicker = $('#copyBudgetMonthPicker').datepicker({
                    inline: true,
                    format: "yyyy/mm",
                    container: parent1,
                    autoPick: true,
                    language: "bg-BG"
                });
                var datepicker = $('#budgetMonthPicker').datepicker({
                    inline: true,
                    format: "yyyy/mm",
                    container: parent,
                    autoPick: true,
                    language: "bg-BG"
                });
                datepicker.datepicker("update");
                $("#budget-manage-form").validate({
                    errorPlacement: function (error, element) {

                    },
                    highlight: function (element, errorClass, validClass) {
                        $(element).addClass("error");
                    },
                    unhighlight: function (element, errorClass, validClass) {
                        $(element).removeClass("error");
                    }
                });

                $(".category-input").each(function (key, val) {
                    var input = $(val);
                    input.rules("add", {
                        required: true,
                        number: true,
                        min: 0,
                        messages: {
                            number: true
                        }
                    });
                });
                var chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'container',
                        type: 'column',
                        events: {
                            load: function (event) {
                                var date = $("#budgetMonthPicker").val();
                                var inputs = date.split("/");
                                var year = inputs[0];
                                var month = inputs[1];
                                var data = getBudgetData(month, year, this);

                            }
                        }
                    },
                    title: {
                        text: 'Бюджет'
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
                            data: getInitialSumOfInputVals(this),
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

                $(".panel input").each(function () {
                    $(this).keyup(function () {
                        getInitialSumOfInputVals(chart);
                    });
                });

                $('#budgetMonthPicker').change(function () {
                    var date = $(this).val();
                    var inputs = date.split("/");
                    var year = inputs[0];
                    var month = inputs[1];
                    getBudgetData(month, year, chart);
                    datepicker.datepicker("update");
                });

                $('#copyBudgetMonthPicker').change(function () {
                    var date = $(this).val();
                    var inputs = date.split("/");
                    var year = inputs[0];
                    var month = inputs[1];
                    getBudgetData(month, year, chart);
                    copyDatepicker.datepicker("update");
                });
            },
            onBeforeInit: function () {},
            onAfterInit: function () {},
            onBeforeChange: function () {},
            onAfterChange: function () {}
        });
    });
</script>
<form id="budget-manage-form" method="post">
    <div class="row">
        <div class="col-4 col-md-4">
            <div class="row">
                <div class="col-12 col-md-12">
                    <div class="panel panel-info">
                        <div class="panel-body">
                            <div class="tabs-container">
                                <div data-pws-tab="anynameyouwant1" data-pws-tab-name="<i class='fa fa-calendar' aria-hidden='true'></i>&nbsp  Бюджет за месец">
                                    <div class="input-container size-1">
                                        <div class="input-title-holder no-select">
                                            <span> 
                                                Бюджет за месец
                                            </span>
                                        </div>
                                        <div class="input-holder">
                                            <input data-toggle="datepicker" type="hidden" name="budgetDate" id="budgetMonthPicker">
                                        </div>
                                    </div>
                                </div><div data-pws-tab="anynameyouwant2" data-pws-tab-name="<i class='fa fa-files-o' aria-hidden='true'></i>&nbsp Копиране от месец">
                                    <div class="input-container size-1">
                                        <div class="input-title-holder no-select">
                                            <span> 
                                                Копиране от месец
                                            </span>
                                        </div>
                                        <div class="input-holder">
                                            <input data-toggle="datepicker" type="hidden" name="budgetCopyDate" id="copyBudgetMonthPicker">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-4 col-md-4">
            <div class="row">
                <div class="col-12 col-md-12">
                    <div class="panel panel-primary">
                        <div class='panel-heading'>
                            Бюджет
                        </div>
                        <div class="panel-body">
                            <div id="container" style="min-width: 300px; height: 400px; margin: 0 auto"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-4 col-md-4">
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
                                            <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                <span class="input-group-addon" id="basic-addon1">${icon}</span>
                                                <input name="category_${category.getId()}" type="text" class="form-control" placeholder="Въведете стойност" aria-describedby="basic-addon1">
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
    <div class="btn-group">
        <button type="submit" class="btn btn-primary">Запазване</button>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>