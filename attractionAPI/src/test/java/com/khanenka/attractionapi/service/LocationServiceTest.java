package com.khanenka.attractionapi.service;

import com.khanenka.attractionapi.entity.Location;
import com.khanenka.attractionapi.entity.dto.LocationDTO;
import com.khanenka.attractionapi.exception.LocationNotFoundException;
import com.khanenka.attractionapi.exception.LocationValidationException;
import com.khanenka.attractionapi.repository.LocationRepository;
import com.khanenka.attractionapi.utility.ModelMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class LocationServiceTest {
    @Mock
    private LocationRepository locationRepository;
    @Mock
    ModelMapper modelMapper;
    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddLocation() {
        LocationDTO locationDTO = new LocationDTO(1L, "Minsk", 2000000L, true, null);
        Location location = new Location(1L, "Minsk", 2000000L, true, null);
        when(modelMapper.dtoToLocationEntity(locationDTO)).thenReturn(location);
        when(locationRepository.save(location)).thenReturn(location);
        Location createdLocation = locationService.addLocation(locationDTO);
        assertNotNull(createdLocation);
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void testUpdateLocationNotFound() {
        Long id = 1L;
        Long newPopulation = 2000L;

        when(locationRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(LocationNotFoundException.class, () -> {
            locationService.updateLocation(id, newPopulation, true);
        });
        assertEquals("Location not found with id: " + id, exception.getMessage());
    }

    @Test
    void testAddLocationWithNegativePopulation() {
        LocationDTO locationDTO = new LocationDTO(1L, "Minsk",
                2000000L, true, null);
        locationDTO.setPopulationLocation(-1000L);
        Exception exception = assertThrows(LocationValidationException.class, () -> {
            locationService.addLocation(locationDTO);
        });
        assertEquals("Population cannot be negative.", exception.getMessage());
    }

    @Test
    void testUpdateLocationSuccess() {
        Long id = 1L;
        Long newPopulation = 5000L;
        boolean newHasMetro = true;
        Location existingLocation = new Location();
        existingLocation.setPopulationLocation(3000L);
        existingLocation.setHasMetro(false);
        when(locationRepository.findById(id)).thenReturn(Optional.of(existingLocation));
        when(locationRepository.save(existingLocation)).thenReturn(existingLocation);
        Optional<Location> updatedLocation = locationService.updateLocation(id, newPopulation, newHasMetro);
        assertTrue(updatedLocation.isPresent());
        assertEquals(newPopulation, updatedLocation.get().getPopulationLocation());
        assertEquals(newHasMetro, updatedLocation.get().getHasMetro());
        verify(locationRepository, times(1)).findById(id);
        verify(locationRepository, times(1)).save(existingLocation);
    }

    @Test
    void testUpdateLocationWithNegativePopulation() {
        Long id = 1L;
        Long newPopulation = -2000L;
        Location location = new Location(
                1L, "Minsk", 1000000L, true, null);
        when(locationRepository.findById(id)).thenReturn(Optional.of(location));
        Exception exception = assertThrows(LocationValidationException.class, () -> {
            locationService.updateLocation(id, newPopulation, true);
        });
        assertEquals("Population cannot be negative.", exception.getMessage());
    }
}
