package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.entity.Tour;
import app.service.TourService;

import java.util.List;

@RestController
@RequestMapping(value = "/tour")
@CrossOrigin(origins = "*")
public class TourController {

    @Autowired
    private TourService tourService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Tour tour = tourService.findById(id);
            if (tour != null) {
                return new ResponseEntity<>(tour, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Tour not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching tour: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<Tour> tours = tourService.findAll();
            return new ResponseEntity<>(tours, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching tours: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Tour tour) {
        try {
            Tour savedTour = tourService.postMapping(tour);
            return new ResponseEntity<>(savedTour, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving tour: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            tourService.deleteMapping(id);
            return new ResponseEntity<>("Tour deleted successfully", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting tour: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> edit(@RequestBody Tour tour) {
        try {
            Tour updatedTour = tourService.putMapping(tour);
            return new ResponseEntity<>(updatedTour, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating tour: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
