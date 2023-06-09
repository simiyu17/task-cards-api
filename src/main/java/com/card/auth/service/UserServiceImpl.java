
package com.card.auth.service;


import com.card.auth.domain.User;
import com.card.auth.domain.UserRepository;
import com.card.auth.dto.JwtRequest;
import com.card.auth.dto.LoginResponse;
import com.card.auth.dto.UserDto;
import com.card.auth.exception.UserNotFoundException;
import com.card.security.CurrentUserDetails;
import com.card.security.JwtTokenUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

/**
 *
 * @author simiyu
 */
@Service
@Validated
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUserDetails currentUserDetails;

    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, CurrentUserDetails currentUserDetails) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.currentUserDetails = currentUserDetails;
    }


    @Transactional
    @Override
    public void createUser(@Valid UserDto userDto) {
        userRepository.save(User.createUser(userDto, passwordEncoder));
    }

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException("User Not Found By User Name"));
    }

    @Override
    public LoginResponse authenticateUser(@Valid final JwtRequest request) {
          final var userDetails = currentUserDetails.loadUserByUsername(request.username());
          final var user = findUserByUserName(userDetails.getUsername());
          this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails, request.password(), userDetails.getAuthorities()));
          return new LoginResponse("Login Was Successful", JwtTokenUtil.createToken(user));
    }


    @Transactional
    @Override
    public void createDefaultUsers(){
        if (this.userRepository.findAll().isEmpty()) {
            final var adminUserDto = new UserDto("Daniel", "Simiyu", "admin.admin@yahoo.com", "admin!@#", Set.of("ADMIN"));
            final var memberUserDto = new UserDto("John", "Doe", "member.member@yahoo.com", "member!@#", Set.of("MEMBER"));
            this.createUser(adminUserDto);
            this.createUser(memberUserDto);

        }
    }
    
}
