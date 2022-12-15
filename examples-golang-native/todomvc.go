package main

import (
	_ "embed"
	"fmt"
	"log"
	"net/http"
	"text/template"
)

//go:embed index.html
var indexTemplate string

type Todo struct {
	Text string
}

type Data struct {
	ActiveCount int
	Todos       []Todo
}

func main() {

	var data = Data{0, []Todo{}}

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
			// Call ParseForm() to parse the raw query and update r.PostForm and r.Form.
			if err := r.ParseForm(); err != nil {
				fmt.Fprintf(w, "ParseForm() err: %v", err)
				return
			}
			log.Printf("Post from website! r.PostFrom = %v\n", r.PostForm)
			data.Todos = append(data.Todos, Todo{r.FormValue("todo")})
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
