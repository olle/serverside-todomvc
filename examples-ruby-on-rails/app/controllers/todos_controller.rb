class TodosController < ApplicationController
  def index
    @active = Todo.active
    @completed = Todo.completed
    @hidden = session[:hidden] || false
    @editing = Todo.exists?(:editing => true)
  end

  def createTodo
    @todo = Todo.new(todo: params[:todo])
    @todo.save
    redirect_to root_path
  end

  def updateTodo
    @todo = Todo.find(params[:id])
    @todo.todo = params[:update]
    @todo.editing = false
    @todo.save
    redirect_to root_path
  end

  def handleTodo
    if params[:complete]
      Todo.find(params[:complete]).completed!
    end

    if params[:delete]
      Todo.delete(params[:delete])
    end

    if params[:edit]
      @todo = Todo.find(params[:edit])
      @todo.editing = true
      @todo.save
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
