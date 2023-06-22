package com.springboot.practice.service;

import com.springboot.practice.entities.User;
import com.springboot.practice.exception.ResourceNotFoundException;
import com.springboot.practice.payloads.UserDTO;
import com.springboot.practice.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public interface UserService {
  UserDTO createUser(UserDTO user);

  UserDTO updateUser(UserDTO user, Integer userId);

  UserDTO getUserById(Integer userId);

  List<UserDTO> getAllUsers();

  void deleteUser(Integer userId);
 }