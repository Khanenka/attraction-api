package com.khanenka.attractionapi.controller;

import com.khanenka.attractionapi.entity.Attraction;
import com.khanenka.attractionapi.entity.dto.AttractionDTO;
import com.khanenka.attractionapi.entity.enums.AttractionType;
import com.khanenka.attractionapi.service.AttractionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AttractionControllerTest {

    private MockMvc mockMvc;
    @Mock
    private AttractionService attractionService;
    @InjectMocks
    private AttractionController attractionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(attractionController).build();
    }

    @Test
    void testAddAttraction() throws Exception {
        AttractionDTO attractionDTO = new AttractionDTO(
                3L, "w", "12.01.2022", "ndescriptio1n",
                AttractionType.PALACE, null, null);
        Attraction attraction = new Attraction(
                3L, "w", "12.01.2022", "ndescriptio1n",
                AttractionType.PALACE, null, null);

        when(attractionService.saveAttraction(attractionDTO)).thenReturn(attraction);

        mockMvc.perform(post("/attractions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"idAttraction\":3,\n" +
                                "    \"name\":\"w\",\n" +
                                "    \"creationDate\":\"12.01.2022\",\n" +
                                "    \"description\":\"ndescriptio1n\",\n" +
                                "    \"type\":\"PALACE\",\n" +
                                "    \"location\":null,\n" +
                                "    \"services\":[\n" +
                                "        {\n" +
                                "            \"idService\":3,\n" +
                                "            \"name\":\"name\",\n" +
                                "            \"description\":\"description\"\n" +
                                "        }\n" +
                                "    ]\n" +
                                "}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetAttractions() throws Exception {
        List<Attraction> attractions = Collections.singletonList(new Attraction(/* инициализация */));

        when(attractionService.getAttractions(null, "name")).thenReturn(attractions);

        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(attractions.get(0).getName()));
    }

    @Test
    void testGetAttractionsByLocation() throws Exception {
        List<Attraction> attractions = Collections.singletonList(new Attraction(
                3L, "w", "12.01.2022", "ndescriptio1n",
                AttractionType.PALACE, null, null));
        String locationName = "SomeLocation";

        when(attractionService.findAllAttractionByLocationName(locationName)).thenReturn(attractions);

        mockMvc.perform(get("/attractions/{locationName}", locationName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(attractions.get(0).getName()));
    }

    @Test
    public void testUpdateShortDescription() throws Exception {
        Long id = 1L;
        String description = "New description";
        Attraction attraction = new Attraction(/* инициализация */);
        when(attractionService.updateAttraction(id, description)).thenReturn(Optional.of(attraction));

        mockMvc.perform(put("/attractions/{id}/description", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(description))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(attraction.getName()));
    }

    @Test
    public void testUpdateShortDescriptionNotFound() throws Exception {
        Long id = 1L;
        String description = "New description";

        when(attractionService.updateAttraction(id, description)).thenReturn(Optional.empty());

        mockMvc.perform(put("/attractions/{id}/description", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(description))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteAttraction() throws Exception {
        Long id = 1L;

        doNothing().when(attractionService).deleteAttraction(id);

        mockMvc.perform(delete("/attractions/{id}", id))
                .andExpect(status().isOk());
    }
}
