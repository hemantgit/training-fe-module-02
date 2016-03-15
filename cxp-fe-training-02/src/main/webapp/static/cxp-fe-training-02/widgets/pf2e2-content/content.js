/**
* Copyright © 2003/2013 Backbase B.V.
*/


if (!window.be) var be = {};

be.contentWidget = (function($, bd) {
    'use strict';

    return {
        start: function(oWidget) {

            // Extend widget in design mode
            if (be.ice && bd && bd.designMode == 'true') {

                oWidget.iceConfig = be.ice.config;

                var isMasterpage = be.utils.module('top.bd.PageMgmtTree.selectedLink')['isMasterPage'],
                isManageable = isMasterpage || (
                    oWidget.model.manageable === 'true' ||
                    oWidget.model.manageable === '' ||
                    oWidget.model.manageable === undefined
                );

                if (isManageable && be.ice.controller) {
                    var templateUrl = String(oWidget.getPreference('templateUrl')),
                    enableEditing = function(){

                        // it is possible to swap template for editorial
                        // here is an example for image template
                        if(templateUrl.match(/\/image\.html$/)){
                            templateUrl = templateUrl.replace(/\/image\.html$/, '/image-editorial.html');
                        }

                        return be.ice.controller.edit(oWidget, templateUrl)
                        .then(function(dom) {
                            $(oWidget.body).find('.bp-g-include').html(dom);

                            return dom;
                        });
                    };

                    // this widget has property rendering example (template: simple.html)
                    // so we need to refresh widget after property 'title' modified
                    oWidget.model.addEventListener('PrefModified', function (oEvent) {
                        if (oEvent.attrName === 'title') {
                            enableEditing();
                        }
                    }, false, oWidget);

                    return enableEditing();
                }

            } else {
                // Hide broken images on live
                $('img[src=""], img:not([src])', oWidget.body).addClass('be-ice-hide-image');
            }

        }
    };
}(window.jQuery, window.bd));
