package com.khanenka.attractionapi.entity.dto;

import com.khanenka.attractionapi.entity.Location;
import com.khanenka.attractionapi.entity.Service;
import com.khanenka.attractionapi.entity.enums.AttractionType;
import lombok.*;

import java.util.List;


/**
 * Класс AttractionDTO со свойствами <b>idAttraction</b>,<b>name</b>,<b>creationDate</b>,<b>description</b>,<b>type</b>,
 * <b>location</b>,<b>services</b>
 *
 * @author Khanenka
 * *
 * * @version 1.0
 */

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AttractionDTO {
    /**
     * Поле idAttraction класса AttractionDTO
     */

    private Long idAttraction;
    /**
     * Поле name класса AttractionDTO
     */
    private String name;
    /**
     * Поле creationDate класса AttractionDTO
     */
    private String creationDate;
    /**
     * Поле description класса AttractionDTO
     */
    private String description;
    /**
     * Поле type класса AttractionDTO
     */
    private AttractionType type;
    /**
     * Поле location класса AttractionDTO
     */
    private Location location;
    /**
     * Поле services класса AttractionDTO
     */
    private List<Service> services;
}
