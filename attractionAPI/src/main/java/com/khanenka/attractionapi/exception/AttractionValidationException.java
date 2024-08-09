package com.khanenka.attractionapi.exception;

/**
 * обработка исключений на валидность достопремичательностей .
 */
public class AttractionValidationException extends RuntimeException {
    /**
     * @param message метод для вызова исключения с кастомным сообщением.
     */
    public AttractionValidationException(String message) {
        super(message);
    }
}
