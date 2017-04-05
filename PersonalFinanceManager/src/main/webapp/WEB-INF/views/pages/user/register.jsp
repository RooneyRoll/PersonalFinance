<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="form-container">
    <div class="form-content">
        <form th:action="@{/register}" method="post">
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
                    </div><input class="input-style-4" type="text" name="UserName" placeholder="Потребителско име" id="user-name"/>
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
                    </div><input type="password" name="Password" placeholder="Парола" id="user-name"/>
                </div>
            </div>
            <div class="input-container with-icon">
                <div class="input-title-holder no-select">
                    <span> 
                        е-mail
                    </span>
                </div>
                <div class="input-holder">
                    <div class="input-icon">
                        <i class="fa fa-envelope" aria-hidden="true"></i>
                    </div><input type="text" name="Email" placeholder="e-mail" id="user-name"/>
                </div>
            </div>
            <div class="input-container with-icon">
                <div class="input-title-holder no-select">
                    <span> 
                        Име
                    </span>
                </div>
                <div class="input-holder">
                    <div class="input-icon">
                        <i class="fa fa-id-card-o" aria-hidden="true"></i>
                    </div><input type="text" name="FirstName" placeholder="Име" id="user-name"/>
                </div>
            </div>
            <div class="input-container with-icon">
                <div class="input-title-holder no-select">
                    <span> 
                        Презиме
                    </span>
                </div>
                <div class="input-holder">
                    <div class="input-icon">
                        <i class="fa fa-id-card-o" aria-hidden="true"></i>
                    </div><input type="text" name="MiddleName" placeholder="Презиме" id="user-name"/>
                </div>
            </div>
            <div class="input-container with-icon">
                <div class="input-title-holder no-select">
                    <span> 
                        Фамилия
                    </span>
                </div>
                <div class="input-holder">
                    <div class="input-icon">
                        <i class="fa fa-id-card-o" aria-hidden="true"></i>
                    </div><input type="text" name="LastName" placeholder="Фамилия" id="user-name"/>
                </div>
            </div>
            <div class="buttons-container">
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