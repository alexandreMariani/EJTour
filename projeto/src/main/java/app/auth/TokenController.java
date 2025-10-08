package app.auth;

import app.entity.AppUser;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/token")
public class TokenController {

    // Verifique se a URL e a porta (8088) do Keycloak estão corretas!
    private static final String KEYCLOAK_TOKEN_URL = "http://localhost:8088/realms/ejtour/protocol/openid-connect/token";

    // Substitua 'my-keycloak-client-id' pelo ID do cliente que você configurou no
    // Keycloak
    private static final String KEYCLOAK_CLIENT_ID = "site_ejtour";

    @PostMapping
    public ResponseEntity<String> token(@RequestBody AppUser user) {

        // O Keycloak espera que o corpo da requisição seja do tipo
        // application/x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        RestTemplate rt = new RestTemplate();

        // Montagem dos parâmetros necessários para o Keycloak (OAuth 2.0 / OIDC)
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        // 1. OBRIGATÓRIO: Tipo de concessão (Password Grant)
        formData.add("grant_type", "password");

        // 2. OBRIGATÓRIO: Client ID configurado no Keycloak
        formData.add("client_id", KEYCLOAK_CLIENT_ID);

        // 3. OBRIGATÓRIO: Credenciais do Usuário. O Keycloak geralmente usa 'username'
        // Estou usando o email como 'username', que é um padrão comum
        formData.add("username", user.getEmail());
        formData.add("password", user.getPassword());

        // Note que campos como 'id' e 'birthday' não são enviados para a API de token
        // do Keycloak.

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);

        try {
            // Chamada à API de token do Keycloak
            ResponseEntity<String> result = rt.postForEntity(KEYCLOAK_TOKEN_URL, entity, String.class);

            // Retorna o resultado (token, etc.) que veio do Keycloak
            return result;

        } catch (Exception e) {
            // Este bloco trata erros de conexão (como Keycloak desligado) ou credenciais
            // inválidas.
            System.err.println("Erro ao comunicar com o Keycloak: " + e.getMessage());

            // Retorna um erro 500 ou 401, dependendo da exceção (adapte conforme
            // necessário)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Falha na autenticação ou na comunicação com o servidor.\"}");
        }
    }
}
