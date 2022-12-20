package net.twitter.controller;

import io.javalin.http.Context;
import net.twitter.dto.*;
import net.twitter.service.LikedTweetService;
import net.twitter.service.TweetService;

import java.util.Date;

public class LikedTweetController {
    public static void addLikedTweet(Context ctx) {
        LikedTweetCreatedDto likedTweetCreatedDto = ctx.bodyAsClass(LikedTweetCreatedDto.class);
        LikedTweetDto likedTweetDto = new LikedTweetDto();
        likedTweetDto.setLikedId(likedTweetCreatedDto.getLikedId());
        likedTweetDto.setLikerId(ctx.sessionAttribute("user_id"));
        IdDto idDto = new IdDto();
        idDto.setId(LikedTweetService.getInstance().addLikedTweet(likedTweetDto));
        ctx.json(idDto);
    }

    public static void findLikedTweet(Context ctx) {
        LikedTweetDto likedTweetDto = LikedTweetService.getInstance().findLikedTweet(ctx.pathParamAsClass("id", Integer.class).getOrDefault(null));
        if (likedTweetDto == null) {
            ctx.status(404);
        } else {
            ctx.json(likedTweetDto);
        }
    }
}
