package com.springboot.practice.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private int id;

    @NotEmpty
    @Size(min = 4, message = "Username must be min of 4 characters.")
    private String name;

    @Email(message = "Email is not valid")
    private String email;

    @NotEmpty
    @Size(min=3, max=10, message = "Character must be more that 3 and less than 10")
    private String password;

    @NotEmpty
    private String about;
}
