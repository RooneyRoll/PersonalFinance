<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    $(document).ready(function () {
        $("#payment-type-edit-form").validate({
            rules: {
                paymentAmount: {"required":true,"number":true}
            },
            messages: {
                paymentAmount: "Моля, въведете име на категория",
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
        $('.site-content .form-container input[type="checkbox"],.site-content .form-container input[type="radio"]').each(function (key, val) {
            var self = $(this)
            var label = self.next();
            var label_text = label.text();
            var visible = "label-check-not-visible";
            if (label.hasClass("visible"))
                visible = "label-check-visible";

            label.remove();
            $(this).on("ifChanged", function (event) {
                $(this).change();
            });
            var check = self.iCheck({
                checkboxClass: 'icheckbox_line-grey visibility-check',
                radioClass: 'iradio_line-grey visibility-check ' + visible,
                insert: '<div class="icheck_line-icon"></div>' + "<div class='label-text'>" + label_text + "</div>"
            });
        });
        $("#categories-select").select2({"theme": "classic"});
    });
</script>
<div class="form-container">
    <c:if test="${errorMessage != null}"><tiles:insertAttribute name="categoryAddError" /></c:if>
        <div class="form-content">
            <form id="payment-type-edit-form" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="input-container size-1">
                <div class="input-title-holder no-select">
                    <span> 
                        Сума<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <input type="text" name="paymentAmount" placeholder="сума" id="password" value="${payment.getAmount()}"/>
                </div>
            </div><div class="input-container size-2 side-padding-right">
                <div class="input-title-holder no-select">
                    <span> 
                        Дата на плащане<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <input type="text" readonly disabled name="paymentDate" placeholder="Дата на плащане" id="password" value="${payment.getDate()}"/>
                </div>
            </div><div class="input-container size-2 side-padding-left">
                <div class="input-title-holder no-select">
                    <span> 
                        Активност
                    </span>
                </div>
                <div class="input-holder">
                    <input  type="radio" <c:if test="${payment.isActive()}">checked="checked"</c:if>
                            name="paymentActive" value="1"/><label
                        class="visible">Плащането е активно</label><input type="radio" <c:if test="${payment.isActive() == false}">checked="checked"</c:if>
                           name="paymentActive" value="2"/><label>
                        Плащането не е активно</label>
                </div>
            </div><div class="input-container size-1">
                <div class="input-title-holder no-select">
                    <span> 
                        Описание на плащане
                    </span>
                </div>
                <div class="input-holder">
                    <textarea resize="false" placeholder="Описание" name="paymentDescription">${payment.getDescription()}</textarea>
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
                              <option <c:if test = "${element.getId() == payment.getCategory()}">selected</c:if>  value="${element.getId()}">${element.getName()}</option>
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