package app.controller;

import org.springframework.web.bind.annotation.RestController;

import app.service.AuthenticationService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("authentication")
    public String authenticate(Authentication authentication) {
        return authenticationService.authenticate(authentication);
    }
    



}
