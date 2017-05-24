<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="header">
    <div class="header-content">
        <div class="header-content-left">
            <!--<a href="home">-->
            <div class="header-logo">
                <img/>
            </div><div class="header-text no-select">
                <i class="fa fa-credit-card-alt" aria-hidden="true"></i>
                Personal Finance Manager
                <div class="header-sub-text no-select">
                    Application for managing personal finances 
                </div>
            </div>
            <!--</a>-->
        </div><div class="header-content-right ">
            <div class="header-right-buttons">
                <div class="horizontal-menu" id="main-nav">
                    <input id="main-menu-state" type="checkbox" />
                    <label class="main-menu-btn" for="main-menu-state">
                        <spring:url var = "register" value='/register' />
                        <spring:url var = "login" value='/login' />
                        <spring:url var = "categories" value='/categories' />
                        <spring:url var = "categories-add" value='/categories/add' />
                        <spring:url var = "types" value='/types' />
                        <spring:url var = "payments" value='/payments' />
                        <spring:url var = "categoryBudget" value='/userBudget' />
                        <span class="main-menu-btn-icon"></span></label><ul class="top-menu-container sm sm-mint " id="main-menu">
                        <c:if test="${pageContext.request.userPrincipal.name == null}">
                            <li><a class="animation" href="${register}">Регистрация</a></li>
                            <li><a class="animation" href="${login}">Вход</a></li>
                            </c:if>
                            <c:if test="${pageContext.request.userPrincipal.name != null}">
                            <li><a class="animation" href="${payments}">Плащания</a>
                                <ul>
                                    <li>
                                        <a class="animation" href="${categories}">Категории</a>
                                    </li>
                                </ul>
                            </li>
                            <li><a class="animation" href="${categoryBudget}">Бюджет</a></li>
                            <li><a class="animation" href="javascript:formSubmit()">Изход</a></li>
                            </c:if>
                    </ul>
                </div>
            </div>
            <div style="display:none;font-size:0">
                <spring:url var = "logout" value='/logout' />
                <form action="${logout}" method="post" id="logoutForm">
                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}" />
                </form>
                <script>
                    function formSubmit() {
                        document.getElementById("logoutForm").submit();
                    }
                </script>
            </div>
        </div>
    </div>
</div>
<div class="navigation-title">
    <span class="navigation-title-text no-select">
        <tiles:getAsString name="navigation" />
    </span>
</div>