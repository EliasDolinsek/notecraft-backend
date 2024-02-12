package com.eliasdolinsek.notecraft.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class JwtResponse {

    @Getter
    private final String token;

    @Getter
    private final String type;

    @Getter
    private final Long id;

    @Getter
    private final String email;

    @Getter
    private final List<String> roles;

}
