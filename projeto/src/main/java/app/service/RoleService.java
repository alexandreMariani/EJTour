package app.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Role;
import app.repository.RoleRepository;
import jakarta.validation.Validator;


@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    private Validator validator;

    public RoleService(RoleRepository roleRepository, Validator validator) {
        this.roleRepository = roleRepository;
        this.validator = validator;
    }
    public Role findById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            throw new RuntimeException("permissao não encontrado!");
        }
        return role.get();
    }

    public Role postMapping(Role role){
        Role post = roleRepository.save(role);
        return post;
    }

}
