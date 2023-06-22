package com.springboot.practice.controller;

import com.springboot.practice.entities.User;
import com.springboot.practice.payloads.ApiResponse;
import com.springboot.practice.payloads.UserDTO;
import com.springboot.practice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users/")
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserDTO> createUserWithResponse(@Valid @RequestBody UserDTO userDto ){
        UserDTO createUserDTO = this.userService.createUser(userDto);
        return new ResponseEntity<> (createUserDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Integer userId){
        UserDTO updatedUser = this.userService.updateUser( userDTO, userId);
        return ResponseEntity.ok (updatedUser);

    }
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
        this.userService.deleteUser ( userId );
        return new ResponseEntity<>(new ApiResponse ("User Deleted Successfully", true), HttpStatus.OK );

    }
    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllusers(){
        List<UserDTO> allUsers = this.userService.getAllUsers ();
        return new ResponseEntity<> (allUsers, HttpStatus.OK );

    }
    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserDTO> getUserWithId(@PathVariable Integer userId){
       UserDTO userWithId = this.userService.getUserById ( userId );
       return ResponseEntity.ok ( userWithId );

    }
}
