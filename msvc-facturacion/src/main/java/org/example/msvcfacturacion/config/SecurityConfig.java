package org.example.msvcfacturacion.config;





import org.example.msvcfacturacion.filters.HeaderAuthFilter;
import org.example.msvcfacturacion.security.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String secretKey;

    @Autowired
    private HeaderAuthFilter headerAuthFilter; // 👈 Inyectás tu filtro personalizado

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomAccessDeniedHandler accessDeniedHandler) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(accessDeniedHandler) // 👈 lo agregás acá
                )
                .addFilterBefore(headerAuthFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class); // 👈 lo agregás antes del filtro estándar

        return http.build();
    }
}
