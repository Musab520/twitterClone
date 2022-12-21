package net.twitter.repository;

import net.twitter.dto.FollowerDto;
import net.twitter.dto.TweetDto;
import net.twitter.dto.UserDto;

import java.util.List;

import static net.twitter.provider.DaoProvider.daos;

public class FollowerRepository {
    private static FollowerRepository instance = null;

    private FollowerRepository() {}

    public static FollowerRepository getInstance() {
        if (instance == null) {
            instance = new FollowerRepository();
        }
        return instance;
    }

    public int insert(FollowerDto followerDto) {
        return daos().withFollowerDao(followerDao -> followerDao.insert(followerDto));
    }

    public FollowerDto find(String id) {
        return daos().withFollowerDao(followerDao -> followerDao.find(id));
    }

    public List<UserDto> listUserFollowers(String userId) {
        return daos().withFollowerDao(followerDao -> followerDao.listUserFollowers(userId));
    }

    public void delete(String userId, String followingId) {
        daos().useFollowerDao(followerDao -> followerDao.delete(userId, followingId));
    }
}
