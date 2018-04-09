/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global moment */

var recurringPaymentUtils = function (urls, recuringTypes, formToSubmit) {
    this.currentStartDate = new Date();
    this.draftData;
    this.finalDateCalendar;
    this.yearCalendarByAmount;
    this.yearCalendarByPeriod;
    this.requestTypes = {
        byAmount: 1,
        byPeriod: 2
    };

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

    var processOverviewResponse = function (data, name, description) {
        var calendarData = [];
        $.each(data.payments, function (key, paymentInfo) {
            var dateNumber = parseInt(paymentInfo.dateRepresent);
            var date = new Date(dateNumber);
            var currentPaymentData = {
                id: 0,
                name: name,
                location: description,
                startDate: date,
                endDate: date,
                color: "#6db33f"
            };
            calendarData[key] = currentPaymentData;
        });
        return calendarData;
    };

    var fillDraftData = function (data) {
        var clone = Object.assign({}, data);
        delete clone.payments;
        var startDate = new Date(data.paymentStartDate);
        var finishDate = new Date(data.paymentFinishDate);
        clone.paymentStartDate = moment(startDate).format("YYYY-MM-DD");
        clone.paymentFinishDate = moment(finishDate).format("YYYY-MM-DD");
        instance.draftData = clone;
    };

    var createSaveRequest = function (data) {
        var token = $('meta[name=\"_csrf\"]').attr('content');
        formToSubmit.html("");
        var input = $("<input type='hidden' name = '_csrf'  value = '" + token + "'/>");
        formToSubmit.append(input);
        $.each(data, function (key, val) {
            var input = $("<input type='hidden' name = '" + key + "'  value = '" + val + "'/>");
            formToSubmit.append(input);
        });
        formToSubmit.submit();
    };

    var initValidation = function () {
        $("#form-amount").validate({
            rules: {
                "payment-name": {"required": true},
                "payment-final-amount": {"required": true, "number": true, "min": 1},
                "payment-periods-count": {"required": true, "number": true, "min": 1},
                "payment-miss-periods": {"required": true, "number": true, "min": 0},
                "payment-initial-amount": {"required": true, "number": true, "min": 0}
            },
            messages: {
                "payment-name": "Моля въведете име на плащане.",
                "payment-final-amount": "Моля въведете крайна сума за плащане.",
                "payment-periods-count": "Моля въведете брой периоди свързани с плащане.",
                "payment-miss-periods": "Моля въведете стойност за пропускане между периодите.",
                "payment-initial-amount": "Моля въведете начално състояние на плащането."
            },
            errorClass: "error",
            validClass: "valid",
            errorPlacement: function (error, element) {
                error.insertAfter(element.parent());
            },
            highlight: function (element, errorClass, validClass) {
                $(element).addClass(errorClass);
            },
            unhighlight: function (element, errorClass, validClass) {
                $(element).removeClass(errorClass);
            }
        });
        $("#form-period").validate({
            rules: {
                "payment-name": {"required": true},
                "payment-miss-periods": {"required": true, "number": true, "min": 0},
                "payment-period-amount": {"required": true, "number": true, "min": 1}
            },
            messages: {
                "payment-name": "Моля въведете име на плащане.",
                "payment-miss-periods": "Моля въведете стойност за пропускане между периодите.",
                "payment-period-amount": "Моля въведете начално състояние на плащането."
            },
            errorClass: "error",
            validClass: "valid",
            errorPlacement: function (error, element) {
                error.insertAfter(element.parent());
            },
            highlight: function (element, errorClass, validClass) {
                $(element).addClass(errorClass);
            },
            unhighlight: function (element, errorClass, validClass) {
                $(element).removeClass(errorClass);
            }
        });
    };

    var createOverviewRequest = function (data) {
        var token = $('meta[name=\"_csrf\"]').attr('content');
        var header = $('meta[name=\"_csrf_header\"]').attr('content');
        var requestType = instance.requestTypes.byAmount;
        var url;
        if (isDefined(data.paymentFinalAmount)) {
            url = urls.byAmount;
        } else {
            url = urls.byPeriod;
            requestType = instance.requestTypes.byPeriod;
        }
        $.ajax({
            url: url,
            type: 'POST',
            async: false,
            method: 'post',
            contentType: 'application/json',
            data: JSON.stringify(data),
            beforeSend: function (xhr, settings) {
                xhr.setRequestHeader(header, token);
            }
        }).done(function (response) {
            fillDraftData(response);
            var name = data.paymentName;
            var description = data.paymentDescription;
            var dataSource = processOverviewResponse(response, name, description);
            if (requestType === instance.requestTypes.byAmount)
                instance.yearCalendarByAmount.setDataSource(dataSource);
            if (requestType === instance.requestTypes.byPeriod)
                instance.yearCalendarByPeriod.setDataSource(dataSource);
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
                next: ' Напред',
                previous: 'Назад'
            },
            toolbarSettings: {
                toolbarPosition: 'bottom',
                toolbarButtonPosition: 'right',
                showNextButton: true,
                showPreviousButton: true,
                toolbarExtraButtons: [
                    $('<button class="finish-button"></button>').html('Запазване &nbsp; <i class="fa fa-file-text" aria-hidden="true"></i>')
                            .addClass('btn btn-success')
                            .css("display", "none")
                            .on('click', function () {
                                createSaveRequest(instance.draftData);
                            })
                ]
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
        }).on("leaveStep", function (e, anchorObject, stepNumber, stepDirection) {
            var form = $(this).find("form");
            if (form.valid()) {
                if (stepNumber === 0 && stepDirection === "forward") {
                    $(this).find(".finish-button").show();
                    data = buildDataForRequest($(this));
                    createOverviewRequest(data);
                } else {
                    $(this).find(".finish-button").hide();
                }
            } else {
                return false;
            }
        });
    };

    var initPaymentsOverviewCalendar = function (selector) {
        var yearCalendar = $(selector).calendar({
            language: "bg",
            enableContextMenu: true,
            enableRangeSelection: true,
            dataSource: [
            ],
            contextMenuItems: [
            ],
            selectRange: function (e) {
            },
            mouseOnDay: function (e) {
                if (e.events.length > 0) {
                    var content = '';
                    for (var i in e.events) {
                        content += '<div class="event-tooltip-content">'
                                + '<div class="event-name" style="color:' + e.events[i].color + '">' + e.events[i].name + '</div>'
                                + '<div class="event-location">' + e.events[i].location + '</div>'
                                + '</div>';
                    }

                    $(e.element).popover({
                        trigger: 'manual',
                        container: 'body',
                        html: true,
                        content: content
                    });

                    $(e.element).popover('show');
                }
            },
            mouseOutDay: function (e) {
                if (e.events.length > 0) {
                    $(e.element).popover('hide');
                }
            },
            dayContextMenu: function (e) {
                $(e.element).popover('hide');
            }
        });
        return yearCalendar;
    };

    this.initialize = function () {
        initTabs();
        initStepWizards();
        initValidation();
        this.finalDateCalendar = initDatePicker("#by-period-finish-date");
        this.yearCalendarByAmount = initPaymentsOverviewCalendar("#payments-calendar-amount");
        this.yearCalendarByPeriod = initPaymentsOverviewCalendar("#payments-calendar-period");
        addDaysToDatepicker(this.finalDateCalendar, 1);
        initDatePicker("#by-period-start-date");
        initDatePicker("#by-amount-start-date");
        initSelect2("#by-period-rec-type");
        initSelect2("#by-amount-category");
        initSelect2("#by-period-category");
        initSelect2("#by-amount-rec-type");
    };
};


