package com.flaster.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
import com.flaster.blog.repository.CommentRepository;
import com.flaster.blog.repository.PostRepository;
import com.flaster.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "user1", authorities = "READER")
    void testAddComment() throws Exception {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("pass");
        user.setEmail("u1@example.com");
        userRepository.save(user);
        Post post = new Post();
        post.setTitle("Post");
        post.setContent("Content");
        postRepository.save(post);
        mockMvc.perform(post("/api/comments/" + post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("Hello comment"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testDeleteComment() throws Exception {
        User user = new User();
        user.setUsername("adminUser");
        user.setPassword("pass");
        user.setEmail("admin@example.com");
        userRepository.save(user);
        Post post = new Post();
        post.setTitle("Title");
        post.setContent("Content");
        postRepository.save(post);
        mockMvc.perform(post("/api/comments/" + post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("Admin comment"))
                .andExpect(status().isOk());
        Long commentId = commentRepository.findAll().get(0).getId();
        mockMvc.perform(delete("/api/comments/delete/" + commentId))
                .andExpect(status().isOk());
    }
}
