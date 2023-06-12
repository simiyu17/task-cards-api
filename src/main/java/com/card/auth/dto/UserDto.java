
package com.card.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Set;

/**
 * @author simiyu
 */
public record UserDto(
        @Schema(description = "User First Name",
                example = "Daniel")
        @NotBlank(message = "{first.name.missing}")
        String firstName,

        @Schema(description = "Last Name",
                example = "Simiyu")
        @NotBlank(message = "{last.name.missing}")
        String lastName,

        @Schema(description = "User Email, also to be used later as username during authentication",
                example = "daniel.simiyu@gmail.com")
        @Email(message = "{email.invalid}")
        @NotBlank(message = "{email.missing}")
        String email,

        @Schema(description = "User Password, to be used later during authentication",
                example = "Fix Logging")
        @NotBlank(message = "{user.password.missing}")
        String password,

        @Schema(description = "User Roles",
                example = "[ROLE_ADMIN]")
        @NotEmpty(message = "{user.role.missing}")
        Set<String> roles
) implements Serializable {




}
