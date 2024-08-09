package com.khanenka.attractionapi.service;

import com.khanenka.attractionapi.entity.Attraction;
import com.khanenka.attractionapi.entity.dto.AttractionDTO;
import com.khanenka.attractionapi.entity.enums.AttractionType;
import com.khanenka.attractionapi.exception.AttractionNotFoundException;
import com.khanenka.attractionapi.exception.AttractionValidationException;
import com.khanenka.attractionapi.repository.AttractionRepository;
import com.khanenka.attractionapi.utility.ModelMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class AttractionServiceTest {

    @Mock
    private AttractionRepository attractionRepository;
    @Mock
    ModelMapper modelMapper;
    @InjectMocks
    private AttractionService attractionService;
    private String messageException = "Достопримечательность не найдена с id: {}";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveAttraction() {
        AttractionDTO attractionDTO = new AttractionDTO(
                1L, "Якуб Колас", "10.10.1970", "Основан в 1970",
                AttractionType.PARK, null, null);
        Attraction attraction = new Attraction(
                1L, "Якуб Колас", "10.10.1970", "Основан в 1970",
                AttractionType.PARK, null, null);
        when(modelMapper.dtoToAttractionEntity(attractionDTO)).thenReturn(attraction);
        when(attractionRepository.save(any(Attraction.class))).thenReturn(attraction);
        Attraction savedAttraction = attractionService.saveAttraction(attractionDTO);
        assertNotNull(savedAttraction);
    }

    @Test
    void testSaveAttraction_NullName_ThrowsException() {
        AttractionDTO attractionDTO = new AttractionDTO();
        attractionDTO.setDescription("Описание");
        AttractionValidationException thrown = assertThrows(
                AttractionValidationException.class,
                () -> attractionService.saveAttraction(attractionDTO),
                "Expected saveAttraction to throw, but it didn't"
        );
        assertEquals("Имя и описание не могут быть null", thrown.getMessage());
    }

    @Test
    void testGetAttractionById_Success() {
        Long id = 1L;
        Attraction attraction = new Attraction();
        when(attractionRepository.findById(id)).thenReturn(Optional.of(attraction));
        AttractionDTO result = attractionService.getAttractionById(id);
        assertNotNull(result);
        verify(attractionRepository).findById(id);
    }

    @Test
    void testGetAttractionById_NotFound() {
        Long id = 1L;
        when(attractionRepository.findById(id)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            attractionService.getAttractionById(id);
        });
        assertEquals(messageException + id, exception.getMessage());
    }

    @Test
    void testGetAttractions_SortedById() {
        AttractionType type = AttractionType.PARK;
        List<Attraction> attractions = new ArrayList<>();
        when(attractionRepository.findAllByTypeOrderByIdAttraction(type)).thenReturn(attractions);
        List<Attraction> result = attractionService.getAttractions(type, "id");
        assertEquals(attractions, result);
        verify(attractionRepository).findAllByTypeOrderByIdAttraction(type);
    }

    @Test
    void testGetAttractions_SortedByName() {
        AttractionType type = AttractionType.PARK;
        List<Attraction> attractions = new ArrayList<>();
        when(attractionRepository.findAllByTypeOrderByName(type)).thenReturn(attractions);
        List<Attraction> result = attractionService.getAttractions(type, "name");
        assertEquals(attractions, result);
        verify(attractionRepository).findAllByTypeOrderByName(type);
    }

    @Test
    void testGetAttractions_SortedByDescription() {
        AttractionType type = AttractionType.PARK;
        List<Attraction> attractions = new ArrayList<>();
        when(attractionRepository.findAllByTypeOrderByDescription(type)).thenReturn(attractions);
        List<Attraction> result = attractionService.getAttractions(type, "description");
        assertEquals(attractions, result);
        verify(attractionRepository).findAllByTypeOrderByDescription(type);
    }

    @Test
    void testGetAttractions_SortedByDate() {
        AttractionType type = AttractionType.PARK;
        List<Attraction> attractions = new ArrayList<>();
        when(attractionRepository.findAllByTypeOrderByCreationDate(type)).thenReturn(attractions);
        List<Attraction> result = attractionService.getAttractions(type, "date");
        assertEquals(attractions, result);
        verify(attractionRepository).findAllByTypeOrderByCreationDate(type);
    }

    @Test
    void testGetAttractions_SortedByLocation() {
        AttractionType type = AttractionType.PARK;
        List<Attraction> attractions = new ArrayList<>();
        when(attractionRepository.findAllByTypeOrderByLocation(type)).thenReturn(attractions);
        List<Attraction> result = attractionService.getAttractions(type, "location");
        assertEquals(attractions, result);
        verify(attractionRepository).findAllByTypeOrderByLocation(type);
    }

    @Test
    void testGetAttractions_SortedByService() {
        AttractionType type = AttractionType.PARK;
        List<Attraction> attractions = new ArrayList<>();
        when(attractionRepository.findAllByTypeOrderByServices(type)).thenReturn(attractions);
        List<Attraction> result = attractionService.getAttractions(type, "service");
        assertEquals(attractions, result);
        verify(attractionRepository).findAllByTypeOrderByServices(type);
    }


    @Test
    void testUpdateAttraction_Success() {
        Long idAttraction = 1L;
        String newDescription = "Updated Description";
        Attraction attraction = new Attraction();
        when(attractionRepository.findById(idAttraction)).thenReturn(Optional.of(attraction));
        when(attractionRepository.save(attraction)).thenReturn(attraction);
        Optional<Attraction> result = attractionService.updateAttraction(idAttraction, newDescription);
        assertTrue(result.isPresent());
        assertEquals(newDescription, result.get().getDescription());
        verify(attractionRepository).save(attraction);
    }

    @Test
    void testDeleteAttraction_AttractionExists() {
        Long attractionId = 1L;
        when(attractionRepository.existsById(attractionId)).thenReturn(true);
        attractionService.deleteAttraction(attractionId);
        verify(attractionRepository).deleteById(attractionId);
    }

    @Test
    public void testFindAllAttractionByLocationName_WithAttractions() {
        String locationName = "Paris";
        List<Attraction> mockAttractions = List.of(new Attraction(
                1L, "Якуб Колас", "10.10.1970", "Основан в 1970",
                AttractionType.MUSEUM, null, null), new Attraction(
                2L, "Янка Купала", "10.10.1970", "Основан в 1970",
                AttractionType.MUSEUM, null, null));
        when(attractionRepository.findByLocation_NameLocation(locationName)).thenReturn(mockAttractions);
        List<Attraction> attractions = attractionService.findAllAttractionByLocationName(locationName);
        assertEquals(2, attractions.size());
        assertEquals("Якуб Колас", attractions.get(0).getName());
        assertEquals("Янка Купала", attractions.get(1).getName());
    }

    @Test
    public void testFindAllAttractionByLocationName_NoAttractions() {
        String locationName = "Unknown Location";
        when(attractionRepository.findByLocation_NameLocation(locationName)).thenReturn(Collections.emptyList());
        AttractionNotFoundException exception = assertThrows(AttractionNotFoundException.class,
                () -> attractionService.findAllAttractionByLocationName(locationName));
        assertEquals("Не найдены достопремичательности для локации: " + locationName, exception.getMessage());
    }
}
