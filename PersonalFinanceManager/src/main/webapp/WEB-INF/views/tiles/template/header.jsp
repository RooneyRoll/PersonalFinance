<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="header">
    <div class="header-content">
        <div class="header-content-left">
            <!--<a href="home">-->
            <div class="header-logo">
                <img/>
            </div>
            <div class="header-text no-select">
                <i class="fa fa-credit-card-alt" aria-hidden="true"></i>
                Personal Finance Manager
                <div class="header-sub-text no-select">
                    Application for managing personal finances
                </div>
            </div>
            <!--</a>-->
        </div><div class="header-content-right ">
            <div class="header-right-buttons">
                <c:if test="${pageContext.request.userPrincipal.name == null}">
                    <a href = "register" class="header-button animation">
                        Регистрация
                    </a>
                    <a href = "login" class="header-button animation">
                        Вход
                    </a>
                </c:if>
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <a href = "logout" class="header-button animation">
                        Изход
                    </a>
                </c:if>
            </div>
        </div>
    </div>
</div>