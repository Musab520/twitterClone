package net.twitter.controller;

import io.javalin.http.Context;
import net.twitter.dto.FollowerCreatedDto;
import net.twitter.dto.FollowerDto;
import net.twitter.dto.TweetCreatedDto;
import net.twitter.dto.TweetDto;
import net.twitter.service.FollowerService;
import net.twitter.service.TweetService;

import java.util.Date;

public class FollowerController {
    public static void addFollower(Context ctx) {
        FollowerCreatedDto followerCreatedDto = ctx.bodyAsClass(FollowerCreatedDto.class);
        FollowerDto followerDto = new FollowerDto();
        followerDto.setFollowerId(ctx.sessionAttribute("user_id"));
        followerDto.setFollowedId(followerCreatedDto.getFollowedId());
        ctx.json(FollowerService.getInstance().addFollower(followerDto));
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
