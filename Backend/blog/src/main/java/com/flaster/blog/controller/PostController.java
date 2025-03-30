package com.flaster.blog.controller;

import com.flaster.blog.dto.PostDTO;
import com.flaster.blog.mapper.PostMapper;
import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
import com.flaster.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private PostMapper postMapper;
    
    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts().stream().map(postMapper::toDto).collect(Collectors.toList());
    }
    
    @PostMapping
    @PreAuthorize("hasAnyAuthority('AUTHOR','ADMIN')")
    public PostDTO createPost(@RequestBody PostDTO postDTO, Authentication auth) {
        User user = postService.findUserByUsername(auth.getName());
        Post post = postMapper.toEntity(postDTO);
        post.setAuthor(user);
        Post created = postService.createPost(post);
        return postMapper.toDto(created);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('AUTHOR','ADMIN')")
    public PostDTO updatePost(@PathVariable Long id, @RequestBody PostDTO updatedDTO, Authentication auth) {
        User user = postService.findUserByUsername(auth.getName());
        Post updated = postMapper.toEntity(updatedDTO);
        Post result = postService.updatePost(id, updated, user);
        return postMapper.toDto(result);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('AUTHOR','ADMIN')")
    public void deletePost(@PathVariable Long id, Authentication auth) {
        User user = postService.findUserByUsername(auth.getName());
        postService.deletePost(id, user);
    }
}
