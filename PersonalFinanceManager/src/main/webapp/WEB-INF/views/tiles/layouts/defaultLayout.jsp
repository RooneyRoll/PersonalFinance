<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<spring:url var = "budgetRecPaymentsAdd" value ="/recurringPayments/add"/>
<spring:url var = "budgetRecPayments" value ="/recurringPayments"/>
<spring:url var = "upcoming" value ="/getUpcoming"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <title><tiles:getAsString name="title" /></title>
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet"> </link>
        <link href="<c:url value='/resources/css/font-awesome-4.7.0/css/font-awesome.css'/>" rel="stylesheet"  type="text/css" />
        <link href="<c:url value='/resources/js/DataTables/datatables.min.css'/>" rel="stylesheet" ></link>
        <link href="<c:url value='/resources/js/flatpickr/flatpickr.min.css' />" rel="stylesheet" type="text/css"></link>
        <link href="<c:url value='/resources/js/flatpickr/themes/material_green.css' />" rel="stylesheet" type="text/css"></link>
        <link href="<c:url value='/resources/js/select2/css/select2.min.css' />" rel="stylesheet" ></link> 
        <link href="<c:url value='/resources/js/pwsTabs/jquery.pwstabs.min.css' />" rel="stylesheet" ></link> 
        <link href="<c:url value='/resources/js/datePicker/datepicker.min.css' />" rel="stylesheet" type="text/css"></link> 
        <link href="<c:url value='/resources/css/gridCustomCss/grid.css' />" rel="stylesheet" ></link> 
        <link href='<c:url value='/resources/js/smartmenus/css/sm-core-css.css' />' rel='stylesheet' type='text/css' />
        <link href='<c:url value='/resources/js/smartmenus/css/sm-mint/sm-mint.css' />' rel='stylesheet' type='text/css' />
        <link href='<c:url value='/resources/css/footer/footer-distributed-with-address-and-phones.css' />' rel='stylesheet' type='text/css' />
        <link href='<c:url value='/resources/js/smartwizard/css/smart_wizard.min.css' />' rel='stylesheet' type='text/css' />
        <link href='<c:url value='/resources/js/smartwizard/css/smart_wizard_theme_circles.min.css' />' rel='stylesheet' type='text/css' />
        <link href='<c:url value='/resources/js/bootstrap-year-calendar-1.1.0/css/bootstrap-year-calendar.min.css' />' rel='stylesheet' type='text/css' />
        <link href='<c:url value='/resources/js/slideout/slideout.css' />' rel='stylesheet' type='text/css' />
        <link href='<c:url value='/resources/js/sidebarMenu/sidebar-menu.css' />' rel='stylesheet' type='text/css' />
        <link href="<c:url value='/resources/css/bootstrap-3.3.7/bootstrapThemes/yeti/bootstrap.min.css'/>" rel="stylesheet" ></link>
        <link href="<c:url value='/resources/css/site.css' />" rel="stylesheet"></link>
        <script src="<c:url value='/resources/js/jquery/jquery.js' />"></script>
        <script src="<c:url value='/resources/js/smartwizard/js/jquery.smartWizard.min.js' />"></script>
        <script src="<c:url value='/resources/js/smartmenus/jquery.smartmenus.min.js' />" type="text/javascript"></script>
        <script src="<c:url value='/resources/js/jquery/jquery.validate.min.js' />"></script>
        <script src="<c:url value='/resources/js/DataTables/datatables.min.js' />"></script>
        <script src="<c:url value='/resources/js/select2/js/select2.min.js' />"></script>
        <script src="<c:url value='/resources/js/flatpickr/flatpickr.js' />"></script>
        <script src="<c:url value='/resources/js/flatpickr/l10n/bg.js' />"></script>
        <script src="<c:url value='/resources/js/datePicker/datepicker.min.js' />"></script>
        <script src="<c:url value='/resources/js/datePicker/i18n/datepicker.bg-BG.js' />"></script>
        <script src="<c:url value='/resources/js/moment/moment.min.js' />"></script>
        <script src="<c:url value='/resources/js/pwsTabs/jquery.pwstabs.min.js' />"></script>
        <script src="<c:url value='/resources/js/slideout/slideout.min.js' />"></script>
        <script src="<c:url value='/resources/js/sidebarMenu/sidebar-menu.js' />"></script>
        <script src="<c:url value='/resources/css/bootstrap-3.3.7/js/bootstrap.min.js' />"></script>
        <!--<link href="<c:url value='/resources/css/core/core.min.css' />" rel="stylesheet"></link>
        <script src="<c:url value='/resources/js/core/core.min.js' />"></script>-->
    </head>
    <body>
        <nav id="menu">
            <ul class="sidebar-menu">
                <li class="sidebar-header">MAIN NAVIGATION</li>
                <li>
                    <a href="${home}">
                        <i class="fa fa-home"></i> <span> Начало</span> <i class="fa fa-angle-left pull-right"></i>
                    </a>
                </li>
                <c:if test="${pageContext.request.userPrincipal.name == null}">
                    <li>
                        <a href="${register}">
                            <i class="fa fa-pencil-square-o"></i> <span> Регистрация</span> <i class="fa fa-angle-left pull-right"></i>
                        </a>
                    </li>
                    <li>
                        <a href="${login}">
                            <i class="fa fa-sign-in"></i> <span> Вход</span> <i class="fa fa-angle-left pull-right"></i>
                        </a>
                    </li>

                </c:if>
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <li>
                        <a href="#">
                            <i class="fa fa-book"></i> <span> Бюджет</span> <i class="fa fa-angle-left pull-right"></i>
                        </a>
                        <ul class="sidebar-submenu">
                            <li><a href="${categoryBudget}"><i class="fa fa-circle-o"></i> Управление</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#">
                            <i class="fa fa-tasks"></i> <span> Категории</span> <i class="fa fa-angle-left pull-right"></i>
                        </a>
                        <ul class="sidebar-submenu">
                            <li><a href="${categoriesАdd}"><i class="fa fa-circle-o"></i> Нова категория</a></li>
                            <li><a href="${categories}"><i class="fa fa-circle-o"></i> Управление</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#">
                            <i class="fa fa-gift"></i> <span> Плащания</span> <i class="fa fa-angle-left pull-right"></i>
                        </a>
                        <ul class="sidebar-submenu">
                            <li><a href="${paymentAdd}"><i class="fa fa-circle-o"></i> Ново плащане</a></li>
                            <li><a href="${payments}"><i class="fa fa-circle-o"></i> Управление</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#">
                            <i class="fa fa-calendar"></i> <span> Периодични плащания</span> <i class="fa fa-angle-left pull-right"></i>
                        </a>
                        <ul class="sidebar-submenu">
                            <li><a href="${budgetRecPaymentsAdd}"><i class="fa fa-circle-o"></i> Ново плащане</a></li>
                            <li><a href="${budgetRecPayments}"><i class="fa fa-circle-o"></i> Управление</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#">
                            <i class="fa fa-bar-chart"></i> <span> Справки</span> <i class="fa fa-angle-left pull-right"></i>
                        </a>
                        <ul class="sidebar-submenu">
                            <li><a href="${userBudgetStatus}"><i class="fa fa-circle-o"></i> Планирано/действително</a></li>
                            <li><a href="${categoryCoverage}"><i class="fa fa-circle-o"></i> Покритие</a></li>
                            <li><a href="${paymentsStatus}"><i class="fa fa-circle-o"></i> Приходи към разходи</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="${upcoming}">
                            <i class="fa fa-clock-o"></i> <span> Предстоящи плащания</span> <i class="fa fa-angle-left pull-right"></i>
                        </a>
                    </li>
                </c:if>
            </ul>
            <script>
                $.sidebarMenu($('.sidebar-menu'));
            </script>
        </nav>
        <main id="panel">
            <div>
                <header id="header">
                    <tiles:insertAttribute name="header" />
                </header>

                <section id="site-content" class="site-content">
                    <div class="container-fluid">
                        <tiles:insertAttribute name="body" />
                    </div>
                </section>

                <footer id="footer">
                    <tiles:insertAttribute name="footer" />
                </footer>
            </div>
        </main>
        <script>
            var slideout = new Slideout({
                'panel': document.getElementById('panel'),
                'menu': document.getElementById('menu'),
                'padding': 256,
                'tolerance': 70
            });
            document.querySelector('.toggle-button').addEventListener('click', function () {
                slideout.toggle();
            });
        </script>
    </body>
</html>