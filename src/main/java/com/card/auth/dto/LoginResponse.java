
package com.card.auth.dto;

import java.io.Serializable;

/**
 * @author simiyu
 */
public record LoginResponse(String msg, String authToken) implements Serializable {

}
