<?php

require_once ('php/db.php');

$db = loadDb();

$meta  =& $db['meta'];
$items =& $db['items'];

if (isset($_POST['action'])) {

  if ($_POST['action'] === 'create') {
    $items[$_POST['item-text']] = 'active';
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
    // Rotate the meta state to `toggle` it
    array_push($meta['status'], array_shift($meta['status']));
    saveDb($db);
    header('Location: /');
  }

  if ($_POST['action'] === 'toggle') {
    $items[$_POST['item-text']] = $_POST['item-status'] === 'active' ? 'completed' : 'active';
    saveDb($db);
    header('Location: /');
  }

  if ($_POST['action'] === 'delete') {
    unset($items[$_POST['item-text']]);
    saveDb($db);
    header('Location: /');
  }

  if ($_POST['action'] === 'clear') {
    foreach ($items as $todo => $status) {
      if ($status === 'completed') {
        unset($items[$todo]);
      }
    }
    saveDb($db);
    header('Location: /');
  }

}

$active = 0;
$completed = 0;
foreach ($items as $todo => $status) {
  $active += $status === 'active' ? 1 : 0;
  $completed += $status === 'completed' ? 1 : 0;
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
        <h1>Todos</h1>
        <h2 class="is-hidden">Create a new task</h2>
        <form method="post" class="items-create-new">
          <input type="hidden" name="action" value="create" />
          <!--
            TODO: Remove `autofocus="autofocus"` attribute if the page is
                  currently in the mode of editing a todo item.
           -->
          <input type="text" name="item-text" placeholder="What needs to be done?" autofocus="autofocus" autocomplete="off" class="text-input"/>
          <button class="is-hidden"><span class="is-hidden">Create new todo</span></button>
        </form>
      </header>

      <!--
        TODO: This section must only be visible (exist) if there are any todo
              items to show at all.
       -->
      <?php if (!empty($items)): ?>
      <section class="primary">
        <h3 class="is-hidden">Mark as completed</h3>
        <form method="post" class="items-mark-all-completed">
          <input type="hidden" name="action" value="toggle-all" />
          <button class="icon angle-double down"><span class="is-hidden">Mark all as completed</span></button>
        </form>

        <h2 class="is-hidden">List of ${current-filter} tasks:</h2>
        <ul class="items-list">
          <?php foreach ($items as $todo => $status): ?>
          <li class="item <?= $status ?> clearfix">
            <form method="post" class="item-toggle-completed">
              <input type="hidden" name="action" value="toggle" />
              <input type="hidden" name="item-text" value="<?= $todo ?>" />
              <input type="hidden" name="item-status" value="<?= $status ?>" />
              <button class="icon check <?= $status === 'completed' ? 'is-active' : '' ?>"><span class="is-hidden">Mark as <?= $status ?></span></button>
            </form>
            <!--
              TODO: Clicking the link, should create a page where the todo,
                    identified by the given id, is rendered in edit-mode.

                    See last list element in the template.
             -->
            <a href="?action=edit&item-id=${item-id}" class="item-text"><?= $todo ?></a>
            <form method="post" class="item-delete">
              <input type="hidden" name="action" value="delete" />
              <input type="hidden" name="item-text" value="<?= $todo ?>" />
              <button class="icon cross"><span class="is-hidden">Delete</span></button>
            </form>
          </li>
        <?php endforeach ?>

          <!--
            TODO: Mark the list item as `editing` for a todo item that is
                  currently being edited.
           -->
          <li class="item editing clearfix">
              <!--
                TODO: Posting this form must save the changes made to the todo
                      item, identified by the given id.
               -->
              <form method="post" class="item-edit">
                <input type="hidden" name="action" value="update" />
                <input type="hidden" name="item-id" value="${item-id}" />
                <!--
                  NOTE: This should be the only element with the `autofocus`
                        attribute set.

                        Check the create-form on the top.
                 -->
                <input type="text" name="item-text" placeholder="${todo-item-text}" autofocus="autofocus" autocomplete="off" class="item-text" />
              </form>
          </li>

        </ul>
      </section>
      <section class="secondary">
        <h3 class="is-hidden">Current status</h3>
        <p class="items-active-count"><strong><?= $active ?></strong> <?= $active > 1 ? 'items' : 'item' ?> left</p>
        <h3 class="is-hidden">Filter task list</h3>
        <ul class="items-filter-selection">
          <!--
            TODO: Allow to see todo items based on filters `active` (only the
                  active) and `completed` (only the completed items) - as well
                  as showing `all`, which is selected by default.
           -->
          <li><a href="?filter=all" class="is-active">All</a></li>
          <li><a href="?filter=active">Active</a></li>
          <li><a href="?filter=completed">Completed</a></li>
        </ul>

        <?php if ($completed): ?>
        <h3 class="is-hidden">Clear completed tasks</h3>
        <!--
          TODO: Posting this form must delete all todo items that are already
                marked as being completed.
         -->
        <form method="post" class="items-clear-completed is-inline">
          <input type="hidden" name="action" value="clear" />
          <button class="button embossed"><span>Clear completed (<?= $completed ?>)</span></button>
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
