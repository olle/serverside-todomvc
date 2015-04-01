Java Servlet Example
====================

This is a classic. What could be simpler than some sticky Java-code all served
up through the Servlet-API. Well, even though it might be a bit bare, the goal
here is to spice up the implementation.

## Model View Controller

A proper model. There's no need for any persistance other than what is running
in memory. A collection of tasks or _todo-items_ is actually all we need. We
will start by modeling that, and then moving on to the tasks. Our goal is to
use as modern code as possible - let's see what we can do.

### Imagine a DSL for Todos

This shouldn't be so hard. We know the domain, right? Yes, more or less, I guess
it's a matter of taste. I'm taking an approach where I try to use more of a
natural language style.

    create task with description "Buy oranges"
    delete task 42
    change task 11 to description "Buy apples"
    mark task 3 as completed
    show all tasks
    show completed tasks
    mark all tasks as completed
    clear completed tasks

Ok, so I'm not going to crank out the language workbench just yet. This is stuff
that I want to convert into an internal DSL - a domain design that allows me to
interact with todos in somewhat the same manner as above.

    tasks.add("Buy oranges");
    tasks.clear(42);
    tasks.change(11, "Buy apples");
    tasks.complete(3);
    tasks.list();
    tasks.list(Predicates.completed());
    tasks.complete();
    tasks.clear(Predicates.completed());

## Getting started

To get started you have to have `java` and Maven (`mvn`) installed, then it
should just be a matter of running:

    mvn jetty:run

Happy hacking!
