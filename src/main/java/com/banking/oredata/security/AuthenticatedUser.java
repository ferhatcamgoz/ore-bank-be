package com.banking.oredata.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class AuthenticatedUser {

    private final UUID id;
    private final String username;
    private final String passwordHash;
    private final String email;

}
