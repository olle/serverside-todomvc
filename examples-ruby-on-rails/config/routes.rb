Rails.application.routes.draw do
  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html
  root 'todos#index'
  post "/todos", to: 'todos#create', as: 'create_todo'
end
