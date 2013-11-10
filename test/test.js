var Browser = require('zombie'),
    assert  = require('assert');

describe('Server-side Todo MVC', function () {

    var browser = new Browser();
    browser.debug = true;
    browser.site = 'http://localhost:8080';
    browser.waitFor = 5000;

/*
    describe('load', function () {
	before(function (done) {
	    browser
		.visit('/')
		.then(done, done);
	});
	it('should display the page with the heading text'); function () {
	    assert.equal(browser.text('h1'), 'todos');
	    browser.close();
	});
    });
*/

    describe('new', function () {
	before(function (done) {	
	    browser
		.visit('/')
		.then(function () {
		    browser.fill('#new-todo', 'Frobulate');
		})
		.then(done, done);
	});
	
	it('should show the new todo in todo list', function () {
	    browser.fire('#form', 'submit')
		.then(function () {
		    browser.dump();
		    console.log('hwere we assert');
		});
	    //console.log('WHAT WE FOUND: ' + browser.document);
	    //assert.equal(t, 'Frobulate');
	});
    });

});

