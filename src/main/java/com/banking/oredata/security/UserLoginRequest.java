package com.banking.oredata.security;

import lombok.Data;

@Data
public class UserLoginRequest {

    private String username;
    private String password;

}
