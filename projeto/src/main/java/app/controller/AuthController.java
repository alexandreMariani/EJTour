package app.controller;

import app.controller.dto.LoginRequest;
import app.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/auth") // Rota mais limpa para autenticação
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.OPTIONS }, allowedHeaders = "*")
public class AuthController {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private AppUserService appUserService; // Para buscar o usuário

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Para comparar a senha

    @PostMapping("/token") // Novo endpoint de login
    public ResponseEntity<String> token(@RequestBody LoginRequest loginRequest) {

        // 1. Busca o usuário pelo email
        var user = appUserService.findByEmail(loginRequest.email());

        // 2. Verifica se o usuário existe e se a senha está correta (usando BCrypt)
        if (user == null || !passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            return new ResponseEntity<>("Email ou senha incorretos", HttpStatus.UNAUTHORIZED);
        }

        // 3. Geração do JWT
        // Define o tempo de expiração para 1 hora (60 minutos)
        Instant now = Instant.now();
        long expiry = 3600L;

        // Cria a lista de roles (usando o papel do usuário, se tiver)
        String roles = "ROLE_USER"; // Assumindo um papel padrão, ajuste conforme sua entidade

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(user.getEmail()) // O assunto do token é o email do usuário
                .claim("userId", user.getId()) // Adiciona o ID do usuário como um claim extra
                .claim("scope", roles) // Adiciona o escopo/papel
                .build();

        // Codifica e retorna o token
        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(token);
    }
}
