<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
${grid}

<div class="home-carousel">
    <div>
        <img style="max-width: 100%;height: auto" src="<c:url value='/resources/images/carousel/1.jpeg'/>"/>
    </div>
    <div>
        <img style="max-width: 100%;height: auto" src="<c:url value='/resources/images/carousel/2.jpeg'/>"/>
    </div>
    <div>
        <img style="max-width: 100%;height: auto" src="<c:url value='/resources/images/carousel/3.jpeg'/>"/>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        $('.home-carousel').slick();
    });
</script>
