package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.entity.User;
import app.service.UserService;

import java.util.List;

@RestController
@CrossOrigin(
  origins = "*",
  methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
  allowedHeaders = "*"
)
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
            User user = userService.findById(id);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        User user = userService.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Email ou senha incorretos", HttpStatus.UNAUTHORIZED);
        }

}

    @GetMapping
    public ResponseEntity<?> findAll() {
            List<User> users = userService.findAll();
            return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @GetMapping("")
    public ResponseEntity<?> searchUsers(@RequestParam String name) {

            List<User> users = userService.getUsersByName(name);
            if (users.isEmpty()) {
                return new ResponseEntity<>("No users found with that name", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody User user) {

            User savedUser = userService.postMapping(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
            userService.deleteMapping(id);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.NO_CONTENT);

    }

    @PutMapping
    public ResponseEntity<?> edit(@RequestBody User user) {
            User updatedUser = userService.putMapping(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }
}
