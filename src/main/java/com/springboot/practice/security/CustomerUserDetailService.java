package com.springboot.practice.security;

import com.springboot.practice.entities.User;
import com.springboot.practice.exception.ResourceNotFoundException;
import com.springboot.practice.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Configuration
@RequiredArgsConstructor

public class CustomerUserDetailService implements UserDetailsService {
    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException(" User ", " email " +username, 0 ));
        return user;
    }
}
