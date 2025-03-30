package com.flaster.blog.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LikeDTO {
    private Long id;
    
    @NotNull
    private Long postId;
    
    @NotNull
    private Long userId;
}
