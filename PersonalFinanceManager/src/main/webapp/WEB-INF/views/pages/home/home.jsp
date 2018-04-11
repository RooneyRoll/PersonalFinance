<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:if test="${pageContext.request.userPrincipal.name != null}">
    <div class="content">
        <div class="partial-contentainer size-2 side-padding">
            <h2>Здравейте ${pageContext.request.userPrincipal.name}!</h2>
            <p>На тази страница имате възможност да прегледате състоянието на вашия бюджет, както и статистики, 
                свързани с нето.</p>
        </div>
    </div>
</c:if>
<c:if test="${pageContext.request.userPrincipal.name == null}">
    <div class="row">
        <div class="col-4 col-md-4">
            <div class="row">
                <div class="col-12 col-md-12">
                    <div class="panel panel-default">
                        <div class='panel-heading'>Управление на финанси</div>
                        <div class="panel-body"><p>Умението да управлявате парите си означава да осъществявате дългосрочните и краткосрочните си цели с лекота, да се наслаждавате пълноценно на реализираните си планове, както и да се радвате на финансова стабилност.</p></div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-12 col-md-12">
                    <div class="panel panel-default">
                        <div class='panel-heading'>Дневник на разходите</div>
                        <div class="panel-body"><p>Следващата стъпка е да разберете за какво харчите парите си.
                                Приложението за управление на лични финанси предлага функционалности за подробни справки на вашите разходи.
                                План на месечните плащания</p>
                            <br></div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-12 col-md-12">
                    <div class="panel panel-default">
                        <div class='panel-heading'>План за спестяване</div>
                        <div class="panel-body"><p>След като знаете каква част от доходите Ви ще покрият задължителните Ви месечни разходи, ще можете лесно да прецените и каква сума да спестите.</p>
                            <br></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-4 col-md-4">
            <div class="row">
                <div class="col-12 col-md-12">
                    <div class="panel panel-default">
                        <div class='panel-heading'>Бюджет</div>
                        <div class="panel-body"><p>Съставянето на бюджет е първата стъпка, когато искате да сложите финансите си в ред. За целта можеш да си помогнеш с текущото онлайн приложение, което ще Ви улесни в управлението на личните финанси.
                                Дневник на разходите</p></div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-12 col-md-12">
                    <div class="panel panel-default">
                        <div class='panel-heading'> План на месечните плащания</div>
                        <div class="panel-body"><p>Много е важно да правите плащанията по месечните си задължения навреме, за да избегнете начисляване на лихви за просрочията. С помощта на приложението, можете да изготвите план на месечните си плащания и да ги контролирате.</p></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-4 col-md-4">
            <div class="row">
                <div class="col-12 col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div id="myCarousel" class="carousel slide" data-ride="carousel">
                                <!-- Indicators -->
                                <ol class="carousel-indicators">
                                    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                                    <li data-target="#myCarousel" data-slide-to="1"></li>
                                    <li data-target="#myCarousel" data-slide-to="2"></li>
                                </ol>

                                <!-- Wrapper for slides -->
                                <div class="carousel-inner">
                                    <div class="item active">
                                        <img src="<c:url value='/resources/images/carousel/1.jpeg'/>" alt="Los Angeles">
                                    </div>

                                    <div class="item">
                                        <img src="<c:url value='/resources/images/carousel/2.jpeg'/>" alt="Chicago">
                                    </div>

                                    <div class="item">
                                        <img src="<c:url value='/resources/images/carousel/3.jpeg'/>" alt="New York">
                                    </div>
                                </div>

                                <!-- Left and right controls -->
                                <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                                    <span class="glyphicon glyphicon-chevron-left"></span>
                                    <span class="sr-only">Previous</span>
                                </a>
                                <a class="right carousel-control" href="#myCarousel" data-slide="next">
                                    <span class="glyphicon glyphicon-chevron-right"></span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>



