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
        <script src="<c:url value='/resources/css/bootstrap-3.3.7/js/bootstrap.min.js' />"></script>
        <script src="<c:url value='/resources/js/sidebarMenu/sidebar-menu.js' />"></script>
        <script src="<c:url value='/resources/css/bootstrap-3.3.7/js/bootstrap.min.js' />"></script>
        <!--<link href="<c:url value='/resources/css/core/core.min.css' />" rel="stylesheet"></link>
        <script src="<c:url value='/resources/js/core/core.min.js' />"></script>-->
        <script>
            $(document).ready(function () {

            });
        </script>
    </head>
    <body>
        <nav id="menu">
            <ul class="sidebar-menu">
                <li class="sidebar-header">MAIN NAVIGATION</li>
                <li>
                    <a href="#">
                        <i class="fa fa-dashboard"></i> <span>Dashboard</span> <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="sidebar-submenu">
                        <li><a href="../../index.html"><i class="fa fa-circle-o"></i> Dashboard v1</a></li>
                        <li><a href="../../index2.html"><i class="fa fa-circle-o"></i> Dashboard v2</a></li>
                    </ul>
                </li>
                <li>
                    <a href="#">
                        <i class="fa fa-files-o"></i>
                        <span>Layout Options</span>
                        <span class="label label-primary pull-right">4</span>
                    </a>
                    <ul class="sidebar-submenu" style="display: none;">
                        <li><a href="top-nav.html"><i class="fa fa-circle-o"></i> Top Navigation</a></li>
                        <li><a href="boxed.html"><i class="fa fa-circle-o"></i> Boxed</a></li>
                        <li><a href="fixed.html"><i class="fa fa-circle-o"></i> Fixed</a></li>
                        <li class=""><a href="collapsed-sidebar.html"><i class="fa fa-circle-o"></i> Collapsed Sidebar</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="../widgets.html">
                        <i class="fa fa-th"></i> <span>Widgets</span>
                        <small class="label pull-right label-info">new</small>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="fa fa-pie-chart"></i>
                        <span>Charts</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="sidebar-submenu">
                        <li><a href="../charts/chartjs.html"><i class="fa fa-circle-o"></i> ChartJS</a></li>
                        <li><a href="../charts/morris.html"><i class="fa fa-circle-o"></i> Morris</a></li>
                        <li><a href="../charts/flot.html"><i class="fa fa-circle-o"></i> Flot</a></li>
                        <li><a href="../charts/inline.html"><i class="fa fa-circle-o"></i> Inline charts</a></li>
                    </ul>
                </li>
                <li>
                    <a href="#">
                        <i class="fa fa-laptop"></i>
                        <span>UI Elements</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="sidebar-submenu">
                        <li><a href="../UI/general.html"><i class="fa fa-circle-o"></i> General</a></li>
                        <li><a href="../UI/icons.html"><i class="fa fa-circle-o"></i> Icons</a></li>
                        <li><a href="../UI/buttons.html"><i class="fa fa-circle-o"></i> Buttons</a></li>
                        <li><a href="../UI/sliders.html"><i class="fa fa-circle-o"></i> Sliders</a></li>
                        <li><a href="../UI/timeline.html"><i class="fa fa-circle-o"></i> Timeline</a></li>
                        <li><a href="../UI/modals.html"><i class="fa fa-circle-o"></i> Modals</a></li>
                    </ul>
                </li>
                <li>
                    <a href="#">
                        <i class="fa fa-edit"></i> <span>Forms</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="sidebar-submenu">
                        <li><a href="../forms/general.html"><i class="fa fa-circle-o"></i> General Elements</a></li>
                        <li><a href="../forms/advanced.html"><i class="fa fa-circle-o"></i> Advanced Elements</a></li>
                        <li><a href="../forms/editors.html"><i class="fa fa-circle-o"></i> Editors</a></li>
                    </ul>
                </li>
                <li>
                    <a href="#">
                        <i class="fa fa-table"></i> <span>Tables</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="sidebar-submenu">
                        <li><a href="../tables/simple.html"><i class="fa fa-circle-o"></i> Simple tables</a></li>
                        <li><a href="../tables/data.html"><i class="fa fa-circle-o"></i> Data tables</a></li>
                    </ul>
                </li>
                <li>
                    <a href="../calendar.html">
                        <i class="fa fa-calendar"></i> <span>Calendar</span>
                        <small class="label pull-right label-danger">3</small>
                    </a>
                </li>
                <li>
                    <a href="../mailbox/mailbox.html">
                        <i class="fa fa-envelope"></i> <span>Mailbox</span>
                        <small class="label pull-right label-warning">12</small>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="fa fa-folder"></i> <span>Examples</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="sidebar-submenu">
                        <li><a href="../examples/invoice.html"><i class="fa fa-circle-o"></i> Invoice</a></li>
                        <li><a href="../examples/profile.html"><i class="fa fa-circle-o"></i> Profile</a></li>
                        <li><a href="../examples/login.html"><i class="fa fa-circle-o"></i> Login</a></li>
                        <li><a href="../examples/register.html"><i class="fa fa-circle-o"></i> Register</a></li>
                        <li><a href="../examples/lockscreen.html"><i class="fa fa-circle-o"></i> Lockscreen</a></li>
                        <li><a href="../examples/404.html"><i class="fa fa-circle-o"></i> 404 Error</a></li>
                        <li><a href="../examples/500.html"><i class="fa fa-circle-o"></i> 500 Error</a></li>
                        <li><a href="../examples/blank.html"><i class="fa fa-circle-o"></i> Blank Page</a></li>
                        <li><a href="../examples/pace.html"><i class="fa fa-circle-o"></i> Pace Page</a></li>
                    </ul>
                </li>
                <li>
                    <a href="#">
                        <i class="fa fa-share"></i> <span>Multilevel</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="sidebar-submenu">
                        <li><a href="#"><i class="fa fa-circle-o"></i> Level One</a></li>
                        <li>
                            <a href="#"><i class="fa fa-circle-o"></i> Level One <i class="fa fa-angle-left pull-right"></i></a>
                            <ul class="sidebar-submenu">
                                <li><a href="#"><i class="fa fa-circle-o"></i> Level Two</a></li>
                                <li>
                                    <a href="#"><i class="fa fa-circle-o"></i> Level Two <i class="fa fa-angle-left pull-right"></i></a>
                                    <ul class="sidebar-submenu">
                                        <li><a href="#"><i class="fa fa-circle-o"></i> Level Three</a></li>
                                        <li><a href="#"><i class="fa fa-circle-o"></i> Level Three</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </li>
                        <li><a href="#"><i class="fa fa-circle-o"></i> Level One</a></li>
                    </ul>
                </li>
                <li><a href="../../documentation/index.html"><i class="fa fa-book"></i> <span>Documentation</span></a></li>
                <li class="sidebar-header">LABELS</li>
                <li><a href="#"><i class="fa fa-circle-o text-red"></i> <span>Important</span></a></li>
                <li><a href="#"><i class="fa fa-circle-o text-yellow"></i> <span>Warning</span></a></li>
                <li><a href="#"><i class="fa fa-circle-o text-aqua"></i> <span>Information</span></a></li>
            </ul>
            <script>
                $.sidebarMenu($('.sidebar-menu'));
            </script>






            <ul class="nav">
                <li><a href="home"><i class="fa fa-home" aria-hidden="true"></i>Начало</a></li>
                    <c:if test="${pageContext.request.userPrincipal.name == null}">
                    <li><a href="${register}"><i class="fa fa-pencil-square-o" aria-hidden="true"></i>Регистрация</a></li>
                    <li><a href="${login}"><i class="fa fa-sign-in" aria-hidden="true"></i>Вход</a></li>
                    </c:if>
                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <li><a href="#"><i class="fa fa-book fa-lg"></i> Бюджет</a>
                        <ul>
                            <li><a href="${categoryBudget}">Управление</a></li>
                        </ul>
                    </li>
                    <li><a href="#"><i class="fa fa-tasks fa-lg"></i> Категории</a>
                        <ul>
                            <li><a href="${categoriesАdd}">Нова категория</a></li>
                            <li><a href="${categories}">Управление</a></li>
                        </ul>
                    </li>
                    <li><a href="#"><i class="fa fa-gift fa-lg"></i>Плащания</a>
                        <ul>
                            <li><a href="${paymentAdd}">Ново плащане</a></li>
                            <li><a href="${payments}">Управление</a></li>
                        </ul>
                    </li>
                    <li><a href="#"><i class="fa fa-calendar fa-lg"></i>Периодични плащания</a>
                        <ul>
                            <li><a href="${budgetRecPaymentsAdd}">Ново плащане</a></li>
                            <li><a href="${budgetRecPayments}">Управление</a></li>
                        </ul>
                    </li>
                    <li><a href="#"><i class="fa fa-bar-chart fa-lg"></i>Справки</a>
                        <ul>
                            <li><a href="${userBudgetStatus}">Планирано/действително</a></li>
                            <li><a href="${categoryCoverage}">Покритие</a></li>
                            <li><a href="${paymentsStatus}">Приходи към разходи</a></li>

                        </ul>
                    </li>
                </c:if>
            </ul>
        </nav>

        <main id="panel">
            <!--<div id="sidebar-wrapper">
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
    </div>-->

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