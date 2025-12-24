package com.banking.oredata.user;

import com.banking.oredata.security.LoginResponse;
import com.banking.oredata.security.UserLoginRequest;
import com.banking.oredata.user.dto.UserRegisterRequest;
import com.banking.oredata.user.dto.UserResponse;
import jakarta.validation.Valid;

public interface RestUserService {

    UserResponse register(UserRegisterRequest userRegisterRequest);

}
