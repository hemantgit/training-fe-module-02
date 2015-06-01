define([], function() {
    'use strict';

    var self = null;

    function FeedReader(widget) {
        self = this;

        self.widget = widget;

        return self;
    }

    FeedReader.prototype.init = function() {
        // refresh on preferences changed
        self.widget.addEventListener('preferencesSaved', function(event) {
            self.widget.refreshHTML();
        });
    };

    return function(widget) {
        var reader = new FeedReader(widget);
        reader.init();
    };
});
