/*! SmartMenus jQuery Plugin Keyboard Addon - v0.3.1 - November 1, 2016
 * http://www.smartmenus.org/
 * Copyright Vasil Dinkov, Vadikom Web Ltd. http://vadikom.com; Licensed MIT */(function(t){"function"==typeof define&&define.amd?define(["jquery","jquery.smartmenus"],t):"object"==typeof module&&"object"==typeof module.exports?module.exports=t(require("jquery")):t(jQuery)})(function(t){function e(t){return t.find("> li > a:not(.disabled), > li > :not(ul) a:not(.disabled)").eq(0)}function s(t){return t.find("> li > a:not(.disabled), > li > :not(ul) a:not(.disabled)").eq(-1)}function i(t,s){var i=t.nextAll("li").find("> a:not(.disabled), > :not(ul) a:not(.disabled)").eq(0);return s||i.length?i:e(t.parent())}function o(e,i){var o=e.prevAll("li").find("> a:not(.disabled), > :not(ul) a:not(.disabled)").eq(/^1\.8\./.test(t.fn.jquery)?0:-1);return i||o.length?o:s(e.parent())}return t.fn.focusSM=function(){return this.length&&this[0].focus&&this[0].focus(),this},t.extend(t.SmartMenus.Keyboard={},{docKeydown:function(a){var n=a.keyCode;if(/^(37|38|39|40)$/.test(n)){var r=t(this),u=r.data("smartmenus"),h=t(a.target);if(u&&h.is("a")&&u.handleItemEvents(h)){var l=h.closest("li"),d=l.parent(),c=d.dataSM("level");switch(r.hasClass("sm-rtl")&&(37==n?n=39:39==n&&(n=37)),n){case 37:if(u.isCollapsible())break;c>2||2==c&&r.hasClass("sm-vertical")?u.activatedItems[c-2].focusSM():r.hasClass("sm-vertical")||o((u.activatedItems[0]||h).closest("li")).focusSM();break;case 38:if(u.isCollapsible()){var m;c>1&&(m=e(d)).length&&h[0]==m[0]?u.activatedItems[c-2].focusSM():o(l).focusSM()}else 1==c&&!r.hasClass("sm-vertical")&&u.opts.bottomToTopSubMenus?(!u.activatedItems[0]&&h.dataSM("sub")&&(u.opts.showOnClick&&(u.clickActivated=!0),u.itemActivate(h),h.dataSM("sub").is(":visible")&&(u.focusActivated=!0)),u.activatedItems[0]&&u.activatedItems[0].dataSM("sub")&&u.activatedItems[0].dataSM("sub").is(":visible")&&!u.activatedItems[0].dataSM("sub").hasClass("mega-menu")&&s(u.activatedItems[0].dataSM("sub")).focusSM()):(c>1||r.hasClass("sm-vertical"))&&o(l).focusSM();break;case 39:if(u.isCollapsible())break;1==c&&r.hasClass("sm-vertical")?(!u.activatedItems[0]&&h.dataSM("sub")&&(u.opts.showOnClick&&(u.clickActivated=!0),u.itemActivate(h),h.dataSM("sub").is(":visible")&&(u.focusActivated=!0)),u.activatedItems[0]&&u.activatedItems[0].dataSM("sub")&&u.activatedItems[0].dataSM("sub").is(":visible")&&!u.activatedItems[0].dataSM("sub").hasClass("mega-menu")&&e(u.activatedItems[0].dataSM("sub")).focusSM()):1!=c&&(!u.activatedItems[c-1]||u.activatedItems[c-1].dataSM("sub")&&u.activatedItems[c-1].dataSM("sub").is(":visible")&&!u.activatedItems[c-1].dataSM("sub").hasClass("mega-menu"))||r.hasClass("sm-vertical")?u.activatedItems[c-1]&&u.activatedItems[c-1].dataSM("sub")&&u.activatedItems[c-1].dataSM("sub").is(":visible")&&!u.activatedItems[c-1].dataSM("sub").hasClass("mega-menu")&&e(u.activatedItems[c-1].dataSM("sub")).focusSM():i((u.activatedItems[0]||h).closest("li")).focusSM();break;case 40:if(u.isCollapsible()){var p,f;if(u.activatedItems[c-1]&&u.activatedItems[c-1].dataSM("sub")&&u.activatedItems[c-1].dataSM("sub").is(":visible")&&!u.activatedItems[c-1].dataSM("sub").hasClass("mega-menu")&&(p=e(u.activatedItems[c-1].dataSM("sub"))).length)p.focusSM();else if(c>1&&(f=s(d)).length&&h[0]==f[0]){for(var v=u.activatedItems[c-2].closest("li"),b=null;v.is("li")&&!(b=i(v,!0)).length;)v=v.parent().parent();b.length?b.focusSM():e(r).focusSM()}else i(l).focusSM()}else 1!=c||r.hasClass("sm-vertical")||u.opts.bottomToTopSubMenus?(c>1||r.hasClass("sm-vertical"))&&i(l).focusSM():(!u.activatedItems[0]&&h.dataSM("sub")&&(u.opts.showOnClick&&(u.clickActivated=!0),u.itemActivate(h),h.dataSM("sub").is(":visible")&&(u.focusActivated=!0)),u.activatedItems[0]&&u.activatedItems[0].dataSM("sub")&&u.activatedItems[0].dataSM("sub").is(":visible")&&!u.activatedItems[0].dataSM("sub").hasClass("mega-menu")&&e(u.activatedItems[0].dataSM("sub")).focusSM())}a.stopPropagation(),a.preventDefault()}}}}),t(document).delegate("ul.sm, ul.navbar-nav:not([data-sm-skip])","keydown.smartmenus",t.SmartMenus.Keyboard.docKeydown),t.extend(t.SmartMenus.prototype,{keyboardSetHotkey:function(s,i){var o=this;t(document).bind("keydown.smartmenus"+this.rootId,function(a){if(s==a.keyCode){var n=!0;i&&("string"==typeof i&&(i=[i]),t.each(["ctrlKey","shiftKey","altKey","metaKey"],function(e,s){return t.inArray(s,i)>=0&&!a[s]||0>t.inArray(s,i)&&a[s]?(n=!1,!1):void 0})),n&&(e(o.$root).focusSM(),a.stopPropagation(),a.preventDefault())}})}}),t});