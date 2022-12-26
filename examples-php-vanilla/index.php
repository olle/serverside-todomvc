<?php

require_once('php/db.php');

$db = loadDb();
$meta = & $db['meta'];
$items = & $db['items'];


function createNewTodo($text)
{
  global $items, $db;
  $newTodo = array();
  $newTodo['id'] = uniqid();
  $newTodo['status'] = 'active';
  $newTodo['editing'] = false;
  $newTodo['todo'] = $text;
  $items[] = $newTodo;
  saveDb($db);
}

function markTodoCompleted($id)
{
  $todo = findById($id);
  $todo['status'] = 'completed';
  updateTodo($todo);
}
function markTodoActive($id)
{
  $todo = findById($id);
  $todo['status'] = 'active';
  updateTodo($todo);
}

function markTodoEditing($id)
{
  global $meta;
  $todo = findById($id);
  $todo['editing'] = true;
  $meta['editing'] = true;
  updateTodo($todo);
}

function updateTodo($todo)
{
  global $items, $db;
  $nextTodos = array();
  for ($i = 0; $i < sizeof($items); $i++) {
    if ($items[$i]['id'] == $todo['id']) {
      $nextTodos[] = $todo;
    } else {
      $nextTodos[] = $items[$i];
    }
  }
  $db['items'] = $nextTodos;
  saveDb($db);
}

function updateTodoText($id, $text)
{
  global $meta;
  $todo = findById($id);
  $todo['todo'] = $text;
  $todo['editing'] = false;
  $meta['editing'] = false;
  updateTodo($todo);
}

function deleteById($id)
{
  global $items, $db;
  $newItems = array();
  for ($i = 0; $i < sizeof($items); $i++) {
    if ($items[$i]['id'] != $id) {
      $newItems[] = $items[$i];
    }
  }
  $db['items'] = $newItems;
  saveDb($db);
}

function findById($id)
{
  global $items;
  for ($i = 0; $i < sizeof($items); $i++) {
    if ($items[$i]['id'] == $id) {
      return $items[$i];
    }
  }
  throw new Exception('Todo item not found.');
}

function hideCompleted()
{
  global $meta, $db;
  $meta['showing'] = false;
  saveDb($db);
}

function showCompleted()
{
  global $meta, $db;
  $meta['showing'] = true;
  saveDb($db);
}

function clearCompleted()
{
  global $items, $db;
  $newItems = array();
  for ($i = 0; $i < sizeof($items); $i++) {
    if ($items[$i]['status'] != 'completed') {
      $newItems[] = $items[$i];
    }
  }
  $db['items'] = $newItems;
  saveDb($db);
}


if ($_POST) {
  $path = $_SERVER['PATH_INFO'] ?? '';

  if ($path == '/todos' && $_POST['todo']) {
    createNewTodo($_POST['todo']);
    header('Location: /');
  } else if (str_starts_with($path, '/todos/') && $_POST['id'] && $_POST['update']) {
    updateTodoText($_POST['id'], $_POST['update']);
    header('Location: /');
  }

  if ($path == '/todo' && $_POST['complete']) {
    markTodoCompleted($_POST['complete']);
    header('Location: /');
  } else if ($path == '/todo' && $_POST['revert']) {
    markTodoActive($_POST['revert']);
    header('Location: /');
  } else if ($path == '/todo' && $_POST['delete']) {
    deleteById($_POST['delete']);
    header('Location: /');
  } else if ($path == '/todo' && $_POST['edit']) {
    markTodoEditing($_POST['edit']);
    header('Location: /');
  }

  if ($path == '/controls' && $_POST['hide']) {
    hideCompleted();
    header('Location: /');
  } else if ($path == '/controls' && $_POST['show']) {
    showCompleted();
    header('Location: /');
  } else if ($path == '/controls' && $_POST['clear']) {
    clearCompleted();
    header('Location: /');
  }

  print_r($path);
  print_r($_POST);
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
$_editing = $meta['editing'] ?? false;

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

    <form action="todos" method="post">
      <label for="todo">Todo</label>
      <input placeholder="What needs to be done?" <?php if (!$_editing) { ?>autofocus<?php } ?> required
        autocomplete="off" name="todo" id="todo" />
    </form>

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