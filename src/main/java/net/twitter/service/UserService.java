package net.twitter.service;

import com.auth0.json.mgmt.users.User;
import net.twitter.dto.UserDto;
import net.twitter.repository.UserRepository;

public class UserService {
    public static UserService instance = null;

    private UserService() {

    }

    public static final UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void addUser(UserDto userDto) {
        UserRepository.getInstance().insert(userDto);
    }

    public UserDto findUser(String id) {
        return UserRepository.getInstance().find(id);
    }

    public void updateUser(String id, UserDto userDto) {
        UserRepository.getInstance().update(id, userDto);
    }

    public UserDto findUserByUsername(String username) {
        return UserRepository.getInstance().findUserByUsername(username);
    }
}
