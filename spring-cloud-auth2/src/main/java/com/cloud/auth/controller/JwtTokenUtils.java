package com.cloud.auth.controller;

import org.springframework.security.core.Authentication;

public class JwtTokenUtils {


    public static String createToken(Authentication authentication) {
        System.out.println(authentication);
        return authentication.getName();
    }
}
