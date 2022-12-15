package main

import (
	_ "embed"
	"fmt"
	"log"
	"net/http"
	"text/template"
	"time"
)

//go:embed index.html
var indexTemplate string

var seq = time.Now().UnixMilli()

type Todo struct {
	Id        int64
	Text      string
	Completed bool
}

type Data struct {
	ActiveCount    int
	ActiveTodos    []Todo
	CompletedTodos []Todo
}

var data = Data{
	0,
	[]Todo{},
	[]Todo{}}

func addTodo(text string, data Data) Data {
	seq = seq + 1
	var NewTodo = Todo{seq, text, false}
	return addNewTodo(NewTodo, data)
}

func addNewTodo(todo Todo, data Data) Data {
	data.ActiveTodos = append(data.ActiveTodos, todo)
	data.ActiveCount = len(data.ActiveTodos)
	return data
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
			log.Printf("HANDLING: r.PostForm = %v - r.URL = %v\n", r.PostForm, r.URL)
			switch r.URL.Path {
			case "/todos":
				data = addTodo(r.FormValue("todo"), data)
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
