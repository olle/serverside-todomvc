class TodosController < ApplicationController
  def index
    @active = Todo.all
  end
  
  def create
    
    @todo = Todo.new(todo:params[:todo])
    if @todo.save
      redirect_to root_path
    end
  end

end
