package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import app.entity.AppUser;
import app.service.AppUserService;

import java.util.List;

@RestController
@RequestMapping(value = "/appUsers")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE,
        RequestMethod.OPTIONS }, allowedHeaders = "*")
public class AppUserController {
    @Autowired
    private AppUserService appUserService;

    @PostMapping("/register")
     public ResponseEntity<?> register(@RequestBody AppUser appUser) {
 AppUser savedAppUser = appUserService.postMapping(appUser);
 return new ResponseEntity<>(savedAppUser, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
     @PreAuthorize("hasRole('admin') or #id == authentication.principal.id") // Permite admin OU o próprio usuário
public ResponseEntity<?> findById(@PathVariable Long id) {
AppUser appUser = appUserService.findById(id);
if (appUser != null) {
return new ResponseEntity<>(appUser, HttpStatus.OK);
} else {
return new ResponseEntity<>("AppUser not found", HttpStatus.NOT_FOUND);
          }
    }

    @GetMapping
    @PreAuthorize("hasRole('admin')") // Apenas administradores podem listar todos
    public ResponseEntity<?> findAll() {
        List<AppUser> appUsers = appUserService.findAll();
        return new ResponseEntity<>(appUsers, HttpStatus.OK);
    }

    @PostMapping
@PreAuthorize("hasRole('admin')")
public ResponseEntity<?> save(@RequestBody AppUser appUser) {
 AppUser savedAppUser = appUserService.postMapping(appUser);
return new ResponseEntity<>(savedAppUser, HttpStatus.CREATED);
    }

      

    @DeleteMapping(value = "/{id}")
@PreAuthorize("hasRole('admin')")
public ResponseEntity<?> delete(@PathVariable Long id) {
appUserService.deleteMapping(id);
 return new ResponseEntity<>("AppUser deleted successfully", HttpStatus.NO_CONTENT);
    }

      

    @PutMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> edit(@RequestBody AppUser appUser) {
        AppUser updatedAppUser = appUserService.putMapping(appUser);
        return new ResponseEntity<>(updatedAppUser, HttpStatus.OK);
    }
