package blog.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

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
import com.flaster.blog.dto.CommentDTO;
import com.flaster.blog.mapper.CommentMapper;
import com.flaster.blog.model.Comment;
import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
import com.flaster.blog.service.CommentService;
import com.flaster.blog.repository.PostRepository;
import com.flaster.blog.repository.UserRepository;
import com.flaster.blog.BlogApplication;

@SpringBootTest(classes = BlogApplication.class)
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private CommentService commentService;
    
    @MockBean
    private CommentMapper commentMapper;
    
    @MockBean
    private PostRepository postRepository;
    
    @MockBean
    private UserRepository userRepository;
    
    @Test
    @WithMockUser(username = "user", roles = {"READER"})
    void testAddComment() throws Exception {
        Post post = new Post();
        post.setId(1L);
        User user = new User();
        user.setUsername("user");
        Comment comment = new Comment();
        CommentDTO dto = new CommentDTO();
        
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(userRepository.findByUsername("user")).thenReturn(user);
        when(commentService.createComment(post, user, "Test comment")).thenReturn(comment);
        when(commentMapper.toDto(comment)).thenReturn(dto);
        
        mockMvc.perform(post("/api/comments/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("Test comment"))
            .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(username = "user", roles = {"READER"})
    void testGetComments() throws Exception {
        Post post = new Post();
        post.setId(1L);
        Comment comment = new Comment();
        CommentDTO dto = new CommentDTO();
        
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(commentService.getCommentsByPost(post)).thenReturn(Arrays.asList(comment));
        when(commentMapper.toDto(comment)).thenReturn(dto);
        
        mockMvc.perform(get("/api/comments/1"))
            .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteComment() throws Exception {
        // Ako metoda deleteComment nema dodatno stubanje, samo provjeravamo status
        mockMvc.perform(delete("/api/comments/delete/1"))
            .andExpect(status().isOk());
    }
}
