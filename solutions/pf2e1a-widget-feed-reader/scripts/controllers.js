/**
 * Controllers
 * @module controllers
 */
define(function (require, exports) {

    'use strict';

    // @ngInject
    exports.MainCtrl = function(lpWidget) {

        // listen for pref changes, and reload the feed
        lpWidget.addEventListener('preferencesSaved', function(e) {
            lpWidget.refreshHTML();
        });
    };
});
