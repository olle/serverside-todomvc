<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="description"
    content="Helping you remember or select a server-side MV* framework - Todo apps for Spring Boot, Flask, PHP and many more">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Java Servlet/JSP Jetty • Server-Side TodoMVC</title>
  <link rel="stylesheet" href="/css/style.css">
  <!-- Look mom, no JavaScript! -->
</head>

<body>
  <main>
    <h1>Todos <small title="${active.size()} Active items"><c:out value="${active.size()}" /></small></h1>
    <form action="controls.do" method="post">
      <button name="clear" value="completed" title="Clear ${completed.size()} completed"><c:out value="${completed.size()}" /> Completed • Clear</button>
      <!--
          TODO: Render the hide/show button depending on the current state of
                such a filter, to either show completed items or not.
      -->
      <button name="hide" value="completed" title="Hide completed todo items">Hide</button>
      <button name="show" value="completed" title="Show completed todo items">Show</button>
    </form>

    <form action="todos.do" method="post">
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
    <form id="todo-item" method="post" action="todo.do"></form>
    <ul>
      <!--
          TODO: First render from a list of all active todo items.
      -->
      <c:forEach items="${active}" var="todo">
        <c:if test="${todo.isEditing()}">
          <li>
            <button name="complete" value="${todo.getId()}" form="todo-item" title="Mark completed"></button>
            <form class="inline" method="post" action="todos/${todo.getId()}">
              <input type="hidden" name="id" value="${todo.getTodo()}" />
              <input name="update" value="{todo-text}" autofocus required autocomplete="off" />
            </form>
          </li>
        </c:if>
        <c:if test="${todo.isNotEditing()}">
          <li>
            <button name="complete" value="${todo.getId()}" form="todo-item" title="Mark completed"></button>
            <button name="edit" value="${todo.getId()}" form="todo-item" title="Click to edit"><c:out value="${todo.getTodo()}" /></button>
            <button name="delete" value="${todo.getId()}" form="todo-item" title="Delete todo item">&#x2715;</button>
          </li>
        </c:if>
      </c:forEach>

      <!--
          TODO: Then render the list of todo items marked as completed, unless
                the current filter is toggled to hide completed.
      -->
      <c:forEach items="${completed}" var="todo">
        <li>
          <button name="revert" value="${todo.getId()}" form="todo-item" title="Mark as active"></button>
          <span><c:out value="${todo.getTodo()}" /></span>
          <button name="delete" value="${todo.getId()}" form="todo-item" title="Delete todo item">&#x2715;</button>
        </li>
      </c:forEach>
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