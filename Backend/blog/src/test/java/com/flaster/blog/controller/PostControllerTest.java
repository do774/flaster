package com.flaster.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testGetAllPosts() throws Exception {
        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "author", authorities = "AUTHOR")
    void testCreatePost() throws Exception {
        User user = new User();
        user.setUsername("author");
        user.setPassword("pass");
        user.setEmail("author@example.com");
        user.setRole("AUTHOR");
        userRepository.save(user);
        Post post = new Post();
        post.setTitle("New Post");
        post.setContent("New Content");
        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "author2", authorities = "AUTHOR")
    void testUpdatePost() throws Exception {
        User user = new User();
        user.setUsername("author2");
        user.setPassword("pass");
        user.setEmail("author2@example.com");
        user.setRole("AUTHOR");
        userRepository.save(user);
        Post post = new Post();
        post.setTitle("Old Title");
        post.setContent("Old Content");
        post.setAuthor(user);
        postRepository.save(post);
        post.setTitle("Updated Title");
        post.setContent("Updated Content");
        mockMvc.perform(put("/api/posts/" + post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "author3", authorities = "AUTHOR")
    void testDeletePost() throws Exception {
        User user = new User();
        user.setUsername("author3");
        user.setPassword("pass");
        user.setEmail("author3@example.com");
        user.setRole("AUTHOR");
        userRepository.save(user);
        Post post = new Post();
        post.setTitle("Title3");
        post.setContent("Content3");
        post.setAuthor(user);
        postRepository.save(post);
        mockMvc.perform(delete("/api/posts/" + post.getId()))
                .andExpect(status().isOk());
    }
}
