package com.pragma.powerup.smallsquaremicroservice.configuration;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.*;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(NoDataFoundException noDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(Constants.RESPONSE_ERROR_MESSAGE_KEY, Constants.NO_DATA_FOUND_MESSAGE));
    }

    @ExceptionHandler(RestaurantAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleRestaurantAlreadyExistsException(
            RestaurantAlreadyExistsException restaurantAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(Constants.RESPONSE_ERROR_MESSAGE_KEY, Constants.RESTAURANT_ALREADY_EXISTS_MESSAGE));
    }
    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            RestaurantNotFoundException restaurantNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(Constants.RESPONSE_ERROR_MESSAGE_KEY, Constants.RESTAURANT_NOT_FOUND_MESSAGE));
    }

    @ExceptionHandler(UserNotRoleOwnerException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            UserNotRoleOwnerException ownerNotRoleOwnerException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(Constants.RESPONSE_ERROR_MESSAGE_KEY, Constants.USER_NOT_ROLE_OWNER));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            UserNotFoundException userNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(Constants.RESPONSE_ERROR_MESSAGE_KEY, Constants.USER_NOT_FOUND_MESSAGE));

    }


    @ExceptionHandler(RestaurantNotExistException.class)
    public ResponseEntity<Map<String, String>> handleRestaurantNotExistException(
            RestaurantNotExistException restaurantNotExistException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(Constants.RESPONSE_ERROR_MESSAGE_KEY, Constants.RESTAURANT_NOT_EXIST_MESSAGE));
    }

    @ExceptionHandler(NameRequiredException.class)
    public ResponseEntity<Map<String, String>> handleNamePlateRequiredException(
            NameRequiredException namePlateRequiredException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(Constants.RESPONSE_ERROR_MESSAGE_KEY, Constants.NAME_REQUIRED_MESSAGE));
    }

    @ExceptionHandler(AddressRequiredException.class)
    public ResponseEntity<Map<String, String>> handleAddressRequiredException(
            AddressRequiredException addressRequiredException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(Constants.RESPONSE_ERROR_MESSAGE_KEY, Constants.ADDRESS_REQUIRED_MESSAGE));
    }

    @ExceptionHandler(PhoneRequiredException.class)
    public ResponseEntity<Map<String, String>> handlePhoneRequiredException(
            PhoneRequiredException phoneRequiredException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(Constants.RESPONSE_ERROR_MESSAGE_KEY, Constants.PHONE_REQUIRED_MESSAGE));
    }

    @ExceptionHandler(UrlLogoRequiredException.class)
    public ResponseEntity<Map<String, String>> handleUrlLogoRequiredException(
            UrlLogoRequiredException urlLogoRequiredException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(Constants.RESPONSE_ERROR_MESSAGE_KEY, Constants.URL_LOGO_REQUIRED_MESSAGE));
    }

    @ExceptionHandler(IdOwnerRequiredException.class)
    public ResponseEntity<Map<String, String>> handleIdOwnerRequiredException(
            IdOwnerRequiredException idOwnerRequiredException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(Constants.RESPONSE_ERROR_MESSAGE_KEY, Constants.IDOWNER_REQUIRED_MESSAGE));
    }

    @ExceptionHandler(DniNumberRequiredException.class)
    public ResponseEntity<Map<String, String>> handleDniNumberRequiredException(
            DniNumberRequiredException dniNumberRequiredException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(Constants.RESPONSE_ERROR_MESSAGE_KEY, Constants.DNI_NUMBER_REQUIRED_MESSAGE));
    }
}
