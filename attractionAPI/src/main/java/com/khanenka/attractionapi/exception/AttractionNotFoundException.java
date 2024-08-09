package com.khanenka.attractionapi.exception;

/**
 * обработка исключений если достопремичательностей не может найти или нет.
 */
public class AttractionNotFoundException extends RuntimeException {
    /**
     * @param message метод для вызова исключения с кастомным сообщением.
     */
    public AttractionNotFoundException(String message) {
        super(message);
    }
}
