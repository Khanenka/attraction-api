package com.khanenka.attractionapi.exception;

/**
 * исключени на валидность локации .
 */
public class LocationValidationException extends RuntimeException {
    /**
     * @param message метод для вызова исключения с кастомным сообщением.
     */
    public LocationValidationException(String message) {
        super(message);
    }
}
