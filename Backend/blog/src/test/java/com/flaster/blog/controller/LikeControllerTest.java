package com.flaster.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
import com.flaster.blog.repository.LikeRepository;
import com.flaster.blog.repository.PostRepository;
import com.flaster.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LikeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        likeRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "likeUser")
    void testLikePost() throws Exception {
        User user = new User();
        user.setUsername("likeUser");
        user.setPassword("pass");
        user.setEmail("like@example.com");
        userRepository.save(user);
        Post post = new Post();
        post.setTitle("Likeable");
        post.setContent("Content");
        postRepository.save(post);
        mockMvc.perform(post("/api/likes/" + post.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "likeUser2")
    void testUnlikePost() throws Exception {
        User user = new User();
        user.setUsername("likeUser2");
        user.setPassword("pass");
        user.setEmail("like2@example.com");
        userRepository.save(user);
        Post post = new Post();
        post.setTitle("Likeable2");
        post.setContent("Content2");
        postRepository.save(post);
        mockMvc.perform(post("/api/likes/" + post.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/api/likes/" + post.getId()))
                .andExpect(status().isOk());
    }
}
