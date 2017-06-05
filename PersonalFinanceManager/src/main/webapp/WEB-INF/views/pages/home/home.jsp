<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="<c:url value='/resources/js/slider-pro/dist/js/jquery.sliderPro.min.js' />"></script>
<script src="<c:url value='/resources/js/Highcharts-5.0.12/code/highcharts.js' />"></script>
<script>
    $(document).ready(function () {
        $('#my-slider').sliderPro({
            width: "100%",
            orientation: 'horizontal',
            thumbnailPosition: 'right',
            buttons: true,
            thumbnailWidth: 40,
            thumbnailHeight: 40,
            arrows: false,
            fade: true,
            fadeArrows: false,
            touchSwipe: true,
            imageScaleMode: "cover",
            allowScaleUp: true,
            fullScreen: false,
            autoHeight: true,
            responsive: true
        })
    <c:if test="${pageContext.request.userPrincipal.name == null}">
    });
    </c:if>
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <spring:url var = "paymentsServiceUrl" value ="/getPayments"/>
        <spring:url var = "plannedVsSpentUrl" value ="/budget/plannedVsSpent"/>
    var dateObj = new Date();
    var month = dateObj.getUTCMonth() + 1; //months from 1-12
    var year = dateObj.getUTCFullYear();
    function getBudgetStatus(month, year, chart) {
        var data = JSON.stringify({"month": month, "year": year});
        var sum = [];
        $.ajax({
            type: "POST",
            url: "${plannedVsSpentUrl}",
            dataType: "json",
            data: data,
            async: false,
            contentType: 'application/json',
            beforeSend: function (xhr, settings) {
                var token = $('meta[name="_csrf"]').attr('content');
                xhr.setRequestHeader('X-CSRF-TOKEN', token);
            }
        }).done(function (data) {
            var sumTotal = sumTotal = 0;
            var subTitleText = "";
            $(data).each(function (key, val) {
                sumTotal = 0;
                var color = "#7CB5EC";
                if (key !== 0)
                    color = "#E74C3C";
                sum.push({y: val.planned, color: color, name: "Планирани " + val.paymentType});
                sum.push({y: val.actual, color: color, name: "Действителни " + val.paymentType});
                sumTotal = parseInt(val.planned) - (val.actual);
                subTitleText += "Оставащи " + val.paymentType + " до план: " + sumTotal + "<br>";
            });
            if (typeof (chart.series) !== 'undefined') {
                chart.series[0].setData(sum, true);
                chart.setTitle(null, {text: subTitleText});
            }
        });
        return sum;
    }
    function getPaymentsStatus(month, year, chart) {
        console.log(month);
        console.log(year);
        var series = [];
        var data = JSON.stringify({"month": month, "year": year});
        $.ajax({
            type: "POST",
            url: "${paymentsServiceUrl}",
            dataType: "json",
            data: data,
            async: false,
            contentType: 'application/json',
            beforeSend: function (xhr, settings) {
                var token = $('meta[name="_csrf"]').attr('content');
                xhr.setRequestHeader('X-CSRF-TOKEN', token);
            }
        }).done(function (data) {
            $(data).each(function (key, val) {
                var name = val.paymentType;
                var color = "#7CB5EC";
                var opacity = 1;
                if (name !== "Приходи") {
                    color = "#E74C3C";
                }
                if (val.budget) {
                    opacity = 0;
                    name = name + " - план"
                } else {
                    opacity = 0.3;
                }
                var serie = {
                    name: name,
                    data: val.amounts,
                    fillOpacity: opacity,
                    color: color
                };
                series.push(serie);
                sumTotal = 0;

            });
            if (typeof (chart.series) !== 'undefined') {
                $(series).each(function (key, serie) {
                    chart.series[key].setData(serie.data, true);
                });
            }
        });
        return series;
    }
    function getBudgetCategoryStatus(month, year, chart) {
        var data = JSON.stringify({"month": month, "year": year});
        var sum = [];
        <spring:url var = "serviceUrl" value ="/budget/plannedVsSpentCategories"/>
        $.ajax({
            type: "POST",
            url: "${serviceUrl}",
            dataType: "json",
            data: data,
            async: false,
            contentType: 'application/json',
            beforeSend: function (xhr, settings) {
                var token = $('meta[name="_csrf"]').attr('content');
                xhr.setRequestHeader('X-CSRF-TOKEN', token);
            }
        }).done(function (data) {
            $(data).each(function (key, val) {
                var color = "#7CB5EC";
                var secondDcolor = "#A1D1FF";
                if (key !== 0) {
                    color = "#E74C3C";
                    secondDcolor = "#FF7061";
                }
                sum.push({y: val.planned, color: color, name: /*"Планирани " +*/ val.categoryName});
                sum.push({y: val.actual, color: secondDcolor, name: /*"Действителни " +*/ val.categoryName});
                var percent = val.percents;
                var catId = val.categoryId;
                var widthPercent = 0;
                if (percent > 100) {
                    widthPercent = 100;
                } else {
                    widthPercent = percent;
                }
                percent = parseFloat(widthPercent.toFixed(2));
                $("#percent_" + catId).css("background-color", color).animate({
                    width: widthPercent + '%'
                });
                $("#percent_" + catId).text(percent + "%");
            });
            if (typeof (chart.series) !== 'undefined') {
                chart.series[0].setData(sum, true);
            }
        });
        return sum;
    }
    new Highcharts.chart('container-1', {
        chart: {
            type: 'area'
        },
        title: {
            text: 'Приходи и разходи за месец'
        },
        xAxis: {
            allowDecimals: false,
            labels: {
                formatter: function () {
                    return this.value; //
                }
            }
        },
        yAxis: {
            title: {
                text: 'Стойност'
            },
            labels: {
                formatter: function () {
                    return this.value; //
                }
            }
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.y:,.0f}</b><br/> за ден {point.x}'
        },
        plotOptions: {
            area: {
                pointStart: 1,
                marker: {
                    enabled: false,
                    symbol: 'circle',
                    radius: 1,
                    states: {
                        hover: {
                            enabled: true
                        }
                    }
                }
            }
        },
        series: getPaymentsStatus(month, year, this)
    });
    new Highcharts.Chart({
        chart: {
            renderTo: 'container-2',
            type: 'column',
            events: {
                load: function (event) {

                }
            }
        },
        title: {
            text: 'Планирани и действителни приходи/разходи'
        },
        xAxis: {
            type: 'category',
            labels: {
                style: {
                    fontSize: '14px',
                    fontFamily: 'Roboto,Aral, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Стойност'
            }
        },
        tooltip: {
            pointFormat: '<b>{point.y:.1f}</b>'
        },
        legend: {
            enabled: false
        },
        series: [{
                animation: false,
                name: "",
                data: getBudgetStatus(month, year, this),
                dataLabels: {
                    enabled: true,
                    rotation: 0,
                    color: '#FFFFFF',
                    align: 'center',
                    y: 35,
                    style: {
                        fontSize: '14px',
                        fontFamily: 'Roboto,Arial, sans-serif'
                    }
                }
            }]
    });
    new Highcharts.Chart({
    chart: {
    renderTo: 'container-3',
            type: 'column',
            events: {
            load: function (event) {

            }
            }
    },
            title: {
            text: 'Планирани и действителни приходи/разходи'
            },
            xAxis: {
            type: 'category',
                    labels: {
                    style: {
                    fontSize: '14px',
                            fontFamily: 'Roboto,Aral, sans-serif'
                    }
                    }
            },
            yAxis: {
            min: 0,
                    title: {
                    text: 'Стойност'
                    }
            },
            tooltip: {
            pointFormat: '<b>{point.y:.1f}</b>'
            },
            legend: {
            enabled: false
            },
            series: [{
            animation: false,
                    name: "",
                    data: getBudgetCategoryStatus(month, year, this),
                    dataLabels: {
                    enabled: true,
                            rotation: 0,
                            color: '#FFFFFF',
                            align: 'center',
                            y: 35,
                            style: {
                            fontSize: '14px',
                                    fontFamily: 'Roboto,Arial, sans-serif'
                            }
                    }
            }]
    });
    }
    );
    </c:if>
