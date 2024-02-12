package com.eliasdolinsek.notecraft.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class MessageResponse {
    @Getter
    private final String message;
}
