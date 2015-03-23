define(['jquery'], function($) {
	'use strict';

	var self = null,
		SELECTORS = {
			'WRAPPER': '[data-js="feed-wrapper"]',
			'TEMPLATE': '[data-template="articles"]'
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

		self.proxyPath = b$.portal.portalServer.serverURL + 'proxy?pipe=jsonPipe';
		self.feedURL = null;
		self.getFeedURL();

		return self;
	}

	/**
	 * @method init
	 * @return undefined
	 */
	FeedReader.prototype.init = function() {
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
		self.feedURL = self.widget.model.getPreference('feedURL');
	};

	/**
	* @method getArticles
	* @return undefined
	*/
	FeedReader.prototype.getArticles = function() {
		$.ajax({
			dataType: 'json',
			url: self.proxyPath,
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
