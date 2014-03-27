<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Servlet • TodoMVC</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<section id="todoapp">
		<header id="header">
			<h1>todos</h1>
			<form method="post" action="new.do">
				<input id="new-todo" name="new-todo" placeholder="What needs to be done?" autofocus="autofocus" autocomplete="off">
			</form>
		</header>
		<section id="main" class="empty-${todos.isEmpty()}">
			<input id="toggle-all" type="checkbox"> <label for="toggle-all">Mark all as complete</label>
			<ul id="todo-list">
				<c:forEach items="${todos}" var="todo">
					<li class="${todo.getStatus().toString()}">
						<div class="view">
							<a href="toggle.do?todo-id=${todo.getId()}"><span class="toggle"></span></a>
							<label><c:out value="${todo.getTodo()}" /></label>
							<form method="post" action="delete.do"><input type="hidden" name="todo-id" value="${todo.getId()}" /><button class="destroy"></button></form>
						</div> <input class="edit" value="Rule the web">
					</li>
				</c:forEach>
			</ul>
		</section>
		<footer id="footer" class="empty-${todos.isEmpty()}">
			<span id="todo-count"><strong><c:out value="${todos.size()}" /></strong> item<c:if test="${todos.size() == 0 or todos.size() gt 1}">s</c:if> left</span>
			<!-- Hidden if no completed items are left ↓ -->
			<form method="post" action="clear.do">
        <button id="clear-completed" class="empty-${completed.isEmpty()}">Clear completed (<c:out value="${completed.size()}" />)</button>
 			</form>
		</footer>
	</section>
	<footer id="info">
		<!-- <p>Double-click to edit a todo</p> -->
    <p>Created by <a href="https://github.com/olle">Olle Törnström</a></p>
    <p>Part of <a href="https://github.com/olle/serverside-todomvc">Server-side TodoMVC</a></p>
	</footer>
</body>
</html>
