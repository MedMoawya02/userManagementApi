package com.usermanagement.service.impl;

import com.usermanagement.dto.PasswordChangeRequest;
import com.usermanagement.dto.UserCreateRequest;
import com.usermanagement.dto.UserResponse;
import com.usermanagement.dto.UserUpdateRequest;
import com.usermanagement.entity.Role;
import com.usermanagement.entity.User;
import com.usermanagement.exception.EmailAlreadyUsedException;
import com.usermanagement.exception.ForbiddenException;
import com.usermanagement.exception.ResourceNotFoundException;
import com.usermanagement.mapper.UserMapper;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.security.CurrentUserHolder;
import com.usermanagement.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponse create(UserCreateRequest dto) {
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new EmailAlreadyUsedException(dto.getEmail());
        }
        User user=userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User saved=userRepository.save(user);
        return  userMapper.toResponse(saved);
    }

    @Override
    public List<UserResponse> findAllActive() {
        return userRepository.findAllByActiveTrue().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse findById(Long id) {
        User user=userRepository.findByIdAndActiveTrue(id)
                .orElseThrow(()->new ResourceNotFoundException("user",id));

        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse update(Long id, UserUpdateRequest dto) {
        User user=userRepository.findByIdAndActiveTrue(id)
                .orElseThrow(()->new ResourceNotFoundException("user",id));
        if (userRepository.existsByEmailAndIdNot(dto.getEmail(),id)) {
            throw new EmailAlreadyUsedException(dto.getEmail());
        }
        userMapper.updateEntity(dto,user);
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public void deactivate(Long id) {
        requireAdmin();
        User user=userRepository.findByIdAndActiveTrue(id)
                .orElseThrow(()->new ResourceNotFoundException("user",id));
        user.setActive(false);
    }

    @Override
    public UserResponse getCurrentUser() {
        return userMapper.toResponse(getAuthenticatedUser());
    }

    @Override
    @Transactional
    public UserResponse updateCurrentUser(UserUpdateRequest dto) {
        User current=getAuthenticatedUser();
        if (userRepository.existsByEmailAndIdNot(dto.getEmail(),current.getId())) {
            throw new EmailAlreadyUsedException(dto.getEmail());
        }
        userMapper.updateEntity(dto,current);
        return userMapper.toResponse(current);
    }

    @Override
    public void changePassword(PasswordChangeRequest dto) {
        User user=getAuthenticatedUser();
        if(!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())){
            throw new ForbiddenException("Ancien mot de passe incorrect");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
    }
    private User getAuthenticatedUser(){
        Long currentUserId= CurrentUserHolder.getCurrentUserId();
        return  userRepository.findByIdAndActiveTrue(currentUserId).orElseThrow(()->new ResourceNotFoundException("User",currentUserId));
    }
    private void requireAdmin(){
        User current=getAuthenticatedUser();
        if(current.getRole()!= Role.ADMIN){
            throw new ForbiddenException("Accès réservé à l'administrateur");
        }
    }
}
