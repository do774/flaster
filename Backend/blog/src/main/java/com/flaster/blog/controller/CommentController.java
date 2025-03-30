package com.flaster.blog.controller;

import com.flaster.blog.dto.CommentDTO;
import com.flaster.blog.mapper.CommentMapper;
import com.flaster.blog.model.Comment;
import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
import com.flaster.blog.service.CommentService;
import com.flaster.blog.repository.PostRepository;
import com.flaster.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentMapper commentMapper;
    
    @PostMapping("/{postId}")
    public CommentDTO addComment(@PathVariable Long postId, @RequestBody String content, Authentication auth) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = userRepository.findByUsername(auth.getName());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        Comment comment = commentService.createComment(post, user, content);
        return commentMapper.toDto(comment);
    }
    
    @GetMapping("/{postId}")
    public List<CommentDTO> getComments(@PathVariable Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        List<Comment> comments = commentService.getCommentsByPost(post);
        return comments.stream().map(commentMapper::toDto).collect(Collectors.toList());
    }
    
    @DeleteMapping("/delete/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
