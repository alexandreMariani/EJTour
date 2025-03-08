package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
