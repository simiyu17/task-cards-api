
package com.card.config;


import com.card.security.JWTAuthorizationFilter;
import com.card.security.exception.RestAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 *
 * @author simiyu
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityFilterChainConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;
    private final AuthenticationEntryPoint authEntryPoint;

    private static final String[] AUTH_WHITELIST = {
            "/auth/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/cards-openapi.html",
            "/swagger-resources/**"
    };

    public WebSecurityFilterChainConfig(AuthenticationProvider authenticationProvider,
                                        JWTAuthorizationFilter jwtAuthorizationFilter,
                                        @Qualifier("customAuthenticationEntryPoint") AuthenticationEntryPoint authEntryPoint) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(AUTH_WHITELIST).permitAll();
                    auth.requestMatchers("/cards/**").hasAnyRole("ADMIN", "MEMBER");
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->{
                    logout.logoutUrl("/auth/logout");
                    logout.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
                })
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authEntryPoint)
                        .accessDeniedHandler(new RestAccessDeniedHandler()));

        return http.build();
    }


}
