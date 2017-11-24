<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<spring:url var = "add" value='/categories/add' />
<div class="row">
    <div class="col-12 col-md-12">
        <div class="panel panel-default">
            <div class="panel-body">
                ${grid}
            </div>
            <div class="panel-heading">
                <div class="btn-group">
                    <a href = "${add}"><button type="submit" class="btn btn-primary">Нова категория</button></a>
                </div>
            </div>
        </div>
    </div>
</div>