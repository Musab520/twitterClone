package net.twitter.controller;

import io.javalin.http.Context;
import net.twitter.dto.IdDto;
import net.twitter.dto.TweetCreatedDto;
import net.twitter.dto.TweetDto;
import net.twitter.dto.UserDto;
import net.twitter.service.TweetService;
import net.twitter.service.UserService;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.List;

import static net.twitter.provider.DaoProvider.daos;

public class TweetController {
    public static void addTweet(Context ctx) {
        TweetCreatedDto tweetCreatedDto = ctx.bodyAsClass(TweetCreatedDto.class);
        TweetDto tweetDto = new TweetDto();
        tweetDto.setAuthorId(ctx.sessionAttribute("user_id"));
        tweetDto.setCreatedOn(new Date());
        tweetDto.setMessage(tweetCreatedDto.getMessage());
        IdDto idDto = new IdDto();
        idDto.setId(TweetService.getInstance().addTweet(tweetDto));
        ctx.json(idDto);
    }

    public static void findTweet(Context ctx) {
        TweetDto tweetDto = TweetService.getInstance().findTweet(ctx.pathParamAsClass("tweetId", Integer.class).getOrDefault(null));
        if (tweetDto == null) {
            ctx.status(404);
        } else {
            ctx.json(tweetDto);
        }
    }

    public static void listFriendsAndLikedFriendsTweets(Context ctx) {
        List<TweetDto> tweetDtoList = TweetService.getInstance().listFriendsAndLikedFriendsTweets(ctx.sessionAttribute("user_id"));
        if (tweetDtoList == null) {
            ctx.status(404);
        } else {
            ctx.json(tweetDtoList);
        }
    }

    public static void listUserTweets(Context ctx) {
        List<TweetDto> tweetDtoList = TweetService.getInstance().listUserTweets(ctx.sessionAttribute("user_id"));
        if (tweetDtoList == null) {
            ctx.status(404);
        } else {
            ctx.json(tweetDtoList);
        }
    }

    public static void listTweets(Context ctx) {
        List<TweetDto> tweetDtoList = TweetService.getInstance().listTweets();
        if (tweetDtoList == null) {
            ctx.status(404);
        } else {
            ctx.json(tweetDtoList);
        }
    }
}
