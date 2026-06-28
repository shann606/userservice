package com.exp.userservice.mapper;

import org.mapstruct.Mapper;

import com.exp.userservice.dto.UserRequest;
import com.exp.userservice.entity.User;

@Mapper(componentModel = "spring")
public interface CustomMapper {

	User toUserEntity(UserRequest request);

}
