<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pfm.enums.RecurringTypes" %>
<spring:url var = "serviceUrl" value ="/getPayments"/>
<script src="<c:url value='/resources/js/basic/recurringPaymentUtils.js' />"></script>
<script>
    $(document).ready(function () {
        $("#by-amount-category,#by-amount-rec-type,#by-period-category,#by-period-rec-type").select2({"theme": "classic"});
        var overviewUrl = "${serviceUrl}";
        var utils = new recurringPaymentUtils(overviewUrl);
        utils.initialize();
    });

</script>
<div class="row">
    <div class="col-12 col-md-12">
        <div class="tabs-container">
            <div data-pws-tab="wizard-amount-first" data-pws-tab-name="<i class='fa fa-calendar' aria-hidden='true'></i>&nbsp  Създаване спрямо сума">
                <div id="smartwizard-amount-first">
                    <ul>
                        <li><a href="#step-amount-1">Настройки<br /><small>за плащане</small></a></li>
                        <li><a href="#step-amount-2">Преглед<br /><small>на план за насрочени плащания</small></a></li>
                    </ul>
                    <div>
                        <div id="step-amount-1" class="">
                            <div class="row">
                                <div class="col-6 col-md-6">
                                    <div class="panel panel-primary">
                                        <div class='panel-heading'>Данни за плащане</div>
                                        <div class="panel-body">
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Име на плащане</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                                    <input name="payment-name" type="text" class="form-control" placeholder="Име на плащане" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Описание</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                                    <textarea name="payment-description" class="form-control"  resize="false" placeholder="Описание" aria-describedby="basic-addon1"></textarea>
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Желана крайна сума</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-usd" aria-hidden="true"></i></span>
                                                    <input name="payment-final-amount" value="0" type="text" class="form-control" placeholder="Желана крайна сума" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Начална сума</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-usd" aria-hidden="true"></i></span>
                                                    <input name="payment-initial-amount" value="0" type="text" class="form-control" placeholder="Начална сума" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-6 col-md-6">
                                    <div class="panel panel-info">
                                        <div class='panel-heading'>Настройки на плащане</div>
                                        <div class="panel-body">
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Начална дата на плащане</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-calendar" aria-hidden="true"></i></span>
                                                    <fmt:parseDate pattern="yyyy-MM-dd" value="${payment.getDate()}" var="parsedDate" />
                                                    <fmt:formatDate value="${parsedDate}" var="date" pattern="yyyy-MM-dd" />
                                                    <input readonly name="payment-start-date" id="by-amount-start-date" placeholder="Начална дата на повтарящо плащане" value="${date}" class="form-control" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Брой периоди</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-sort" aria-hidden="true"></i></span>
                                                    <input name="payment-periods-count" type="text" value="1" class="form-control" placeholder="Брой периоди" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Пропускане през брой периоди</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-sort" aria-hidden="true"></i></span>
                                                    <input name="payment-miss-periods" type="text" value="0" class="form-control" placeholder="Пропускане през" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Категория</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-bars" aria-hidden="true"></i></span>
                                                    <select name="payment-category" id="by-amount-category">
                                                        <c:forEach items="${categories}" var="element">
                                                            <option value="${element.getId()}">${element.getName()}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <spring:url var = "categoriesAdd" value='/categories/add' />
                                                    <div class="input-add-button">
                                                        <a href="${categoriesAdd}" target="_blank"><i class="fa fa-plus-circle" aria-hidden="true"></i></a>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Плащането е</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-bars" aria-hidden="true"></i></span>
                                                    <select name="payment-recuring-type" id="by-amount-rec-type">
                                                        <c:forEach items="${recTypes}" var="element">
                                                            <option value="${element.getId()}">${element.getName()}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="step-amount-2" class="">
                            Step Content
                        </div>
                    </div>
                </div>
            </div>
            <div data-pws-tab="wizard-period-first" data-pws-tab-name="<i class='fa fa-files-o' aria-hidden='true'></i>&nbsp Създаване спрямо периоди">
                <div id="smartwizard-period-first">
                    <ul>
                        <li><a href="#step-period-1">Настройки<br /><small>за плащане</small></a></li>
                        <li><a href="#step-period-2">Преглед<br /><small>на план за насрочени плащания</small></a></li>
                    </ul>
                    <div>
                        <div id="step-period-1">
                            <div class="row">
                                <div class="col-6 col-md-6">
                                    <div class="panel panel-success">
                                        <div class='panel-heading'>Данни за плащане</div>
                                        <div class="panel-body">
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Име на плащане</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                                    <input name="payment-name" type="text" class="form-control" placeholder="Име на плащане" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Сума за период</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-usd" aria-hidden="true"></i></span>
                                                    <input name="payment-period-amount" value="0" type="text" class="form-control" placeholder="Сума за период" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Описание</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                                    <textarea name="payment-description" class="form-control" resize="false" placeholder="Описание" aria-describedby="basic-addon1"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-6 col-md-6">
                                    <div class="panel panel-info">
                                        <div class='panel-heading'>Настройки на плащане</div>
                                        <div class="panel-body">
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Начална дата на плащане</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-calendar" aria-hidden="true"></i></span>
                                                    <fmt:parseDate pattern="yyyy-MM-dd" value="${payment.getDate()}" var="parsedDate" />
                                                    <fmt:formatDate value="${parsedDate}" var="date" pattern="yyyy-MM-dd" />
                                                    <input readonly name="payment-start-date" id="by-period-start-date" placeholder="Начална дата на повтарящо плащане" value="${date}" class="form-control" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Крайна дата на плащане</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-calendar" aria-hidden="true"></i></span>
                                                    <fmt:parseDate pattern="yyyy-MM-dd" value="${payment.getDate()}" var="parsedDate" />
                                                    <fmt:formatDate value="${parsedDate}" var="date" pattern="yyyy-MM-dd" />
                                                    <input readonly name="payment-finish-date" id="by-period-finish-date" placeholder="Крайна дата на повтарящо плащане" value="${date}" class="form-control" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Категория</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-bars" aria-hidden="true"></i></span>
                                                    <select name="payment-category" id="by-period-category">
                                                        <c:forEach items="${categories}" var="element">
                                                            <option value="${element.getId()}">${element.getName()}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <spring:url var = "categoriesAdd" value='/categories/add' />
                                                    <div class="input-add-button">
                                                        <a href="${categoriesAdd}" target="_blank"><i class="fa fa-plus-circle" aria-hidden="true"></i></a>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Плащането е</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-bars" aria-hidden="true"></i></span>
                                                    <select name="payment-recuring-type" id="by-period-rec-type">
                                                        <c:forEach items="${recTypes}" var="element">
                                                            <option value="${element.getId()}">${element.getName()}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="step-period-2" class="">
                            Step Content
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>