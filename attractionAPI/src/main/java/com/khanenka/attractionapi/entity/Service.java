package com.khanenka.attractionapi.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Класс Service со свойствами <b>idService</b>,<b>name</b>,<b>description</b>,
 * <b>attraction</b>
 *
 * @author Khanenka
 * * @version 1.0
 */

@Entity
@Table(name = "services")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Service {
    /**
     * Поле idService класса Service
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idService;
    /**
     * Поле name класса Service
     */
    private String name;
    /**
     * Поле description класса Service
     */
    private String description;
    /**
     * Поле attraction класса Service
     */
    @ManyToOne
    @JoinColumn(name = "id_attraction")
    private Attraction attraction;
}
