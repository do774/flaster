package com.flaster.blog.mapper;

import com.flaster.blog.dto.PostDTO;
import com.flaster.blog.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(source = "author.id", target = "authorId")
    PostDTO toDto(Post post);
    @Mapping(source = "authorId", target = "author.id")
    Post toEntity(PostDTO dto);
}
