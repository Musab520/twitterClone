package net.twitter.repository;

import net.twitter.dao.TweetDao;
import net.twitter.dto.TweetDto;

import java.util.List;

import static net.twitter.provider.DaoProvider.daos;

public class TweetRepository {
    private static TweetRepository instance = null;

    private TweetRepository() {}

    public static TweetRepository getInstance() {
        if (instance == null) {
            instance = new TweetRepository();
        }
        return instance;
    }

    public int insert(TweetDto tweetDto) {
        return daos().withTweetDao(tweetDao -> tweetDao.insert(tweetDto));
    }

    public TweetDto find(int id) {
        return daos().withTweetDao(tweetDao -> tweetDao.find(id));
    }

    public List<TweetDto> listFriendsAndLikedFriendsTweets(String userId) {
        return daos().withTweetDao(tweetDao -> tweetDao.listFriendsAndLikedFriendsTweets(userId));
    }

    public List<TweetDto> listUserTweets(String userId) {
        return daos().withTweetDao(tweetDao -> tweetDao.listUserTweets(userId));
    }

    public List<TweetDto> listTweets() {
        return daos().withTweetDao(TweetDao::listTweets);
    }
}
