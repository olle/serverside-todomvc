@import java.util.List
@import todomvc.Todo

@param boolean editing
@param boolean hidden
@param List<Todo> active
@param List<Todo> completed
@param Long activeCount
@param Long completedCount

<!doctype html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="description"
    content="Helping you remember or select a server-side MV* framework - Todo apps for Spring Boot, Flask, PHP and many more">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Java Javalin JTE • Server-Side TodoMVC</title>
  <link rel="stylesheet" href="/style.css">
  <!-- Look mom, no JavaScript! -->
</head>

<body>
  <main>
    <h1>Todos <small title="${activeCount} Active items">${activeCount}</small></h1>
    <form action="controls" method="post">
      <button name="clear" value="completed" title="Clear ${completedCount} completed">${completedCount} Completed • Clear</button>
      @if(!hidden)
      <button name="hide" value="completed" title="Hide completed todo items">Hide</button>
      @else
      <button name="show" value="completed" title="Show completed todo items">Show</button>
      @endif
    </form>

    <form action="todos" method="post">
      <label for="todo">Todo</label>
      <input placeholder="What needs to be done?" required autocomplete="off" name="todo" id="todo" autofocus="${!editing}" />
    </form>

    <!--
        TODO: This form is posted with action/id pairs, an needs to be handled
              for completing, re-activating, deleting and starting to edit a
              todo item.
    -->
    <form id="todo-item" method="post" action="todo"></form>
    <ul>
      @for(Todo todo : active)
        @if(todo.isEditing())
          <li>
            <button name="complete" value="${todo.getId()}" form="todo-item" title="Mark completed"></button>
            <form class="inline" method="post" action="todos/${todo.getId()}">
              <input type="hidden" name="id" value="${todo.getId()}" />
              <input name="update" value="${todo.getText()}" autofocus required autocomplete="off" />
            </form>
          </li>
        @else
          <li> <!-- TODO: Otherwise render the todo item like this: -->
            <button name="complete" value="${todo.getId()}" form="todo-item" title="Mark completed"></button>
            <button name="edit" value="${todo.getId()}" form="todo-item" title="Click to edit">${todo.getText()}</button>
            <button name="delete" value="${todo.getId()}" form="todo-item" title="Delete todo item">&#x2715;</button>
          </li>
        @endif
      @endfor

      @if(!hidden)
        @for(Todo todo : completed)
          <li>
            <button name="revert" value="${todo.getId()}" form="todo-item" title="Mark as active"></button>
            <span>${todo.getText()}</span>
            <button name="delete" value="${todo.getId()}" form="todo-item" title="Delete todo item">&#x2715;</button>
          </li>
        @endfor
      @endif
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