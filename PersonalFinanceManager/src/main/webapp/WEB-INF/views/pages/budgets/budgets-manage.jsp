<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="<c:url value='/resources/js/Highcharts-5.0.12/code/highcharts.js' />"></script>
<script>
    $(document).ready(function () {
        function getSumOfInputVals(Selector){
        var sum = 0;    
            $(Selector+" input").each(function(key,val){
                var value = $(val).val();
                console.log(value);
                if(value !== ""){
                    sum = sum + parseInt(value);
                }else{
                  sum = sum + 0;  
                }
            });
            return sum;
        }
        function getInitialSumOfInputVals(){
            var sum = [];
            $(".input-group").each(function(key,val){
                var value = 0;
                var name = $(val).find(".input-group-title-holder span").text();
                var inputs = $(val).find("input");
                $(input).each(function(k,v){
                    value = value + parseInt($(v).val());
                });
                sum.push([name,value]);
            });
            return sum;
        }
        function getBudgetData(month, year) {
            var JsonResult;
            var data = JSON.stringify({"month": month, "year": year});
            $.ajax({
                type: "POST",
                url: "budget",
                dataType: "json",
                data: data,
                async:false,
                contentType: 'application/json',
                beforeSend: function (xhr, settings) {
                    var token = $('meta[name="_csrf"]').attr('content');
                    xhr.setRequestHeader('X-CSRF-TOKEN', token);
                }
            }).done(function (data) {
                JsonResult = data;
                if (data.length > 0) {
                    $(data).each(function (key, val) {
                        var id = val.categoryId;
                        var amount = val.amount;
                        var input = $(".category-input[name='category_" + id + "']");
                        input.val(amount);
                    });
                    $(".category-input").each(function (key, value) {
                        var val = $(value).val();
                        if (val == "") {
                            $(this).val(0);
                        }
                    });
                } else {
                    $(".category-input").each(function (key, value) {
                        $(value).val(0);
                    });
                }
                var array = getInitialSumOfInputVals();
                chart.series[0].setData(array, true);
            });
            return JsonResult;
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
                        messages: {
                            number: true
                        }
                    });
                });

                $('#budgetMonthPicker').change(function () {
                    var date = $(this).val();
                    var inputs = date.split("/");
                    var year = inputs[0];
                    var month = inputs[1];
                    getBudgetData(month, year);
                    datepicker.datepicker("update");
                });
                
                $('#copyBudgetMonthPicker').change(function () {
                    var date = $(this).val();
                    var inputs = date.split("/");
                    var year = inputs[0];
                    var month = inputs[1];
                    getBudgetData(month, year);
                    copyDatepicker.datepicker("update");
                });
                var paymentTypes = [];
                var chart = Highcharts.chart('container', {
                    chart: {
                        type: 'column'
                    },
                    title: {
                        text: 'Сравнение (Категории на плащания)'
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
                    legend: {
                        enabled: false
                    },
                    tooltip: {
                        pointFormat: 'Population in 2008: <b>{point.y:.1f} millions</b>'
                    },
                    series: [{
                            name: 'Population',
                            data: paymentTypes,
                            dataLabels: {
                                enabled: true,
                                rotation: 0,
                                color: '#FFFFFF',
                                align: 'right',
                                format: '{point.y:.1f}', // one decimal
                                y: 10, // 10 pixels down from the top
                                style: {
                                    fontSize: '14px',
                                    fontFamily: 'Roboto,Arial, sans-serif'
                                }
                            }
                        }]
                });
                var selectors = [];
                <c:forEach items="${paymentTypes}" var="type">
                    selectors.push({selector:"#group_${type.getId()}",name:"${type.getName()}"});
                </c:forEach>
                <c:forEach items="${paymentTypes}" var="type">
                    $("#group_${type.getId()} input").each(function(key,val){
                        $(val).keyup(function(){
                            paymentTypes = [];
                            $(selectors).each(function(key,val){
                               paymentTypes.push([val.name,parseInt(getSumOfInputVals(val.selector))]);
                            });
                            chart.series[0].setData(paymentTypes, true);
                        });
                    });
                </c:forEach>
                    
                var date = $("#budgetMonthPicker").val();
                var inputs = date.split("/");
                var year = inputs[0];
                var month = inputs[1];
                var jsonData = getBudgetData(month, year);
               
                
            },
            onBeforeInit: function () {},
            onAfterInit: function () {},
            onBeforeChange: function () {},
            onAfterChange: function () {}
        });


    });
</script>
<div class="form-container">
    <c:if test="${errorMessage != null}"><tiles:insertAttribute name="categoryAddError" /></c:if>
        <div class="form-content">
            <form id="budget-manage-form" method="post">
                <div class="partial-contentainer size-2 side-padding">
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
                    <div class="input-container size-1">
                        <div class="input-title-holder no-select">
                            <span> 
                            </span>
                        </div>
                        <div class="input-holder">
                            <div id="container" style="min-width: 300px; height: 400px; margin: 0 auto"></div>
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
                        <c:forEach items="${categories}" var="category"><c:if test="${type.getId() == category.getType()}"><div class="input-container size-2 side-padding">
                                    <div class="input-title-holder no-select">
                                        <span>${category.getName()}:</span>
                                    </div>
                                    <div class="input-holder">
                                        <input type="text" name="category_${category.getId()}" class="category-input" placeholder="Въведете стойност"/>
                                    </div>
                                </div></c:if></c:forEach>
                            </div>
                </c:forEach></div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="buttons-container size-1 side-padding">
                <button name="submit-button" type="submit" value="1" class="button animation">Запази</button>
            </div>
        </form>
    </div>
</div>