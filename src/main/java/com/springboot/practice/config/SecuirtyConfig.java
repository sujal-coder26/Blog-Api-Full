package com.springboot.practice.config;

import com.springboot.practice.security.CustomerUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecuirtyConfig extends WebSecurityConfigurerAdapter {

private final CustomerUserDetailService customerUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean ();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf ( ).disable ( ).cors ( ).disable ( ).authorizeRequests ( ).
                antMatchers ( "/generate-token" , "/user/" ).permitAll ( )
                .antMatchers ( HttpMethod.OPTIONS ).permitAll ( )
                .anyRequest ( ).authenticated ( ).and ( ).httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService ( this.customerUserDetailService ).passwordEncoder ( passwordEncoder ( ) );
    }


}
