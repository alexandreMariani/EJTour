package app.Auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.User;

public interface LoginRepository extends JpaRepository<User, Long>{

	public Optional<User> findByName(String login);
	Optional<User> findByEmail(String email);
	
}
