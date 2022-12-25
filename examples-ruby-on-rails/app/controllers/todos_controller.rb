class TodosController < ApplicationController
  def index
    @active = Todo.active
    @completed = Todo.completed
  end

  def create
    @todo = Todo.new(todo: params[:todo])
    if @todo.save
      redirect_to root_path
    end
  end

  def handle
    if params[:complete]
      @todo = Todo.find(params[:complete])
      @todo.completed!
      if @todo.save
        redirect_to root_path
      end
    end

    if params[:delete]
      Todo.delete(params[:delete])
      redirect_to root_path
    end
  end
end
