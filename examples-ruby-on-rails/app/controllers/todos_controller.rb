class TodosController < ApplicationController
  def index
    @active = Todo.active
    @completed = Todo.completed
    @hidden = session[:hidden] || false
  end

  def createTodo
    @todo = Todo.new(todo: params[:todo])
    if @todo.save
      redirect_to root_path
    end
  end

  def handleTodo
    if params[:complete]
      Todo.find(params[:complete]).completed!
    end

    if params[:delete]
      Todo.delete(params[:delete])
    end

    redirect_to root_path
  end

  def handleControls
    if params[:clear]
      Todo.where(status: :completed).delete_all
    end

    if params[:hide]
      session[:hidden] = true
    end

    if params[:show]
      session[:hidden] = false
    end

    redirect_to root_path
  end
end
