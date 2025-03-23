package com.flaster.blog.controller;

import com.flaster.blog.model.User;
import com.flaster.blog.repository.UserRepository;
import com.flaster.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @PostMapping("/signup")
    public User signUp(@RequestBody User user){
        return userService.signUp(user);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','AUTHOR','READER')")
    public User updateUser(@PathVariable Long id, @RequestBody User updated, Authentication auth){
        User existing = userRepository.findById(id).orElseThrow();
        if(!auth.getName().equals(existing.getUsername()) && !auth.getAuthorities().toString().contains("ADMIN")){
            throw new RuntimeException("Not authorized to update this user");
        }
        existing.setEmail(updated.getEmail());
        existing.setCountry(updated.getCountry());
        existing.setImage(updated.getImage());
        return userRepository.save(existing);
    }
}
