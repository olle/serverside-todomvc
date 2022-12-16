package main

import (
	_ "embed"
	"fmt"
	"log"
	"net/http"
	"strconv"
	"text/template"
	"time"
)

//go:embed index.html
var indexTemplate string

var seq = time.Now().UnixMilli()

type Todo struct {
	Id      int64
	Text    string
	Editing bool
}

type Data struct {
	Show           bool
	NotEditing     bool
	ActiveCount    int
	CompletedCount int
	ActiveTodos    []Todo
	CompletedTodos []Todo
}

var data = Data{true, true, 0, 0, []Todo{}, []Todo{}}

func updateCounts(data Data) Data {
	data.ActiveCount = len(data.ActiveTodos)
	data.CompletedCount = len(data.CompletedTodos)
	return data
}

func addTodo(text string, data Data) Data {
	seq = seq + 1
	var NewTodo = Todo{seq, text, false}
	data.ActiveTodos = append(data.ActiveTodos, NewTodo)
	return updateCounts(data)
}

func toInt(s string) int64 {
	n, err := strconv.ParseInt(s, 10, 64)
	if err != nil {
		log.Fatal(err)
	}
	return n
}

func completeTodo(id string, data Data) Data {
	todoId := toInt(id)
	var NewActiveTodos = []Todo{}
	for i := 0; i < len(data.ActiveTodos); i++ {
		todo := data.ActiveTodos[i]
		if todo.Id == todoId {
			data.CompletedTodos = append(data.CompletedTodos, todo)
		} else {
			NewActiveTodos = append(NewActiveTodos, todo)
		}
	}
	data.ActiveTodos = NewActiveTodos
	return updateCounts(data)
}

func editTodo(id string, data Data) Data {
	todoId := toInt(id)
	var NewActiveTodos = []Todo{}
	for i := 0; i < len(data.ActiveTodos); i++ {
		todo := data.ActiveTodos[i]
		if todo.Id == todoId {
			data.NotEditing = false
			NewActiveTodos = append(NewActiveTodos, Todo{todo.Id, todo.Text, true})
		} else {
			NewActiveTodos = append(NewActiveTodos, todo)
		}
	}
	data.ActiveTodos = NewActiveTodos
	return updateCounts(data)
}

func updateTodo(id string, text string, data Data) Data {
	todoId := toInt(id)
	var NewActiveTodos = []Todo{}
	for i := 0; i < len(data.ActiveTodos); i++ {
		todo := data.ActiveTodos[i]
		if todo.Id == todoId {
			data.NotEditing = true
			NewActiveTodos = append(NewActiveTodos, Todo{todo.Id, text, false})
		} else {
			NewActiveTodos = append(NewActiveTodos, todo)
		}
	}
	data.ActiveTodos = NewActiveTodos
	return updateCounts(data)
}

func revertTodo(id string, data Data) Data {
	todoId := toInt(id)
	var NewCompletedTodos = []Todo{}
	for i := 0; i < len(data.CompletedTodos); i++ {
		todo := data.CompletedTodos[i]
		if todo.Id == todoId {
			data.ActiveTodos = append(data.ActiveTodos, todo)
		} else {
			NewCompletedTodos = append(NewCompletedTodos, todo)
		}
	}
	data.CompletedTodos = NewCompletedTodos
	return updateCounts(data)
}

func clearCompleted(data Data) Data {
	data.CompletedTodos = []Todo{}
	return updateCounts(data)
}

func deleteTodo(id string, data Data) Data {
	todoId := toInt(id)
	data.ActiveTodos = deleteById(todoId, data.ActiveTodos)
	data.CompletedTodos = deleteById(todoId, data.CompletedTodos)
	return updateCounts(data)
}

func deleteById(id int64, todos []Todo) []Todo {
	var NewTodos = []Todo{}
	for i := 0; i < len(todos); i++ {
		todo := todos[i]
		if todo.Id != id {
			NewTodos = append(NewTodos, todo)
		}
	}
	return NewTodos
}

func main() {

	mux := http.NewServeMux()
	mux.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {

		switch r.Method {
		case "GET":
			template, err := template.New("index").Parse(indexTemplate)
			if err != nil {
				w.WriteHeader(500)
				log.Fatal(err)
				return
			}
			w.WriteHeader(200)
			template.Execute(w, data)

		case "POST":
			if err := r.ParseForm(); err != nil {
				fmt.Fprintf(w, "ParseForm() err: %v", err)
				return
			}
			//log.Printf("HANDLING: r.URL=%v r.PostForm=%v\n", r.URL, r.PostForm)
			switch r.URL.Path {
			case "/controls":
				if r.PostForm.Has("hide") {
					data.Show = false
				}
				if r.PostForm.Has("show") {
					data.Show = true
				}
				if r.PostForm.Has("clear") {
					data = clearCompleted(data)
				}
			case "/todos":
				if r.PostForm.Has("update") {
					data = updateTodo(r.FormValue("id"), r.FormValue("update"), data)
				} else {
					data = addTodo(r.FormValue("todo"), data)
				}

			case "/todo":
				if r.PostForm.Has("complete") {
					data = completeTodo(r.FormValue("complete"), data)
				}
				if r.PostForm.Has("revert") {
					data = revertTodo(r.FormValue("revert"), data)
				}
				if r.PostForm.Has("delete") {
					data = deleteTodo(r.FormValue("delete"), data)
				}
				if r.PostForm.Has("edit") {
					data = editTodo(r.FormValue("edit"), data)
				}
			}
			http.Redirect(w, r, "/", 301)

		default:
			fmt.Fprintf(w, "Sorry, only GET and POST methods are supported.")
		}
	})

	err := http.ListenAndServe("127.0.0.1:8080", mux)
	if err != nil {
		log.Fatal(err)
	}
}
