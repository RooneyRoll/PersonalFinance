<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="form-container-2">
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
            </div><input type="checkbox" name="remember-me" />
            <div class="buttons-container">
                <button type="submit" class="button animation">Вход</button>
                <button type="reset" class="button animation">Изчистване на форма</button>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</div>