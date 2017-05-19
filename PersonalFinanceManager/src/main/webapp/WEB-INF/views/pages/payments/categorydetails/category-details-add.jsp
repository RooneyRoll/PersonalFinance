<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    $(document).ready(function () {
        $('select').select2({minimumResultsForSearch: -1});
    });
</script>
<div class="form-container">
    <c:if test="${errorMessage != null}"><tiles:insertAttribute name="categoryAddError" /></c:if>
        <div class="form-content">
            <form id="payment-category-add-form" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="input-container size-1">
                <div class="input-title-holder no-select">
                    <span> 
                        Име на категория<span class="required-tip">&nbsp;*</span>
                    </span>
                </div>
                <div class="input-holder">
                    <select id="category-select" name="categoryId">
                        <c:forEach items="${categoriesMap}" var="element">
                           
                            <option value="${element.getId()}">${element.getName()}</option>
                            
                        </c:forEach>
                    </select>
                    <!--
                                        <select>
                                            <option>1</option>
                                            <option>2</option>
                                            <option>3</option>
                                            <option>4</option>
                                            <option>5</option>
                                            <option>6</option>
                                            <option>7</option>
                    
                                        </select>-->
                </div>
            </div><div class="input-container size-1">
                <div class="input-title-holder no-select">
                    <span> 
                        Лимит на категорията
                    </span>
                </div>
                <div class="input-holder">
                    <!--<textarea resize="false" placeholder="Лимит" name="amount"></textarea>-->
                    <input type="number" name="amount"  />
                </div>
            </div>

            <div class="input-container size-1">
                <div class="input-title-holder no-select">
                    <span> 
                        От:
                    </span>
                </div>
                <div class="input-holder">
                    <input type="date" name="fromDate"  />
                </div>
            </div>

            <div class="input-container size-1">
                <div class="input-title-holder no-select">
                    <span> 
                        До:
                    </span>
                </div>
                <div class="input-holder">
                    <input type="date" name="toDate"  />
                </div>
            </div>

            <div class="buttons-container size-1">
                <button name="submit-button" type="submit" value="1" class="button animation">Запази</button>
                <button name="submit-button" type="submit" value="2" class="button animation">Запази и редактирай</button>
                <button name="submit-button" type="submit" value="3" class="button animation">Запази и Нов</button>
                <button type="reset" class="button animation">Изчистване на форма</button>
            </div>
        </form>
    </div>
</div>