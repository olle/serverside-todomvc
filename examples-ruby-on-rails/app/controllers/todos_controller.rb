class TodosController < ApplicationController
  def index
    @active = Todo.all
  end
end
