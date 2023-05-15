package com.pragma.powerup.smallsquaremicroservice.configuration;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final Long OWNER_ROLE_ID = 2L;
    public static final String RESPONSE_MESSAGE_KEY = "message";
    public static final String RESTAURANT_CREATED_MESSAGE = "Restaurant created successfully";
    public static final String PLATE_CREATED_MESSAGE = "Plate created successfully";
    public static final String RESPONSE_ERROR_MESSAGE_KEY = "error";
    public static final String USER_NOT_FOUND_MESSAGE = "No user found with the role provided";
    public static final String NO_DATA_FOUND_MESSAGE = "No data found for the requested petition";
    public static final String RESTAURANT_ALREADY_EXISTS_MESSAGE = "A Restaurant already exists with the role provided";
    public static final String RESTAURANT_NOT_FOUND_MESSAGE = "No Restaurant found with the role provided";
    public static final String SWAGGER_TITLE_MESSAGE = "SmallSquare API Pragma Power Up";
    public static final String USER_NOT_ROLE_OWNER = "User does not have owner role";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "SmallSquare microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";
}
