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
@RequestMapping(value = "/appuser")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE,
        RequestMethod.OPTIONS }, allowedHeaders = "*")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    // Busca por ID
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

    // ---

    // Busca todos os usuários (Apenas para ADMIN)
    @GetMapping("/findAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AppUser>> findAll() {
        List<AppUser> appUsers = appUserService.findAll();
        return new ResponseEntity<>(appUsers, HttpStatus.OK);
    }

    // ---

    // ---

    // Deleta AppUser por ID (Apenas para ADMIN)
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        appUserService.deleteMapping(id);
        // Retorna 204 No Content, que é o padrão para DELETE bem-sucedido sem corpo de
        // resposta
        return new ResponseEntity<>("AppUser deleted successfully", HttpStatus.NO_CONTENT);
    }

    // ---

    // Edita AppUser (Apenas para ADMIN)
    @PutMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<AppUser> edit(@RequestBody AppUser appUser) {
        AppUser updatedAppUser = appUserService.putMapping(appUser);
        return new ResponseEntity<>(updatedAppUser, HttpStatus.OK);
    }
}