package testController;

import app.controller.TourController;
import app.entity.Tour;
import app.service.TourService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TourControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TourService tourService;

    @InjectMocks
    private TourController tourController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tourController).build();
    }

    @Test
    void testFindById() throws Exception {
        Tour tour = new Tour();
        tour.setId(1L);
        tour.setName("Tour 1");
        tour.setDescription("Description 1");

        when(tourService.findById(1L)).thenReturn(tour);

        mockMvc.perform(get("/tour/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Tour 1"))
                .andExpect(jsonPath("$.description").value("Description 1"));
    }

    @Test
    void testFindByIdNotFound() throws Exception {
        when(tourService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/tour/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Tour not found"));
    }

    @Test
    void testFindAll() throws Exception {
        Tour tour1 = new Tour();
        tour1.setId(1L);
        tour1.setName("Tour 1");

        Tour tour2 = new Tour();
        tour2.setId(2L);
        tour2.setName("Tour 2");

        when(tourService.findAll()).thenReturn(List.of(tour1, tour2));

        mockMvc.perform(get("/tour"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tour 1"))
                .andExpect(jsonPath("$[1].name").value("Tour 2"));
    }

    @Test
    void testSave() throws Exception {
        Tour tour = new Tour();
        tour.setId(1L);
        tour.setName("New Tour");
        tour.setDescription("Description");

        when(tourService.postMapping(any(Tour.class))).thenReturn(tour);

        mockMvc.perform(post("/tour")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"New Tour\", \"description\":\"Description\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Tour"));
    }

    @Test
    void testDelete() throws Exception {
        when(tourService.deleteMapping(1L)).thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(delete("/tour/{id}", 1L))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Tour deleted successfully"));
    }

    @Test
    void testEdit() throws Exception {
        Tour tour = new Tour();
        tour.setId(1L);
        tour.setName("Updated Tour");

        when(tourService.putMapping(any(Tour.class))).thenReturn(tour);

        mockMvc.perform(put("/tour")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1, \"name\":\"Updated Tour\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Tour"));
    }
}
