package blog.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

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
import com.flaster.blog.dto.LikeDTO;
import com.flaster.blog.mapper.LikeMapper;
import com.flaster.blog.model.Like;
import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
import com.flaster.blog.service.LikeService;
import com.flaster.blog.repository.PostRepository;
import com.flaster.blog.repository.UserRepository;
import com.flaster.blog.BlogApplication;

@SpringBootTest(classes = BlogApplication.class)
@AutoConfigureMockMvc
class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private LikeService likeService;
    
    @MockBean
    private LikeMapper likeMapper;
    
    @MockBean
    private PostRepository postRepository;
    
    @MockBean
    private UserRepository userRepository;
    
    @Test
    @WithMockUser(username = "user", roles = {"READER"})
    void testLikePostSuccess() throws Exception {
        Post post = new Post();
        post.setId(1L);
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        Like like = new Like();
        like.setId(1L);
        like.setPost(post);
        like.setUser(user);
        LikeDTO dto = new LikeDTO();
        
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(userRepository.findByUsername("user")).thenReturn(user);
        when(likeService.addLike(post, user)).thenReturn(like);
        when(likeService.countLikes(post)).thenReturn(1L);
        when(likeMapper.toDto(like)).thenReturn(dto);
        
        mockMvc.perform(post("/api/likes/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(username = "user", roles = {"READER"})
    void testUnlikePostSuccess() throws Exception {
        Post post = new Post();
        post.setId(1L);
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(userRepository.findByUsername("user")).thenReturn(user);
        when(likeService.removeLike(post, user)).thenReturn(true);
        when(likeService.countLikes(post)).thenReturn(0L);
        
        mockMvc.perform(delete("/api/likes/1"))
            .andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(username = "user", roles = {"READER"})
    void testGetLikeInfo() throws Exception {
        Post post = new Post();
        post.setId(1L);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(likeService.countLikes(post)).thenReturn(2L);
        when(likeService.isLikedByUser(post, null)).thenReturn(false);
        
        mockMvc.perform(get("/api/likes/1"))
            .andExpect(status().isOk());
    }
}
