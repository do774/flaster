package com.flaster.blog.service;

import com.flaster.blog.model.User;
import com.flaster.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testSignUp() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpass");
        user.setEmail("test@example.com");
        User saved = userService.signUp(user);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("anotheruser");
        user.setPassword("pass");
        user.setEmail("another@example.com");
        userService.signUp(user);
        User found = userService.findByUsername("anotheruser");
        assertNotNull(found);
    }

    @Test
    void testUpdateUserRole() {
        User user = new User();
        user.setUsername("roleuser");
        user.setPassword("rolepass");
        user.setEmail("role@example.com");
        User saved = userService.signUp(user);
        User updated = userService.updateUserRole(saved.getId(), "ADMIN");
        assertEquals("ADMIN", updated.getRole());
    }
}
