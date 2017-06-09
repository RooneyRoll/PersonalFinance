<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    $(document).ready(function () {
        $("#recurring-payment-add-form").validate({
            rules: {
                recurringPaymentAmount: {"required": true, "number": true, "min": 1},
                recurringPaymentPeriodsCount: {"required": true, "number": true, "min": 1},
                recurringPaymentName: {"required":true}
            },
            messages: {
                recurringPaymentAmount: "Моля въведете сума.",
                recurringPaymentPeriodsCount: "Моля въведете брой периоди на плащането.",
                recurringPaymentName: "Моля въведете име на плащането."
            },
            errorPlacement: function (error, element) {

            },
            highlight: function (element, errorClass, validClass) {
                $(element).addClass("error");
            },
            unhighlight: function (element, errorClass, validClass) {
                $(element).removeClass("error");
            }
        });
        $("#categories-select,#recurring-types-select").select2({"theme": "classic"});
        $('#recurringPaymentStartDate').flatpickr({
            'locale': 'bg',
            'mode': 'single',
            'enableTime': true,
            'enableTime': true,
            'defaultDate': 'today',
            'dateFormat': "Y-m-d",
            onChange: function (rawdate, altdate, FPOBJ) {
                FPOBJ.close();
            }
        });
    });
</script>
<div class="form-container">
    <c:if test="${errorMessage != null}"><tiles:insertAttribute name="recurringPaymentAddError" /></c:if>
        <div class="form-content">
            <form id="recurring-payment-add-form" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="input-container size-1">
                <div class="input-title-holder no-select">
                    <span> 
                        Име на плащане<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <input type="text" name="recurringPaymentName" placeholder="Име"/>
                </div>
            </div><div class="input-container size-1">
                <div class="input-title-holder no-select">
                    <span> 
                        Сума за период<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <input type="text" name="recurringPaymentAmount" placeholder="Сума"/>
                </div>
            </div><div class="input-container size-2 side-padding-right">
                <div class="input-title-holder no-select">
                    <span> 
                        Начален период на плащане<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <fmt:parseDate pattern="yyyy-MM-dd" value="${payment.getDate()}" var="parsedDate" />
                    <fmt:formatDate value="${parsedDate}" var="date" pattern="yyyy-MM-dd" />
                    <input type="text" readonly name="recurringPaymentPeriodStart" id="recurringPaymentStartDate" placeholder="Началена дата на повтарящо плащане" value="${date}"/>
                </div>
            </div><div class="input-container size-2">
                <div class="input-title-holder no-select">
                    <span> 
                        Брой периоди<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <input type="text" name="recurringPaymentPeriodsCount" value="1" placeholder="Брой периоди"/>
                </div>
            </div><div class="input-container size-1">
                <div class="input-title-holder no-select">
                    <span> 
                        Описание на плащане
                    </span>
                </div>
                <div class="input-holder">
                    <textarea resize="false" placeholder="Описание" name="recurringPaymentDescription"></textarea>
                </div>
            </div><div class="input-container size-1">
                <div class="input-title-holder no-select">
                    <span> 
                        Категория на плащане<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <select id="categories-select" name="recurringPaymentCategory">
                        <c:forEach items="${categories}" var="element">
                            <option value="${element.getId()}">${element.getName()}</option>
                        </c:forEach>
                    </select>
                    <spring:url var = "categoriesAdd" value='/categories/add' />
                    <div class="input-add-button">
                        <a href="${categoriesAdd}" target="_blank"><i class="fa fa-plus-circle" aria-hidden="true"></i></a>
                    </div>
                </div>
            </div><div class="input-container size-1">
                <div class="input-title-holder no-select">
                    <span> 
                        Плащането е<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <select id="recurring-types-select" name="recurringPaymentRecurringType">
                        <c:forEach items="${recTypes}" var="element">
                            <option value="${element.getId()}">${element.getName()}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="buttons-container size-1">
                <button name="submit-button" type="submit" value="1" class="button animation">Запази</button>
                <button name="submit-button" type="submit" value="2" class="button animation">Запази и редактирай</button>
                <button name="submit-button" type="submit" value="3" class="button animation">Запази и Нов</button>
                <button type="reset" class="button animation">Изчистване на форма</button>
            </div>
        </form>
    </div>
</div>