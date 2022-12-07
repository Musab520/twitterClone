package net.twitter.repository;

import com.auth0.json.mgmt.users.User;
import net.twitter.dto.UserDto;
import net.twitter.provider.DaoProvider;

import static net.twitter.provider.DaoProvider.daos;

public class UserRepository {
    private static UserRepository instance = null;

    private UserRepository() {

    }

    private static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserDto find(String userId) {
        return daos().withUserDao((userDao) -> userDao.find(userId));
    }

    public void insert(String id, UserDto userDto) {
        daos().useUserDao((userDao) -> userDao.insert(id, userDto));
    }

    private void update(UserDto userDto) {
        daos().useUserDao((userDao) -> userDao.update(userDto));
    }
}
