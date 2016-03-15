/**
 * Controllers
 * @module controllers
 */
define(function (require, exports) {

    'use strict';

    // @ngInject
    exports.MainCtrl = function($http, $sce, lpWidget, FeedService) {
        var ctrl = this; //self this controller

        ctrl.servicePath = b$.portal.portalServer.serverURL + 'services/rest/feed/json';

        ctrl.feedData = null;

        function populateFeed(servicePath) {
            var feedURL = lpWidget.getPreference('url');

            FeedService
                .getContents(servicePath, feedURL)
                .success(function(res) {
                    ctrl.feedData = res;
                })
                .error(function(e) {
                    throw e;
                });
        }

        // get default feed
        populateFeed(ctrl.servicePath);

        // listen for pref changes, and reload the feed
        lpWidget.addEventListener('preferencesSaved', function(e) {
            populateFeed(ctrl.servicePath);
        });
    };
});