</script>
<c:if test="${pageContext.request.userPrincipal.name != null}">
    <div class="content">
        <div class="partial-contentainer size-2 side-padding">
            <h2>Здравейте ${pageContext.request.userPrincipal.name}!</h2>
            <p>На тази страница имате възможност да прегледате състоянието на вашия бюджет, както и статистики, 
                свързани с нето.</p>
        </div><div class="partial-contentainer size-2 side-padding">
            <div class="slider-pro" id="my-slider">
                <div class="sp-slides">
                    <div class="sp-slide">
                        <div id="container-1" style="min-width: 300px; height: 400px; margin: 0 auto"></div>
                    </div>
                    <div class="sp-slide">
                        <div id="container-2" style="min-width: 300px; height: 400px; margin: 0 auto"></div>
                    </div>
                    <div class="sp-slide">
                        <div id="container-3" style="min-width: 300px; height: 400px; margin: 0 auto"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>
<c:if test="${pageContext.request.userPrincipal.name == null}">
    <div class="content">
        <div class="partial-contentainer size-2 side-padding">
            <p>Умението да управлявате парите си означава да осъществявате дългосрочните и 
                краткосрочните си цели с лекота, да се наслаждавате пълноценно на реализираните 
                си планове, както и да се радвате на финансова стабилност.</p>
            <h2>Създаване на бюджет</h2>
            <p>Съставянето на бюджет е първата стъпка, когато искате да сложите финансите си в
                ред. За целта можеш да си помогнеш с текущото  онлайн приложение, което ще Ви улесни в управлението на личните финанси.</p>
            <h2>Дневник на разходите</h2>
            <p>Следващата стъпка е да разберете за какво харчите парите си.<br>
                Приложението за управление на лични финанси предлага функционалности за подробни справки на вашите разходи.</p>
            <h2>План на месечните плащания</h2>
            <p>Много е важно да правите плащанията по месечните си задължения навреме, за да избегнете начисляване на лихви за просрочията.
                С помощта на приложението, можете да изготвите план на месечните си плащания и да ги контролирате.</p>
            <h2>План за спестяване</h2>
            <p>След като знаете каква част от доходите Ви ще покрият задължителните Ви месечни разходи, ще можете лесно да прецените и каква сума да спестите.</p>
        </div><div class="partial-contentainer size-2 side-padding">
            <div class="slider-pro" id="my-slider">
                <div class="sp-slides">
                    <div class="sp-slide">
                        <img class="sp-image" src="<c:url value='/resources/images/carousel/1.jpeg'/>"/>
                    </div>
                    <div class="sp-slide">
                        <img class="sp-image" src="<c:url value='/resources/images/carousel/2.jpeg'/>"/>
                    </div>
                    <div class="sp-slide">
                        <img class="sp-image" src="<c:url value='/resources/images/carousel/3.jpeg'/>"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>



