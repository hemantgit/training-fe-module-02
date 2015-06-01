define(['jquery'], function($) {
    'use strict';

    var self = null,
        SELECTORS = {
            'WRAPPER': '[data-js="bt-pf2e1b-feed-wrapper"]',
            'TEMPLATE': '[data-template="bt-pf2e1b-articles"]'
        };

    /**
    * @constructor FeedReader
    * @return this
    */
    function FeedReader(widget) {
        self = this;

        self.widget = widget;
        self.$widget = $(widget.body);
        self.$wrapper = self.$widget.find(SELECTORS.WRAPPER);

        self.template = self.$widget.find(SELECTORS.TEMPLATE).text();

        self.servicePath = b$.portal.portalServer.serverURL + 'services/rest/feed/json';
        self.feedURL = null;

        return self;
    }

    /**
    * @method init
    * @return undefined
    */
    FeedReader.prototype.init = function() {
        self.getFeedURL();
        self.getArticles();

        // handles preference changes
        self.widget.addEventListener('preferencesSaved', function() {
            // get the new url
            self.getFeedURL();
            // refresh the articles
            self.getArticles();
        });
    };

    /**
    * @method getFeedURL
    * @return undefined
    */
    FeedReader.prototype.getFeedURL = function() {
        self.feedURL = self.widget.model.getPreference('url');
    };

    /**
    * @method getArticles
    * @return undefined
    */
    FeedReader.prototype.getArticles = function() {
        $.ajax({
            dataType: 'json',
            url: self.servicePath,
            data: {
                url: self.feedURL
            }
        })
        .then(function(res) {
            self.$wrapper.html(self.renderArticles(res));
        });
    };

    /**
    * @method renderArticles
    * @return {String} html
    */
    FeedReader.prototype.renderArticles = function(data) {
        return Mustache.render(self.template, data);
    };

    return function(widget) {
        var reader = new FeedReader(widget);
        reader.init();
    };
});
