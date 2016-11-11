/**
 *  ----------------------------------------------------------------
 *  Copyright © Backbase B.V.
 *  ----------------------------------------------------------------
 *  Filename : main.js
 *  Description: pf2e1a-widget-feed-reader
 *  ----------------------------------------------------------------
 */
define( function (require, exports, module) {

    'use strict';

    module.name = 'pf2e1a-widget-feed-reader';

     // External Dependencies
    var base = require('base');
    var core = require('core');
    var ui = require('ui');

    // Internal Dependencies 
    var Models = require('./model');   
    var MainCtrl = require('./controllers/main-ctrl');

    var deps = [
        core.name,
        ui.name
    ];

    /**
     * @ngInject
     */
    function run() {
        // Module is Bootstrapped
    }

    module.exports = base.createModule(module.name, deps)
        .constant('WIDGET_NAME', module.name )
        .controller('MainCtrl', MainCtrl )
        .service( 'FeedService', Models )
        .run( run );
});
