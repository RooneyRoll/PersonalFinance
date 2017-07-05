<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<spring:url var = "home" value='/home' />
<spring:url var = "register" value='/register' />
<spring:url var = "login" value='/login' />
<spring:url var = "categories" value='/categories' />
<spring:url var = "categoriesАdd" value='/categories/add' />
<spring:url var = "paymentAdd" value='/payments/add' />
<spring:url var = "types" value='/types' />
<spring:url var = "payments" value='/payments' />
<spring:url var = "categoryBudget" value='/userBudget' />
<spring:url var = "userBudgetStatus" value ="/userBudget/status"/>
<spring:url var = "categoryCoverage" value ="/userBudget/categoriesStatus"/>
<spring:url var = "paymentsStatus" value ="/payments/status"/>
<spring:url var = "budgetRecPayments" value ="/userBudget/recurring/add"/>
<div class="header">
    <div class="header-content">
        <div class="header-content-left">
            <a href="${home}">
                <div class="header-logo">
                    <img/>
                </div><div class="header-text no-select">
                    <i class="fa fa-credit-card-alt" aria-hidden="true"></i>
                    Personal Finance Manager
                    <div class="header-sub-text no-select">
                        Application for managing personal finances 
                    </div>

                </div>
            </a>
        </div><div class="header-content-right ">
            <div class="header-right-buttons">
                <div class="horizontal-menu" id="main-nav">
                    <input id="main-menu-state" type="checkbox" />
                    <label class="main-menu-btn" for="main-menu-state">
                        <span class="main-menu-btn-icon"></span></label><ul class="top-menu-container sm sm-mint " id="main-menu">
                        <c:if test="${pageContext.request.userPrincipal.name == null}">
                            <li><a class="animation" href="${register}">Регистрация</a></li>
                            <li><a class="animation" href="${login}">Вход</a></li>
                            </c:if>
                            <c:if test="${pageContext.request.userPrincipal.name != null}">
                            <li>
                                <a class="animation" href="${paymentAdd}">Ново плащане</a>
                            </li>
                            <li>
                                <a class="animation" href="${categoriesАdd}">Нова категория</a>
                            </li>
                            <li><a class="animation" href="${payments}">Плащания</a>
                                <ul>
                                    <li>
                                        <a class="animation" href="${categories}">Категории</a>
                                    </li>
                                </ul>
                            </li>
                            <li><a class="animation" href="${categoryBudget}">Бюджет</a><ul>
                                    <li>
                                        <a class="animation" href="${budgetRecPayments}">Повтарящи се плащания</a>
                                    </li>
                                </ul></li>
                            <li>
                                <a class="animation" href="#">Статистики</a><ul>
                                    <li>
                                        <a class="animation" href="${userBudgetStatus}">Планирано/Действително</a>
                                    </li>
                                    <li>
                                        <a class="animation" href="${categoryCoverage}">Покритие по категории</a>
                                    </li>
                                    <li>
                                        <a class="animation" href="${paymentsStatus}">Приходи към разходи</a>
                                    </li>
                                </ul>
                            </li>
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