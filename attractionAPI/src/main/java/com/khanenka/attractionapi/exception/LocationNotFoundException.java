package com.khanenka.attractionapi.exception;

/**
 * обработка исключений если локации  нет.
 */
public class LocationNotFoundException extends RuntimeException {
    /**
     * @param id метод для вызова исключения с кастомным сообщением по id.
     */
    public LocationNotFoundException(Long id) {
        super("Location not found with id: " + id);
    }
}
