# Server-Side TodoMVC

## Helping you to select an MVC server-side framework.

<img align="right" src="http://todomvc.com/site-assets/logo-icon.png" />

There is without a doubt an almost frightening amount of options for developers
to choose from, when selecting a framework for development of web applications.

With the immense popularity of client-side or JavaScript-based solutions,
it's easy to forget about the merits of good old server-side solutions. They
deserve to be remembered, recorded and preserved and in order to provide their
legacy to the after-web.

In an answer to this question, and as an answer to a calling - we're doing just
that! We have cloned the popular [TodoMVC](http://todomvc.com) project, to
re-build it in an old and familiar way.

**This time, server-side only!**

## Our goal with this project

Choosing a framework or a set of tools is probably more about the subjective
_feeling_ that we get (at least more than we care to admit) than it is about
making an objective decision.

What we would like to do here is to provide a large set of code examples, that
show how each framework goes about doing the same thing.

We're hoping that this can provide a base of reference when discussing more
subjective feelings and views on which tool is the best for the task at hand. A
suggestion is to couple this with other, more objective evaluations. For example
the very interesting [Web Frameworks Benchmark](https://github.com/TechEmpower/FrameworkBenchmarks).

## Getting Involved

The doors are open. Anyone deeply in love with a server-side framework may
join-in, clone-out and pull-request-away with ideas and contributions for the
list of example implementations.

We're also very grateful for suggestions, comments and constructive criticism on
how to make each solution present the framework it uses in the best possible
idiomatic way.

See the [template-example](./template-example/) subdirectory for instructions
on how to create your own Server-Side TodoMVC implementation.

### Some words on the way

Even though this probably is more of a project for old farts, and of course made
with a bit of tongue-in-cheek. I think it's interesting to provide example
implementations that still push the envelope of using the most modern _language
features_ possible - for example using helper libraries for collections, maps,
JSON serialization or simplified file-I/O. Go nuts!

## Building, testing and running

Currently `make` is your friend - there's even a default target that guides you
into setting up your development environment, building, testing and running the
examples. Just try it out:

    make <help>

## License

This project is a derivative work of the great [TodoMVC](http://todomvc.com)
project, so we stay with the MIT License (please see the LICENSE file for more
detailed information).

We also whish to aknowledge the [authors](https://github.com/tastejs/todomvc/blob/gh-pages/readme.md#team)
and [contributors](https://github.com/tastejs/todomvc/graphs/contributors) of
the original project.
