
package com.card.auth.service;


import com.card.auth.domain.User;
import com.card.auth.dto.JwtRequest;
import com.card.auth.dto.LoginResponse;
import com.card.auth.dto.UserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author simiyu
 */
public interface UserService {
    
    void createUser(@Valid UserDto userDto);

    User findUserByUserName(final String userName);
    
    LoginResponse authenticateUser(@Valid final JwtRequest request);

    void createDefaultUsers();
}
