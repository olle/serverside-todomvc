var Browser = require('zombie'),
    assert  = require('assert');

var site =  process.env.URL || "http://localhost:8080/";

if (site.slice(-1) != '/') {
	site += '/';
}

describe('Server-side Todo MVC', function () {

  var browser = new Browser();

  describe('load', function () {

    before(function (done) {
      browser
        .visit(site)
        .then(done, done);
    });

    it('should display the page with the heading text', function () {
      assert.equal(browser.text('h1'), 'todos');
      browser.close();
    });

  });

});

