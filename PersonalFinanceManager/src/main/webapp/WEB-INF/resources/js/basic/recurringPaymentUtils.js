/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var recurringPaymentUtils = function () {

    this.initialize = function () {
        this.initTabs();
        this.initStepWizards();
    }

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
        $('#smartwizard-amount-first,#smartwizard-period-first').smartWizard(
                {
                    selected: 0,
                    keyNavigation: true,
                    autoAdjustHeight: true,
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
                }
        );
    };
    $('#smartwizard-amount-first #recurringPaymentStartDate').flatpickr({
        'locale': 'bg',
        'mode': 'single',
        'enableTime': false,
        'defaultDate': 'today',
        'dateFormat': "Y-m-d",
        onChange: function (rawdate, altdate, FPOBJ) {
            updateInputValues();
            FPOBJ.close();
        }
    });
};


