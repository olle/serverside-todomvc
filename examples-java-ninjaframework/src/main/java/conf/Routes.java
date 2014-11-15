package conf;

import controllers.TodoMVCController;
import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;

public class Routes implements ApplicationRoutes {

  @Override
  public void init(Router router) {

    router.GET().route("/").with(TodoMVCController.class, "index");

    router.POST().route("/todos").with(TodoMVCController.class, "createOrUpdateTodo");
    router.POST().route("/todos/{id}/delete").with(TodoMVCController.class, "deleteTodo");
    router.POST().route("/todos/{id}/toggle").with(TodoMVCController.class, "toggleTodoStatus");
    router.POST().route("/clear").with(TodoMVCController.class, "clearCompleted");
    router.POST().route("/complete").with(TodoMVCController.class, "markCompleted");

    router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");
  }
}
