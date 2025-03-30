package com.flaster.blog.controller;

import com.flaster.blog.dto.UserDTO;
import com.flaster.blog.mapper.UserMapper;
import com.flaster.blog.model.User;
import com.flaster.blog.repository.UserRepository;
import com.flaster.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    
    @GetMapping("/{username}")
    public UserDTO getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return userMapper.toDto(user);
    }
    
    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers().stream().map(userMapper::toDto).collect(Collectors.toList());
    }
    
    @PostMapping("/signup")
    public UserDTO signUp(@RequestBody UserDTO userDTO){
        User created = userService.signUp(userMapper.toEntity(userDTO));
        return userMapper.toDto(created);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','AUTHOR','READER')")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO updatedDTO, Authentication auth){
        User existing = userRepository.findById(id).orElseThrow();
        if(!auth.getName().equals(existing.getUsername()) && !auth.getAuthorities().toString().contains("ADMIN")){
            throw new RuntimeException("Not authorized to update this user");
        }
        existing.setEmail(updatedDTO.getEmail());
        existing.setCountry(updatedDTO.getCountry());
        existing.setAge(updatedDTO.getAge());
        User saved = userRepository.save(existing);
        return userMapper.toDto(saved);
    }
}
