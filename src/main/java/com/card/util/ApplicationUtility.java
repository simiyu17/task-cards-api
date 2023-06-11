package com.card.util;

import com.card.auth.domain.User;
import com.card.auth.domain.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApplicationUtility {

    private final UserRepository userRepository;

    public ApplicationUtility(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentLoggedInUser() {

        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .flatMap(u -> this.userRepository.findByUsername(((UserDetails) u).getUsername()))
                .orElse(null);
    }

}
