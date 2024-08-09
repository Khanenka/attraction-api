package com.khanenka.attractionapi.service;

import com.khanenka.attractionapi.entity.Location;
import com.khanenka.attractionapi.entity.dto.LocationDTO;
import com.khanenka.attractionapi.exception.LocationNotFoundException;
import com.khanenka.attractionapi.exception.LocationValidationException;
import com.khanenka.attractionapi.repository.LocationRepository;
import com.khanenka.attractionapi.utility.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис для работы с локациями.
 * Предоставляет методы для добавления и обновления информации о локациях.
 */
@Service
public class LocationService {

    private LocationRepository locationRepository;
    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

    /**
     * Конструктор для инициализации сервиса с репозиторием локаций.
     *
     * @param locationRepository Репозиторий для работы с локациями.
     */
    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * Добавляет новую локацию на основании данных переданных в виде {@link LocationDTO}.
     *
     * @param locationDTO Данные локации для добавления.
     * @return Добавленная локация.
     * @throws LocationValidationException Если население локации отрицательное.
     */
    public Location addLocation(LocationDTO locationDTO) {
        logger.info("Adding location with name: {}", locationDTO);
        if (locationDTO.getPopulationLocation() < 0) {
            logger.error("Failed to add location. Population cannot be negative: {}",
                    locationDTO.getPopulationLocation());
            throw new LocationValidationException("Population cannot be negative.");
        }
        Location location = ModelMapper.INSTANCE.dtoToLocationEntity(locationDTO);
        Location savedLocation = locationRepository.save(location);
        logger.info("Location added successfully: {}", savedLocation);
        return savedLocation;
    }

    /**
     * Обновляет информацию о локации по данному идентификатору.
     *
     * @param id         Уникальный идентификатор локации.
     * @param population Новое население локации.
     * @param hasMetro   Новое состояние наличия метро в локации.
     * @return Обновленная локация.
     * @throws LocationValidationException Если население локации отрицательное.
     * @throws LocationNotFoundException   Если локация с заданным идентификатором не найдена.
     */
    public Optional<Location> updateLocation(Long id, Long population, boolean hasMetro) {
        logger.info("Updating location with id: {}", id);
        Optional<Location> locationById = locationRepository.findById(id);
        if (locationById.isPresent()) {
            Location location = locationById.get();
            if (population < 0) {
                logger.error("Failed to update location id {}. Population cannot be negative: {}", id, population);
                throw new LocationValidationException("Population cannot be negative.");
            }
            location.setPopulationLocation(population);
            location.setHasMetro(hasMetro);
            Location updatedLocation = locationRepository.save(location);
            logger.info("Location updated successfully: {}", updatedLocation);
            return Optional.of(updatedLocation);
        }
        logger.warn("Location not found with id: {}", id);
        throw new LocationNotFoundException(id);
    }
}
