/**
 * Controllers
 * @module controllers
 */
define(function (require, exports) {

    'use strict';

    // @ngInject
    exports.MainCtrl = function($http, $sce, lpWidget) {
        var ctrl = this; //self this controller

        ctrl.servicePath = b$.portal.portalServer.serverURL + 'services/rest/feed/json';

        ctrl.feedData = null;

        $http({
            method: 'GET',
            url: ctrl.servicePath,
            responseType: 'json',
            params: {
                url: lpWidget.model.getPreference('url')
            }
        })
        .success(function(res) {
            ctrl.feedData = res;
        })
        .error(function(e) {
            throw e;
        });
    };
});
