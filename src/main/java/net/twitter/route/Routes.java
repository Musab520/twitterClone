package net.twitter.route;

import com.auth0.json.mgmt.users.User;
import io.javalin.apibuilder.EndpointGroup;
import net.twitter.controller.FollowerController;
import net.twitter.controller.LikedTweetController;
import net.twitter.controller.TweetController;
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

        path("api/tweets", () -> {
            get("{tweetId}", TweetController::findTweet);
            post(TweetController::addTweet);
        });
        path("api/followers", () -> {
            get("{id}", FollowerController::findFollower);
            post(FollowerController::addFollower);
        });
        path("api/likedTweets", () -> {
            get("{id}", LikedTweetController::findLikedTweet);
            post(LikedTweetController::addLikedTweet);
        });
    }
}

