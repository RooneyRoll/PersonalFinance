<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<spring:url var = "paymentEdit" value='/payments/edit/${payment.getId()}' />
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
                insert: '<div class="icheck_line-icon"></div>' + "<div class='label-text'>" + label_text + "</div>"
            });
        });
        $("#categories-select").select2({"theme": "classic", disabled: true, minimumResultsForSearch: Infinity});
    });
</script>
<div class="row">
    <div class="col-6 col-md-6">
        <div class="row">
            <div class="col-12 col-md-12">
                <div class="panel panel-success">
                    <div class='panel-heading'>Данни за плащане</div>
                    <div class="panel-body">
                        <div class="form-group ">
                            <label for="exampleFormControlInput1">Сума</label>
                            <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                <input readonly type="text" class="form-control" placeholder="сума" id="password" value="${payment.getAmount()}"/aria-describedby="basic-addon1">
                            </div>
                        </div>
                        <div class="form-group ">
                            <label for="exampleFormControlInput1">Описание на плащане</label>
                            <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                <textarea class="form-control" readonly placeholder="Описание" aria-describedby="basic-addon1">${payment.getDescription()}</textarea>
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
                        Настройки на плащане
                    </div>
                    <c:choose>
                        <c:when test="${payment.isConfirmed()}">
                            <c:set var = "panelType" scope = "session" value = "success"/>
                            <c:set var = "confirmedPhrase" scope = "session" value = "<i class='fa fa-check' aria-hidden='true'></i> Изпълнено "/>
                        </c:when>
                        <c:otherwise>
                            <c:set var = "panelType" scope = "session" value = "warning"/>
                            <c:set var = "confirmedPhrase" scope = "session" value = "<i class='fa fa-clock-o' aria-hidden='true'></i> Предстоящо "/>
                        </c:otherwise>
                    </c:choose>
                    <div class="panel-body">
                        <div class="form-group ">
                            <div class="alert alert-${panelType}" role="alert">
                                <!--Плащането е: -->
                                <strong>
                                    ${confirmedPhrase}
                                </strong> 
                            </div>
                        </div>
                        <div class="form-group ">
                            <label for="exampleFormControlInput1">Дата на плащане</label>
                            <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                <span class="input-group-addon" id="basic-addon1"><i class="fa fa-calendar" aria-hidden="true"></i></span>
                                    <fmt:parseDate pattern="yyyy-MM-dd" value="${payment.getDate()}" var="parsedDate" />
                                    <fmt:formatDate value="${parsedDate}" var="date" pattern="yyyy-MM-dd" />
                                <input readonly class="form-control" name="paymentDate" id="paymentDate" placeholder="Дата на плащане" value="${date}" aria-describedby="basic-addon1">
                            </div>
                        </div>
                        <div class="form-group ">
                            <label for="exampleFormControlInput1">Категорията е</label>
                            <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                <span class="input-group-addon" id="basic-addon1"><i class="fa fa-check" aria-hidden="true"></i></span>
                                <div class="btn-group">
                                    <button disabled class="btn btn-primary 
                                            <c:if test='${payment.isActive()}'>
                                                active
                                            </c:if>" type="button" value="1">
                                        Активно
                                    </button>
                                    <button disabled class="btn btn-primary 
                                            <c:if test='${payment.isActive() == false}'>
                                                active
                                            </c:if>" type="button" value="2">
                                        Неактивно
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label for="exampleFormControlInput1">Категория</label>
                            <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                <span class="input-group-addon" id="basic-addon1"><i class="fa fa-bars" aria-hidden="true"></i></span>
                                <select id="categories-select" name="paymentCategory">
                                    <c:forEach items="${categories}" var="element">
                                        <option <c:if test = "${element.getId() == payment.getCategory()}">selected</c:if>  value="${element.getId()}">${element.getName()}</option>
                                    </c:forEach>
                                </select>
                                <spring:url var = "categoriesAdd" value='/categories/add' />
                                <div class="input-add-button">
                                    <a href="${categoriesAdd}" target="_blank"><i class="fa fa-plus-circle" aria-hidden="true"></i></a>
                                </div>
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
                    <a href = "${paymentEdit}"><button type="submit" class="btn btn-primary">Редакция</button></a>
                </div>
            </div>
        </div>
    </div>
</div>