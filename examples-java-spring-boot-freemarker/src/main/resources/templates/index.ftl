<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Java Spring Boot • Server-side TodoMVC</title>
		<link rel="stylesheet" href="index.css">
	</head>
	<body>
		<section class="todoapp">
			<header class="header">
				<h1>todos</h1>
        <!--
          TODO: When this form is posted, a new todo should be created and visible
                in the todo-list, as an `active` item.
        -->
        <form action="todos/" method="post">
          <!--
            TODO: Remove the `autofocus="autofocus"` attribute if a todo-item is
                  currently being edited.
          -->
          <input type="text" name="item-text" placeholder="What needs to be done?" autocomplete="off" class="new-todo" autofocus>
        </form>
			</header>
      <!--
        TODO: This section must only be visible (exist) if there are _visible_
              todo items (don't forget the filter).
      -->
			<section class="main">
        <!--
          TODO: If this form is posted, then all, not yet completed tasks, must be
                marked as completed.
        -->
        <form action="todos/status" method="post" class="toggle-all">
          <button class="icon angle-double down"></button>
        </form>
				<ul class="todo-list">
          <!--
            TODO: This is a todo-item. Make sure to mark it with either classes:
                  `completed` or `editing` depending on it's current status.
          -->
					<li class="editing {completed}">
            <!--
              TODO: When the item is in `editing` status, only the following form
                    should be visible. The if-else-end markers show the blocks
                    that should be rendered.
            -->
            <!-- # if (is-editing) # -->
            <form action="todos/item-id" method="post" class="">
              <input type="hidden" name="method" value="put" />
              <!--
                TODO: This should be the only element with the `autofocus`
                      attribute set. Check the create-form on the top.
               -->
              <input type="text" name="item-text" placeholder="${item-text}" autofocus="autofocus" autocomplete="off" class="edit" />
            </form>
            <!-- # else # -->
            <div class="view">
              <!--
                TODO: Posting this form must toggle the completed state of the
                      todo item, identified by the given id.
              -->
              <form action="todos/{item-id}/status" method="post" class="item-toggle-completed">
                <!--
                  TODO: Add the class `is-active` if the todo is completed.
                -->
                <!-- XXX: Fix toggle icon styling from checkbox input. -->
                <button class="toggle checked"></button>
                <!-- <input class="toggle" type="checkbox" checked> -->
              </form>
              <!--
                TODO: Clicking this link, should create a page where the todo,
                      identified by the given id, is in the `editing` status.

                      If the item is `completed` replace the link with this simple
                      span element:

                      <span class="item-text">${item-text}</span>
              -->
              <!-- XXX: Fix link color from plain label. -->
              <label><a href="?action=edit&amp;item-id=item-id" class="item-text">${item-text}</a></label>
							<!-- <label>Taste JavaScript</label> -->
              <!--
                TODO: Posting this form should delete the todo item, identified
                      by the given id.
              -->
              <form action="todos/item-id" method="post" class="item-delete">
                <input type="hidden" name="method" value="delete" />
                <button class="destroy"></button>
              </form>
						</div>
            <!-- # end # -->
					</li>
          <!--
            TODO: This is just another example item, remove this.
          -->
					<li class="completed">
						<div class="view">
							<input class="toggle" type="checkbox" checked>
							<label>Buy a unicorn</label>
							<button class="destroy"></button>
						</div>
						<input class="edit" value="Rule the web">
					</li>
				</ul>
			</section>
      <!--
        TODO: This footer must only be visible if there are any todo items at
              at all, even if they are not visible.
      -->
			<footer class="footer">
        <!--
          TODO: Provide a count and properly pluralized label of how many _active_
                todo items there are left.
        -->
        <span class="todo-count"><strong>${active}</strong> item|s left</span>
				<!-- Remove this if you don't implement routing -->
				<ul class="filters">
          <!--
            TODO: Allow to see todo items based on filters `active`, `completed`
                  as well as showing `all`, which is selected by default.

                  Mark the current filter in use with the class `is-active`.
          -->
          <li><a href="?filter=all" class="selected">All</a></li>
          <li><a href="?filter=active">Active</a></li>
          <li><a href="?filter=completed">Completed</a></li>
				</ul>
        <!--
          TODO: Posting this form must delete all todo items that are already
                marked as being completed.

                This form should only be visible if there are completed items.
        -->
        <form action="todos/?filter=completed" method="post">
          <input type="hidden" name="method" value="delete" />
          <button class="clear-completed"><span>Clear completed (${completed-count})</span></button>
        </form>
			</footer>
		</section>
		<footer class="info">
      <p>Click on the text to edit a todo.</p>
      <p>
        Original template by <a href="http://github.com/sindresorhus">Sindre Sorhus</a>
        <br /> Reworked by <a href="http://github.com/olle">Olle Törnström</a>
      </p>
      <!--
          TODO: Properly replace the paragraph below with a link to you or your
                organization.
         -->
      <p>
        Created by <a href="http://todomvc.com">you</a>
      </p>
      <p>
        Part of <a href="http://github.com/olle/serverside-todomvc">Server-side TodoMVC</a>
      </p>
		</footer>
    <!-- Look ma, no JavaScript! -->
	</body>
</html>
