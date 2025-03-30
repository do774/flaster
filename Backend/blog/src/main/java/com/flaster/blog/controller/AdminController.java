package com.flaster.blog.controller;

import com.flaster.blog.dto.UserDTO;
import com.flaster.blog.mapper.UserMapper;
import com.flaster.blog.model.User;
import com.flaster.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    
    @GetMapping("/users")
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers().stream().map(userMapper::toDto).collect(Collectors.toList());
    }
    
    @PutMapping("/users/{id}/role")
    public UserDTO updateUserRole(@PathVariable Long id, @RequestBody Map<String, String> payload){
        String role = payload.get("role");
        User user = userService.updateUserRole(id, role);
        return userMapper.toDto(user);
    }
}
