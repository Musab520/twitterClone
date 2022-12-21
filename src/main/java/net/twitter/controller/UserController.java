package net.twitter.controller;

import com.auth0.json.mgmt.users.User;
import io.javalin.http.Context;
import net.twitter.dto.UserDto;
import net.twitter.service.UserService;

public class UserController {

    public static void addUser(Context ctx) {
        UserDto userDto = ctx.bodyAsClass(UserDto.class);
        UserService.getInstance().addUser(userDto);
    }

    public static void updateUser(Context ctx) {
        UserDto userDto = ctx.bodyAsClass(UserDto.class);
        UserService.getInstance().updateUser(ctx.pathParam("userId"), userDto);
    }

    public static void findUser(Context ctx) {
        UserDto userDto = UserService.getInstance().findUser(ctx.pathParam("userId"));
        if (userDto == null) {
            ctx.status(404);
        } else {
            ctx.json(userDto);
        }
    }

    public static void findUserByUsername(Context ctx) {
        UserDto userDto = UserService.getInstance().findUserByUsername(ctx.pathParam("username"));
        if (userDto == null) {
            ctx.status(404);
        } else {
            ctx.json(userDto);
        }
    }
}
