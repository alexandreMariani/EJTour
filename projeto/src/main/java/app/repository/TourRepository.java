package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Tour;

public interface TourRepository extends JpaRepository<Tour, Long> {

}
