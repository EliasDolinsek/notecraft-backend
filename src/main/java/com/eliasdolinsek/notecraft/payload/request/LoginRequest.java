package com.eliasdolinsek.notecraft.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoginRequest {
    
    @NotBlank
    @Getter
    private String email;

    @NotBlank
    @Getter
    private String password;
}
