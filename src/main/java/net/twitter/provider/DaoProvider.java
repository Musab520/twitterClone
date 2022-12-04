package net.twitter.provider;

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
}
