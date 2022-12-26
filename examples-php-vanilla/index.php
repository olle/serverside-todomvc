<?php

require_once('php/db.php');

$db = loadDb();
$meta = & $db['meta'];
$items = & $db['items'];

if ($_POST) {
  $path = $_SERVER['PATH_INFO'] ?? '';

  if ($path == '/todos') {
    $newTodo = array();
    $newTodo['id'] = uniqid();
    $newTodo['status'] = 'active';
    $newTodo['todo'] = $_POST['todo'];
    $newTodo['editing'] = false;
    $items[] = $newTodo;
    saveDb($db);
  }

  header('Location: /');
}

$_active = array_filter($items, function ($todo) {
  if ($todo['status'] == 'active') {
    return $todo;
  }
});

$_completed = array_filter($items, function ($todo) {
  if ($todo['status'] == 'completed') {
    return $todo;
  }
});

$_showing = $meta['showing'] ?? true;

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
    <h1>Todos <small title="<?= sizeof($_active) ?> Active items"><?= sizeof($_active) ?></small></h1>
    <form action="controls" method="post">
      <button name="clear" value="completed" title="Clear <?= sizeof($_completed) ?> completed"><?= sizeof($_completed) ?> Completed •
        Clear</button>
      <?php if ($_showing) { ?>
      <button name="hide" value="completed" title="Hide completed todo items">Hide</button>
      <?php } else { ?>
      <button name="show" value="completed" title="Show completed todo items">Show</button>
      <?php } ?>
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
      <?php
      foreach ($_active as $todo) {
        if ($todo['editing']) {
      ?>
      <li>
        <button name="complete" value="<?= $todo['id'] ?>" form="todo-item" title="Mark completed"></button>
        <form class="inline" method="post" action="todos/<?= $todo['id'] ?>">
          <input type="hidden" name="id" value="<?= $todo['id'] ?>" />
          <input name="update" value="<?= $todo['todo'] ?>" autofocus required autocomplete="off" />
        </form>
      </li>
      <?php } else { ?>
      <li>
        <button name="complete" value="<?= $todo['id'] ?>" form="todo-item" title="Mark completed"></button>
        <button name="edit" value="<?= $todo['id'] ?>" form="todo-item" title="Click to edit"><?= $todo['todo'] ?></button>
        <button name="delete" value="<?= $todo['id'] ?>" form="todo-item" title="Delete todo item">&#x2715;</button>
      </li>
      <?php
        }
      }
      ?>

      <?php
      if ($_showing) {
        foreach ($_completed as $todo) {
      ?>
      <li>
        <button name="revert" value="<?= $todo['id'] ?>" form="todo-item" title="Mark as active"></button>
        <span><?= $todo['todo'] ?></span>
        <button name="delete" value="<?= $todo['id'] ?>" form="todo-item" title="Delete todo item">&#x2715;</button>
      </li>
      <?php
        }
      }
      ?>
    </ul>
  </main>
  <footer>
    <em>Click on the text to edit a todo.</em>
    <p>
      Template by <a href="https://github.com/olle">Olle Törnström</a>,
      inspired by the original <a href="https://todomvc.com">TodoMVC</a> from
      <a href="http://github.com/sindresorhus">Sindre Sorhus</a>.
    </p>
    <p>
      Created by <a href="http://github.com/olle">Olle Törnström</a>.
    </p>
    <p>
      Part of <a href="http://github.com/olle/serverside-todomvc">Server-Side TodoMVC</a>
    </p>
  </footer>
</body>

</html>