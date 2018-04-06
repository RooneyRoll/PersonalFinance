<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
    $(document).ready(function () {
        $("#payment-add-form").validate({
            rules: {
                paymentAmount: {"required": true, "number": true, "min": 0}
            },
            messages: {
                paymentAmount: "Моля въведете сума."
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
            'enableTime': false,
            'defaultDate': 'today',
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
<form id="payment-add-form" method="post">
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
                                    <input name="paymentAmount" placeholder="Сума" type="text" class="form-control" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Описание на плащане</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                    <textarea class="form-control"  resize="false" placeholder="Описание" name="paymentDescription" aria-describedby="basic-addon1"></textarea>
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
                                    <input readonly name="paymentDate" id="paymentDate" placeholder="Дата на плащане" value="${date}" class="form-control" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Категория</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-bars" aria-hidden="true"></i></span>
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