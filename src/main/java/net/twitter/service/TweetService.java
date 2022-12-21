package net.twitter.service;


import net.twitter.dto.TweetDto;
import net.twitter.repository.TweetRepository;

import java.util.List;

import static net.twitter.provider.DaoProvider.daos;

public class TweetService {
    private static TweetService instance = null;

    private TweetService() {

    }

    public static final TweetService getInstance() {
        if (instance == null) {
            instance = new TweetService();
        }
        return instance;
    }

    public int addTweet(TweetDto tweetDto) {
        return TweetRepository.getInstance().insert(tweetDto);
    }

    public TweetDto findTweet(int id) {
        return TweetRepository.getInstance().find(id);
    }

    public List<TweetDto> listFriendsAndLikedFriendsTweets(String userId) {
        return TweetRepository.getInstance().listFriendsAndLikedFriendsTweets(userId);
    }

    public List<TweetDto> listUserTweets(String userId) {
        return TweetRepository.getInstance().listUserTweets(userId);
    }

    public List<TweetDto> listTweets() {
        return TweetRepository.getInstance().listTweets();
    }
}
