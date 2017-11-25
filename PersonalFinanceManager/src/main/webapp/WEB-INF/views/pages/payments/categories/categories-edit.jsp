<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    $(document).ready(function () {
        $("#payment-category-edit-form").validate({
            rules: {
                categoryName: "required",
            },
            messages: {
                categoryName: "Моля, въведете име на категория",
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
                insert: '<div class="icheck_line-icon"></div>' + "<div class='label-text'>" + label_text + "</div>",
            });
        });
        $("#types-select").select2({"theme": "classic"});
    });
</script>
<c:if test="${errorMessage != null}"><tiles:insertAttribute name="categoryAddError" />
</c:if>
<form id="payment-category-edit-form" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="row">
        <div class="col-6 col-md-6">
            <div class="row">
                <div class="col-12 col-md-12">
                    <div class="panel panel-success">
                        <div class='panel-heading'>Данни за категория</div>
                        <div class="panel-body">
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Име на Категория</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                    <input name="categoryName" type="text" class="form-control" placeholder="Име на плащане" value="${category.getName()}" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Описание на категория</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                    <textarea class="form-control"  resize="false" placeholder="Описание" name="categoryDescription" aria-describedby="basic-addon1">${category.getDescription()}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6 col-md-6">
            <div class="row">
                <div class="col-12 col-md-12">
                    <div class="panel panel-info">
                        <div class='panel-heading'>Настройки на категория</div>
                        <div class="panel-body">
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Категорията е</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-check" aria-hidden="true"></i></span>
                                    <div class="btn-group input-group" data-toggle="buttons">
                                        <label class="btn btn-primary 
                                               <c:if test='${category.isActive()}'>
                                                   active
                                               </c:if>">
                                            <input type="radio" value="1" name="categoryActive" id="option-active" 
                                                   <c:if test='${category.isActive()}'>
                                                       checked="checked"
                                                   </c:if> />Активна</label>
                                        <label class="btn btn-primary <c:if test='${category.isActive() == false}'>active</c:if>">
                                            <input type="radio" value="2" name="categoryActive" id="option-not-active" <c:if test='${category.isActive() == false}'>checked="checked"</c:if> />Неактивна</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group ">
                                    <label for="exampleFormControlInput1">Тип на категория</label>
                                    <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                        <span class="input-group-addon" id="basic-addon1">
                                            <i class="fa fa-bars" aria-hidden="true">

                                            </i>
                                        </span>
                                        <select id="types-select" name="categoryType">
                                        <c:forEach items="${types}" var="element">
                                            <option <c:if test = "${element.getId() == category.getType()}">
                                                    selected
                                                </c:if> 
                                                value="${element.getId()}">
                                                ${element.getName()}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="btn-group">
        <button name="submit-button" class="btn btn-primary" type="submit" value="1">Запази</button>
        <button name="submit-button" class="btn btn-primary" type="submit" value="2">Запази и редактирай</button>
        <button name="submit-button" class="btn btn-primary" type="submit" value="3">Запази и Нов</button>
        <button type="reset" class="btn btn-primary">Изчистване на форма</button>
    </div>
</form>