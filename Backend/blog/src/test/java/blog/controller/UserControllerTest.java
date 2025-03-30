package blog.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flaster.blog.dto.UserDTO;
import com.flaster.blog.mapper.UserMapper;
import com.flaster.blog.model.User;
import com.flaster.blog.service.UserService;
import com.flaster.blog.repository.UserRepository;
import com.flaster.blog.BlogApplication;

@SpringBootTest(classes = BlogApplication.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private UserService userService;
    
    @MockBean
    private UserMapper userMapper;
    
    @MockBean
    private UserRepository userRepository;
    
    @Test
    @WithMockUser(username = "test", roles = {"READER"})
    void testGetUserByUsername() throws Exception {
        User user = new User();
        user.setUsername("test");
        UserDTO dto = new UserDTO();
        dto.setUsername("test");
        when(userService.findByUsername("test")).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(dto);
        mockMvc.perform(get("/api/users/test"))
            .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(username = "test", roles = {"READER"})
    void testGetAllUsers() throws Exception {
        User user = new User();
        user.setUsername("test");
        UserDTO dto = new UserDTO();
        dto.setUsername("test");
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user));
        when(userMapper.toDto(user)).thenReturn(dto);
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(username = "new", roles = {"READER"})
    void testSignUp() throws Exception {
        User user = new User();
        user.setUsername("new");
        UserDTO dto = new UserDTO();
        dto.setUsername("new");
        when(userMapper.toEntity(dto)).thenReturn(user);
        when(userService.signUp(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(dto);
        mockMvc.perform(post("/api/users/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(username = "test", roles = {"READER"})
    void testUpdateUser() throws Exception {
        User existing = new User();
        existing.setUsername("test");
        existing.setId(1L);
        UserDTO dto = new UserDTO();
        dto.setUsername("test");
        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userService.updateUserRole(1L, "DUMMY")).thenReturn(existing);
        when(userMapper.toDto(existing)).thenReturn(dto);
        mockMvc.perform(put("/api/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk());
    }
}
