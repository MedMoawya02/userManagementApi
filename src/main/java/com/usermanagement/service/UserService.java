package com.usermanagement.service;

import com.usermanagement.dto.PasswordChangeRequest;
import com.usermanagement.dto.UserCreateRequest;
import com.usermanagement.dto.UserResponse;
import com.usermanagement.dto.UserUpdateRequest;

import java.util.List;

public interface UserService {
    //Admin
    UserResponse create(UserCreateRequest dto);
    List<UserResponse> findAllActive();
    UserResponse findById(Long id);
    UserResponse update(Long id,UserUpdateRequest dto);
    void deactivate(Long id);
    //User
    UserResponse getCurrentUser();
    UserResponse updateCurrentUser(UserUpdateRequest dto);
    void changePassword(PasswordChangeRequest dto);
}
