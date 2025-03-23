package com.flaster.blog.service;

import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
import com.flaster.blog.repository.PostRepository;
import com.flaster.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testCreatePost() {
        User user = new User();
        user.setUsername("postuser");
        user.setPassword("postpass");
        user.setEmail("post@example.com");
        userService.signUp(user);
        Post post = new Post();
        post.setTitle("Title");
        post.setContent("Content");
        post.setAuthor(user);
        Post saved = postService.createPost(post);
        assertNotNull(saved.getId());
    }

    @Test
    void testUpdatePost() {
        User user = new User();
        user.setUsername("postuser2");
        user.setPassword("pass2");
        user.setEmail("post2@example.com");
        userService.signUp(user);
        Post post = new Post();
        post.setTitle("Old Title");
        post.setContent("Old Content");
        post.setAuthor(user);
        Post saved = postService.createPost(post);
        Post updatedData = new Post();
        updatedData.setTitle("New Title");
        updatedData.setContent("New Content");
        Post updated = postService.updatePost(saved.getId(), updatedData, user);
        assertEquals("New Title", updated.getTitle());
    }

    @Test
    void testDeletePost() {
        User user = new User();
        user.setUsername("postuser3");
        user.setPassword("pass3");
        user.setEmail("post3@example.com");
        userService.signUp(user);
        Post post = new Post();
        post.setTitle("To Delete");
        post.setContent("To Delete");
        post.setAuthor(user);
        Post saved = postService.createPost(post);
        postService.deletePost(saved.getId(), user);
        assertFalse(postRepository.findById(saved.getId()).isPresent());
    }
}
