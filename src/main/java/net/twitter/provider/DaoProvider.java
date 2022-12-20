package net.twitter.provider;

import net.twitter.dao.FollowerDao;
import net.twitter.dao.LikedTweetDao;
import net.twitter.dao.TweetDao;
import net.twitter.dao.UserDao;

import java.util.function.Consumer;
import java.util.function.Function;

public class DaoProvider {
    private static DaoProvider instance = instance();

    private DaoProvider() { }

    public static final DaoProvider instance() {
        if (instance == null) {
            instance = new DaoProvider();
        }
        return instance;
    }

    public static final DaoProvider daos() {
        return instance();
    }

    public void useUserDao(Consumer<UserDao> consumer) {
        JdbiProvider.instance().jdbi().useExtension(UserDao.class, consumer::accept);
    }

    public <T> T withUserDao(Function<UserDao, T> function) {
        return JdbiProvider.instance().jdbi().withExtension(UserDao.class, function::apply);
    }

    public void useTweetDao(Consumer<TweetDao> consumer) {
        JdbiProvider.instance().jdbi().useExtension(TweetDao.class, consumer::accept);
    }

    public <T> T withTweetDao(Function<TweetDao, T> function) {
        return JdbiProvider.instance().jdbi().withExtension(TweetDao.class, function::apply);
    }

    public void useFollowerDao(Consumer<FollowerDao> consumer) {
        JdbiProvider.instance().jdbi().useExtension(FollowerDao.class, consumer::accept);
    }

    public <T> T withFollowerDao(Function<FollowerDao, T> function) {
        return JdbiProvider.instance().jdbi().withExtension(FollowerDao.class, function::apply);
    }
    public void useLikedTweetDao(Consumer<LikedTweetDao> consumer) {
        JdbiProvider.instance().jdbi().useExtension(LikedTweetDao.class, consumer::accept);
    }

    public <T> T withLikedTweetDao(Function<LikedTweetDao, T> function) {
        return JdbiProvider.instance().jdbi().withExtension(LikedTweetDao.class, function::apply);
    }

}
