<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    $(document).ready(function () {
        $("#register-form").validate({
            rules: {
                username: "required",
                password: "required",
                email: {
                    required: true,
                    email: true
                },
                firstname: "required",
                middlename: "required",
                lastname: "required"
            },
            messages: {
                username: "Моля, въведете потребителско име",
                password: "Моля, въведете парола",
                email: {
                    required: "Моля, въведете e-mail",
                    email: " Моля, въведете валиден e-mail"
                },
                firstname: "Моля, въведете име",
                middlename: "Моля, въведете презиме",
                lastname: "Моля, въведете фамилия"
            },
            errorPlacement: function (error, element) {

            },
            highlight: function (element, errorClass, validClass) {
                $(element).addClass("error");
            },
            unhighlight: function (element, errorClass, validClass) {
                $(element).removeClass("error");
            }
        })
    });
</script>
<c:if test="${errorMessage != null}"><tiles:insertAttribute name="registerError" /></c:if>
<form id="register-form" th:action="@{/register}" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="row">
        <div class="col-6 col-md-6">
            <div class="row">
                <div class="col-12 col-md-12">
                    <div class="panel panel-success">
                        <div class='panel-heading'>Регистрация</div>
                        <div class="panel-body">
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Потребителско име</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-user" aria-hidden="true"></i></span>
                                    <input type="text" class="form-control" name="username" placeholder="Потребителско име" id="username" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Парола</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-lock" aria-hidden="true"></i></span>
                                    <input class="form-control" type="password" name="password" placeholder="Парола" id="password" value="" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Електронна поща</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-envelope" aria-hidden="true"></i></span>
                                    <input class="form-control" type="text" name="email" placeholder="e-mail" id="email" value="" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Име</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-id-card-o" aria-hidden="true"></i></span>
                                    <input class="form-control" type="text"  name="firstname" placeholder="Име" id="firstnamr" value="" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Презиме</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-id-card-o" aria-hidden="true"></i></span>
                                    <input class="form-control" type="text" name="middlename" placeholder="Презиме" id="middlename" value="" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Фамилия</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-id-card-o" aria-hidden="true"></i></span>
                                    <input class="form-control" type="text" name="lastname" placeholder="Фамилия" id="lastname" aria-describedby="basic-addon1">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="btn-group">
        <button name="submit-button" class="btn btn-primary" type="submit">Регистрация</button>
        <button type="reset" class="btn btn-primary">Изчистване на форма</button>
    </div>
</form>