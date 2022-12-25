package main

import (
	"time"

	"github.com/kataras/iris/v12"
)

const (
	Active    int = 0
	Completed     = 1
)

type Todo struct {
	Id      int64
	Status  int
	Text    string
	Editing bool
}

type Data struct {
	Hidden bool
	Todos  []Todo
}

var seq = time.Now().UnixMilli()

var data = Data{false, []Todo{}}

func addNewTodo(Text string) {
	seq = seq + 1
	var NewTodo = Todo{seq, Active, Text, false}
	data.Todos = append(data.Todos, NewTodo)
}

func count(Status int) int {
	cnt := 0
	for i := 0; i < len(data.Todos); i++ {
		if data.Todos[i].Status == Status {
			cnt++
		}
	}
	return cnt
}

func todos(Status int) []Todo {
	results := []Todo{}
	for i := 0; i < len(data.Todos); i++ {
		if data.Todos[i].Status == Status {
			results = append(results, data.Todos[i])
		}
	}
	return results
}

func main() {
	app := iris.New()

	tmpl := iris.HTML("./templates", ".html")
	tmpl.Reload(true) // reload templates on each request (development mode)

	app.RegisterView(tmpl)

	app.Get("/", showIndex)
	app.Post("/todos", createTodo)

	app.Listen(":8080")
}

func createTodo(ctx iris.Context) {
	todo := ctx.PostValue("todo")
	addNewTodo(todo)

	ctx.Redirect("/", iris.StatusMovedPermanently)
}

func showIndex(ctx iris.Context) {
	ctx.ViewData("Active", todos(Active))
	ctx.ViewData("Completed", todos(Completed))
	ctx.ViewData("ActiveCount", count(Active))
	ctx.ViewData("CompletedCount", count(Completed))
	if err := ctx.View("index.html"); err != nil {
		ctx.HTML("<h3>%s</h3>", err.Error())
		return
	}
}
