
package com.card.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Set;

/**
 * @author simiyu
 */
public record UserDto(
        @NotBlank(message = "{first.name.missing}")
        String firstName,
        @NotBlank(message = "{last.name.missing}")
        String lastName,
        @Email(message = "{email.invalid}")
        @NotBlank(message = "{email.missing}")
        String email,
        @NotBlank(message = "{user.password.missing}")
        String password,
        @NotEmpty(message = "{user.role.missing}")
        Set<String> roles
) implements Serializable {




}
