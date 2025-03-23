package com.flaster.blog.controller;
import com.flaster.blog.model.Comment;
import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
import com.flaster.blog.service.CommentService;
import com.flaster.blog.repository.PostRepository;
import com.flaster.blog.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/{postId}")
    public Comment addComment(@PathVariable Long postId, @RequestBody String content, Authentication auth) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = userRepository.findByUsername(auth.getName());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return commentService.createComment(post, user, content);
    }
    @GetMapping("/{postId}")
    public List<Comment> getComments(@PathVariable Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        return commentService.getCommentsByPost(post);
    }
    @DeleteMapping("/delete/{commentId}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
