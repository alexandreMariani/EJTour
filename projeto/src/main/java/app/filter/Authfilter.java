package app.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class Authfilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        // Rotas públicas
        if (path.equals("/users/login") || (path.equals("/users") && method.equals("POST"))) return true;
        if (path.equals("/authentication")) return true;

        if (path.matches("/tour(/\\d+)?") && method.equals("GET")) return true;
        if (path.matches("/package(/\\d+)?") && method.equals("GET")) return true;
        if (path.equals("/comments") && method.equals("GET")) return true;


        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.equals("Bearer")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Acesso negado: token ausente ou inválido");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
