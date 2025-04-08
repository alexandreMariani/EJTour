package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entity.Tour;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findByDescription(String description);
    List<Tour> findAll();
}
