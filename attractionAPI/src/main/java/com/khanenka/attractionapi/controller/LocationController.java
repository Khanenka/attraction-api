package com.khanenka.attractionapi.controller;

import com.khanenka.attractionapi.entity.Location;
import com.khanenka.attractionapi.entity.dto.LocationDTO;
import com.khanenka.attractionapi.exception.LocationNotFoundException;
import com.khanenka.attractionapi.exception.LocationValidationException;
import com.khanenka.attractionapi.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Контроллер для управления местоположениями.
 * Предоставляет REST API для добавления и обновления местоположений.
 */
@RestController
@RequestMapping("/locations")
public class LocationController {
    private final LocationService locationService;

    /**
     * Конструктор контроллера.
     *
     * @param locationService Сервис для работы с местоположениями.
     */
    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * Добавляет новое местоположение.
     *
     * @param locationDTO Данные о местоположении, передаваемые в теле запроса.
     * @return Объект ResponseEntity с добавленным местоположением и статусом 201 (Created).
     */
    @PostMapping(headers = "Accept=application/json")
    public ResponseEntity<Location> addLocation(@RequestBody LocationDTO locationDTO) {
        Location addedLocation = locationService.addLocation(locationDTO);
        return new ResponseEntity<>(addedLocation, HttpStatus.CREATED);
    }

    /**
     * Обновляет информацию о местоположении.
     *
     * @param id         Идентификатор местоположения, которое нужно обновить.
     * @param population Новое значение населения.
     * @param hasMetro   Указывает наличие метро в местоположении.
     * @return Объект ResponseEntity с обновленным местоположением и статусом 200 (OK),
     * или статусом 404 (Not Found), если местоположение не найдено.
     */
    @PutMapping("/{id}/population/has_metro")
    public ResponseEntity<Location> updateLocation(@PathVariable Long id,
                                                   @RequestBody Long population,
                                                   @RequestBody Boolean hasMetro) {
        Optional<Location> updateLocation = locationService.updateLocation(id, population, hasMetro);
        return updateLocation
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Обрабатывает исключения, связанные с валидацией местоположений.
     *
     * @param ex Исключение валидации местоположения.
     * @return Объект ResponseEntity с сообщением об ошибке и статусом 400 (Bad Request).
     */
    @ExceptionHandler(LocationValidationException.class)
    public ResponseEntity<String> handleLocationValidationException(LocationValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Обрабатывает исключения, возникающие, когда местоположение не найдено.
     *
     * @param ex Исключение, возникающее при отсутствии местоположения.
     * @return Объект ResponseEntity с сообщением об ошибке и статусом 404 (Not Found).
     */
    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<String> handleLocationNotFoundException(LocationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
