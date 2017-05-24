<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    $(document).ready(function () {
        $("#payment-type-add-form").validate({
            rules: {
                paymentAmount: {"required":true,"number":true}
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
    });
</script>
<div class="form-container">
    <c:if test="${errorMessage != null}"><tiles:insertAttribute name="categoryAddError" /></c:if>
        <div class="form-content">
            <form id="payment-type-add-form" method="post">
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