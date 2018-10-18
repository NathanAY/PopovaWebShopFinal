package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.model.User;
import org.testng.annotations.Ignore;

import java.util.ArrayList;
import java.util.List;

@Ignore
@org.junit.Ignore
public class UserServiceMock implements UserService {

    private final List<User> users = new ArrayList<>();

    @Override
    public void save(User user) {
        this.users.add(user);
    }

    @Override
    public User findByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
