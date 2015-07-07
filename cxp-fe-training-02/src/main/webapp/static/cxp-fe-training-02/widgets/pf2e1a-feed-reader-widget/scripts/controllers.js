/**
 * Controllers
 * @module controllers
 */
define(function (require, exports) {

    'use strict';

    // @ngInject
    exports.MainCtrl = function(lpWidget) {
        // listen for changes on the url pref, and automatically reload the html
        lpWidget.addEventListener('preferencesSaved', function(e) {
            lpWidget.refreshHTML();
        });
    };
});
