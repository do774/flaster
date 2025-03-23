package com.flaster.blog.service;

import com.flaster.blog.model.Comment;
import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
import com.flaster.blog.repository.CommentRepository;
import com.flaster.blog.repository.PostRepository;
import com.flaster.blog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

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
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testCreateCommentAndGetCommentsByPost() {
        User user = new User();
        user.setUsername("commentuser");
        user.setPassword("commentpass");
        user.setEmail("comment@example.com");
        userService.signUp(user);
        Post post = new Post();
        post.setTitle("Comment Title");
        post.setContent("Comment Content");
        post.setAuthor(user);
        Post savedPost = postService.createPost(post);
        Comment comment = commentService.createComment(savedPost, user, "Hello World");
        assertNotNull(comment.getId());
        List<Comment> comments = commentService.getCommentsByPost(savedPost);
        assertEquals(1, comments.size());
        assertEquals("Hello World", comments.get(0).getContent());
    }

    @Test
    void testDeleteComment() {
        User user = new User();
        user.setUsername("commentuser2");
        user.setPassword("commentpass2");
        user.setEmail("comment2@example.com");
        userService.signUp(user);
        Post post = new Post();
        post.setTitle("Delete Comment Title");
        post.setContent("Delete Comment Content");
        post.setAuthor(user);
        Post savedPost = postService.createPost(post);
        Comment comment = commentService.createComment(savedPost, user, "To be deleted");
        commentService.deleteComment(comment.getId());
        assertFalse(commentRepository.findById(comment.getId()).isPresent());
    }
}
