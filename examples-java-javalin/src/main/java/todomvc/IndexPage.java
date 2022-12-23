package todomvc;

import io.javalin.http.Context;

class IndexPage {

    public static Context show(Context ctx) {

        return ctx.render("index.jte", Todos.asMap());
    }
}