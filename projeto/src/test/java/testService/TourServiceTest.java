// package testService;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.when;

// import app.entity.Tour;
// import app.repository.TourRepository;
// import app.service.TourService;

// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;

// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import java.util.Arrays;
// import java.util.List;
// import java.util.Optional;

// @ExtendWith(MockitoExtension.class)
// public class TourServiceTest {

//     @Mock
//     private TourRepository tourRepository;

//     @InjectMocks
//     private TourService tourService;

//     @Test
//     @DisplayName("Unit Test - FindAll: deve retornar lista de tours")
//     void testFindAll() {
//         Tour tour = new Tour();
//         when(tourRepository.findAll()).thenReturn(Arrays.asList(tour));

//         List<Tour> result = tourService.findAll();

//         assertEquals(1, result.size());
//     }

//     @Test
//     @DisplayName("Unit Test - FindById: deve retornar tour pelo id")
//     void testFindById() {
//         Tour tour = new Tour();
//         when(tourRepository.findById(1L)).thenReturn(Optional.of(tour));

//         Tour result = tourService.findById(1L);

//         assertEquals(tour, result);
//     }

//     @Test
//     @DisplayName("Unit Test - FindById: deve lançar exceção se tour não encontrado")
//     void testFindByIdNotFound() {
//         when(tourRepository.findById(1L)).thenReturn(Optional.empty());

//         assertThrows(RuntimeException.class, () -> tourService.findById(1L));
//     }

//     @Test
//     @DisplayName("Unit Test - Save (POST): deve salvar tour")
//     void testPostMapping() {
//         Tour tour = new Tour();
//         when(tourRepository.save(tour)).thenReturn(tour);

//         Tour result = tourService.postMapping(tour);

//         assertEquals(tour, result);
//     }

//     @Test
//     @DisplayName("Unit Test - Save (PUT): deve atualizar tour")
//     void testPutMapping() {
//         Tour tour = new Tour();
//         when(tourRepository.save(tour)).thenReturn(tour);

//         Tour result = tourService.putMapping(tour);

//         assertEquals(tour, result);
//     }
// }
