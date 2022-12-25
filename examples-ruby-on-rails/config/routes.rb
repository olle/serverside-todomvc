Rails.application.routes.draw do
  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html
  root "todos#index"
  post "/todos", to: "todos#createTodo"
  post "/todos/:id", to: "todos#updateTodo"
  post "/todo", to: "todos#handleTodo"
  post "/controls", to: "todos#handleControls"
end
