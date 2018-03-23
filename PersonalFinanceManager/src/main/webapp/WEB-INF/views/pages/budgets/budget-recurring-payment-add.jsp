<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pfm.enums.RecurringTypes" %>
<script src="<c:url value='/resources/js/basic/recurringPaymentUtils.js' />"></script>
<script>
    $(document).ready(function () {
        $("#categories-select,#recurring-types-select").select2({"theme": "classic"});
        var utils = new recurringPaymentUtils();
        utils.initialize();
    });

</script>
<div class="row">
    <div class="col-12 col-md-12">
        <div class="tabs-container">
            <div data-pws-tab="anynameyouwant1" data-pws-tab-name="<i class='fa fa-calendar' aria-hidden='true'></i>&nbsp  Създаване спрямо сума">
                <div id="smartwizard-amount-first">
                    <ul>
                        <li><a href="#step-1">Настройки<br /><small>за плащане</small></a></li>
                        <li><a href="#step-2">Преглед<br /><small>на план за насрочени плащания</small></a></li>
                    </ul>
                    <div>
                        <div id="step-1" class="">
                            <div class="row">
                                <div class="col-6 col-md-6">
                                    <div class="panel panel-primary">
                                        <div class='panel-heading'>Данни за плащане</div>
                                        <div class="panel-body">
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Име на плащане</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                                    <input name="recurringPaymentName" type="text" class="form-control" placeholder="Име на плащане" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Описание</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                                    <textarea class="form-control"  resize="false" placeholder="Описание" name="recurringPaymentDescription" aria-describedby="basic-addon1"></textarea>
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Желана крайна сума</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-usd" aria-hidden="true"></i></span>
                                                    <input name="recurringPaymentFinalAmount" id="finalAmount" value="0" type="text" class="form-control" placeholder="Сума за период" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Начална сума</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-usd" aria-hidden="true"></i></span>
                                                    <input name="recurringPaymentInitialAmount" id="initialAmount" value="0" type="text" class="form-control" placeholder="Сума за период" aria-describedby="basic-addon1">
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
                                                    <input readonly name="recurringPaymentPeriodStart" id="recurringPaymentStartDate" placeholder="Начална дата на повтарящо плащане" value="${date}" class="form-control" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Брой периоди</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-sort" aria-hidden="true"></i></span>
                                                    <input name="recurringPaymentPeriodsCount" id = "periodsCount" type="text" value="1" class="form-control" placeholder="Брой периоди" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Пропускане през брой периоди</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-sort" aria-hidden="true"></i></span>
                                                    <input name="recurringPaymentPeriodsMiss" id = "periodsMiss" type="text" value="0" class="form-control" placeholder="Пропускане през" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Категория</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-bars" aria-hidden="true"></i></span>
                                                    <select id="categories-select" name="recurringPaymentCategory">
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
                                                    <select id="recurring-types-select" name="recurringPaymentRecurringType">
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
                        <div id="step-2" class="">
                            Step Content
                        </div>
                    </div>
                </div>
            </div>
            <div data-pws-tab="anynameyouwant2" data-pws-tab-name="<i class='fa fa-files-o' aria-hidden='true'></i>&nbsp Създаване спрямо периоди">
                <div id="smartwizard-period-first">
                    <ul>
                        <li><a href="#step-1">Настройки<br /><small>за плащане</small></a></li>
                        <li><a href="#step-2">Преглед<br /><small>на план за насрочени плащания</small></a></li>
                    </ul>
                    <div>
                        <div id="step-1" class="">
                            <div class="row">
                                <div class="col-6 col-md-6">
                                    <div class="panel panel-success">
                                        <div class='panel-heading'>Данни за плащане</div>
                                        <div class="panel-body">
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Име на плащане</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                                    <input name="recurringPaymentName" type="text" class="form-control" placeholder="Име на плащане" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Сума за период</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-usd" aria-hidden="true"></i></span>
                                                    <input name="recurringPaymentAmount" value="0" id="periodAmount" type="text" class="form-control" placeholder="Сума за период" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Описание</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                                    <textarea class="form-control"  resize="false" placeholder="Описание" name="recurringPaymentDescription" aria-describedby="basic-addon1"></textarea>
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
                                                    <input readonly name="recurringPaymentPeriodStart" id="recurringPaymentStartDate" placeholder="Начална дата на повтарящо плащане" value="${date}" class="form-control" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Крайна дата на плащане</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-calendar" aria-hidden="true"></i></span>
                                                    <fmt:parseDate pattern="yyyy-MM-dd" value="${payment.getDate()}" var="parsedDate" />
                                                    <fmt:formatDate value="${parsedDate}" var="date" pattern="yyyy-MM-dd" />
                                                    <input readonly name="recurringPaymentPeriodEnd" id="reccuringPaymentEndDate" placeholder="Крайна дата на повтарящо плащане" value="${date}" class="form-control" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Брой периоди</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-sort" aria-hidden="true"></i></span>
                                                    <input name="recurringPaymentPeriodsCount" id = "periodsCount" type="text" value="1" class="form-control" placeholder="Брой периоди" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Пропускане през брой периоди</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-sort" aria-hidden="true"></i></span>
                                                    <input name="recurringPaymentPeriodsMiss" id = "periodsMiss" type="text" value="0" class="form-control" placeholder="Пропускане през" aria-describedby="basic-addon1">
                                                </div>
                                            </div>
                                            <div class="form-group ">
                                                <label for="exampleFormControlInput1">Категория</label>
                                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-bars" aria-hidden="true"></i></span>
                                                    <select id="categories-select" name="recurringPaymentCategory">
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
                                                    <select id="recurring-types-select" name="recurringPaymentRecurringType">
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
                        <div id="step-2" class="">
                            Step Content
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

























