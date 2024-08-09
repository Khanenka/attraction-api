package com.khanenka.attractionapi.entity.dto;

import com.khanenka.attractionapi.entity.Attraction;
import lombok.*;

import java.util.List;

/**
 * Класс LocationDTO со свойствами <b>idLocation</b>,<b>nameLocation</b>,<b>populationLocation</b>,
 * <b>hasMetro</b>,<b>attractions</b>,
 *
 * @author Khanenka
 * * @version 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class LocationDTO {
    /**
     * Поле idLocation класса LocationDTO
     */

    private Long idLocation;
    /**
     * Поле nameLocation класса LocationDTO
     */
    private String nameLocation;
    /**
     * Поле populationLocation класса LocationDTO
     */
    private Long populationLocation;
    /**
     * Поле hasMetro класса LocationDTO
     */
    private Boolean hasMetro;
    /**
     * Поле attractions класса LocationDTO
     */
    private List<Attraction> attractions;
}
