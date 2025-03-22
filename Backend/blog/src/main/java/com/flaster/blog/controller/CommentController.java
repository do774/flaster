package com.flaster.blog.controller;

import com.flaster.blog.model.Comment;
import com.flaster.blog.model.User;
import com.flaster.blog.model.Post;
import com.flaster.blog.repository.PostRepository;
import com.flaster.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/{postId}")
    public List<Comment> getComments(@PathVariable Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        return commentService.getCommentsByPost(post);
    }

    @PostMapping("/{postId}")
    public Comment addComment(@PathVariable Long postId, @RequestBody String content, Authentication auth) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = new User();
        user.setUsername(auth.getName());
        return commentService.createComment(post, user, content);
    }
}
