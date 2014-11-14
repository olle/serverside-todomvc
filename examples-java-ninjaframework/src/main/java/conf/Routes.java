package conf;

import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import controllers.TodoMVCController;

public class Routes implements ApplicationRoutes {

  @Override
  public void init(Router router) {

    router.GET().route("/").with(TodoMVCController.class, "index");
    router.POST().route("/todos").with(TodoMVCController.class, "addTodo");
    router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");
  }
}
