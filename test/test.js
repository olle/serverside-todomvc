'use strict';

var
  Browser = require('zombie'),
  expect = require('chai').expect,
  site = process.env.URL || "http://localhost:8080/";

describe('Server-side Todo MVC', function() {

  var browser = new Browser();

  describe('page', function() {

    before(function(done) {
      browser
        .visit(site)
        .then(done, done);
    });

    after(function() {
      browser.close();
    });

    it('should display correct heading text', function() {
      expect(browser.text('h1')).to.equal('todos');
    });

  });

});
