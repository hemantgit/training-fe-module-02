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
    function MainCtrl(FeedService, lpWidget) {
        var ctrl = this;

        ctrl.FeedService = FeedService;
        ctrl.lpWidget = lpWidget;
    }

    MainCtrl.prototype.$onInit = function() {
        // Do initialization here
        var ctrl = this;
        var feedEndpoint = ctrl.lpWidget.getResolvedPreference('feedEndpoint');
        var rssFeedURL = ctrl.lpWidget.getPreference('rssFeedURL');

        ctrl.feedChannelData = null;

        ctrl.FeedService
                .setupConfig({
                    feedEndpoint: feedEndpoint
                })
                .getContents(rssFeedURL)
                .then(function(channel) {                    
                    ctrl.feedChannelData = channel;
                })
                .catch(function(e) {
                    throw e;
                });

        ctrl.lpWidget.addEventListener('preferencesSaved', function(e) {
            ctrl.lpWidget.refreshHTML();
        });        
    };

    module.exports = MainCtrl;
});
