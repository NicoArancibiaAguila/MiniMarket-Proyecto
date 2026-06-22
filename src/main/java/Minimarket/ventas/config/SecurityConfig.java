package Minimarket.ventas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @Configuration le avisa a Spring que este archivo contiene configuraciones del sistema
@Configuration
// @EnableWebSecurity activa el soporte de seguridad web en este microservicio
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // El constructor inyecta automáticamente el filtro que creamos en el Paso 1
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // @Bean expone esta configuración para que Spring la aplique globalmente
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desactiva CSRF (Protección contra falsificación) ya que trabajamos con APIs REST y Tokens
            .csrf(AbstractHttpConfigurer::disable)
            
            // Configura las políticas de acceso a las URLs
            .authorizeHttpRequests(auth -> auth
                // ¡AQUÍ ESTÁ EL CAMBIO!: Permitimos el acceso libre a la documentación de Swagger
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                ).permitAll()
                
                // Cualquier otra petición HTTP requerirá obligatoriamente que el usuario esté autenticado
                .anyRequest().authenticated()
            )
            
            // Define que el servicio no guardará estados de sesión en el servidor (Arquitectura Stateless de Microservicios)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // ¡ESTA ES LA LÍNEA CLAVE!: Agrega nuestro filtro personalizado justo antes 
            // de que el filtro de seguridad por defecto de Spring intente bloquear al usuario.
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Retorna la cadena de seguridad armada
        return http.build();
    }
}