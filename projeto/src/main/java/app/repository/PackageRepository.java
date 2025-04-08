package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import app.entity.Package;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    List<Package> findById(int id);
    List<Package> findAll();
}
