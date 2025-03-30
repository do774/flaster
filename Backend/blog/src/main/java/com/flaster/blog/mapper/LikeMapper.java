package com.flaster.blog.mapper;

import com.flaster.blog.dto.LikeDTO;
import com.flaster.blog.model.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LikeMapper {
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "user.id", target = "userId")
    LikeDTO toDto(Like like);
    @Mapping(source = "postId", target = "post.id")
    @Mapping(source = "userId", target = "user.id")
    Like toEntity(LikeDTO dto);
}
