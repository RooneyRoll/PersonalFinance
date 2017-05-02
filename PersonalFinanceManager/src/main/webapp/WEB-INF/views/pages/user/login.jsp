<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script>
    $(document).ready(function () {
        $('#remember-me').each(function () {
            var self = $(this),
                    label = self.next(),
                    label_text = label.text();

            label.remove();
            $("#remember-me").iCheck({
                checkboxClass: 'icheckbox_line-green checkbox-green',
                radioClass: 'iradio_line-green checkbox-green',
                insert: '<div class="icheck_line-icon"></div>' + label_text
            });
        });
    });
</script>
<div class="form-container size-2">
    <div class="form-content">
        <form id="login-form" name="f" name="loginForm" action="auth/login_check?targetUrl=${targetUrl}" method="post">
            <div class="input-container with-icon">
                <div class="input-title-holder no-select">
                    <span> 
                        Потребителско име
                    </span>
                </div>
                <div class="input-holder">
                    <div class="input-icon">
                        <i class="fa fa-user" aria-hidden="true"></i>
                    </div><input class="input-style-4" type="text" name="username" placeholder="Потребителско име" id="username"/>
                </div>
            </div>
            <div class="input-container with-icon">
                <div class="input-title-holder no-select">
                    <span> 
                        Парола
                    </span>
                </div>
                <div class="input-holder">
                    <div class="input-icon">
                        <i class="fa fa-lock" aria-hidden="true"></i>
                    </div><input type="password" name="password" placeholder="Парола" id="password"/>
                </div>
            </div>
            <div class="input-container">
                <div class="input-title-holder no-select">
                    <span> 
                    </span>
                </div>
                <div class="input-holder">
                    <input type="checkbox" id="remember-me" name="remember-me" /><label for="remember-me">Запомни ме</label>
                </div>
            </div>
            <div class="buttons-container">
                <button type="submit" class="button animation">Вход</button>
                <button type="reset" class="button animation">Изчистване на форма</button>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</div><c:if test="${errorMessage != null}"><tiles:insertAttribute name="loginError" /></c:if>