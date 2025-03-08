package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import app.entity.Tour;
import app.repository.TourRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TourService{

    @Autowired
    private TourRepository tourRepository;

    public List<Tour> findAll() {
        return tourRepository.findAll();
    }

    public Tour findById(Long id) {
        Optional<Tour> tuor = tourRepository.findById(id);
        if (tuor.isEmpty()) {
            throw new RuntimeException("Passeio não encontrado!");
        }
        return tuor.get();
    }

    public Tour postMapping(Tour tour) {
        Tour post = tourRepository.save(tour);
        return post;

    }

    public ResponseEntity<Void> deleteMapping(Long id) {

        if (!tourRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        tourRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public Tour putMapping(Tour tour) {
        Tour put = tourRepository.save(tour);
        return put;
    }

}
