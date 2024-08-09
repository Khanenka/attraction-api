package com.khanenka.attractionapi.service;

import com.khanenka.attractionapi.entity.Attraction;
import com.khanenka.attractionapi.entity.dto.AttractionDTO;
import com.khanenka.attractionapi.entity.enums.AttractionType;
import com.khanenka.attractionapi.exception.AttractionNotFoundException;
import com.khanenka.attractionapi.exception.AttractionValidationException;
import com.khanenka.attractionapi.repository.AttractionRepository;
import com.khanenka.attractionapi.utility.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления Attraction.
 * Предоставляет методы для создания, обновления, получения и удаления достопремичательностей.
 */
@Service
public class AttractionService {

    private AttractionRepository attractionRepository;
    private String messageException = "Достопримечательность не найдена с id: {}";
    private static final Logger logger = LoggerFactory.getLogger(AttractionService.class);

    /**
     * Конструктор для инициализации сервиса достопремичательностей.
     *
     * @param attractionRepository репозиторий для доступа к данным достопремичательностей
     */
    @Autowired
    public AttractionService(AttractionRepository attractionRepository) {
        this.attractionRepository = attractionRepository;
    }

    /**
     * Сохраняет новую достопремичательность.
     *
     * @param attractionDTO данные достопремичательностей для сохранения
     * @return сохраненная достопремичательность
     * @throws AttractionValidationException если имя или описание достопремичательности равно null
     */
    public Attraction saveAttraction(AttractionDTO attractionDTO) {
        if (attractionDTO.getName() == null || attractionDTO.getDescription() == null) {
            logger.error("Имя или описание достопремичательности равно null");
            throw new AttractionValidationException("Имя и описание не могут быть null");
        }
        Attraction attraction = ModelMapper.INSTANCE.dtoToAttractionEntity(attractionDTO);
        logger.info("Сохранена достопримечательность: {}", attractionRepository.save(attraction));
        return attractionRepository.save(attraction);
    }

    /**
     * Получает достопремичательность по заданному идентификатору.
     *
     * @param id идентификатор достопремичательности
     * @return достопремичательность с заданным идентификатором
     * @throws AttractionNotFoundException если достопремичательность не найдена
     */
    public AttractionDTO getAttractionById(Long id) {
        logger.info("Получение достопремичательности по id: {}", id);
        Attraction attraction = attractionRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error(messageException, id);
                    return new AttractionNotFoundException(messageException + id);
                });
        return ModelMapper.INSTANCE.entityToAttractionDto(attraction);
    }

    /**
     * Получает список достопремичательностей по типу и с возможной сортировкой.
     *
     * @param type   тип достопремичательности
     * @param sortBy параметр сортировки
     * @return отсортированный список достопремичательностей
     * @throws AttractionValidationException если передан неверный параметр сортировки
     */
    public List<Attraction> getAttractions(AttractionType type, String sortBy) {
        logger.info("Получение достопремичательностей с типом: {} и сортировкой по: {}", type, sortBy);
        validateSortBy(sortBy);
        return fetchAttractionsBySort(type, sortBy);
    }

    /**
     * Проверяет корректность параметра сортировки.
     *
     * @param sortBy параметр сортировки
     * @throws AttractionValidationException если параметр сортировки некорректен
     */
    private void validateSortBy(String sortBy) {
        List<String> validSortOptions = Arrays.asList("name", "id", "description", "date", "location", "service");
        if (!validSortOptions.contains(sortBy)) {
            logger.error("Некорректный параметр sortBy: {}", sortBy);
            throw new AttractionValidationException("Некорректный параметр sortBy: " + sortBy);
        }
    }

    /**
     * Получает достопремичательности с сортировкой по заданному параметру.
     *
     * @param type   тип достопремичательности
     * @param sortBy параметр сортировки
     * @return список достопремичательностей, отсортированных по заданному параметру
     */
    private List<Attraction> fetchAttractionsBySort(AttractionType type, String sortBy) {
        logger.info("Получение достопремичательностей с сортировкой по: {}", sortBy);
        switch (sortBy) {
            case "name":
                return attractionRepository.findAllByTypeOrderByName(type);
            case "id":
                return attractionRepository.findAllByTypeOrderByIdAttraction(type);
            case "description":
                return attractionRepository.findAllByTypeOrderByDescription(type);
            case "date":
                return attractionRepository.findAllByTypeOrderByCreationDate(type);
            case "location":
                return attractionRepository.findAllByTypeOrderByLocation(type);
            case "service":
                return attractionRepository.findAllByTypeOrderByServices(type);
            default:
                return attractionRepository.findAllByTypeOrderByName(type);
        }
    }

    /**
     * Находит все достопремичательности по имени локации.
     *
     * @param locationName имя локации
     * @return список достопремичательностей, относящихся к указанной локации
     * @throws AttractionNotFoundException если достопремичательность не найдены для указанной локации
     */
    public List<Attraction> findAllAttractionByLocationName(String locationName) {
        logger.info("Поиск достопремичательностей по имени локации: {}", locationName);
        List<Attraction> attractions = attractionRepository.findByLocation_NameLocation(locationName);
        if (attractions.isEmpty()) {
            logger.error("Не найдены достопремичательности для локации: {}", locationName);
            throw new AttractionNotFoundException("Не найдены достопремичательности для локации: " + locationName);
        }
        return attractions;
    }

    /**
     * Обновляет описание достопремичательноси по заданному идентификатору.
     *
     * @param idAttraction идентификатор достопремичательности
     * @param description  новое описание достопремичательности
     * @return обновленная аттракция
     * @throws AttractionNotFoundException   если достопремичательность не найдена
     * @throws AttractionValidationException если описание равно null или пустое
     */
    public Optional<Attraction> updateAttraction(Long idAttraction, String description) {
        logger.info("Обновление достопремичательности с id: {} с новым описанием", idAttraction);
        Optional<Attraction> referenceById = attractionRepository.findById(idAttraction);
        if (!referenceById.isPresent()) {
            logger.error(messageException, idAttraction);
            throw new AttractionNotFoundException(messageException + idAttraction);
        }
        if (description == null || description.isEmpty()) {
            logger.error("Описание не может быть null или пустым");
            throw new AttractionValidationException("Описание не может быть null или пустым");
        }
        Attraction attraction = referenceById.get();
        attraction.setDescription(description);
        Attraction updatedAttraction = attractionRepository.save(attraction);
        logger.info("Достопремичательность  : {} обновлена", updatedAttraction);
        return Optional.of(updatedAttraction);
    }

    /**
     * Удаляет достопремичательность по заданному идентификатору.
     *
     * @param id идентификатор достопремичательности
     * @throws AttractionNotFoundException если достопремичательность не найдена
     */
    public void deleteAttraction(Long id) {
        logger.info("Удаление достопремичательности с id: {}", id);
        if (!attractionRepository.existsById(id)) {
            logger.error(messageException, id);
            throw new AttractionNotFoundException(messageException + id);
        }
        attractionRepository.deleteById(id);
        logger.info("Достопремичательность с id: {} удалена", id);
    }
}
