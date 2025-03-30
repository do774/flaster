package com.flaster.blog.mapper;

import com.flaster.blog.dto.CommentDTO;
import com.flaster.blog.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "user.id", target = "userId")
    CommentDTO toDto(Comment comment);
    @Mapping(source = "postId", target = "post.id")
    @Mapping(source = "userId", target = "user.id")
    Comment toEntity(CommentDTO dto);
}
