package app.auth;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.entity.AppUser;

@RestController
@RequestMapping("/token")
public class TokenController {

    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @PostMapping
    public String token(@RequestBody AppUser appuser) {
        logger.info("🔑 ═════════════════════════════════════════════");
        logger.info("🔑 Requisição para obter token do Keycloak");
        logger.info("  👤 Username: {}", appuser.getUsername());
        logger.info("  🔐 Client ID: {}", appuser.getClient_id());

        HttpHeaders headers = new HttpHeaders();
        RestTemplate rt = new RestTemplate();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String clientCredentials = appuser.getClient_id() + ":" + appuser.getClient_secret();
        String encodedCredentials = Base64.getEncoder().encodeToString(clientCredentials.getBytes());
        headers.set("Authorization", "Basic " + encodedCredentials);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", appuser.getClient_id());
        formData.add("username", appuser.getUsername());
        formData.add("password", appuser.getPassword());
        formData.add("grant_type", appuser.getGrant_type());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);

        String url = "http://localhost:8080/realms/ejtour/protocol/openid-connect/token";

        try {
            logger.info("  🌐 Fazendo requisição para: {}", url);
            var response = rt.postForEntity(url, entity, String.class);

            logger.info("  ✅ Token obtido com sucesso!");
            logger.info("  📊 Status: {}", response.getStatusCode());
            logger.info("🔑 ═════════════════════════════════════════════");

            return response.getBody();

        } catch (Exception e) {
            logger.error("  ❌ ERRO ao obter token do Keycloak:");
            logger.error("    Tipo: {}", e.getClass().getName());
            logger.error("    Mensagem: {}", e.getMessage());
            logger.error("🔑 ═════════════════════════════════════════════");
            throw e;
        }
    }

    /**
     * Endpoint de teste para verificar autenticação JWT
     * Rota: GET /token/verify
     */
    @GetMapping("/verify")
    public Map<String, Object> verifyToken() {
        logger.info("🔍 ═══════════════════════════════════════════");
        logger.info("🔍 Endpoint /token/verify chamado");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> response = new HashMap<>();

        if (authentication == null) {
            logger.warn("❌ Authentication é NULL!");
            response.put("authenticated", false);
            response.put("error", "No authentication found");
            return response;
        }

        logger.info("✅ Authentication encontrada: {}", authentication.getClass().getName());
        logger.info("  👤 Principal: {}", authentication.getName());
        logger.info("  🎭 Authorities: {}", authentication.getAuthorities());

        response.put("authenticated", authentication.isAuthenticated());
        response.put("username", authentication.getName());
        response.put("authorities", authentication.getAuthorities().stream()
                .map(Object::toString)
                .collect(Collectors.toList()));
        response.put("authenticationType", authentication.getClass().getSimpleName());

        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
            Jwt jwt = jwtAuth.getToken();

            logger.info("🔑 JWT Token detectado");
            logger.info("  📋 Subject: {}", jwt.getSubject());

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("email", jwt.getClaimAsString("email"));
            userInfo.put("preferred_username", jwt.getClaimAsString("preferred_username"));
            userInfo.put("given_name", jwt.getClaimAsString("given_name"));
            userInfo.put("family_name", jwt.getClaimAsString("family_name"));
            response.put("userInfo", userInfo);
        }

        logger.info("✅ Resposta gerada com sucesso");
        logger.info("🔍 ═══════════════════════════════════════════");
        return response;
    }
}