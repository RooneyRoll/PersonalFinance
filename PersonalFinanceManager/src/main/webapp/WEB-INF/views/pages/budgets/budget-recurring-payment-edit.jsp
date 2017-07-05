<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
    $(document).ready(function () {
        $("#recurring-payment-edit-form").validate({
            rules: {
                recurringPaymentAmount: {"required": true, "number": true, "min": 1},
                recurringPaymentPeriodsCount: {"required": true, "number": true, "min": 1},
                recurringPaymentName: {"required": true},
                recurringPaymentPeriodsMiss:{"required":true,"number":true}
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
        $('#paymentDate').flatpickr({
            'locale': 'bg',
            'mode': 'single',
            'enableTime': true,
            'defaultDate':'today', 
            'dateFormat': "Y-m-d",
            onChange: function (rawdate, altdate, FPOBJ) {
                FPOBJ.close();
            }
        });
    });
</script>
<div class="form-container">
    <c:if test="${errorMessage != null}"><tiles:insertAttribute name="categoryAddError" /></c:if>
        <div class="form-content">
            <form id="recurring-payment-edit-form" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="input-container size-1">
                <div class="input-title-holder no-select">
                    <span> 
                        Име на плащане<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <input type="text" name="recurringPaymentName" placeholder="Име" value = "${recPayment.getName()}"/>
                </div>
            </div><div class="input-container size-2 side-padding-right">
                <div class="input-title-holder no-select">
                    <span> 
                        Сума за период<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <input type="text" name="recurringPaymentAmount" placeholder="Сума" value = "${recPayment.getAmount()}"/>
                </div>
            </div><div class="input-container size-2 side-padding-left">
                <div class="input-title-holder no-select">
                    <span> 
                        Пропускане през брой периоди<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <input type="text" name="recurringPaymentPeriodsMiss" value="0" placeholder="Пропускане през" value = "${recPayment.getMissPerPeriods()}"/>
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
                    <input type="text" readonly name="recurringPaymentPeriodStart" id="recurringPaymentStartDate" placeholder="Начална дата на повтарящо плащане" value="${recPayment.getStartDate()}"/>
                </div>
            </div><div class="input-container size-2 side-padding-left">
                <div class="input-title-holder no-select">
                    <span> 
                        Брой периоди<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <input type="text" name="recurringPaymentPeriodsCount" value="1" placeholder="Брой периоди" value = "${recPayment.getPeriods()}"/>
                </div>
            </div>
            <div class="input-container size-1">
                <div class="input-title-holder no-select">
                    <span> 
                        Описание на плащане
                    </span>
                </div>
                <div class="input-holder">
                    <textarea resize="false" placeholder="Описание" name="recurringPaymentDescription" value = "${recPayment.getDescription()}"></textarea>
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
                            <option <c:if test = "${element.getId() == recPayment.getPamyntCategoryId()}">selected</c:if> value="${element.getId()}">${element.getName()}</option>
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
                            <option <c:if test = "${element.getId() == recPayment.getRecurringType()}">selected</c:if> value="${element.getId()}">${element.getName()}</option>
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