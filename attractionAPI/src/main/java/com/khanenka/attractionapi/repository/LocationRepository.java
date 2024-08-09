package com.khanenka.attractionapi.repository;

import com.khanenka.attractionapi.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностями типа {@link Location}.
 * Этот интерфейс расширяет {@link JpaRepository} и предоставляет методы
 * для выполнения CRUD-операций  объектов location .
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
