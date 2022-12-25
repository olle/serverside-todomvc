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
      Todo.find(params[:complete]).completed!
    end

    if params[:delete]
      Todo.delete(params[:delete])
    end

    redirect_to root_path
  end

  def controls
    if params[:clear]
      Todo.where(status: :completed).delete_all
    end
    redirect_to root_path
  end
end
