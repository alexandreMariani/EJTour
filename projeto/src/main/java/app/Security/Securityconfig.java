package app.Security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

@EnableMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class Securityconfig {

    @Value("${jwt.public.key}")
    private RSAPublicKey key;

    @Value("${jwt.private.key}")
    private RSAPrivateKey priv;

    // O CAMPO 'authenticationProvider' FOI REMOVIDO PARA CORRIGIR O ERRO DE
    // DEPENDÊNCIA.

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Desabilita o CSRF (padrão para APIs REST)
                .csrf(csrf -> csrf.disable())

                // 2. Aplica a configuração de CORS (usando o bean corsConfigurationSource
                // definido abaixo)
                .cors(Customizer.withDefaults())

                // 3. Define a política de sessão como STATELESS (sem estado, essencial para
                // JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. Configura as regras de autorização de requisições
                .authorizeHttpRequests(authorize -> authorize
                        // Permite acesso irrestrito aos endpoints de autenticação e registro
                        .requestMatchers("/token", "/usuarios", "/register").permitAll()

                        // Exige autenticação (com JWT) para qualquer outra requisição
                        .anyRequest().authenticated())

                // 5. Habilita o servidor de recurso OAuth2/JWT para validar tokens
                // O Spring Security usa automaticamente o bean jwtDecoder()
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Liberar só do seu frontend
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));

        // Métodos permitidos
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Permitir todos os headers (ou especifique os que usar)
        configuration.setAllowedHeaders(List.of("*"));

        // Se precisar enviar cookies/autenticação junto
        configuration.setAllowCredentials(true);

        // Configura o caminho para aplicar essa configuração (todas as rotas)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(key).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        var jwk = new RSAKey.Builder(key).privateKey(priv).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}
