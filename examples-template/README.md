Example HTML Template
=====================

This is the template-kit that we provide for any example implementation. It's derived from the original assets of the [TodoMVC](http://http://todomvc.com) project, but slightly adapted to conform to some aspects that are more server-side idiomatic.

## Getting started

For any example implementation, it should be a simple matter of copying the assets and the `index.html` page into the new example implementation. Sometimes you might have to restructure the placement or linking of resources, for example images or CSS files.

## Request & Response Compatibility

All the features of the TodoMVC application are normalized for a completely server-side based implementation to work. This means we have proper HTML forms, links that go complete GET-requests with parameters, and no Ajax or dynamic browser features are required in the baseline.

The adaptation to this _baseline_ template, required the following changes in comparison to the original template:

* ...