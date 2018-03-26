/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var recurringPaymentUtils = function (overviewUrl) {

    var overviewTypes = {
        dateFirst: "date",
        amountFirst: "amount",
        calendar: "calendar"
    };

    this.initialize = function () {
        this.initTabs();
        this.initStepWizards();
    };

    var buildDataForRequest = function (parent) {
        var requestData = {};
        var missPerPeriods = parent.find("input[name='payment-miss-periods']").val();
        var periodsCount = parent.find("input[name='payment-periods-count']").val();
        var paymentName = parent.find("input[name='payment-name']").val();
        var paymentDescription = parent.find("textarea[name='payment-description']").val();
        var paymentFinalAmount = parent.find("input[name='payment-final-amount']").val();
        var paymentStartDate = parent.find("input[name='payment-start-date']").val();
        var paymentFinishDate = parent.find("input[name='payment-finish-date']").val();
        var paymentCategory = parent.find("select[name='payment-category']:selected").val();
        var recuringType = parent.find("select[name='payment-recuring-type']:selected").val();
        if (typeof missPerPeriods !== "undefined")
            requestData.missPerPeriods = missPerPeriods;
        if (typeof periodsCount !== "undefined")
            requestData.periodsCount = periodsCount;
        if (typeof paymentName !== "undefined")
            requestData.paymentName = paymentName;
        if (typeof paymentDescription !== "undefined")
            requestData.paymentDescription = paymentDescription;
        if (typeof paymentFinalAmount !== "undefined")
            requestData.paymentFinalAmount = paymentFinalAmount;
        if (typeof paymentStartDate !== "undefined")
            requestData.paymentStartDate = paymentStartDate;
        if (typeof paymentFinishDate !== "undefined")
            requestData.paymentFinishDate = paymentFinishDate;
        if (typeof paymentCategory !== "undefined")
            requestData.paymentCategory = paymentCategory;
        if (typeof recuringType !== "undefined")
            requestData.recuringType = recuringType;
        return requestData;
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
                data = JSON.stringify(buildDataForRequest($(this)));
                var token = $('meta[name=\"_csrf\"]').attr('content');
                var header = $('meta[name=\"_csrf_header\"]').attr('content');
                $.ajax({
                    url: overviewUrl,
                    type: 'POST',
                    async: true,
                    method: 'post',
                    contentType: 'application/json',
                    data:data,
                    beforeSend: function (xhr, settings) {
                        xhr.setRequestHeader(header, token);
                    }
                    
                });
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


