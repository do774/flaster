package com.flaster.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    
    @NotBlank
    @Size(max = 255)
    private String username;
    
    @NotBlank
    @Size(min = 6, max = 255)
    private String password;
    
    @NotBlank
    @Email
    @Size(max = 255)
    private String email;
    
    private Integer age;
    
    @Size(max = 255)
    private String country;
    
    @Size(max = 255)
    private String role;
}
