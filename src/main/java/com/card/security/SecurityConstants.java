package com.card.security;

/**
 *
 * @author simiyu
 */
public class SecurityConstants {

    private SecurityConstants(){}

    public static final String SECRET = "SecretKeyToGenJSecretKeyToGenJWTsHDKSFiendishGSHDUEjshskdghKSHDGHDWTsHDKSFiendishGSHDUSecretKeyToGenJWTsHDKSFiendishGSHDUEjshskdghKSHDGHDEjshskdghKSHDGHD";
    public static final long EXPIRATION_TIME = 60_000 * 100L; // 100 minutes
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
}