package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import app.entity.User;
import app.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    @Autowired
	private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private UserRepository userRepository;


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RuntimeException("user não encontrado!");
        }
        return user.get();
    }

    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new RuntimeException("Usuário com esse email não encontrado!");
        }
        return user.get();
    }

    public User postMapping(User user) {
        String senhaCriptografada = bcryptEncoder.encode(user.getPassword());
		user.setPassword(senhaCriptografada);
        User post = userRepository.save(user);
        return post;

    }

    public String save(User user) {
		
		String senhaCriptografada = bcryptEncoder.encode(user.getPassword());
		user.setPassword(senhaCriptografada);
		
		User post = userRepository.save(user);
		return "Usuario salvo com sucesso";
	}

    public List<User> getUsersByName(String name) {
        return userRepository.findByNameContains(name);  
    }

    public ResponseEntity<Void> deleteMapping(Long id) {

        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public User putMapping(User user) {
        User put = userRepository.save(user);
        return put;
    }

    

}
