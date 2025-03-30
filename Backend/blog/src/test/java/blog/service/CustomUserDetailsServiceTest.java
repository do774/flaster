package blog.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.flaster.blog.model.User;
import com.flaster.blog.repository.UserRepository;
import com.flaster.blog.service.CustomUserDetailsService;

class CustomUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testLoadUserByUsernameFound() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("pass");
        user.setRole("ADMIN");
        when(userRepository.findByUsername("test")).thenReturn(user);
        
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("test");
        assertEquals("test", userDetails.getUsername());
        assertEquals("pass", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("ADMIN", userDetails.getAuthorities().iterator().next().getAuthority());
    }
    
    @Test
    void testLoadUserByUsernameNotFound() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("nonexistent");
        });
    }
}
