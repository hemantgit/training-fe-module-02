define([], function() {
	'use strict';

	function FeedReader(widget) {
		this.widget = widget;
	}

	FeedReader.prototype.init = function() {
		var self = this;
		// refresh on preferences changed
		this.widget.addEventListener('preferencesSaved', function(event) {
			self.widget.refreshHTML();
		});
	};

	return function(widget) {
		var widgetInstance = new FeedReader(widget);
		widgetInstance.init();
	};
});
