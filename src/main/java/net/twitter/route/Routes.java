package net.twitter.route;

import com.auth0.json.mgmt.users.User;
import io.javalin.apibuilder.EndpointGroup;
import net.twitter.controller.UserController;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes implements EndpointGroup {

    @Override
    public void addEndpoints() {
        path("api/users", () -> {
            post(UserController::addUser);
            get("{userId}", UserController::findUser);
            put("{userId}", UserController::updateUser);
        });
    }
}

