package com.card.security;

import com.card.auth.exception.UserAuthenticationException;
import com.card.auth.exception.UserNotFoundException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final CurrentUserDetails currentUserDetails;

    public JWTAuthorizationFilter(CurrentUserDetails currentUserDetails) {
        this.currentUserDetails = currentUserDetails;
    }


    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain chain) throws IOException, ServletException {
        final var header = request.getHeader(SecurityConstants.HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        final var token = header.replaceFirst(SecurityConstants.TOKEN_PREFIX, "").trim();
        UserDetails userDetails = getUserDetailsFromAuthToken(token);

        if (Objects.isNull(userDetails) || !JwtTokenUtil.isTokenValid(token, userDetails)) {
            chain.doFilter(request, response);
            return;
        }

        final var authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UserDetails getUserDetailsFromAuthToken(String token){
        try {
            return currentUserDetails
                    .loadUserByUsername(JwtTokenUtil.extractUsername(token));
        }catch (JwtException | UserNotFoundException ex){
            throw new UserAuthenticationException("Bad/Expired Credentials !!");
        }
    }
}