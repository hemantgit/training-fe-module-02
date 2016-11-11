/**
 * Models
 * @module models
 */
define( function (require, exports, module) {

    'use strict';

    /**
     * @constructor
     * @ngInject
     */
    function FeedService($http, $q) {
        this.$http = $http;
        this.$q = $q;
        this.config = {};
    }

    FeedService.prototype.setupConfig = function (config) {
        this.config = config;
        return this;
    };

    FeedService.prototype.getContents = function(feed) {
        var deferred;
        var promise;

        if(!this.config.feedEndpoint) {
            deferred = this.$q.defer();
            deferred.reject(new Error('FeedEndpoint has not been set.'));
            promise = deferred.promise;
        } else {
            promise = this.$http({
                    method: 'GET',
                    url: this.config.feedEndpoint,
                    responseType: 'json',
                    params: {
                        url: feed
                    }
                })
                .then(function (response) {
                    return response.data.channel;
                });
        }
        
        return promise;
    };
    
    module.exports = FeedService;
});