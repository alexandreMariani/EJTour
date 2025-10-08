package app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    
    @Query("SELECT u FROM AppUser u WHERE u.name LIKE %:name%")
    List<AppUser> findByNameContains(String name);
    Optional<AppUser> findByEmail(String name);
    Optional<AppUser> findByName(String name);
}
