package app.auth;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import app.controller.dto.LoginRequest; // Mantendo o DTO se ele existir

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

@RestController
@RequestMapping("/token")
public class TokenController {

    // URL do Keycloak - Assumindo que o Keycloak roda na porta 8080
    private static final String KEYCLOAK_TOKEN_URL = "http://localhost:8080/realms/ejtour/protocol/openid-connect/token";

    // ✅ Usando o Client ID 'site__ejtour'
    private static final String KEYCLOAK_CLIENT_ID = "site__ejtour";

    // Client Secret (Confidential Client, se não for, deixe vazio "")
    private static final String KEYCLOAK_CLIENT_SECRET = "nvl7CoAJsPU1hRiViWMZEc6EyP4VLJIJ";

    @PostMapping
    public ResponseEntity<String> token(@RequestBody LoginRequest loginRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        RestTemplate rt = new RestTemplate();
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("grant_type", "password");
        formData.add("client_id", KEYCLOAK_CLIENT_ID);
        formData.add("username", loginRequest.getEmail());
        formData.add("password", loginRequest.getPassword());
        formData.add("scope", "openid profile email");

        if (!KEYCLOAK_CLIENT_SECRET.isEmpty()) {
            formData.add("client_secret", KEYCLOAK_CLIENT_SECRET);
        }

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);

        try {
            // Tenta obter o token do Keycloak
            ResponseEntity<String> result = rt.postForEntity(KEYCLOAK_TOKEN_URL, entity, String.class);
            return result;

        } catch (HttpClientErrorException e) {
            // Captura erros 4xx (como credenciais inválidas) do Keycloak
            System.err.println(
                    "Falha de autenticação Keycloak (" + e.getStatusCode() + "): " + e.getResponseBodyAsString());
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());

        } catch (ResourceAccessException e) {
            // Captura falhas de conexão
            System.err.println(
                    "ERRO DE CONEXÃO: Keycloak inacessível em " + KEYCLOAK_TOKEN_URL + ". Verifique a URL e a porta.");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("{\"error\": \"Falha ao comunicar com o servidor Keycloak. (Conexão)\"}");

        } catch (Exception e) {
            // Captura qualquer outro erro inesperado
            System.err.println("Erro inesperado no TokenController: " + e.getMessage());
            e.printStackTrace(); // Imprime o Stack Trace para depuração
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Erro interno inesperado no proxy do token.\"}");
        }
    }
}
