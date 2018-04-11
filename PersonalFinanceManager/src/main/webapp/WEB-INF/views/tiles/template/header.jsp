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
        <div class="header-content-left ">
            <div class="header-left-buttons">
                <div class="header-button">
                    <i class="fa fa-user fa-1x" aria-hidden="true"></i>
                </div>
                <div class="header-button">
                    <i class="fa fa-comments fa-1x" aria-hidden="true"></i>
                </div>
                <div class="header-button toggle-button">
                    <i class="fa fa-bars fa-1x" aria-hidden="true"></i>
                </div>
                <a class="animation" href="javascript:formSubmit()">
                    <div class="header-button">
                        <i class="fa fa-sign-out fa-1x" aria-hidden="true"></i>
                    </div>
                </a>
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
        </div><div class="header-content-right">
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
        </div>
    </div>
</div>
<div class="navigation-title">
    <span class="navigation-title-text no-select">
        <tiles:getAsString name="navigation" />
    </span>
</div>