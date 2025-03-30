package blog.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

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
import com.flaster.blog.dto.PostDTO;
import com.flaster.blog.mapper.PostMapper;
import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
import com.flaster.blog.service.PostService;
import com.flaster.blog.BlogApplication;

@SpringBootTest(classes = BlogApplication.class)
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private PostService postService;
    
    @MockBean
    private PostMapper postMapper;
    
    @Test
    @WithMockUser(username = "author", roles = {"AUTHOR"})
    void testGetAllPosts() throws Exception {
        Post post = new Post();
        PostDTO dto = new PostDTO();
        when(postService.getAllPosts()).thenReturn(Arrays.asList(post));
        when(postMapper.toDto(post)).thenReturn(dto);
        mockMvc.perform(get("/api/posts"))
            .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(username = "author", roles = {"AUTHOR"})
    void testCreatePost() throws Exception {
        PostDTO dto = new PostDTO();
        Post post = new Post();
        User user = new User();
        user.setUsername("author");
        when(postMapper.toEntity(dto)).thenReturn(post);
        when(postService.findUserByUsername("author")).thenReturn(user);
        when(postService.createPost(post)).thenAnswer(i -> {
            Post p = (Post) i.getArguments()[0];
            p.setId(1L);
            return p;
        });
        when(postMapper.toDto(any(Post.class))).thenReturn(dto);
        mockMvc.perform(post("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(username = "author", roles = {"AUTHOR"})
    void testUpdatePost() throws Exception {
        PostDTO dto = new PostDTO();
        Post post = new Post();
        User user = new User();
        user.setUsername("author");
        when(postMapper.toEntity(dto)).thenReturn(post);
        when(postService.findUserByUsername("author")).thenReturn(user);
        when(postService.updatePost(1L, post, user)).thenReturn(post);
        when(postMapper.toDto(post)).thenReturn(dto);
        mockMvc.perform(put("/api/posts/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(username = "author", roles = {"AUTHOR"})
    void testDeletePost() throws Exception {
        User user = new User();
        user.setUsername("author");
        when(postService.findUserByUsername("author")).thenReturn(user);
        mockMvc.perform(delete("/api/posts/1"))
            .andExpect(status().isOk());
    }
}
