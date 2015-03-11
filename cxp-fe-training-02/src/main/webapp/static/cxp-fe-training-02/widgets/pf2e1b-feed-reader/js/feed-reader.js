define(['portal!jquery'], function($) {
	'use strict';

	var SELECTORS = {
		MAIN: "[data-js=bt-feed-reader-main]",
		TEMPLATE: "[data-template=bt-feed-reader-template]"
	};

	var OPTIONS = {
		PIPE_URL: b$.portal.config.serverRoot + "/proxy?pipe=jsonPipe"
	};

	function FeedReader(widget) {
		this.widget = widget;
		this.$widgetBody = $(widget.body);

		this.$main = this.$widgetBody.find(SELECTORS.MAIN);

		this.template = this.$widgetBody.find(SELECTORS.TEMPLATE).text();
	}

	FeedReader.prototype.init = function() {
		var self = this;
		this.getJSON();
		this.widget.addEventListener('preferencesSaved', function(event) {
			self.getJSON();
		});
	};

	FeedReader.prototype.getJSON = function() {
		var self = this;
		$.getJSON(OPTIONS.PIPE_URL, {
			url : self.widget.model.getPreference("url"),
		}, function(jsonArray) {
			self.renderItems(jsonArray);
		});
	};

	FeedReader.prototype.renderItems = function(items) {
		var html = Mustache.render(this.template, items);
		this.$main.html(html);
	};

	return function(widget) {
		var widgetInstance = new FeedReader(widget);
		widgetInstance.init();
	};
});
