package net.twitter.controller;

import io.javalin.http.Context;
import net.twitter.dto.TweetDto;
import net.twitter.dto.UserDto;
import net.twitter.service.TweetService;
import net.twitter.service.UserService;

public class TweetController {
    public static void addTweet(Context ctx) {
        TweetDto tweetDto = ctx.bodyAsClass(TweetDto.class);
        TweetService.getInstance().addTweet(tweetDto);
    }

    public static void findTweet(Context ctx) {
        TweetDto tweetDto = TweetService.getInstance().findTweet(Integer.parseInt(ctx.pathParam("tweetId")));
        if (tweetDto == null) {
            ctx.status(404);
        } else {
            ctx.json(tweetDto);
        }
    }
}
