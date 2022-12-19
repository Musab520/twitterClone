package net.twitter.service;

import net.twitter.dto.FollowerDto;
import net.twitter.dto.TweetDto;
import net.twitter.repository.FollowerRepository;
import net.twitter.repository.TweetRepository;

public class FollowerService {
    private static FollowerService instance = null;

    private FollowerService() {

    }

    public static final FollowerService getInstance() {
        if (instance == null) {
            instance = new FollowerService();
        }
        return instance;
    }

    public int addFollower(FollowerDto followerDto) {
        return FollowerRepository.getInstance().insert(followerDto);
    }

    public FollowerDto findFollower(String id) {
        return FollowerRepository.getInstance().find(id);
    }
}
