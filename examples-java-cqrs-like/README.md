Server-Side TodoMVC - Java CQRS Like
====================================

So I thought it would be good to create an example of a CQRS-like
TodoMVC application. It's of course not a pure one, but nevertheless
a starting point for people interested in the principle.

Keeping it server-side
----------------------

In order not having to join the _dark side_ and doing push updates
with WebSockets or some other unholy client-side technology - I'm
going to go ahead and try out some old-school tricks.

The view, and query part, will be implemented as a browser-side polling
IFRAME, using the good-old `<meta http-equiv="refresh" ... />` tag.

