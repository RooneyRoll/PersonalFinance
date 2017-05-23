<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    $(document).ready(function () {
        var parent = $("#budgetMonthPicker").parent();
        var datepicker = $('[data-toggle="datepicker"]').datepicker({"inline": true, "format": "yyyy/mm", "container": parent});
        $('[data-toggle="datepicker"]').change(function () {
            datepicker.datepicker("update");
            var date = $(this).val();
            var inputs = date.split("/");
            var year = inputs[0];
            var month = inputs[1];
            window.location = "?year="+year+"&month="+month;
        });
    });
</script>
<div class="form-container">
    <c:if test="${errorMessage != null}"><tiles:insertAttribute name="categoryAddError" /></c:if>
        <div class="form-content">
            <form id="payment-category-add-form" method="post">
                <div class="partial-contentainer size-2 side-padding">
                    <div class="input-container size-1">
                        <div class="input-title-holder no-select">
                            <span> 
                                Бюджет за период:
                            </span>
                        </div>
                        <div class="input-holder">
                            <input data-toggle="datepicker" type="hidden" value="${year}/${month}" name = "budgetMonth" id="budgetMonthPicker">
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
                                        <input type="text" name="category_${category.getId()}" placeholder="Въведете стойност" value="<c:if test="${exists}"></c:if><c:if test="${exists == false}">0</c:if>"/>
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
                <button name="submit-button" type="submit" value="2" class="button animation">Запази и редактирай</button>
                <button name="submit-button" type="submit" value="3" class="button animation">Запази и Нов</button>
                <button type="reset" class="button animation">Изчистване на форма</button>
            </div>
        </form>
    </div>
</div>