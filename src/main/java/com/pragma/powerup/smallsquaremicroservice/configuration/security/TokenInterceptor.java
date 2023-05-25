package com.pragma.powerup.smallsquaremicroservice.configuration.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pragma.powerup.smallsquaremicroservice.configuration.security.exception.TokenException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.UserNotRoleOwnerException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.List;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    private static final String TOKEN_PREFIX = "Bearer ";

    private static String token;
    @Setter
    private static Long idOwner;
    private static final String OWNER= "ROLE_OWNER";
    private static final String ADMIN= "ROLE_ADMIN";

    public static String getAuthorizationToken() {
        return token;
    }
    public static Long getIdOwner() {
        return idOwner;
    }



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        token = request.getHeader("Authorization");
        if (token == null || !token.startsWith(TOKEN_PREFIX) ){
            throw  new TokenException();
        }

        String jwtToken = token.substring(TOKEN_PREFIX.length());
        List<String> roles;

        DecodedJWT decodedJWT = JWT.decode(jwtToken);
        roles = decodedJWT.getClaim("roles").asList(String.class);
        idOwner = decodedJWT.getClaim("id").asLong();

        String roleUser = roles.get(0);

        if (ADMIN.equals(roleUser) && isAdmin(request.getRequestURI())) {
            return true;
        }

        if (OWNER.equals(roleUser) && isOwner(request.getRequestURI())) {
            return true;
        }
        throw new UserNotRoleOwnerException();
    }

    private boolean isOwner(String requestURI) {

       return requestURI.startsWith("/smallsquare/plate/");

    }

    private boolean isAdmin(String requestURI) {
        return requestURI.startsWith("/smallsquare/restaurant/");

    }
}

