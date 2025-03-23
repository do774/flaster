package com.flaster.blog.service;

import com.flaster.blog.model.User;
import com.flaster.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomUserDetailsServiceTest {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        User user = new User();
        user.setUsername("testdetail");
        user.setPassword("pass");
        user.setEmail("detail@example.com");
        user.setRole("READER");
        userRepository.save(user);
        assertNotNull(customUserDetailsService.loadUserByUsername("testdetail"));
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("missinguser"));
    }
}
