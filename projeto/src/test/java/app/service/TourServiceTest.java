package app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;

import app.entity.Role;
import app.entity.Tour;
import app.entity.User;
import app.repository.TourRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class TourServiceTest {
    @Mock
    private TourRepository tourRepository;
    private TourService tourService;
    private Validator validator;


    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(tourRepository);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        tourService = new TourService(tourRepository, validator);
    }

    @Test
    @DisplayName("Cena 01 - validando campos obrigatorios")
    void cenario01(){
        Tour tour = new Tour(null, null);
        when(tourRepository.save(any(Tour.class))).thenReturn(tour);
        assertThrows(ConstraintViolationException.class, () -> tourService.postMapping(tour));
    }

    @Test
    @DisplayName("Cena 02 - Lista")
    void cenario02(){
        List<Tour> tours = List.of(
            new Tour("jean", "testestestestes")
        );
        when(tourRepository.findAll()).thenReturn(tours);
        List<Tour> result = tourService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Cena 03 - Buscar por ID inexistente")
    void cenario03(){
        when(tourRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> tourService.findById(999L)); 
    }

    @Test
    @DisplayName("Cena 04 - Buscar por ID existente")
    void cenario04(){
        Tour tour = new Tour("jean", "tsts");
        when(tourRepository.findById(1L)).thenReturn(Optional.of(tour));
        Tour result = tourService.findById(1L);
        assertEquals("jean", result.getName());

    }

    @Test
    @DisplayName("Cena 05 - Atualizar Tour")
    void cenario05(){
        Tour tour = new Tour("cataratas", "aguas e aguas");
        when(tourRepository.save(any(Tour.class))).thenReturn(tour);
        Tour result = tourService.putMapping(tour);
        assertEquals("cataratas", result.getName());
    }


    @Test
    @DisplayName("Cena 06 - Excluir tour")
    void cenario06(){
        when(tourRepository.existsById(1L)).thenReturn(true);
        ResponseEntity<Void> response = tourService.deleteMapping(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Cena 07 - Excluir tour inexistente")
    void cenario07(){
        when(tourRepository.existsById(999L)).thenReturn(false);
        ResponseEntity<Void> response = tourService.deleteMapping(999L);
        assertEquals(404, response.getStatusCodeValue());
    }

}
