package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.entity.Role;
import app.service.RoleService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Role role = roleService.findById(id);
            if (role != null) {
                return new ResponseEntity<>(role, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Role not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching role: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Role department) {
        try {
            Role savedRole = roleService.postMapping(department);
            return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving role: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
