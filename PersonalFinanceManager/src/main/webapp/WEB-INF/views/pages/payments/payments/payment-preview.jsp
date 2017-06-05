<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
    $(document).ready(function () {
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
        $("#categories-select").select2({"theme": "classic", disabled: true, minimumResultsForSearch: Infinity});
    });
</script>
<div class="form-container">
    <c:if test="${errorMessage != null}"><tiles:insertAttribute name="categoryAddError" /></c:if>
        <div class="form-content">
            <input type="hidden" readonly name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="input-container size-1">
            <div class="input-title-holder no-select">
                <span> 
                    Сума<span class="required-tip">&nbsp;*</span>
                </span>
            </div>
            <div class="input-holder">
                <input type="text" readonly name="paymentAmount" placeholder="сума" id="password" value="${payment.getAmount()}"/>
            </div>
        </div><div class="input-container size-2 side-padding-right">
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
        </div><div class="input-container size-2 side-padding-left">
            <div class="input-title-holder no-select">
                <span> 
                    Активност
                </span>
            </div>
            <div class="input-holder">
                <input  disabled readonly type="checkbox" <c:if test="${payment.isActive()}">checked="checked"</c:if>
                        name="paymentActive"/><label
                        class="visible">Плащането е активно</label>
                </div>
            </div><div class="input-container size-1">
                <div class="input-title-holder no-select">
                    <span> 
                        Описание на плащане
                    </span>
                </div>
                <div class="input-holder">
                    <textarea resize="false" readonly placeholder="Описание" name="paymentDescription">${payment.getDescription()}</textarea>
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
        <spring:url var = "paymentEdit" value='/payments/edit/${payment.getId()}' />
        <div class="buttons-container size-1">
            <a href = "${paymentEdit}" name="submit-button" type="submit" value="1" class="button animation">Редакция</a>
        </div>
    </div>
</div>