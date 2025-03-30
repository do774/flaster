package blog.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.flaster.blog.model.User;
import com.flaster.blog.repository.UserRepository;
import com.flaster.blog.service.UserService;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private UserService userService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testGetAllUsers() {
        User user1 = new User();
        user1.setUsername("user1");
        User user2 = new User();
        user2.setUsername("user2");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        
        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }
    
    @Test
    void testSignUp() {
        User user = new User();
        user.setUsername("newUser");
        user.setPassword("password");
        when(passwordEncoder.encode("password")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        
        User saved = userService.signUp(user);
        assertEquals("encoded", saved.getPassword());
        assertEquals("READER", saved.getRole());
    }
    
    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        
        User found = userService.findByUsername("testUser");
        assertNotNull(found);
        assertEquals("testUser", found.getUsername());
    }
    
    @Test
    void testUpdateUserRole() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setRole("READER");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        
        User updated = userService.updateUserRole(1L, "ADMIN");
        assertEquals("ADMIN", updated.getRole());
        verify(userRepository, times(1)).save(any(User.class));
    }
}
