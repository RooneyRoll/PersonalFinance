/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global moment */

var recurringPaymentUtils = function (overviewUrls, recuringTypes) {
    this.currentStartDate = new Date();
    this.finalDateCalendar;
    var instance = this;

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
        var paymentCategory = parent.find("select[name='payment-category'] option:selected").val();
        var recuringType = parent.find("select[name='payment-recuring-type'] option:selected").val();
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
            requestData.paymentRecuringType = recuringType;
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

    var initTabs = function () {
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

    var initDatePicker = function (selector) {
        return $(selector).flatpickr({
            'locale': 'bg',
            'mode': 'single',
            "minDate": 'today',
            'enableTime': false,
            'defaultDate': 'today',
            'dateFormat': "Y-m-d",
            onChange: function (rawdate, altdate, FPOBJ) {
                if (selector === "#by-period-start-date") {
                    instance.currentStartDate = rawdate[0];
                    syncCalendars();
                }
                FPOBJ.close();
            }
        });
    };

    var addDaysToDatepicker = function (datePicker, days) {
        var newDate = moment(instance.currentStartDate, "DD-MM-YYYY");
        newDate.add('days', days);
        var date = newDate.toDate();
        instance.finalDateCalendar.set("minDate", date);
        instance.finalDateCalendar.setDate(date);
    };

    var syncCalendars = function () {
        var selectedVal = $("#by-period-rec-type").find("option:selected").val();
        switch (selectedVal) {
            case recuringTypes.daily:
                addDaysToDatepicker(instance.finalDateCalendar, 1);
                break;
            case recuringTypes.weekly:
                addDaysToDatepicker(instance.finalDateCalendar, 7);
                break;
            case recuringTypes.monthly:
                var now = moment();
                var days = moment(now).daysInMonth();
                addDaysToDatepicker(instance.finalDateCalendar, days);
                break;
            case recuringTypes.yearly:
                addDaysToDatepicker(instance.finalDateCalendar, 365);
                break;
        }
    };

    var initSelect2 = function (selector) {
        $(selector).select2({"theme": "classic"});
        if (selector === "#by-period-rec-type") {
            $(selector).on("select2:select", function (e) {
                syncCalendars();
            });
        }
    };

    var initStepWizards = function () {
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
        });
    };

    this.initialize = function () {
        initTabs();
        initStepWizards();
        this.finalDateCalendar = initDatePicker("#by-period-finish-date");
        initDatePicker("#by-period-start-date");
        initDatePicker("#by-amount-start-date");
        initSelect2("#by-period-rec-type");
        initSelect2("#by-amount-category");
        initSelect2("#by-period-category");
        initSelect2("#by-amount-rec-type");
    };
};


