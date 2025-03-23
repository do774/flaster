package com.flaster.blog.service;

import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
import com.flaster.blog.repository.PostRepository;
import com.flaster.blog.repository.LikeRepository;
import com.flaster.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private LikeRepository likeRepository;
    
    @Autowired
    private CommentRepository commentRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post updatedPost, User user) {
        Post existing = postRepository.findById(id).orElseThrow();
        if (!existing.getAuthor().getUsername().equals(user.getUsername()) && !user.getRole().equals("ADMIN")) {
            throw new RuntimeException("Not authorized");
        }
        existing.setTitle(updatedPost.getTitle());
        existing.setContent(updatedPost.getContent());
        existing.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(existing);
    }

    public void deletePost(Long id, User user) {
        Post existing = postRepository.findById(id).orElseThrow();
        if (!existing.getAuthor().getUsername().equals(user.getUsername()) && !user.getRole().equals("ADMIN")) {
            throw new RuntimeException("Not authorized");
        }
        likeRepository.deleteAll(likeRepository.findByPost(existing));
        commentRepository.deleteAll(commentRepository.findByPost(existing));
        postRepository.delete(existing);
    }
    
    public User findUserByUsername(String username) {
        return userService.findByUsername(username);
    }
}
