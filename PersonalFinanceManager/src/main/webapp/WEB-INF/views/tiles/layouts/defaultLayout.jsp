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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <title><tiles:getAsString name="title" /></title>
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet"> </link>
        <link href="<c:url value='/resources/js/icheck-1.x/skins/line/green.css'/>" rel="stylesheet" ></link>
        <link href="<c:url value='/resources/js/icheck-1.x/skins/line/grey.css'/>" rel="stylesheet" ></link> 
        <link href="<c:url value='/resources/css/font-awesome-4.7.0/css/font-awesome.css'/>" rel="stylesheet"  type="text/css" />
        <link href="<c:url value='/resources/js/DataTables-1.10.13/media/css/dataTables.bootstrap.min.css'/>" rel="stylesheet" ></link> 
        <link href="<c:url value='/resources/js/DataTables-1.10.13/extensions/Responsive/css/responsive.dataTables.css' />" rel="stylesheet" ></link>
        <link href="<c:url value='/resources/js/flatpickr/flatpickr.min.css' />" rel="stylesheet" type="text/css"></link>
        <link href="<c:url value='/resources/js/flatpickr/themes/material_green.css' />" rel="stylesheet" type="text/css"></link>
        <link href="<c:url value='/resources/js/datePicker/datepicker.min.css' />" rel="stylesheet" type="text/css"></link> 
        <link href="<c:url value='/resources/js/select2-4.0.3/dist/css/select2.min.css' />" rel="stylesheet" ></link> 
        <link href="<c:url value='/resources/js/pwstabs/jquery.pwstabs.min.css' />" rel="stylesheet" ></link> 
        <link href="<c:url value='/resources/css/gridCustomCss/grid.css' />" rel="stylesheet" ></link> 
        <link href='<c:url value='/resources/js/smartmenus/css/sm-core-css.css' />' rel='stylesheet' type='text/css' />
        <link href='<c:url value='/resources/js/smartmenus/css/sm-mint/sm-mint.css' />' rel='stylesheet' type='text/css' />
        <link href='<c:url value='/resources/js/slider-pro/dist/css/slider-pro.min.css' />' rel='stylesheet' type='text/css' />
        <link href='<c:url value='/resources/js/sideBar/css/simple-sidebar.css' />' rel='stylesheet' type='text/css' />
        <link href='<c:url value='/resources/css/footer/footer-distributed-with-address-and-phones.css' />' rel='stylesheet' type='text/css' />
        <link href="<c:url value='/resources/css/bootstrap-3.3.7/bootstrapThemes/yeti/bootstrap.min.css'/>" rel="stylesheet" ></link>
        <link href="<c:url value='/resources/css/site.css' />" rel="stylesheet"></link>
        <script src="<c:url value='/resources/js/jquery/jquery.js' />"></script>
        <script src="<c:url value='/resources/js/smartmenus/jquery.smartmenus.min.js' />" type="text/javascript"></script>
        <script src="<c:url value='/resources/js/icheck-1.x/icheck.min.js' />"></script>
        <script src="<c:url value='/resources/js/jquery/jquery.validate.min.js' />"></script>
        <script src="<c:url value='/resources/js/DataTables-1.10.13/media/js/jquery.dataTables.min.js' />"></script>
        <script src="<c:url value='/resources/js/DataTables-1.10.13/media/js/dataTables.bootstrap4.min.js' />"></script>
        <script src="<c:url value='/resources/js/DataTables-1.10.13/extensions/Responsive/js/dataTables.responsive.min.js' />"></script>
        <script src="<c:url value='/resources/js/DataTables-1.10.13/extensions/Select/js/dataTables.select.min.js' />"></script>
        <script src="<c:url value='/resources/js/select2-4.0.3/dist/js/select2.min.js' />"></script>
        <script src="<c:url value='/resources/js/flatpickr/flatpickr.js' />"></script>
        <script src="<c:url value='/resources/js/flatpickr/l10n/bg.js' />"></script>
        <script src="<c:url value='/resources/js/datePicker/datepicker.min.js' />"></script>
        <script src="<c:url value='/resources/js/datePicker/i18n/datepicker.bg-BG.js' />"></script>
        <script src="<c:url value='/resources/js/moment/moment.js' />"></script>
        <script src="<c:url value='/resources/js/pwstabs/jquery.pwstabs.min.js' />"></script>
        <script src="<c:url value='/resources/css/bootstrap-3.3.7/js/bootstrap.min.js' />"></script>
        <script>
            $(document).ready(function () {
                $(".top-menu-container").smartmenus({
                    hideTimeout: 0,
                    markCurrentItem: true,
                    showTimeout: 0,
                    subMenusSubOffsetX: 0,
                    subMenusSubOffsetY: -1,
                    mainMenuSubOffsetX: 1
                });

                var $mainMenuState = $('#main-menu-state');
                if ($mainMenuState.length) {
                    $mainMenuState.change(function (e) {
                        var $menu = $('#main-menu');
                        if (this.checked) {
                            $menu.hide().slideDown(250, function () {
                                $menu.css('display', '');
                            });
                        } else {
                            $menu.show().slideUp(250, function () {
                                $menu.css('display', '');
                            });
                        }
                    });
                    $(window).bind('beforeunload unload', function () {
                        if ($mainMenuState[0].checked) {
                            $mainMenuState[0].click();
                        }
                    });
                }

                $(".toggle-menu").click(function () {
                    $(".header").toggleClass("toggled");
                    $("#wrapper").toggleClass("toggled");
                    $(this).toggleClass("toggled");
                    $("#menu").slideToggle();
                });
            });
        </script>
    </head>
    <body>
        <div id="wrapper" class="toggled">
            <div id="sidebar-wrapper">
                <div class="nav-side-menu">
                    <div class="brand"><i class="fa fa-cog fa-spin fa-1x fa-fw"></i> Меню</div>
                    <i class="fa fa-bars fa-2x toggle-btn" data-toggle="collapse" data-target="#menu-content"></i>
                    <div class="menu-list">
                        <ul id="menu-content" class="menu-content collapse out">
                            <c:if test="${pageContext.request.userPrincipal.name == null}">
                                <li><a href="home"><i class="fa fa-home" aria-hidden="true"></i>Начало</a></li>
                                <li><a href="${register}"><i class="fa fa-pencil-square-o" aria-hidden="true"></i>Регистрация</a></li>
                                <li><a href="${login}"><i class="fa fa-sign-in" aria-hidden="true"></i>Вход</a></li>
                                </c:if>
                                <c:if test="${pageContext.request.userPrincipal.name != null}">
                                <li><a href="home"><i class="fa fa-home" aria-hidden="true"></i>Начало</a></li>
                                <li  data-toggle="collapse" data-target="#budget" class="collapsed active">
                                    <a href="#"><i class="fa fa-book fa-lg"></i> Бюджет <span class="arrow"></span></a>
                                </li>
                                <ul class="sub-menu collapse" id="budget">
                                    <li class="active"><a href="${categoryBudget}">Управление</a></li>
                                </ul>
                                <li  data-toggle="collapse" data-target="#categories" class="collapsed">
                                    <a href="#"><i class="fa fa-tasks fa-lg"></i> Категории <span class="arrow"></span></a>
                                </li>
                                <ul class="sub-menu collapse" id="categories">
                                    <li><a href="${categoriesАdd}">Нова категория</a></li>
                                    <li><a href="${categories}">Управление</a></li>
                                </ul>
                                <li  data-toggle="collapse" data-target="#payments" class="collapsed">
                                    <a href="#"><i class="fa fa-gift fa-lg"></i> Плащания <span class="arrow"></span></a>
                                </li>
                                <ul class="sub-menu collapse" id="payments">
                                    <li><a href="${paymentAdd}">Ново плащане</a></li>
                                    <li><a href="${payments}">Управление</a></li>
                                </ul>
                                <li  data-toggle="collapse" data-target="#rec-payments" class="collapsed">
                                    <a href="#"><i class="fa fa-calendar fa-lg"></i> Периодични плащания <span class="arrow"></span></a>
                                </li>
                                <ul class="sub-menu collapse" id="rec-payments">
                                    <li><a href="${budgetRecPaymentsAdd}">Ново плащане</a></li>
                                    <li><a href="${budgetRecPayments}">Управление</a></li>
                                </ul>
                                <li  data-toggle="collapse" data-target="#statistics" class="collapsed">
                                    <a href="#"><i class="fa fa-bar-chart fa-lg"></i> Справки <span class="arrow"></span></a>
                                </li>
                                <ul class="sub-menu collapse" id="statistics">
                                    <li><a href="${userBudgetStatus}">Планирано/действително</a></li>
                                    <li><a href="${categoryCoverage}">Покритие</a></li>
                                    <li><a href="${paymentsStatus}">Приходи към разходи</a></li>
                                </ul>
                                <li  data-toggle="collapse" data-target="#rec-payments" class="collapsed">
                                    <a href="#"><i class="fa fa fa-clock-o fa-lg"></i> Предстоящи плащания <span class="arrow"></span></a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </div>

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
        </div>
    </body>
</html>