<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    $(document).ready(function () {
        $("#payment-category-add-form").validate({
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
        $("#types-select").select2({"theme": "classic"});
    });
</script>
<c:if test="${errorMessage != null}"><tiles:insertAttribute name="categoryAddError" /></c:if>
    <form id="payment-category-add-form" method="post">
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
                                    <input name="categoryName" type="text" class="form-control" placeholder="Име на категория" aria-describedby="basic-addon1">
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
                                <label for="exampleFormControlInput1">Описание на категория</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                    <textarea class="form-control"  resize="false" placeholder="Описание" name="categoryDescription" aria-describedby="basic-addon1"></textarea>
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Тип на категория</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-bars" aria-hidden="true"></i></span>
                                    <select id="types-select" name="categoryType">
                                        <c:forEach items="${types}" var="element">
                                            <option value="${element.getId()}">${element.getName()}</option>
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