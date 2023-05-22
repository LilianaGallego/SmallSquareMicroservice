package com.pragma.powerup.smallsquaremicroservice.configuration.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.NotSerializableException;
import java.util.List;

@Component
public class TokenInterceptor implements HandlerInterceptor {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = request.getHeader("Authorization");
        List<String> roles;
        try{
            DecodedJWT decodedJWT = JWT.decode(token);
            roles = decodedJWT.getClaim("roles").asList(String.class);
            String roleUser = roles.get(0);
            if (!validateRole(roleUser, request, response)){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User UnAuthorized");
            }


        }catch (Exception e){
            throw new NotSerializableException();
        }

        return true;
    }

    public boolean validateRole(String role, HttpServletRequest request, HttpServletResponse ignoredResponse){

        if(role.equals("ROLE_ADMIN") && request.getRequestURI().startsWith("/smallsquare/createRestaurant")){
            return true;
        }
        if (role.equals("ROLE_OWNER") && request.getRequestURI().startsWith("/smallsquare//createPlate")) {
            return true;
        }

        return false;

    }

}

