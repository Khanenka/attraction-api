package com.khanenka.attractionapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Класс Location со свойствами <b>idLocation</b>,<b>nameLocation</b>,<b>populationLocation</b>,
 * <b>hasMetro</b>,<b>attractions</b>,
 *
 * @author Khanenka
 * * @version 1.0
 */

@Entity
@Table(name = "locations")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    /**
     * Поле idLocation класса Location
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idLocation;
    /**
     * Поле nameLocation класса Location
     */
    private String nameLocation;
    /**
     * Поле populationLocation класса Location
     */
    private Long populationLocation;
    /**
     * Поле hasMetro класса Location
     */
    private Boolean hasMetro;
    /**
     * Поле attractions класса Location
     */
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Attraction> attractions;
}
