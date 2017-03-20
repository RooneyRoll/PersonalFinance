<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="form-container-2">
    <div class="form-content">
        <form name="f" th:action="@{/login}" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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
            <div class="buttons-container">
                <button type="submit" class="button animation">Вход</button>
                <button type="reset" class="button animation">Изчистване на форма</button>
            </div>
        </form>
    </div>
</div>