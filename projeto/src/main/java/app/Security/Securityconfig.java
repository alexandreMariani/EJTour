package app.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import app.Security.KeycloakRoleConverter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List; // Import necessário

@Configuration
@EnableWebSecurity
public class Securityconfig {

    // Configurações do OAuth2 Resource Server para proteger os endpoints
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. ATIVA O CORS DEFINIDO ABAIXO
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para APIs REST
                .authorizeHttpRequests(auth -> auth
                        // Permite acesso irrestrito ao endpoint de token (login)
                        .requestMatchers("/token").permitAll()
                        // A URL /usuarios/novo é para cadastro, vamos permitir também.
                        .requestMatchers("/usuarios/novo").permitAll()
                        // Todos os outros endpoints exigem autenticação
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                // Configura o conversor customizado para extrair roles do JWT
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return http.build();
    }

    // Bean para configurar o CORS globalmente (necessário para front-end)
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permite o envio de cookies/autorização, fundamental para o preflight
        config.setAllowCredentials(true);

        // CORRIGIDO: Removido o '*', pois ele é ilegal quando AllowCredentials é
        // 'true'.
        // Devemos listar explicitamente a origem do frontend Angular.
        config.setAllowedOrigins(List.of("http://localhost:4200")); // Usando List.of do Java 9+

        // Permite todos os cabeçalhos e métodos, incluindo o OPTIONS do preflight.
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));

        // Define o tempo que o navegador pode cachear a resposta do preflight (8 horas)
        config.setMaxAge(28800L);

        // Registra a configuração para todos os paths ("/**")
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    /**
     * Configura o conversor de JWT que será usado pelo Spring Security.
     */
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        // 1. Cria a instância do JwtAuthenticationConverter
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();

        // 2. Define o KeycloakRoleConverter como o mapeador de authorities (roles)
        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        // 3. Opcional: define a claim que será usada como principal (nome do usuário
        // logado)
        jwtConverter.setPrincipalClaimName("preferred_username");

        return jwtConverter;
    }
}
