
package com.card.auth.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author simiyu
 */
public record JwtRequest(@NotBlank String username, @NotBlank String password) implements Serializable {

}
