package app.Security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Converte as claims de 'realm_access' do Keycloak (que contém as roles) em
 * objetos GrantedAuthority utilizáveis pelo Spring Security.
 */
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        // O Keycloak armazena as roles na claim 'realm_access' como um Map.
        Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

        if (realmAccess == null || realmAccess.isEmpty()) {
            // Retorna um conjunto vazio se a claim 'realm_access' não existir
            return new HashSet<>();
        }

        // As roles estão dentro de 'realm_access' sob a chave 'roles' como uma lista
        List<String> roles = (List<String>) realmAccess.get("roles");

        if (roles == null) {
            return new HashSet<>();
        }

        // Mapeia cada string de role para um objeto SimpleGrantedAuthority
        // e adiciona o prefixo "ROLE_" (padrão do Spring Security)
        Set<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toSet());

        return authorities;
    }
}
