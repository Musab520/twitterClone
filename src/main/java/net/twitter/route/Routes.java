package net.twitter.route;

import com.auth0.json.mgmt.users.User;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.plugin.rendering.vue.VueComponent;
import net.twitter.controller.UserController;
import net.twitter.infra.MapIncludesRenderer;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes implements EndpointGroup {

    @Override
    public void addEndpoints() {
        path("/", () -> {
            get(new VueComponent("home", null, new MapIncludesRenderer()));
        });
        path("api/users", () -> {
            post(UserController::addUser);
            get("{userId}", UserController::findUser);
            put("{userId}", UserController::updateUser);
        });
    }
}

