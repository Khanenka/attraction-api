package com.khanenka.attractionapi.entity;

import com.khanenka.attractionapi.entity.enums.AttractionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Класс Attraction со свойствами <b>idAttraction</b>,<b>name</b>,<b>creationDate</b>,<b>description</b>,<b>type</b>,
 * <b>location</b>,<b>services</b>
 *
 * @author Khanenka
 * *
 * * @version 1.0
 */

@Entity
@Table(name = "attractions")
@Data
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Attraction {
    /**
     * Поле idAttraction класса Attraction
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idAttraction;
    /**
     * Поле name класса Attraction
     */
    private String name;
    /**
     * Поле creationDate класса Attraction
     */
    private String creationDate;
    /**
     * Поле description класса Attraction
     */
    private String description;

    /**
     * Поле type класса Attraction
     */
    @Enumerated(EnumType.STRING)
    private AttractionType type;

    /**
     * Поле location класса Attraction
     */
    @ManyToOne
    @JoinColumn(name = "id_location")
    private Location location;

    /**
     * Поле services класса Attraction
     */
    @OneToMany(mappedBy = "attraction", cascade = CascadeType.ALL)
    private List<Service> services;
}
