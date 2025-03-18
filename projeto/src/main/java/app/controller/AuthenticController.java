package app.controller;

import app.service.AuthenticService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticController {

    private final AuthenticService authenticService;

    public AuthenticController(AuthenticService authenticService) {
        this.authenticService = authenticService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        return ResponseEntity.ok(authenticService.authenticate(email, password));
    }
}
