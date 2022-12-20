package net.twitter.controller;

import io.javalin.http.Context;
import net.twitter.dto.*;
import net.twitter.service.FollowerService;
import net.twitter.service.TweetService;

import java.util.Date;

public class FollowerController {
    public static void addFollower(Context ctx) {
        FollowerCreatedDto followerCreatedDto = ctx.bodyAsClass(FollowerCreatedDto.class);
        FollowerDto followerDto = new FollowerDto();
        followerDto.setFollowerId(ctx.sessionAttribute("user_id"));
        followerDto.setFollowedId(followerCreatedDto.getFollowedId());
        IdDto idDto = new IdDto();
        idDto.setId(FollowerService.getInstance().addFollower(followerDto));
        ctx.json(idDto);
    }

    public static void findFollower(Context ctx) {
        FollowerDto followerDto = FollowerService.getInstance().findFollower(ctx.pathParam("id"));
        if (followerDto == null) {
            ctx.status(404);
        } else {
            ctx.json(followerDto);
        }
    }
}
