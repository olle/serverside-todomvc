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
	ActiveCount    int
	CompletedCount int
	ActiveTodos    []Todo
	CompletedTodos []Todo
}

var data = Data{true, 0, 0, []Todo{}, []Todo{}}

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

func completeTodo(id string, data Data) Data {
	todoId, err := strconv.ParseInt(id, 10, 64)
	if err != nil {
		log.Fatal(err)
	}
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
			log.Printf("HANDLING: r.URL=%v r.PostForm=%v\n", r.URL, r.PostForm)
			switch r.URL.Path {
			case "/controls":
				if r.PostForm.Has("hide") {
					data.Show = false
				}
				if r.PostForm.Has("show") {
					data.Show = true
				}
			case "/todos":
				data = addTodo(r.FormValue("todo"), data)

			case "/todo":
				if r.PostForm.Has("complete") {
					data = completeTodo(r.FormValue("complete"), data)
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
