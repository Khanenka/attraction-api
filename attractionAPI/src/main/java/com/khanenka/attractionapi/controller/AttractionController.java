package com.khanenka.attractionapi.controller;

import com.khanenka.attractionapi.entity.Attraction;
import com.khanenka.attractionapi.entity.dto.AttractionDTO;
import com.khanenka.attractionapi.entity.enums.AttractionType;
import com.khanenka.attractionapi.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер для управления достопримечательностями в приложении.
 * Предоставляет REST API для выполнения операций с достопримечательностями.
 *
 * @author Khanenka
 * @version 1.0
 */

@RestController
@RequestMapping("/attractions")
public class AttractionController {


    private final AttractionService attractionService;

    /**
     * Конструктор контроллера, который инжектит сервис достопримечательностей.
     *
     * @param attractionService сервис для взаимодействия с достопримечательностями.
     */
    @Autowired
    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    /**
     * Создает новую достопримечательность.
     *
     * @param attraction DTO объекта достопримечательность, содержащий данные для создания.
     * @return ResponseEntity с созданным достопримечательностями и статусом HTTP 201 (Создано).
     */
    @PostMapping(headers = "Accept=application/json")
    public ResponseEntity<Attraction> addAttraction(@RequestBody AttractionDTO attraction) {
        return new ResponseEntity<>(attractionService.saveAttraction(attraction), HttpStatus.CREATED);
    }

    /**
     * Получает список достопримечательностей с возможностью фильтрации по типу и сортировки.
     *
     * @param type   тип достопримечательностей (может быть null для получения всех).
     * @param sortBy поле для сортировки (по умолчанию 'name').
     * @return ResponseEntity со списком достопримечательностей и статусом HTTP 200 (ОК).
     */
    @GetMapping
    public ResponseEntity<List<Attraction>> getAttractions(
            @RequestParam(required = false) AttractionType type,
            @RequestParam(defaultValue = "name") String sortBy) {
        return new ResponseEntity<>(attractionService.getAttractions(type, sortBy), HttpStatus.OK);
    }

    /**
     * Получает список достопримечательностей по имени локации.
     *
     * @param locationName имя локации для поиска достопримечательностей.
     * @return ResponseEntity со списком достопримечательностей и статусом HTTP 200 (ОК).
     */
    @GetMapping("/{locationName}")
    public ResponseEntity<List<Attraction>> getAttractionsByLocation(@PathVariable String locationName) {
        return new ResponseEntity<>(attractionService.findAllAttractionByLocationName(locationName), HttpStatus.OK);
    }

    /**
     * Обновляет краткое описание достопримечательностей по его идентификатору.
     *
     * @param id          идентификатор достопримечательностей, который нужно обновить.
     * @param description новое краткое описание.
     * @return ResponseEntity с обновленной достопримечательностью и статусом HTTP 200 (ОК) или статусом 404 (Не найден)
     * если аттракцион не найден.
     */
    @PutMapping("/{id}/description")
    public ResponseEntity<Attraction> updateShortDescription(@PathVariable Long id, @RequestBody String description) {
        Optional<Attraction> updatedAttraction = attractionService.updateAttraction(id, description);
        return updatedAttraction
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Удаляет достопримечательность по его идентификатору.
     *
     * @param id идентификатор достопримечательности, который нужно удалить.
     */
    @DeleteMapping("/{id}")
    public void deleteAttraction(@PathVariable Long id) {
        attractionService.deleteAttraction(id);
    }
}
