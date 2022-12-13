package net.twitter.repository;

import net.twitter.dto.TweetDto;

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

    public void insert(TweetDto tweetDto) {
        daos().useTweetDao(tweetDao -> tweetDao.insert(tweetDto));
    }

    public TweetDto find(int id) {
        return daos().withTweetDao(tweetDao -> tweetDao.find(id));
    }
}
