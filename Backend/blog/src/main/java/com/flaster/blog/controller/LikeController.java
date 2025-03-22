package com.flaster.blog.controller;

import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
import com.flaster.blog.repository.PostRepository;
import com.flaster.blog.repository.UserRepository;
import com.flaster.blog.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{postId}")
    public ResponseEntity<?> likePost(@PathVariable Long postId, Authentication auth) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = userRepository.findByUsername(auth.getName());
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }
        var like = likeService.addLike(post, user);
        if (like != null) {
            long count = likeService.countLikes(post);
            return ResponseEntity.ok(Map.of("likeCount", count));
        }
        return ResponseEntity.status(409).body(Map.of("message", "Already liked"));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> unlikePost(@PathVariable Long postId, Authentication auth) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = userRepository.findByUsername(auth.getName());
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }
        boolean removed = likeService.removeLike(post, user);
        if (removed) {
            long count = likeService.countLikes(post);
            return ResponseEntity.ok(Map.of("likeCount", count));
        }
        return ResponseEntity.status(409).body(Map.of("message", "Not liked yet"));
    }

    @GetMapping("/{postId}")
    public Map<String, Object> getLikeInfo(@PathVariable Long postId, Authentication auth) {
        Post post = postRepository.findById(postId).orElseThrow();
        long count = likeService.countLikes(post);
        boolean userLiked = false;
        if (auth != null && auth.isAuthenticated()) {
            User user = userRepository.findByUsername(auth.getName());
            if (user != null && likeService.isLikedByUser(post, user)) {
                userLiked = true;
            }
        }
        return Map.of("likeCount", count, "userLiked", userLiked);
    }
}
