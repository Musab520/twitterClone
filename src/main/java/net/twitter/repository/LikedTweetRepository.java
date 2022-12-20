package net.twitter.repository;

import net.twitter.dto.LikedTweetDto;
import net.twitter.dto.TweetDto;

import static net.twitter.provider.DaoProvider.daos;

public class LikedTweetRepository {
    private static LikedTweetRepository instance = null;

    private LikedTweetRepository() {}

    public static LikedTweetRepository getInstance() {
        if (instance == null) {
            instance = new LikedTweetRepository();
        }
        return instance;
    }

    public int insert(LikedTweetDto likedTweetDto) {
        return daos().withLikedTweetDao(likedTweetDao -> likedTweetDao.insert(likedTweetDto));
    }

    public LikedTweetDto find(int id) {
        return daos().withLikedTweetDao(likedTweetDao -> likedTweetDao.find(id));
    }
}
