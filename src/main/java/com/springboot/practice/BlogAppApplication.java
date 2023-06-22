package com.springboot.practice;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = {"com.springboot.practice.repository"} )
public class BlogAppApplication implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    public static void main ( String[] args ) {
        SpringApplication.run ( BlogAppApplication.class , args );
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper ();
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.passwordEncoder.encode("xyz"));
    }
}
