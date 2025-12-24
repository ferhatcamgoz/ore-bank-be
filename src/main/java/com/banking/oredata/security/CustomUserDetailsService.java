package com.banking.oredata.security;

import com.banking.oredata.user.UserModel;
import com.banking.oredata.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    //sometimes you need to think outside the box.
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        AuthenticatedUser authenticatedUser = AuthenticatedUser.builder()
            .id(user.getId())
            .username(username)
            .email(user.getEmail())
            .passwordHash(user.getPassword())
            .build();

        return new CustomUserDetails(authenticatedUser);
    }
}
