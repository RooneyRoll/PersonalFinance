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
<div class="form-container">
    <c:if test="${errorMessage != null}"><tiles:insertAttribute name="registerError" /></c:if>
        <div class="form-content">
            <form id="register-form" th:action="@{/register}" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="input-container with-icon size-2 side-padding-right">
                <div class="input-title-holder no-select">
                    <span> 
                        Потребителско име<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <div class="input-icon">
                        <i class="fa fa-user" aria-hidden="true"></i>
                    </div><input class="input-style-4" type="text" name="username" placeholder="Потребителско име" id="username"/>
                </div>
            </div><div class="input-container with-icon size-2 side-padding-left">
                <div class="input-title-holder no-select">
                    <span> 
                        Парола<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <div class="input-icon">
                        <i class="fa fa-lock" aria-hidden="true"></i>
                    </div><input type="password" name="password" placeholder="Парола" id="password"/>
                </div>
            </div><div class="input-container with-icon size-2 side-padding-right">
                <div class="input-title-holder no-select">
                    <span> 
                        е-mail<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <div class="input-icon">
                        <i class="fa fa-envelope" aria-hidden="true"></i>
                    </div><input type="text" name="email" placeholder="e-mail" id="email"/>
                </div>
            </div><div class="input-container with-icon size-2 side-padding-left">
                <div class="input-title-holder no-select">
                    <span> 
                        Име<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <div class="input-icon">
                        <i class="fa fa-id-card-o" aria-hidden="true"></i>
                    </div><input type="text" name="firstname" placeholder="Име" id="firstnamr"/>
                </div>
            </div><div class="input-container with-icon size-2 side-padding-right">
                <div class="input-title-holder no-select">
                    <span> 
                        Презиме<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <div class="input-icon">
                        <i class="fa fa-id-card-o" aria-hidden="true"></i>
                    </div><input type="text" name="middlename" placeholder="Презиме" id="middlename"/>
                </div>
            </div><div class="input-container with-icon size-2 side-padding-left">
                <div class="input-title-holder no-select">
                    <span> 
                        Фамилия<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <div class="input-icon">
                        <i class="fa fa-id-card-o" aria-hidden="true"></i>
                    </div><input type="text" name="lastname" placeholder="Фамилия" id="lastname"/>
                </div>
            </div>
            <div class="buttons-container size-2">
                <button type="submit" class="button animation">
                    Регисиация
                </button>
                <button type="reset" class="button animation">
                    Изчисване на форма
                </button>
            </div>
        </form>
    </div>
</div>