<?php

/* Whaaaa! Not secure! OMG! */

require_once ('php/db.php');

$db = loadDb();

$meta  =& $db['meta'];
$items =& $db['items'];

if (isset($_GET['filter'])) {
  $filter = strval($_GET['filter']);
  if ($filter === 'active') {
    $meta['filter'] = 'active';
  } else if ($filter === 'completed') {
    $meta['filter'] = 'completed';
  } else {
    $meta['filter'] = 'all';
  }
  unset($meta['edit']);
  saveDb($db);
  header('Location: /');
}

if (isset($_GET['action'])) {
  $action = strval($_GET['action']);
  if ($action === 'edit') {
    $todo = strval($_GET['item-text']);
    if (array_key_exists($todo, $items)) {
      $meta['edit'] = $todo;
    }
  }
  saveDb($db);
  header('Location: /');
}

if (isset($_POST['action'])) {

  if ($_POST['action'] === 'create') {
    $items[$_POST['item-text']] = 'active';
    unset($meta['edit']);
    saveDb($db);
    header('Location: /');
  }

  if ($_POST['action'] === 'toggle-all') {
    if (!isset($meta['status'])) {
      $meta['status'] = array('completed', 'active');
    }
    foreach ($items as $todo => $status) {
      $items[$todo] = $meta['status'][0];
    }
    array_push($meta['status'], array_shift($meta['status']));
    unset($meta['edit']);
    saveDb($db);
    header('Location: /');
  }

  if ($_POST['action'] === 'toggle') {
    if (array_key_exists($_POST['item-text'], $items)) {
      $key = $_POST['item-text'];
      $old = $items[$key];
      $items[$key] = $old === 'active' ? 'completed' : 'active';
      unset($meta['edit']);
    }
    saveDb($db);
    header('Location: /');
  }

  if ($_POST['action'] === 'delete') {
    unset($items[$_POST['item-text']]);
    unset($meta['edit']);
    saveDb($db);
    header('Location: /');
  }

  if ($_POST['action'] === 'clear') {
    foreach ($items as $todo => $status) {
      if ($status === 'completed') {
        unset($items[$todo]);
      }
    }
    unset($meta['status']);
    unset($meta['edit']);
    saveDb($db);
    header('Location: /');
  }

  if ($_POST['action'] === 'update') {
    $itemText = strval($_POST['item-text']);
    $newText = strval($_POST['new-text']);
    if (!array_key_exists($newText, $items)) {
      $keys = array_keys($items);
      $values = array_values($items);
      for ($i = 0; $i < count($keys); $i++) {
        if ($keys[$i] === $itemText) {
          $keys[$i] = $newText;
        }
      }
      $items = array_combine($keys, $values);
    }
    unset($meta['status']);
    unset($meta['edit']);
    saveDb($db);
    header('Location: /');
  }

}

$_filter = isset($meta['filter']) ? $meta['filter'] : 'all';
$_status = isset($meta['status']) ? $meta['status'][0] : 'completed';
$_edit = isset($meta['edit']) ? $meta['edit'] : null;
$_active = 0;
$_completed = 0;
$_filtered = array();

foreach ($items as $todo => $status) {

  $_active += $status === 'active' ? 1 : 0;
  $_completed += $status === 'completed' ? 1 : 0;

  if ($_filter === 'all' || $_filter === $status) {
    $_filtered[$todo] = $status;
  }
}

?><!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>PHP Vanilla • Server-side TodoMVC</title>
    <link rel="stylesheet" href="css/style.css" />
  </head>
  <body class="grey-plaid-bg modern-font">
    <section id="serverside-todomvc">
      <header>
        <h1>todos</h1>
        <h2 class="is-hidden">Create a new task</h2>
        <form method="post" class="items-create-new">
          <input type="hidden" name="action" value="create" />
          <input type="text" name="item-text" placeholder="What needs to be done?" <?= empty($_edit) ? 'autofocus="autofocus"' : '' ?> autocomplete="off" class="text-input"/>
          <button class="is-hidden"><span class="is-hidden">Create new todo</span></button>
        </form>
      </header>
      <?php if (!empty($items)): ?>
      <section class="primary">
        <h3 class="is-hidden">Mark as <?= $_status ?></h3>
        <form method="post" class="items-mark-all-completed">
          <input type="hidden" name="action" value="toggle-all" />
          <button class="icon angle-double down <?= $_status === 'active' ? 'is-active' : '' ?>"><span class="is-hidden">Mark all as <?= $_status ?></span></button>
        </form>
        <h2 class="is-hidden">List of <?= $filter ?> tasks:</h2>
        <ul class="items-list">
          <?php foreach ($_filtered as $todo => $status): ?>
          <li class="item <?= $todo !== $_edit ? $status : 'editing' ?> clearfix">
            <?php if ($todo !== $_edit): ?>
            <form method="post" class="item-toggle-completed">
              <input type="hidden" name="action" value="toggle" />
              <input type="hidden" name="item-text" value="<?= $todo ?>" />
              <input type="hidden" name="item-status" value="<?= $status ?>" />
              <button class="icon check <?= $status === 'completed' ? 'is-active' : '' ?>"><span class="is-hidden">Mark as <?= $status ?></span></button>
            </form>
            <a href="?action=edit&item-text=<?= urlencode($todo) ?>" class="item-text"><?= $todo ?></a>
            <form method="post" class="item-delete">
              <input type="hidden" name="action" value="delete" />
              <input type="hidden" name="item-text" value="<?= $todo ?>" />
              <button class="icon cross"><span class="is-hidden">Delete</span></button>
            </form>
            <?php else: ?>
            <form method="post" class="item-edit">
              <input type="hidden" name="action" value="update" />
              <input type="hidden" name="item-text" value="<?= $todo ?>" />
              <input type="text" name="new-text" value="<?= $todo ?>" autofocus="autofocus" autocomplete="off" class="item-text" />
            </form>
            <?php endif ?>
          </li>
          <?php endforeach ?>
        </ul>
      </section>
      <section class="secondary">
        <h3 class="is-hidden">Current status</h3>
        <p class="items-active-count"><strong><?= $_active ?></strong> <?= $_active > 1 ? 'items' : 'item' ?> left</p>
        <h3 class="is-hidden">Filter task list</h3>
        <ul class="items-filter-selection">
          <li><a href="?filter=all" class="<?= $_filter === 'all' ? 'is-active' : '' ?>">All</a></li>
          <li><a href="?filter=active" class="<?= $_filter === 'active' ? 'is-active' : '' ?>">Active</a></li>
          <li><a href="?filter=completed" class="<?= $_filter === 'completed' ? 'is-active' : '' ?>">Completed</a></li>
        </ul>
        <?php if ($_completed): ?>
        <h3 class="is-hidden">Clear completed tasks</h3>
        <form method="post" class="items-clear-completed is-inline">
          <input type="hidden" name="action" value="clear" />
          <button class="button embossed"><span>Clear completed (<?= $_completed ?>)</span></button>
        </form>
        <?php endif ?>
      </section>
      <?php endif ?>
    </section>
    <footer>
      <h4 class="is-hidden">Help</h4>
      <p>Click on the text to edit a todo.</p>
      <h4 class="is-hidden">About the Server-side TodoMVC application</h4>
      <p>Created by <a href="http://github.com/olle">Olle Törnström</a></p>
      <p>Part of <a href="http://github.com/olle/serverside-todomvc">Server-side TodoMVC</a></p>
    </footer>
    <!-- Look ma no JavaScript! -->
  </body>
</html>
