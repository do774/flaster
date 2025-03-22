package com.flaster.blog.controller;

import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
import com.flaster.blog.service.LikeService;
import com.flaster.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/{postId}")
    public String likePost(@PathVariable Long postId, Authentication auth) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = new User();
        user.setUsername(auth.getName());
        if (likeService.addLike(post, user) != null) {
            return "Post liked";
        }
        return "Already liked";
    }

    @GetMapping("/count/{postId}")
    public long countLikes(@PathVariable Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        return likeService.countLikes(post);
    }
}
