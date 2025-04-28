package app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entity.Tour;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findByDescription(String description);
    List<Tour> findAll();
    List<Tour> findByNameContains(String name);
    Optional<Tour> findByName(String name);
}
