package net.proselyte.springsecurityapp.validator;

import net.proselyte.springsecurityapp.model.User;
import net.proselyte.springsecurityapp.service.UserServiceMock;
import org.springframework.validation.*;
import org.springframework.web.bind.EscapedErrors;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

@Test
public class UserValidatorTest {
    private final UserValidator userValidator = new UserValidator();

    @BeforeTest
    public void setUp() {
        UserServiceMock userServiceMock = new UserServiceMock();
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("name1");
        userServiceMock.save(user1);
        userValidator.setUserService(userServiceMock);
    }

    @Test
    public void validateDuplicateUserTest() {
        Map<String, String> errors = new HashMap<>();
        User user = new User();
        user.setUsername("name1");
        user.setPassword("1");
        user.setConfirmPassword("1");
        userValidator.valid(user, errors);
        assertEquals("Duplicate.userForm.username", errors.get("username"));
    }

    @Test
    public void validateShortPasswordTest() {
        Map<String, String> errors = new HashMap<>();
        User user = new User();
        user.setUsername("name");
        user.setPassword("");
        user.setConfirmPassword("1");
        userValidator.valid(user, errors);
        assertEquals("Size.userForm.password", errors.get("password"));
    }

    @Test
    public void validateLongPasswordTest() {
        Map<String, String> errors = new HashMap<>();
        User user = new User();
        user.setUsername("name");
        user.setPassword("89123hry37819rhufye798f3hufhewof890ujhiwr893rhuewioh1");
        user.setConfirmPassword("1");
        userValidator.valid(user, errors);
        assertEquals("Size.userForm.password", errors.get("password"));
    }

    @Test
    public void validateShortUsernameTest() {
        Map<String, String> errors = new HashMap<>();
        User user = new User();
        user.setUsername("");
        user.setPassword("1");
        user.setConfirmPassword("1");
        userValidator.valid(user, errors);
        assertEquals("Size.userForm.username", errors.get("username"));
    }

    @Test
    public void validateLongUsernameTest() {
        Map<String, String> errors = new HashMap<>();
        User user = new User();
        user.setUsername("nameoiwaeioaewjifikimfnwvowev[pkdmkznvjiero1");
        user.setPassword("1");
        user.setConfirmPassword("1");
        userValidator.valid(user, errors);
        assertEquals("Size.userForm.username", errors.get("username"));
    }

    @Test
    public void validatePasswordConfirmationTest() {
        Map<String, String> errors = new HashMap<>();
        User user = new User();
        user.setUsername("name");
        user.setPassword("1312");
        user.setConfirmPassword("131");
        userValidator.valid(user, errors);
        assertEquals("Different.userForm.password", errors.get("confirmPassword"));
    }
}
