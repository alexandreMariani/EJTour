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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import app.Security.KeycloakRoleConverter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Enumeration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
@org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
public class Securityconfig {

    private static final Logger logger = LoggerFactory.getLogger(Securityconfig.class);

    // Configurações do OAuth2 Resource Server para proteger os endpoints
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("🔧 Configurando SecurityFilterChain");

        http
                // Adiciona o filtro de debug ANTES do filtro de autenticação
                .addFilterBefore(new RequestLoggingFilter(), BasicAuthenticationFilter.class)
                // 1. ATIVA O CORS DEFINIDO ABAIXO
                .cors(Customizer.withDefaults())
                .csrf(csrf -> {
                    logger.debug("🔒 CSRF desabilitado para API REST");
                    csrf.disable();
                })
                .authorizeHttpRequests(auth -> {
                    logger.info("📋 Configurando regras de autorização:");
                    logger.info("  ✅ /token - Acesso público");
                    logger.info("  ✅ /usuarios/novo - Acesso público");
                    logger.info("  🔐 Demais endpoints - Requerem autenticação");

                    auth
                            .requestMatchers("/token").permitAll()
                            .requestMatchers("/usuarios/novo").permitAll()
                            .anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 -> {
                    logger.info("🔑 Configurando OAuth2 Resource Server com JWT");
                    oauth2.jwt(jwt -> {
                        logger.debug("⚙️  Aplicando JwtAuthenticationConverter customizado");
                        jwt.jwtAuthenticationConverter(jwtAuthenticationConverter());
                    });
                });

        logger.info("✅ SecurityFilterChain configurado com sucesso");
        return http.build();
    }

    // Bean para configurar o CORS globalmente (necessário para front-end)
    @Bean
    public CorsFilter corsFilter() {
        logger.info("🌐 Configurando CORS Filter");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setMaxAge(28800L);

        logger.info("  ✅ Origem permitida: http://localhost:4200");
        logger.info("  ✅ Credentials permitidos: true");
        logger.info("  ✅ Todos os headers e métodos permitidos");
        logger.info("  ⏱️  MaxAge: 28800s (8 horas)");

        source.registerCorsConfiguration("/**", config);

        logger.info("✅ CORS Filter configurado com sucesso");
        return new CorsFilter(source);
    }

    /**
     * Configura o conversor de JWT que será usado pelo Spring Security.
     */
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        logger.info("🔄 Criando JwtAuthenticationConverter");

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();

        KeycloakRoleConverter roleConverter = new KeycloakRoleConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(roleConverter);
        logger.info("  ✅ KeycloakRoleConverter configurado");

        jwtConverter.setPrincipalClaimName("preferred_username");
        logger.info("  ✅ Principal claim: preferred_username");

        return jwtConverter;
    }

    /**
     * Filtro customizado para logar todas as requisições e verificar autenticação
     */
    private static class RequestLoggingFilter extends OncePerRequestFilter {
        private static final Logger filterLogger = LoggerFactory.getLogger(RequestLoggingFilter.class);

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                FilterChain filterChain) throws ServletException, IOException {

            String method = request.getMethod();
            String uri = request.getRequestURI();
            String queryString = request.getQueryString();

            filterLogger.info("════════════════════════════════════════════════════════");
            filterLogger.info("🔍 REQUISIÇÃO RECEBIDA:");
            filterLogger.info("  📍 Método: {}", method);
            filterLogger.info("  📍 URI: {}", uri);
            if (queryString != null) {
                filterLogger.info("  📍 Query: {}", queryString);
            }

            // Log todos os headers
            filterLogger.info("  📨 HEADERS:");
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);

                // Mascara o token para não expor no log
                if ("authorization".equalsIgnoreCase(headerName) && headerValue != null) {
                    if (headerValue.startsWith("Bearer ")) {
                        String token = headerValue.substring(7);
                        String maskedToken = token.length() > 20
                                ? token.substring(0, 10) + "..." + token.substring(token.length() - 10)
                                : "***";
                        filterLogger.info("    🔑 {}: Bearer {}", headerName, maskedToken);
                    } else {
                        filterLogger.info("    🔑 {}: {}", headerName, headerValue);
                    }
                } else {
                    filterLogger.info("    📋 {}: {}", headerName, headerValue);
                }
            }

            // Verifica se há token de autorização
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null) {
                filterLogger.warn("  ⚠️  NENHUM HEADER DE AUTORIZAÇÃO ENCONTRADO!");
            } else if (!authHeader.startsWith("Bearer ")) {
                filterLogger.warn("  ⚠️  Header de autorização não é Bearer token: {}", authHeader);
            } else {
                filterLogger.info("  ✅ Bearer token presente");
            }

            try {
                // Executa o filtro
                filterChain.doFilter(request, response);

                // Log após processamento
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();

                filterLogger.info("  📤 RESPOSTA:");
                filterLogger.info("    Status: {}", response.getStatus());

                if (auth != null && auth.isAuthenticated()) {
                    filterLogger.info("    ✅ AUTENTICADO:");
                    filterLogger.info("      👤 Principal: {}", auth.getName());
                    filterLogger.info("      🎭 Authorities: {}", auth.getAuthorities());
                    filterLogger.info("      🔐 Tipo: {}", auth.getClass().getSimpleName());
                } else if (auth == null) {
                    filterLogger.warn("    ❌ SEM AUTENTICAÇÃO (Authentication é null)");
                } else {
                    filterLogger.warn("    ❌ NÃO AUTENTICADO (isAuthenticated=false)");
                }

            } catch (Exception e) {
                filterLogger.error("  ❌ ERRO DURANTE PROCESSAMENTO:");
                filterLogger.error("    Tipo: {}", e.getClass().getName());
                filterLogger.error("    Mensagem: {}", e.getMessage());

                // Se for erro de autenticação, loga detalhes
                if (e.getMessage() != null && e.getMessage().contains("JWT")) {
                    filterLogger.error("    🔴 ERRO RELACIONADO A JWT!");
                }

                throw e;
            } finally {
                filterLogger.info("════════════════════════════════════════════════════════");
            }
        }
    }
}