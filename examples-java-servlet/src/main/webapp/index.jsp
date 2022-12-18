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
      <c:if test="${showing}">
        <button name="hide" value="completed" title="Hide completed todo items">Hide</button>
      </c:if>
      <c:if test="${showing == false}">
        <button name="show" value="completed" title="Show completed todo items">Show</button>
      </c:if>
    </form>

    <form action="todos.do" method="post">
      <label for="todo">Todo</label>
      <input placeholder="What needs to be done?" ${editing == false ? 'autofocus' : ''} required autocomplete="off" name="todo" id="todo" />
    </form>

    <form id="todo-item" method="post" action="todo.do"></form>
    <ul>
      <c:forEach items="${active}" var="todo">
        <c:if test="${todo.isEditing()}">
          <li>
            <button name="complete" value="${todo.getId()}" form="todo-item" title="Mark completed"></button>
            <form class="inline" method="post" action="todos.do">
              <input type="hidden" name="id" value="${todo.getId()}" />
              <input name="update" value="${todo.getTodo()}" autofocus required autocomplete="off" />
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

      <c:if test="${showing}">
        <c:forEach items="${completed}" var="todo">
          <li>
            <button name="revert" value="${todo.getId()}" form="todo-item" title="Mark as active"></button>
            <span><c:out value="${todo.getTodo()}" /></span>
            <button name="delete" value="${todo.getId()}" form="todo-item" title="Delete todo item">&#x2715;</button>
          </li>
        </c:forEach>
      </c:if>
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