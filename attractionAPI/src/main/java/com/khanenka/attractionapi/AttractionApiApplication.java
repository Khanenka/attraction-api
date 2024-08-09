package com.khanenka.attractionapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *Класс запуска спринг бут приложения поиска достопремичательнойстей
 *
 * @author Khanenka
 * @version 1.0
 */
@SpringBootApplication
public class AttractionApiApplication {
    /**
     * @param args аргументы командой строки для запуска проекта.
     */
    public static void main(String[] args) {
        SpringApplication.run(AttractionApiApplication.class, args);
    }
}
