package blog.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import java.util.Arrays;

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
import com.flaster.blog.BlogApplication;

@SpringBootTest(classes = BlogApplication.class)
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private UserService userService;
    
    @MockBean
    private UserMapper userMapper;
    
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetAllUsers() throws Exception {
        User user = new User();
        user.setUsername("user");
        UserDTO dto = new UserDTO();
        dto.setUsername("user");
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user));
        when(userMapper.toDto(user)).thenReturn(dto);
        mockMvc.perform(get("/api/admin/users"))
            .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateUserRole() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        UserDTO dto = new UserDTO();
        dto.setUsername("user");
        when(userService.updateUserRole(1L, "ADMIN")).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(dto);
        mockMvc.perform(put("/api/admin/users/1/role")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"role\":\"ADMIN\"}"))
            .andExpect(status().isOk());
    }
}
