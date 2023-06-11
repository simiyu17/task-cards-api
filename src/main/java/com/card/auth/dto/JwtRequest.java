
package com.card.auth.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author simiyu
 */
public record JwtRequest(
        @NotBlank(message = "{user.username.missing}")
        String username,
        @NotBlank(message = "{user.password.missing}")
        String password
) implements Serializable {

}
