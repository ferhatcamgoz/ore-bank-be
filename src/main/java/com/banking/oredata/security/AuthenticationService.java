package com.banking.oredata.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public LoginResponse login(UserLoginRequest request) {

        Authentication authentication =
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
                )
            );

        CustomUserDetails userDetails =
            (CustomUserDetails) authentication.getPrincipal();

        String token = jwtTokenUtil.generateToken(userDetails);

        return new LoginResponse(
            userDetails.getUserId(),
            userDetails.getUsername(),
            userDetails.getUser().getEmail(),
            token
        );
    }
}

