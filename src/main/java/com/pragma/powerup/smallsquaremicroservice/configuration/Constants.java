package com.pragma.powerup.smallsquaremicroservice.configuration;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final Long OWNER_ROLE_ID = 2L;
    public static final String RESPONSE_MESSAGE_KEY = "message";
    public static final String RESTAURANT_CREATED_MESSAGE = "Restaurant created successfully";
    public static final String USER_CREATED_MESSAGE = "User created successfully";

    public static final String PLATE_CREATED_MESSAGE = "Plate created successfully";
    public static final String PLATE_UPDATED_MESSAGE = "Plate updated successfully";
    public static final String RESPONSE_ERROR_MESSAGE_KEY = "error";
    public static final String RESPONSE_ERROR_MESSAGE_TOKEN= "A problem with the token has occurred";
    public static final String USER_NOT_FOUND_MESSAGE = "No user found with the role provided";
    public static final String NO_DATA_FOUND_MESSAGE = "No data found for the requested petition";
    public static final String NAME_REQUIRED_MESSAGE = "Name is required";
    public static final String ADDRESS_REQUIRED_MESSAGE = "Address is required";
    public static final String PHONE_REQUIRED_MESSAGE = "Phone is required";
    public static final String URL_LOGO_REQUIRED_MESSAGE = "UrlLogo is required";
    public static final String HTTP_MESSAGE_NOT_READABLE_EXCEPTION = "The field cannot be null or exceed its characters";
    public static final String NOT_OWNER_RESTAURANT= "The user is not the owner of this restaurant";
    public static final String IDOWNER_REQUIRED_MESSAGE = "IdOwner is required";
    public static final String DNI_NUMBER_REQUIRED_MESSAGE = "DniNumber is required";
    public static final String PLATE_PRICE_NOT_VALID_MESSAGE = "The price of the invalid dish must be greater than 0";
    public static final String RESTAURANT_ALREADY_EXISTS_MESSAGE = "A Restaurant already exists with the role provided";
    public static final String RESTAURANT_NOT_EXIST_MESSAGE = "The Restaurant does not exist ";
    public static final String PLATE_ALREADY_EXISTS_MESSAGE = "A Plate already exists with the name provided";
    public static final String CATEGORY_ALREADY_EXISTS_MESSAGE = "A Category already exists with the name provided";
    public static final String CATEGORY_NOT_EXIST_MESSAGE = "The Category not exists";
    public static final String PLATE_NOT_EXIST_MESSAGE = "The Plate not exists";
    public static final String RESTAURANT_NOT_FOUND_MESSAGE = "No Restaurant found with the role provided";
    public static final String DESCRIPTION_REQUIRED_MESSAGE = "Description is required";
    public static final String URL_IMAGE_REQUIRED_MESSAGE = "UrlImage is required";
    public static final String SWAGGER_TITLE_MESSAGE = "SmallSquare API Pragma Power Up";
    public static final String USER_NOT_ROLE_OWNER = "User does not have owner role";
    public static final String USER_NOT_ROLE_AUTHORIZED= "the user's role is not authorized";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "SmallSquare microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";
}