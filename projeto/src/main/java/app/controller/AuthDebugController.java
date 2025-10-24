package app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller de debug para testar autenticação e autorização
 */
@RestController
@RequestMapping("/api/debug")
public class AuthDebugController {

    private static final Logger logger = LoggerFactory.getLogger(AuthDebugController.class);

    /**
     * Endpoint protegido que retorna informações sobre o usuário autenticado
     */
    @GetMapping("/me")
    public Map<String, Object> getCurrentUser() {
        logger.info("🔍 ═══════════════════════════════════════════");
        logger.info("🔍 Endpoint /api/debug/me chamado");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Map<String, Object> response = new HashMap<>();

        if (authentication == null) {
            logger.warn("❌ Authentication é NULL!");
            response.put("authenticated", false);
            response.put("error", "No authentication found in SecurityContext");
            return response;
        }

        logger.info("✅ Authentication encontrada: {}", authentication.getClass().getName());
        logger.info("  👤 Principal: {}", authentication.getPrincipal());
        logger.info("  🎭 Authorities: {}", authentication.getAuthorities());
        logger.info("  ✅ Is Authenticated: {}", authentication.isAuthenticated());

        response.put("authenticated", authentication.isAuthenticated());
        response.put("username", authentication.getName());
        response.put("authorities", authentication.getAuthorities().stream()
                .map(Object::toString)
                .collect(Collectors.toList()));
        response.put("authenticationType", authentication.getClass().getSimpleName());

        // Se for JwtAuthenticationToken, adiciona informações do JWT
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
            Jwt jwt = jwtAuth.getToken();

            logger.info("🔑 JWT Token detectado");
            logger.info("  📋 Subject: {}", jwt.getSubject());
            logger.info("  📋 Issuer: {}", jwt.getIssuer());
            logger.info("  📋 Claims: {}", jwt.getClaims().keySet());

            response.put("jwtSubject", jwt.getSubject());
            response.put("jwtIssuer", jwt.getIssuer() != null ? jwt.getIssuer().toString() : null);
            response.put("jwtClaims", jwt.getClaims().keySet());

            // Adiciona algumas claims interessantes
            Map<String, Object> claims = new HashMap<>();
            claims.put("email", jwt.getClaimAsString("email"));
            claims.put("preferred_username", jwt.getClaimAsString("preferred_username"));
            claims.put("given_name", jwt.getClaimAsString("given_name"));
            claims.put("family_name", jwt.getClaimAsString("family_name"));
            response.put("userInfo", claims);
        }

        logger.info("✅ Resposta gerada com sucesso");
        logger.info("🔍 ═══════════════════════════════════════════");
        return response;
    }

    /**
     * Endpoint para testar acesso com role específica
     * Este endpoint requer a role 'ADMIN'
     */
    @GetMapping("/admin")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> adminOnly() {
        logger.info("🔐 ═══════════════════════════════════════════");
        logger.info("🔐 Endpoint /api/debug/admin chamado");
        logger.info("  ✅ Usuário tem permissão ADMIN");

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Você tem acesso de ADMIN!");
        response.put("username", SecurityContextHolder.getContext().getAuthentication().getName());

        logger.info("🔐 ═══════════════════════════════════════════");
        return response;
    }

    /**
     * Endpoint para testar acesso com role USER
     */
    @GetMapping("/user")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('USER')")
    public Map<String, Object> userOnly() {
        logger.info("🔐 ═══════════════════════════════════════════");
        logger.info("🔐 Endpoint /api/debug/user chamado");
        logger.info("  ✅ Usuário tem permissão USER");

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Você tem acesso de USER!");
        response.put("username", SecurityContextHolder.getContext().getAuthentication().getName());

        logger.info("🔐 ═══════════════════════════════════════════");
        return response;
    }

    /**
     * Endpoint público para teste
     */
    @GetMapping("/public")
    public Map<String, Object> publicEndpoint() {
        logger.info("🌐 ═══════════════════════════════════════════");
        logger.info("🌐 Endpoint /api/debug/public chamado (público)");

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Este é um endpoint público!");
        response.put("timestamp", System.currentTimeMillis());

        logger.info("🌐 ═══════════════════════════════════════════");
        return response;
    }
}