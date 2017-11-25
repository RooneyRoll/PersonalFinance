<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<spring:url var = "categoryEdit" value='/categories/edit/${category.getId()}' />
<script>
    $(document).ready(function () {
        $('.site-content .form-container input[type="checkbox"],.site-content .form-container input[type="radio"]').each(function (key, val) {
            var self = $(this)
            var label = self.next();
            var label_text = label.text();
            var visible = "label-check-not-visible";
            if (label.hasClass("visible"))
                visible = "label-check-visible";

            label.remove();
            $(this).on("ifChanged", function (event) {
                $(this).change();
            });
            var check = self.iCheck({
                checkboxClass: 'icheckbox_line-grey visibility-check',
                radioClass: 'iradio_line-grey visibility-check ' + visible,
                insert: '<div class="icheck_line-icon"></div>' + "<div class='label-text'>" + label_text + "</div>",
            });
        });
        $("#types-select").select2({"theme": "classic", disabled: true});
    });
</script>
<div class="row">
    <div class="col-6 col-md-6">
        <div class="row">
            <div class="col-12 col-md-12">
                <div class="panel panel-success">
                    <div class='panel-heading'>Данни за категория</div>
                    <div class="panel-body">
                        <div class="form-group ">
                            <label for="exampleFormControlInput1">Име на Категория</label>
                            <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                <input name="categoryName" readonly type="text" class="form-control" placeholder="Име на плащане" value="${category.getName()}" aria-describedby="basic-addon1">
                            </div>
                        </div>
                        <div class="form-group ">
                            <label for="exampleFormControlInput1">Описание на категория</label>
                            <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                <textarea class="form-control" readonly placeholder="Описание" name="categoryDescription" aria-describedby="basic-addon1">${category.getDescription()}</textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-6 col-md-6">
        <div class="row">
            <div class="col-12 col-md-12">    
                <div class="panel panel-info">
                    <div class='panel-heading'>
                        Настройки на категория
                    </div>
                    <div class="panel-body">
                        <div class="form-group ">
                            <label for="exampleFormControlInput1">Категорията е</label>
                            <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                <span class="input-group-addon" id="basic-addon1"><i class="fa fa-check" aria-hidden="true"></i></span>
                                <div class="btn-group">
                                    <button disabled class="btn btn-primary 
                                            <c:if test='${category.isActive()}'>
                                                active
                                            </c:if>" type="button" value="1">
                                        Активна
                                    </button>
                                    <button disabled class="btn btn-primary 
                                            <c:if test='${category.isActive() == false}'>
                                                active
                                            </c:if>" type="button" value="2">
                                        Неактивна
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label for="exampleFormControlInput1">Тип на категория</label>
                            <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                <span class="input-group-addon" id="basic-addon1">
                                    <i class="fa fa-bars" aria-hidden="true">

                                    </i>
                                </span>
                                <select id="types-select" name="categoryType">
                                    <c:forEach items="${types}" var="element">
                                        <option <c:if test = "${element.getId() == category.getType()}">
                                                selected
                                            </c:if> 
                                            value="${element.getId()}">
                                            ${element.getName()}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-12 col-md-12">
        <div class="row">
            <div class="col-12 col-md-12">    
                <div class="btn-group">
                    <a href = "${categoryEdit}"><button type="submit" class="btn btn-primary">Редакция</button></a>
                </div>
            </div>
        </div>
    </div>
</div>