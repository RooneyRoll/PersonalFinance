<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    $(document).ready(function () {
        $("#payment-type-edit-form").validate({
            rules: {
                paymentAmount: {"required": true, "number": true, "min": 0}
            },
            messages: {
                paymentAmount: "Моля, въведете име на категория"
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
        $('#paymentDate').flatpickr({
            'locale': 'bg',
            'mode': 'single',
            'enableTime': true,
            'enableTime': true,
            'dateFormat': "Y-m-d",
            onChange: function (rawdate, altdate, FPOBJ) {
                FPOBJ.close();
            }
        });
    });
</script>
<c:if test="${errorMessage != null}">
    <tiles:insertAttribute name="categoryAddError" />
</c:if>
<form id="payment-type-edit-form" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="row">
        <div class="col-6 col-md-6">
            <div class="row">
                <div class="col-12 col-md-12">
                    <div class="panel panel-success">
                        <div class='panel-heading'>Данни за плащане</div>
                        <div class="panel-body">
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Сума</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                    <input name="paymentAmount" placeholder="Сума" type="text" class="form-control" value="${payment.getAmount()}" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Описание на плащане</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                    <textarea class="form-control"  resize="false" placeholder="Описание" name="paymentDescription" aria-describedby="basic-addon1">${payment.getDescription()}</textarea>
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
                        <div class='panel-heading'>Настройки на плащане</div>
                        <div class="panel-body">
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Дата на плащане</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-calendar" aria-hidden="true"></i></span>
                                        <fmt:parseDate pattern="yyyy-MM-dd" value="${payment.getDate()}" var="parsedDate" />
                                        <fmt:formatDate value="${parsedDate}" var="date" pattern="yyyy-MM-dd" />
                                    <input readonly class="form-control" name="paymentDate" id="paymentDate" placeholder="Дата на плащане" value="${date}" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Категорията е</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-check" aria-hidden="true"></i></span>
                                    <div class="btn-group input-group" data-toggle="buttons">
                                        <label class="btn btn-primary 
                                               <c:if test='${payment.isActive()}'>
                                                   active
                                               </c:if>">
                                            <input type="radio" value="1" name="paymentActive" id="option-active" 
                                                   <c:if test='${payment.isActive()}'>
                                                       checked="checked"
                                                   </c:if> />Активнo</label>
                                        <label class="btn btn-primary <c:if test='${payment.isActive() == false}'>active</c:if>">
                                            <input type="radio" value="2" name="paymentActive" id="option-not-active" <c:if test='${payment.isActive() == false}'>checked="checked"</c:if> />Неактивнo</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group ">
                                    <label for="exampleFormControlInput1">Категория</label>
                                    <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                        <span class="input-group-addon" id="basic-addon1"><i class="fa fa-bars" aria-hidden="true"></i></span>
                                        <select id="categories-select" name="paymentCategory">
                                        <c:forEach items="${categories}" var="element">
                                            <option <c:if test = "${element.getId() == payment.getCategory()}">selected</c:if>  value="${element.getId()}">${element.getName()}</option>
                                        </c:forEach>
                                    </select>
                                    <spring:url var = "categoriesAdd" value='/categories/add' />
                                    <div class="input-add-button">
                                        <a href="${categoriesAdd}" target="_blank"><i class="fa fa-plus-circle" aria-hidden="true"></i></a>
                                    </div>
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