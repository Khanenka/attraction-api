package com.khanenka.attractionapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khanenka.attractionapi.entity.Location;
import com.khanenka.attractionapi.entity.dto.LocationDTO;
import com.khanenka.attractionapi.service.LocationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.Mockito.*;

class LocationControllerTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;
    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddLocation() {
        LocationDTO locationDTO = new LocationDTO(
                1L, "Minsk", 2000000L,
                true, null);
        Location location = new Location(1L, "Minsk",
                2000000L, true, null);
        when(locationService.addLocation(any(LocationDTO.class))).thenReturn(location);
        ResponseEntity<Location> response = locationController.addLocation(locationDTO);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(location, response.getBody());
        verify(locationService, times(1)).addLocation(locationDTO);
    }

    @Test
    void testUpdateLocation() {
        Long id = 1L;
        Long population = 100000L;
        boolean hasMetro = true;
        Location location = new Location(
                1L, "Minsk", 2000000L, true, null);
        when(locationService.updateLocation(id, population, hasMetro)).thenReturn(Optional.of(location));
        ResponseEntity<Location> response = locationController.updateLocation(id, population, hasMetro);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(location, response.getBody());
        verify(locationService, times(1)).updateLocation(id, population, hasMetro);
    }

    @Test
    void updateLocation_ShouldReturnNotFound() {
        Long id = 999L;
        Long population = 1000L;
        boolean hasMetro = true;
        when(locationService.updateLocation(id, population, hasMetro)).thenReturn(Optional.empty());
        ResponseEntity<Location> response = locationController.updateLocation(id, population, hasMetro);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
        verify(locationService, times(1)).updateLocation(id, population, hasMetro);
    }
}