package com.flaster.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    
    @NotNull
    private Long postId;
    
    @NotNull
    private Long userId;
    
    @NotBlank
    private String content;
    
    private LocalDateTime createdAt;
}
