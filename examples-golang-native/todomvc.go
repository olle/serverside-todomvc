package main

import (
	_ "embed"
	"log"
	"net/http"
	"text/template"
)

//go:embed index.html
var indexTemplate string

func main() {
	mux := http.NewServeMux()
	mux.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {

		template, err := template.New("index").Parse(indexTemplate)

		if err != nil {
			w.WriteHeader(500)
			log.Fatal(err)
			return
		}

		w.WriteHeader(200)

		data := struct {
			ActiveCount int
		}{
			ActiveCount: 0,
		}
		template.Execute(w, data)
	})
	err := http.ListenAndServe("127.0.0.1:8000", mux)

	if err != nil {
		log.Fatal(err)
	}
}
