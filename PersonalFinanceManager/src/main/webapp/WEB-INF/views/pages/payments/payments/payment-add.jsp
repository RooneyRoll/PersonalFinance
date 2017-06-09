<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
    $(document).ready(function () {
        $("#payment-add-form").validate({
            rules: {
                paymentAmount: {"required": true, "number": true,"min":0}
            },
            messages: {
                paymentAmount: "Моля въведете сума.",
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
        $("#categories-select").select2({"theme": "classic"});
        $('#paymentDate').flatpickr({
            'locale': 'bg',
            'mode': 'single',
            'enableTime': true,
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
            <form id="payment-add-form" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="input-container size-1">
                <div class="input-title-holder no-select">
                    <span> 
                        Сума<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <input type="text" name="paymentAmount" placeholder="Сума"/>
                </div>
            </div><div class="input-container size-1">
                <div class="input-title-holder no-select">
                    <span> 
                        Описание на плащане
                    </span>
                </div>
                <div class="input-holder">
                    <textarea resize="false" placeholder="Описание" name="paymentDescription"></textarea>
                </div>
            </div><div class="input-container size-1 side-padding-right">
                <div class="input-title-holder no-select">
                    <span> 
                        Дата на плащане<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <fmt:parseDate pattern="yyyy-MM-dd" value="${payment.getDate()}" var="parsedDate" />
                    <fmt:formatDate value="${parsedDate}" var="date" pattern="yyyy-MM-dd" />
                    <input type="text" readonly name="paymentDate" id="paymentDate" placeholder="Дата на плащане" value="${date}"/>
                </div>
            </div><div class="input-container size-1">
                <div class="input-title-holder no-select">
                    <span> 
                        Категория на плащане<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <select id="categories-select" name="paymentCategory">
                        <c:forEach items="${categories}" var="element">
                            <option value="${element.getId()}">${element.getName()}</option>
                        </c:forEach>
                    </select>
                    <spring:url var = "categoriesAdd" value='/categories/add' />
                    <div class="input-add-button">
                        <a href="${categoriesAdd}" target="_blank"><i class="fa fa-plus-circle" aria-hidden="true"></i></a>
                    </div>
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