/**
 * Controllers
 * @module controllers
 */
define(function (require, exports, module) {

    'use strict';

      /**
     * Main controller
     * @ngInject
     * @constructor
     */
    function MainCtrl(lpWidget) {
        var ctrl = this;

        ctrl.lpWidget = lpWidget;
    }

    MainCtrl.prototype.$onInit = function() { 
    	// Do initialization here
    	var ctrl = this;

    	ctrl.lpWidget.addEventListener('preferencesSaved', function(e) {
            ctrl.lpWidget.refreshHTML();
        });
    };

    module.exports = MainCtrl;
});
