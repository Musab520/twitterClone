package net.twitter.service;

import com.auth0.json.mgmt.users.User;
import net.twitter.dto.UserDto;
import net.twitter.repository.UserRepository;

public class UserService {
    private static UserService instance = null;
    private final UserRepository userRepository;

    public UserService() {
        userRepository = UserRepository.getInstance();
    }

    public static final UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void insert(UserDto userDto) {
        userRepository.insert(userDto);
    }

    public UserDto findUser(String id) {
        return userRepository.find(id);
    }

    public void update(String id, UserDto userDto) {
        userRepository.update(id, userDto);
    }
}
