package net.twitter.service;


import net.twitter.dto.TweetDto;
import net.twitter.repository.TweetRepository;

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

    public void addTweet(TweetDto userDto) {
        TweetRepository.getInstance().insert(userDto);
    }

    public TweetDto findTweet(int id) {
        return TweetRepository.getInstance().find(id);
    }
}
