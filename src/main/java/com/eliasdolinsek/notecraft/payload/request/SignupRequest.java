package com.eliasdolinsek.notecraft.payload.request;

import java.util.Set;

import com.eliasdolinsek.notecraft.models.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SignupRequest {

    @NotBlank
    @Getter
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    @Getter
    private String password;
   
    @Getter
    private Set<Role> roles;
}
