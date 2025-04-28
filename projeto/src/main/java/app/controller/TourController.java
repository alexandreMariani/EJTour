package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.entity.Tour;
import app.service.TourService;

import java.util.List;

@RestController
@CrossOrigin(
  origins = "*",
  methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
  allowedHeaders = "*"
)
@RequestMapping(value = "/tour")
public class TourController {

    @Autowired
    private TourService tourService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
            Tour tour = tourService.findById(id);
            if (tour != null) {
                return new ResponseEntity<>(tour, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Tour not found", HttpStatus.NOT_FOUND);
            }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
            List<Tour> tours = tourService.findAll();
            return new ResponseEntity<>(tours, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Tour tour) {
            Tour savedTour = tourService.postMapping(tour);
            return new ResponseEntity<>(savedTour, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
            tourService.deleteMapping(id);
            return new ResponseEntity<>("Tour deleted successfully", HttpStatus.NO_CONTENT);
        
    }

    @PutMapping
    public ResponseEntity<?> edit(@RequestBody Tour tour) {
            Tour updatedTour = tourService.putMapping(tour);
            return new ResponseEntity<>(updatedTour, HttpStatus.OK);
    }
}
