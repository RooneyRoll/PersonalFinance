<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    $(document).ready(function () {
        $("#login-form").validate({
            rules: {
                username: "required",
                password: "required"
            },
            messages: {
                username: "Моля, въведете потребителско име",
                password: "Моля, въведете парола"
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
    });
</script>

<div class="panel panel-success">
    <div class="panel-heading">Вход в системата</div>
    <div class="panel-body">
        <form id="login-form" name="f" name="loginForm" action="auth/login_check?targetUrl=${targetUrl}" method="post">
            <div class="form-group ">
                <label for="exampleFormControlInput1">Потребителско име</label>
                <div class="input-group col-lg-6 col-md-12 col-sm-12">
                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-user" aria-hidden="true"></i></span>
                    <input name="username" type="text" class="form-control" placeholder="Потребителско име" aria-describedby="basic-addon1">
                </div>
            </div>
            <div class="form-group  ">
                <label for="exampleFormControlInput1">Парола</label>
                <div class="input-group col-lg-6 col-md-12 col-sm-12">
                    <span class="input-group-addon" id="basic-addon1"> <i class="fa fa-lock" aria-hidden="true"></i></span>
                    <input type="password" name="password" class="form-control" placeholder="Парола" aria-describedby="basic-addon1">
                </div>
            </div>
            <div class="form-check">
                <label class="form-check-label">
                    <input class="form-check-input" type="checkbox" id="remember-me" name="remember-me" />
                    Запомни ме
                </label>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </div>
            <div class="btn-group">
                <button type="submit" class="btn btn-success">Вход</button>
                <button type="reset" class="btn btn-success">Изчистване на форма</button>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</div>