<!--

<form id="recurring-payment-add-form" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="row">
        <div class="col-6 col-md-6">
            <div class="row">
                <div class="col-12 col-md-12">
                    <div class="panel panel-success">
                        <div class='panel-heading'>Данни за плащане</div>
                        <div class="panel-body">
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Име на плащане</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                    <input name="recurringPaymentName" type="text" class="form-control" placeholder="Име на плащане" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Желана крайна сума</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-usd" aria-hidden="true"></i></span>
                                    <input name="recurringPaymentFinalAmount" id="finalAmount" value="0" type="text" class="form-control" placeholder="Сума за период" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Начална сума</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-usd" aria-hidden="true"></i></span>
                                    <input name="recurringPaymentInitialAmount" id="initialAmount" value="0" type="text" class="form-control" placeholder="Сума за период" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Сума за период</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-usd" aria-hidden="true"></i></span>
                                    <input name="recurringPaymentAmount" value="0" id="periodAmount" type="text" class="form-control" placeholder="Сума за период" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Описание</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-pencil" aria-hidden="true"></i></span>
                                    <textarea class="form-control"  resize="false" placeholder="Описание" name="recurringPaymentDescription" aria-describedby="basic-addon1"></textarea>
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
                        <div class='panel-heading'>Настройки на плащане</div>
                        <div class="panel-body">
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Начална дата на плащане</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-calendar" aria-hidden="true"></i></span>
                                    <fmt:parseDate pattern="yyyy-MM-dd" value="${payment.getDate()}" var="parsedDate" />
                                    <fmt:formatDate value="${parsedDate}" var="date" pattern="yyyy-MM-dd" />
                                    <input readonly name="recurringPaymentPeriodStart" id="recurringPaymentStartDate" placeholder="Начална дата на повтарящо плащане" value="${date}" class="form-control" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Крайна дата на плащане</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-calendar" aria-hidden="true"></i></span>
                                    <fmt:parseDate pattern="yyyy-MM-dd" value="${payment.getDate()}" var="parsedDate" />
                                    <fmt:formatDate value="${parsedDate}" var="date" pattern="yyyy-MM-dd" />
                                    <input readonly name="recurringPaymentPeriodEnd" id="reccuringPaymentEndDate" placeholder="Крайна дата на повтарящо плащане" value="${date}" class="form-control" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Брой периоди</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-sort" aria-hidden="true"></i></span>
                                    <input name="recurringPaymentPeriodsCount" id = "periodsCount" type="text" value="1" class="form-control" placeholder="Брой периоди" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Пропускане през брой периоди</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-sort" aria-hidden="true"></i></span>
                                    <input name="recurringPaymentPeriodsMiss" id = "periodsMiss" type="text" value="0" class="form-control" placeholder="Пропускане през" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group ">
                                <label for="exampleFormControlInput1">Категория</label>
                                <div class="input-group col-lg-12 col-md-12 col-sm-12">
                                    <span class="input-group-addon" id="basic-addon1"><i class="fa fa-bars" aria-hidden="true"></i></span>
                                    <select id="categories-select" name="recurringPaymentCategory">
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
                                    <select id="recurring-types-select" name="recurringPaymentRecurringType">
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
    </div>
    <div class="btn-group">
        <button name="submit-button" class="btn btn-primary" type="submit" value="1">Запази</button>
        <button name="submit-button" class="btn btn-primary" type="submit" value="2">Запази и редактирай</button>
        <button name="submit-button" class="btn btn-primary" type="submit" value="3">Запази и Нов</button>
        <button type="reset" class="btn btn-primary">Изчистване на форма</button>
    </div>
</form>-->