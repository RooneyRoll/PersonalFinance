/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var recurringPaymentUtils = function (overviewUrls) {
    this.initialize = function () {
        this.initTabs();
        this.initStepWizards();
    };

    var isDefined = function (variable) {
        return typeof variable !== "undefined";
    };

    var buildDataForRequest = function (parent) {
        var requestData = {};
        var missPerPeriods = parent.find("input[name='payment-miss-periods']").val();
        var periodsCount = parent.find("input[name='payment-periods-count']").val();
        var paymentName = parent.find("input[name='payment-name']").val();
        var paymentDescription = parent.find("textarea[name='payment-description']").val();
        var paymentFinalAmount = parent.find("input[name='payment-final-amount']").val();
        var paymentInitialAmount = parent.find("input[name='payment-initial-amount']").val();
        var paymentSinglePeriodAmount = parent.find("input[name='payment-period-amount']").val();
        var paymentStartDate = parent.find("input[name='payment-start-date']").val();
        var paymentFinishDate = parent.find("input[name='payment-finish-date']").val();
        var paymentCategory = parent.find("select[name='payment-category']:selected").val();
        var recuringType = parent.find("select[name='payment-recuring-type']:selected").val();
        if (isDefined(missPerPeriods))
            requestData.missPerPeriods = missPerPeriods;
        if (isDefined(periodsCount))
            requestData.periodsCount = periodsCount;
        if (isDefined(paymentName))
            requestData.paymentName = paymentName;
        if (isDefined(paymentDescription))
            requestData.paymentDescription = paymentDescription;
        if (isDefined(paymentFinalAmount))
            requestData.paymentFinalAmount = paymentFinalAmount;
        if (isDefined(paymentStartDate))
            requestData.paymentStartDate = paymentStartDate;
        if (isDefined(paymentFinishDate))
            requestData.paymentFinishDate = paymentFinishDate;
        if (isDefined(paymentCategory))
            requestData.paymentCategory = paymentCategory;
        if (isDefined(recuringType))
            requestData.recuringType = recuringType;
        if (isDefined(paymentInitialAmount))
            requestData.paymentInitialAmount = paymentInitialAmount;
        if (isDefined(paymentSinglePeriodAmount))
            requestData.paymentSinglePeriodAmount = paymentSinglePeriodAmount;
        return requestData;
    };

    var createOverviewRequest = function (data) {
        var token = $('meta[name=\"_csrf\"]').attr('content');
        var header = $('meta[name=\"_csrf_header\"]').attr('content');
        var url;
        if (isDefined(data.paymentFinalAmount)) {
            url = overviewUrls.byAmount;
        } else {
            url = overviewUrls.byPeriod;
        }
        $.ajax({
            url: url,
            type: 'POST',
            async: true,
            method: 'post',
            contentType: 'application/json',
            data: JSON.stringify(data),
            beforeSend: function (xhr, settings) {
                xhr.setRequestHeader(header, token);
            }
        });
    };

    this.initTabs = function () {
        $('.tabs-container').pwstabs({
            effect: 'scale',
            defaultTab: 1,
            containerWidth: '100%',
            tabsPosition: 'horizontal',
            horizontalPosition: 'top',
            verticalPosition: 'left',
            responsive: true,
            theme: 'pws_theme_green',
            rtl: false,
            onBeforeFirstInit: function () {},
            onAfterFirstInit: function () {},
            onBeforeInit: function () {},
            onAfterInit: function () {},
            onBeforeChange: function () {},
            onAfterChange: function () {}
        });
    };

    this.initStepWizards = function () {
        $('#smartwizard-period-first,#smartwizard-amount-first').smartWizard({
            selected: 0,
            keyNavigation: true,
            autoAdjustHeight: false,
            cycleSteps: false,
            backButtonSupport: true,
            useURLhash: true,
            lang: {
                next: 'Напред',
                previous: 'Назад'
            },
            toolbarSettings: {
                toolbarPosition: 'bottom',
                toolbarButtonPosition: 'right',
                showNextButton: true,
                showPreviousButton: true
            },
            anchorSettings: {
                anchorClickable: true,
                enableAllAnchors: false,
                markDoneStep: true,
                enableAnchorOnDoneStep: true
            },
            contentURL: null,
            disabledSteps: [],
            errorSteps: [],
            theme: 'default',
            transitionEffect: 'fade',
            transitionSpeed: '400'
        }).on("showStep", function (e, anchorObject, stepNumber, stepDirection) {
            if (stepNumber === 1 && stepDirection === "forward") {
                data = buildDataForRequest($(this));
                createOverviewRequest(data);
            }
            ;
        });
    };

    $('#by-amount-start-date,#by-period-start-date,#by-period-finish-date').flatpickr({
        'locale': 'bg',
        'mode': 'single',
        'enableTime': false,
        'defaultDate': 'today',
        'dateFormat': "Y-m-d",
        onChange: function (rawdate, altdate, FPOBJ) {
            FPOBJ.close();
        }
    });
};


