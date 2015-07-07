/**
 * Controllers
 * @module controllers
 */
define(function (require, exports) {

    'use strict';

    // @ngInject
    exports.FeedService = function($http) {
        return {
            getContents: function(url, feed) {
                return $http({
                    method: 'GET',
                    url: url,
                    responseType: 'json',
                    params: {
                        url: feed
                    }
                });
            }
        };
    };
});
