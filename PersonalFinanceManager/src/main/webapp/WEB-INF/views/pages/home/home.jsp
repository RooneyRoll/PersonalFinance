<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="<c:url value='/resources/js/slider-pro/dist/js/jquery.sliderPro.min.js' />"></script>
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
            autoHeight: false,
            responsive: true
        });
    });
</script>

<div class="form-container">
    <div class="form-content">
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
            След като знаете каква част от доходите Ви ще покрият задължителните Ви месечни разходи, ще можете лесно да прецените и каква сума да спестите.
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
</div>


