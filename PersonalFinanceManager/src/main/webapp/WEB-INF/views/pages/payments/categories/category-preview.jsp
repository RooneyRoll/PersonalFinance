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
                insert: '<div class="icheck_line-icon"></div>' + "<div class='label-text'>" + label_text + "</div>",
            });
        });
        $("#types-select").select2({"theme": "classic",disabled:true});
    });
</script>
<div class="form-container">
    <c:if test="${errorMessage != null}"><tiles:insertAttribute name="categoryAddError" /></c:if>
        <div class="form-content">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="input-container size-2 side-padding-right">
            <div class="input-title-holder no-select">
                <span> 
                    Име на категория<span class="required-tip">&nbsp;*</span>
                </span>
            </div>
            <div class="input-holder">
                <input readonly type="text" name="categoryName" placeholder="Име" id="password" value="${category.getName()}"/>
            </div>
        </div><div class="input-container size-2 side-padding-left">
            <div class="input-title-holder no-select">
                <span> 
                    Активност
                </span>
            </div>
            <div class="input-holder">
                <input  disabled readonly type="radio" <c:if test="${category.isActive()}">checked="checked"</c:if>
                        name="categoryActive" value="1"/><label
                        class="visible">Категорията е активна</label>
                </div>
            </div><div class="input-container size-1">
                <div class="input-title-holder no-select">
                    <span> 
                        Описание на категория
                    </span>
                </div>
                <div class="input-holder">
                    <textarea readonly resize="false" placeholder="Описание" name="categoryDescription">${category.getDescription()}</textarea>
            </div>
        </div><div class="input-container size-1">
            <div class="input-title-holder no-select">
                <span> 
                    Тип на Категория<span class="required-tip">&nbsp;*</span>
                </span>
            </div>
            <div class="input-holder">
                <select id="types-select" name="categoryType">
                    <c:forEach items="${types}" var="element">
                        <option <c:if test = "${element.getId() == category.getType()}">selected</c:if> value="${element.getId()}">${element.getName()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <spring:url var = "categoryEdit" value='/categories/edit/${category.getId()}' />
        <div class="buttons-container size-1">
            <a href = "${categoryEdit}" name="submit-button" type="submit" value="1" class="button animation">Редакция</a>
        </div>
    </div>
</div>