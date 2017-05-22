<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <meta i>
        <title><tiles:getAsString name="title" /></title>
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet"> </link>
        <link href="<c:url value='/resources/js/icheck-1.x/skins/line/green.css'/>" rel="stylesheet" ></link>
        <link href="<c:url value='/resources/js/icheck-1.x/skins/line/grey.css'/>" rel="stylesheet" ></link> 
        <link href="<c:url value='/resources/css/font-awesome-4.7.0/css/font-awesome.css'/>" rel="stylesheet"  type="text/css" />
        <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" ></link> 
        <link href="https://cdn.datatables.net/1.10.15/css/dataTables.bootstrap.min.css" rel="stylesheet" ></link> 
        <link href="<c:url value='/resources/js/DataTables-1.10.13/extensions/Responsive/css/responsive.dataTables.css' />" rel="stylesheet" ></link> 
        <link href="<c:url value='/resources/js/flatpickr/flatpickr.min.css' />" rel="stylesheet" type="text/css"></link>
        <link href="<c:url value='/resources/js/flatpickr/themes/material_green.css' />" rel="stylesheet" type="text/css"></link> 
        <link href="<c:url value='/resources/js/select2-4.0.3/dist/css/select2.min.css' />" rel="stylesheet" ></link> 
        <link href="<c:url value='/resources/css/gridCustomCss/grid.css' />" rel="stylesheet" ></link> 
        <link href='<c:url value='/resources/js/smartmenus/css/sm-core-css.css' />' rel='stylesheet' type='text/css' />
        <link href='<c:url value='/resources/js/smartmenus/css/sm-mint/sm-mint.css' />' rel='stylesheet' type='text/css' />
        <link href="<c:url value='/resources/css/site.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/resources/css/slick-carousel/slick.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/resources/css/slick-carousel/slick-theme.css' />" rel="stylesheet"></link>
        <script src="<c:url value='/resources/js/jquery/jquery.js' />"></script>
        <script src="<c:url value='/resources/js/smartmenus/jquery.smartmenus.min.js' />" type="text/javascript"></script>
        <script src="<c:url value='/resources/js/icheck-1.x/icheck.min.js' />"></script>
        <script src="<c:url value='/resources/js/jquery/jquery.validate.min.js' />"></script>
        <script src="<c:url value='/resources/js/DataTables-1.10.13/media/js/jquery.dataTables.js' />"></script>
        <script src="<c:url value='/resources/js/DataTables-1.10.13/media/js/dataTables.bootstrap4.min.js' />"></script>
        <script src="<c:url value='/resources/js/DataTables-1.10.13/extensions/Responsive/js/dataTables.responsive.js' />"></script>
        <script src="<c:url value='/resources/js/select2-4.0.3/dist/js/select2.min.js' />"></script>
        <script src="<c:url value='/resources/js/flatpickr/flatpickr.min.js' />"></script>
        <script src="<c:url value='/resources/js/flatpickr/l10n/bg.js' />"></script>
        <script src="<c:url value='/resources/js/slick-carousel/slick.min.js' />"></script>
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
            });
        </script>
    </head>
    <body>
        <header id="header">
            <tiles:insertAttribute name="header" />
        </header>

        <section id="site-content" class="site-content">
            <div class="content-container">
                <tiles:insertAttribute name="body" />
            </div>
        </section>

        <footer id="footer">
            <tiles:insertAttribute name="footer" />
        </footer>
    </body>
</html>