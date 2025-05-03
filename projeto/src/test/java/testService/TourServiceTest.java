
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import app.entity.Tour;
import app.repository.TourRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import app.service.TourService;

@ExtendWith(MockitoExtension.class)
public class TourServiceTest {

    @Mock
    private TourRepository tourRepository;

    @InjectMocks
    private TourService tourService;

    @Test
    void testFindAll() {
        Tour tour = new Tour();
        when(tourRepository.findAll()).thenReturn(Arrays.asList(tour));

        List<Tour> result = tourService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void testFindById() {
        Tour tour = new Tour();
        when(tourRepository.findById(1L)).thenReturn(Optional.of(tour));

        Tour result = tourService.findById(1L);

        assertEquals(tour, result);
    }

    @Test
    void testFindByIdNotFound() {
        when(tourRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> tourService.findById(1L));
    }

    @Test
    void testPostMapping() {
        Tour tour = new Tour();
        when(tourRepository.save(tour)).thenReturn(tour);

        Tour result = tourService.postMapping(tour);

        assertEquals(tour, result);
    }

    @Test
    void testPutMapping() {
        Tour tour = new Tour();
        when(tourRepository.save(tour)).thenReturn(tour);

        Tour result = tourService.putMapping(tour);

        assertEquals(tour, result);
    }
}
