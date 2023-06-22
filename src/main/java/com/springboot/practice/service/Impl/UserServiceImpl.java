package com.springboot.practice.service.Impl;

import com.springboot.practice.entities.User;
import com.springboot.practice.exception.ResourceNotFoundException;
import com.springboot.practice.payloads.UserDTO;
import com.springboot.practice.repository.UserRepo;
import com.springboot.practice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

  private final UserRepo userRepo;
  private final ModelMapper modelMapper;

  @Override
  public UserDTO createUser(UserDTO userDTO) {
    User user = this.dtoToUser(userDTO);
    User savedUser = this.userRepo.save(user);
    return this.userToDTO(savedUser);
  }

  @Override
  public UserDTO updateUser(UserDTO userDTO, Integer userId) {
    User user =
        this.userRepo
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    user.setName(userDTO.getName());
    user.setEmail(userDTO.getEmail());
    user.setPassword(userDTO.getPassword());
    user.setAbout(userDTO.getAbout());

    User updatedUser = this.userRepo.save(user);
    return this.userToDTO(updatedUser);
  }

  @Override
  public UserDTO getUserById(Integer userId) {
    User user =
        this.userRepo
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    return this.userToDTO(user);
  }

  @Override
  public List<UserDTO> getAllUsers() {
    List<User> users = this.userRepo.findAll();
    return users.stream().map( this::userToDTO ).collect(Collectors.toList());
  }

  @Override
  public void deleteUser(Integer userId) {
    User user =
        this.userRepo
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    this.userRepo.delete(user);
  }

  private User dtoToUser(UserDTO userDTO) {
    return this.modelMapper.map ( userDTO, User.class );
  }

  private UserDTO userToDTO(User user) {

    return this.modelMapper.map ( user, UserDTO.class );
  }
    }