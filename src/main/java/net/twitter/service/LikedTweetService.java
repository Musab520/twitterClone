package net.twitter.service;

import net.twitter.dto.LikedTweetDto;
import net.twitter.dto.TweetDto;
import net.twitter.repository.LikedTweetRepository;
import net.twitter.repository.TweetRepository;

public class LikedTweetService {
    private static LikedTweetService instance = null;

    private LikedTweetService() {

    }

    public static final LikedTweetService getInstance() {
        if (instance == null) {
            instance = new LikedTweetService();
        }
        return instance;
    }

    public int addLikedTweet(LikedTweetDto likedTweetDto) {
        return LikedTweetRepository.getInstance().insert(likedTweetDto);
    }

    public LikedTweetDto findLikedTweet(int id) {
        return LikedTweetRepository.getInstance().find(id);
    }
}
