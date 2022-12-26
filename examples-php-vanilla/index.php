<?php

require_once('php/db.php');
$db = loadDb();
$meta = & $db['meta'];
$items = & $db['items'];

?>
<!doctype html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="description"
    content="Helping you remember or select a server-side MV* framework - Todo apps for Spring Boot, Flask, PHP and many more">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>PHP Vanilla • Server-Side TodoMVC</title>
  <link rel="stylesheet" href="/css/style.css">
  <!-- Look mom, no JavaScript! -->
</head>

<body>
  <main>

    <!--
        TODO: Replace {active-count} with the number of not yet completed
              todo items.
    -->
    <h1>Todos <small title="{active-count} Active items">{active-count}</small></h1>
    <form action="controls" method="post">
      <!--
          TODO: Replace {completed-count} with the number of todo items marked
                completed.
      -->
      <button name="clear" value="completed" title="Clear {completed-count} completed">{completed-count} Completed •
        Clear</button>

      <!--
          TODO: Render the hide/show button depending on the current state of
                such a filter, to either show completed items or not.
      -->
      <button name="hide" value="completed" title="Hide completed todo items">Hide</button>
      <button name="show" value="completed" title="Show completed todo items">Show</button>
    </form>


    <!--
        TODO: When this form is posted, a new todo item should be created and
              added to the todo-list, as an active item.
    -->
    <form action="todos" method="post">
      <label for="todo">Todo</label>
      <!--
          TODO: Take care to remove the `autofocus` attribute, if there is a
                todo item currently being edited.
      -->
      <input placeholder="What needs to be done?" autofocus required autocomplete="off" name="todo" id="todo" />
    </form>


    <!--
        TODO: This form is posted with action/id pairs, an needs to be handled
              for completing, re-activating, deleting and starting to edit a
              todo item.
    -->
    <form id="todo-item" method="post" action="todo"></form>
    <ul>
      <!--
          TODO: First render from a list of all active todo items.
      -->

      <li> <!-- TODO: If the todo item is being edited use this:  -->
        <button name="complete" value="{todo-id}" form="todo-item" title="Mark completed"></button>
        <form class="inline" method="post" action="todos/{todo-id}">
          <input type="hidden" name="id" value="{todo-id}" />
          <input name="update" value="{todo-text}" autofocus required autocomplete="off" />
        </form>
      </li>

      <li> <!-- TODO: Otherwise render the todo item like this: -->
        <button name="complete" value="{todo-id}" form="todo-item" title="Mark completed"></button>
        <button name="edit" value="{todo-id}" form="todo-item" title="Click to edit">{todo-text}</button>
        <button name="delete" value="{todo-id}" form="todo-item" title="Delete todo item">&#x2715;</button>
      </li>

      <!--
          TODO: Then render the list of todo items marked as completed, unless
                the current filter is toggled to hide completed.
      -->
      <li>
        <button name="revert" value="{todo-id}" form="todo-item" title="Mark as active"></button>
        <span>{todo-text}</span>
        <button name="delete" value="{todo-id}" form="todo-item" title="Delete todo item">&#x2715;</button>
      </li>
    </ul>
  </main>
  <footer>
    <em>Click on the text to edit a todo.</em>
    <p>
      Template by <a href="https://github.com/olle">Olle Törnström</a>,
      inspired by the original <a href="https://todomvc.com">TodoMVC</a> from
      <a href="http://github.com/sindresorhus">Sindre Sorhus</a>.
    </p>
    <!--
      TODO: Replace the paragraph below with a link to you or your
            homepage, person, repository or organization. -->
    <p>
      Created by <a href="http://todomvc.com">you</a>.
    </p>
    <p>
      Part of <a href="http://github.com/olle/serverside-todomvc">Server-Side TodoMVC</a>
    </p>
  </footer>
</body>

</html>