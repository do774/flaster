package com.flaster.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostDTO {
    private Long id;
    
    @Size(max = 255)
    private String title;
    
    @NotBlank
    private String content;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @NotNull
    private Long authorId;
}
