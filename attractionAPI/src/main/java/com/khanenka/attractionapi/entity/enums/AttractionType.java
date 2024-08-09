package com.khanenka.attractionapi.entity.enums;

import lombok.Getter;

/**
 * ENUM AttractionType со свойствами <b>PALACE</b>,<b>PARK</b>,<b>MUSEUM</b>,<b>ARCHAEOLOGICAL_SITE</b>,
 * <b>NATURE_RESERVE</b>
 *
 * @author Khanenka
 * *
 * * @version 1.0
 * @version $Id: $Id
 */

@Getter
public enum AttractionType {
    /**
     * Поле PALACE класса AttractionType
     */
    PALACE,
    /**
     * Поле PARK класса AttractionType
     */
    PARK,
    /**
     * Поле MUSEUM класса AttractionType
     */
    MUSEUM,
    /**
     * Поле ARCHAEOLOGICAL_SITE класса AttractionType
     */
    ARCHAEOLOGICAL_SITE,
    /**
     * Поле NATURE_RESERVE класса AttractionType
     */
    NATURE_RESERVE
}
