package net.twitter.service;

import net.twitter.dto.FollowerDto;
import net.twitter.dto.TweetDto;
import net.twitter.dto.UserDto;
import net.twitter.repository.FollowerRepository;
import net.twitter.repository.TweetRepository;

import java.util.List;

import static net.twitter.provider.DaoProvider.daos;

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

    public List<UserDto> listUserFollowers(String userId) {
        return FollowerRepository.getInstance().listUserFollowers(userId);
    }

    public void delete(String userId, String followingId) {
        FollowerRepository.getInstance().delete(userId, followingId);
    }
}
