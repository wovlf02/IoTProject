package com.smartcampus.back.common.util;

import jakarta.servlet.http.HttpServletRequest;

public class TokenUtil {
    public static String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
