package com.usermanagement.mapper;

import com.usermanagement.dto.UserCreateRequest;
import com.usermanagement.dto.UserResponse;
import com.usermanagement.dto.UserUpdateRequest;
import com.usermanagement.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserCreateRequest dto){
       if(dto==null){
           return null;
       }
       User user=new User();
       user.setName(dto.getName());
       user.setEmail(dto.getEmail());
       user.setPassword(dto.getPassword());
       user.setRole(dto.getRole());
       user.setActive(true);
       return user;
    }
    public void updateEntity(UserUpdateRequest dto,User user){
        if (dto==null ||user==null){
            return;
        }
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
    }
    public UserResponse toResponse(User user){
        if(user==null){
            return null;
        }
        UserResponse dto=new UserResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setActive(user.isActive());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}
