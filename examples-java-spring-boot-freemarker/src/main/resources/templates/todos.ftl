<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Java Spring Boot • Server-side TodoMVC</title>
    <link rel="stylesheet" href="/css/style.css">
</head>

<body>
    <section class="todoapp">
        <header class="header">
            <h1>todos</h1>
            <form action="todos/" method="post">
                <input type="text" name="item-text" placeholder="What needs to be done?" autocomplete="off" class="new-todo" ${editing?string('', 'autofocus')} />
            </form>
        </header>
        <#if todos?size &gt; 0>
        <section class="main">
            <form action="todos/toggle?filter=completed" method="post" class="toggle-all">
                <button type="submit" class="icon angle-double down">&or;</button>
            </form>
            <ul class="todo-list">
                <#list todos as item>
                <li class="${item.status!''}">
                    <#if item.editing == true>
                    <form action="todos/${item.id}" method="post">
                        <input type="text" name="item-text" value="${item.text}" autofocus="autofocus" autocomplete="off" class="edit-todo" />
                    </form>
                    <#else>
                    <div class="view">
                        <form action="todos/${item.id}/status" method="post" class="item-toggle-completed is-active">
                            <button class="toggle ${item.status!''}"></button>
                        </form>
                        <#if item.active>
                        <label><a href="?action=edit&amp;item-id=${item.id}" class="item-text">${item.text}</a></label>
                        <#else>
                        <label>${item.text}</label>
                        </#if>
                        <form action="todos/${item.id}" method="post" class="item-delete">
                            <input type="hidden" name="_method" value="delete" />
                            <button class="destroy"></button>
                        </form>
                    </div>
                    </#if>
                </li>
                </#list>
            </ul>
        </section>
        </#if>
        <#if todos?size &gt; 0>
        <footer class="footer">
            <span class="todo-count"><strong>${active}</strong> item<#if active &gt; 1 || active == 0>s</#if> left</span>
            <ul class="filters">
              <#if completed &gt; 0>
                <li><a href="?filter=all" <#if filter == "all">class="selected"</#if>>All</a></li>
                <li><a href="?filter=active" <#if filter == "active">class="selected"</#if>>Active</a></li>
                <li><a href="?filter=completed" <#if filter == "completed">class="selected"</#if>>Completed</a></li>
              </#if>
            </ul>
            <#if completed &gt; 0>
            <form action="todos/?filter=completed" method="post">
                <input type="hidden" name="_method" value="delete" />
                <button class="clear-completed"><span>Clear completed ({completed-count})</span></button>
            </form>
            </#if>
        </footer>
        </#if>
    </section>
    <footer class="info">
        <p>Click on the text to edit a todo.</p>
        <p>
            Original template by <a href="http://github.com/sindresorhus">Sindre Sorhus</a>
            <br /> Reworked by <a href="http://github.com/olle">Olle Törnström</a>
        </p>
        <p>
            Part of <a href="http://github.com/olle/serverside-todomvc">Server-side TodoMVC</a>
        </p>
    </footer>
    <!-- Look ma, no JavaScript! -->
</body>

</html>
