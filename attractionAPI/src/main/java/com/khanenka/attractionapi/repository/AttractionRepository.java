package com.khanenka.attractionapi.repository;

import com.khanenka.attractionapi.entity.Attraction;
import com.khanenka.attractionapi.entity.enums.AttractionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с сущностями типа {@link Attraction}.
 * Этот интерфейс расширяет {@link JpaRepository} и предоставляет методы
 * для выполнения CRUD-операций и поиска объектов attractions по различным критериям.
 */
@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    /**
     * находит все достопримечательности определенного типа и сортирует их по имени.
     *
     * @param type поиск по типу достопримечательности
     * @return список достопримечательностей определенного типа, отсортированный по имени
     */
    List<Attraction> findAllByTypeOrderByName(AttractionType type);

    /**
     * находит все достопримечательности определенного типа и сортирует их по имени.
     *
     * @param type поиск по типу достопримечательности
     * @return список достопримечательностей определенного типа, отсортированный по типу description
     */
    List<Attraction> findAllByTypeOrderByDescription(AttractionType type);

    /**
     * находит все достопримечательности определенного типа и сортирует их по имени.
     *
     * @param type поиск по типу достопримечательности
     * @return список достопримечательностей определенного типа, отсортированный по CreationDate
     */
    List<Attraction> findAllByTypeOrderByCreationDate(AttractionType type);

    /**
     * находит все достопримечательности определенного типа и сортирует их по Location.
     *
     * @param type поиск по типу достопримечательности
     * @return список достопримечательностей определенного типа, отсортированный по Location
     */
    List<Attraction> findAllByTypeOrderByLocation(AttractionType type);

    /**
     * находит все достопримечательности определенного типа и сортирует их по service.
     *
     * @param type поиск по типу достопримечательности
     * @return список достопримечательностей определенного типа, отсортированный по Services
     */
    List<Attraction> findAllByTypeOrderByServices(AttractionType type);

    /**
     * находит все достопримечательности определенного типа и сортирует их по id.
     *
     * @param type поиск по типу достопримечательности
     * @return список достопримечательностей определенного типа, отсортированный по id
     */
    List<Attraction> findAllByTypeOrderByIdAttraction(AttractionType type);

    /**
     * находит все достопримечательности определенного типа и сортирует их по locationName.
     *
     * @param locationName поиск по локации
     * @return список достопримечательностей , отсортированный по location.
     */
    List<Attraction> findByLocation_NameLocation(String locationName);
}
