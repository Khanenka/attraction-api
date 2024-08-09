package com.khanenka.attractionapi.utility;

import com.khanenka.attractionapi.entity.Attraction;
import com.khanenka.attractionapi.entity.Location;
import com.khanenka.attractionapi.entity.dto.AttractionDTO;
import com.khanenka.attractionapi.entity.dto.LocationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Interface  ModelMapper с методами <b>entityToAttractionDto</b>,
 * <b>dtoToAttractionEntity</b>,<b>dtoToLocationEntity</b>
 *
 * @author Khanenka
 * *
 * * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface ModelMapper {
    /**
     * mapstruct прописываем к классу ModelMapper
     */
    ModelMapper INSTANCE = Mappers.getMapper(ModelMapper.class);
    /**
     * конвертирует attraction в attractionDTO.
     *
     * @param attraction конвертируем attraction
     * @return AttractionDTO
     */
    @Mapping(source = "idAttraction", target = "idAttraction")
    AttractionDTO entityToAttractionDto(Attraction attraction);
    /**
     * конвертирует  attractionDTO в attraction .
     *
     * @param attractionDTO конвертируем attractionDTO
     * @return AttractionDTO
     */
    @Mapping(source = "idAttraction", target = "idAttraction")
    Attraction dtoToAttractionEntity(AttractionDTO attractionDTO);
    /**
     * конвертирует  locationDTO в location .
     *
     * @param locationDTO конвертируем locationDTO
     * @return AttractionDTO
     */
    @Mapping(source = "idLocation", target = "idLocation")
    Location dtoToLocationEntity(LocationDTO locationDTO);
}
