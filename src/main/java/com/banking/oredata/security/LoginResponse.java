package com.banking.oredata.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class LoginResponse {
    private UUID id;
    private String username;
    private String email;
    private String token;
}
