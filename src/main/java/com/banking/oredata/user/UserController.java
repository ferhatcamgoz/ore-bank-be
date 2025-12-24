package com.banking.oredata.user;

import com.banking.oredata.base.BaseApiResponse;
import com.banking.oredata.security.AuthenticationService;
import com.banking.oredata.security.LoginResponse;
import com.banking.oredata.security.UserLoginRequest;
import com.banking.oredata.user.dto.UserRegisterRequest;
import com.banking.oredata.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management")
public class UserController {
    private final RestUserService userService;
    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody UserRegisterRequest request) {
        UserResponse user = userService.register(request);
        return ResponseEntity.ok(new BaseApiResponse(true, "User registered successfully", user));
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody UserLoginRequest request) throws BadRequestException {
        LoginResponse response = authenticationService.login(request);
        return ResponseEntity.ok(new BaseApiResponse(true, "Login successfully", response));
    }
}
