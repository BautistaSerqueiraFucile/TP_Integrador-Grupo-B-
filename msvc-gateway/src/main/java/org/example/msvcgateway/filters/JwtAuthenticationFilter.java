package org.example.msvcgateway.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        System.out.println("🧪 Filtro JWT ejecutado - PATH: " + path);

        // ✅ Rutas públicas que no requieren autenticación
        if (path.startsWith("/auth") ) {
            System.out.println("✅ Ruta pública: " + path);
            return chain.filter(exchange);
        }

        // 🔐 Validar encabezado Authorization
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("⛔ Falta el token o formato inválido");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7); // Quitar "Bearer "

        Claims claims;
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            System.out.println("⛔ Token inválido: " + e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Extraer datos del token
        String role = claims.get("role", String.class);
        String username = claims.getSubject();

        System.out.println("🔐 Token válido - Usuario: " + username + " | Rol: " + role);
        System.out.println("📤 Propagando headers:");
        System.out.println("    - Authorization: " + authHeader);
        System.out.println("    - X-User: " + username);
        System.out.println("    - X-Role: ROLE_" + role.toUpperCase());

        // Reenviar datos al microservicio destino
        ServerHttpRequest modifiedRequest = request.mutate()
                .header("X-User", username)
                .header("X-Role", "ROLE_" + role.toUpperCase())
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    @Override
    public int getOrder() {
        return -1; // Se ejecuta antes que otros filtros
    }
}
