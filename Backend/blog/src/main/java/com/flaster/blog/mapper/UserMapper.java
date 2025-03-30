package com.flaster.blog.mapper;

import com.flaster.blog.dto.UserDTO;
import com.flaster.blog.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(UserDTO dto);
}
