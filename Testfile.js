
var async = require('async');
var $ = require('jquerygo');

$.config.site = 'http://localhost:8080';
$.config.addJQuery = false;

async.series([
    $.go('visit', '/'),
    $.go('waitForElement', '#new-todo')
], function () {
    $.capture('result.png');
    $.close();
    process.exit(0);
});
