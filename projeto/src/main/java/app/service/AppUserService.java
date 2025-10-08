package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import app.entity.AppUser;
import app.repository.AppUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private AppUserRepository AppUserRepository;

    public List<AppUser> findAll() {
        return AppUserRepository.findAll();
    }

    public AppUser findById(Long id) {
        Optional<AppUser> AppUser = AppUserRepository.findById(id);
        if (AppUser.isEmpty()) {
            throw new RuntimeException("AppUser não encontrado!");
        }
        return AppUser.get();
    }

    public AppUser findByEmail(String email) {
        Optional<AppUser> AppUser = AppUserRepository.findByEmail(email);
        if (AppUser.isEmpty()) {
            throw new RuntimeException("Usuário com esse email não encontrado!");
        }
        return AppUser.get();
    }

    public AppUser postMapping(AppUser AppUser) {
        String senhaCriptografada = bcryptEncoder.encode(AppUser.getPassword());
        AppUser.setPassword(senhaCriptografada);
        AppUser post = AppUserRepository.save(AppUser);
        return post;

    }

    public String save(AppUser AppUser) {

        String senhaCriptografada = bcryptEncoder.encode(AppUser.getPassword());
        AppUser.setPassword(senhaCriptografada);

        AppUser post = AppUserRepository.save(AppUser);
        return "Usuario salvo com sucesso";
    }

    public List<AppUser> getAppUsersByName(String name) {
        return AppUserRepository.findByNameContains(name);
    }

    public ResponseEntity<Void> deleteMapping(Long id) {

        if (!AppUserRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        AppUserRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public AppUser putMapping(AppUser AppUser) {
        AppUser put = AppUserRepository.save(AppUser);
        return put;
    }

}
