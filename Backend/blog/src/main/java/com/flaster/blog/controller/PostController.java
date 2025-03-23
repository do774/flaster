package com.flaster.blog.controller;

import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
import com.flaster.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('AUTHOR','ADMIN')")
    public Post createPost(@RequestBody Post post, Authentication auth) {
        User user = postService.findUserByUsername(auth.getName());
        post.setAuthor(user);
        return postService.createPost(post);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('AUTHOR','ADMIN')")
    public Post updatePost(@PathVariable Long id, @RequestBody Post updatedPost, Authentication auth) {
        User user = postService.findUserByUsername(auth.getName());
        return postService.updatePost(id, updatedPost, user);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('AUTHOR','ADMIN')")
    public void deletePost(@PathVariable Long id, Authentication auth) {
        User user = postService.findUserByUsername(auth.getName());
        postService.deletePost(id, user);
    }
}
