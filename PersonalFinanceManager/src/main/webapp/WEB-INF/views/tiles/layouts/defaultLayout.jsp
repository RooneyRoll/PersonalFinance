<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
 
<html>
 
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title><tiles:getAsString name="title" /></title>
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet"> 
    <link href="resources/js/icheck-1.x/skins/all.css" rel="stylesheet" ></link> 
    <link href="resources/js/icheck-1.x/skins/line/green.css" rel="stylesheet" ></link> 
    <link type="text/css" href="<c:url value='/resources/css/font-awesome-4.7.0/css/font-awesome.css'/>" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/site.css' />" rel="stylesheet"></link>
    <script src="<c:url value='/resources/js/jquery/jquery.js' />"></script>
    <script src="<c:url value='/resources/js/icheck-1.x/icheck.min.js' />"></script>
    <script>
        $(document).ready(function(){
           
        });
    </script>
</head>
<body>
    <header id="header">
        <tiles:insertAttribute name="header" />
    </header>

    <section id="site-content" class="site-content">
        <tiles:insertAttribute name="body" />
    </section>

    <footer id="footer">
        <tiles:insertAttribute name="footer" />
    </footer>
</body>
</html>