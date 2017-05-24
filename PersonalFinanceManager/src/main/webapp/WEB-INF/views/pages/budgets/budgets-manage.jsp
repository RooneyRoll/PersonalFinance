<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    $(document).ready(function () {
        var parent = $("#budgetMonthPicker").parent();
        var parent1 = $("#copyBudgetMonthPicker").parent();
        var copyDatepicker = $('#copyBudgetMonthPicker').datepicker({
            "inline": true,
            "format": "yyyy/mm",
            "container": parent1,
            "autoPick": true,
            "language": "bg-BG"
        });
        var datepicker = $('#budgetMonthPicker').datepicker({
            "inline": true,
            "format": "yyyy/mm",
            "container": parent,
            "autoPick": true,
            "language": "bg-BG"
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
        function getBudgetData(month, year) {
            var data = JSON.stringify({"month": month, "year": year});
            $.ajax({
                type: "POST",
                url: "budget",
                dataType: "json",
                data: data,
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
            });
        }

        $('#budgetMonthPicker').change(function () {
            var date = $(this).val();
            var inputs = date.split("/");
            var year = inputs[0];
            var month = inputs[1];
            getBudgetData(month, year);
            datepicker.datepicker("update");
        });
        var date = $("#budgetMonthPicker").val();
        var inputs = date.split("/");
        var year = inputs[0];
        var month = inputs[1];
        getBudgetData(month,year);

    });
</script>
<div class="form-container">
    <c:if test="${errorMessage != null}"><tiles:insertAttribute name="categoryAddError" /></c:if>
        <div class="form-content">
            <form id="budget-manage-form" method="post">
                <div class="partial-contentainer size-2 side-padding">
                    <div class="input-container size-2">
                        <div class="input-title-holder no-select">
                            <span> 
                                Бюджет за месец:
                            </span>
                        </div>
                        <div class="input-holder">
                            <input data-toggle="datepicker" type="hidden" name="budgetDate" id="budgetMonthPicker">
                        </div>
                    </div><div class="input-container size-2">
                        <div class="input-title-holder no-select">
                            <span> 
                                Копиране от месец:
                            </span>
                        </div>
                        <div class="input-holder">
                            <input data-toggle="datepicker" type="hidden" name="budgetCopyDate" id="copyBudgetMonthPicker">
                        </div>
                    </div>
                </div><div class="partial-contentainer size-2 side-padding">
                <c:forEach items="${paymentTypes}" var="type">
                    <div class = "input-group">
                        <div class="input-group-title-holder">
                            <span> 
                                ${type.getName()}
                            </span>
                        </div>
                        <c:forEach items="${categories}" var="category">
                            <c:if test="${type.getId() == category.getType()}">
                                <div class="input-container size-1">
                                    <div class="input-title-holder no-select">
                                        <span> 
                                            ${category.getName()}:
                                        </span>
                                    </div>
                                    <div class="input-holder">
                                        <input type="text" name="category_${category.getId()}" class="category-input" placeholder="Въведете стойност"/>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </c:forEach>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="buttons-container size-1 side-padding">
                <button name="submit-button" type="submit" value="1" class="button animation">Запази</button>
            </div>
        </form>
    </div>
</div>