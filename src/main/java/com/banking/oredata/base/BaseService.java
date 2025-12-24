package com.banking.oredata.base;

import com.banking.oredata.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public abstract class BaseService {

    protected CustomUserDetails currentUser() {
        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof CustomUserDetails)) {
            throw new IllegalStateException("Unexpected principal type: " + principal);
        }

        return (CustomUserDetails) principal;
    }

    protected UUID currentUserId() {
        return currentUser().getUserId();
    }

    protected String currentUsername() {
        return currentUser().getUsername();
    }
}
