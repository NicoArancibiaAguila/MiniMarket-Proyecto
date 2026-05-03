package com.example.loginauth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration    //clase de configuracion, como para registrar "herramientas" que otros componentes como AuthService usen mas adelante
@EnableWebSecurity   //Interruptor, practicamente le dice a Spring "desactiva la seguridad y activa config personalizada que voy a escribir en esta clase"
public class SecurityConfig {
    
    //Bean en le dice a Spring "ejecuta este metodo y guarda el objeto q retorna en tu memoria"
    @Bean
    //este metodo define el filtro por el que debe pasar cada peticion HTTP que llegue a puerto 8081
    // con HttpSecurity http como objeto que arma las reglas de seguridad
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            //desactiva el Cross-Site Request Forgery - proteccion para formularios HTML clasicos
            .csrf(csrf -> csrf.disable())
            //se configura la sesion como STATELESS (sin estado)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            //aqui se define quien pasa y quien no
            .authorizeHttpRequests(auth -> auth          
                //aquie pasa quien empieza con "/api/auth/" sin preguntar nada
                .requestMatchers("/api/auth/**").permitAll()
                //aca si exige ser identificado por cualquier otra ruta
                .requestMatchers("/api/auth").permitAll()
                .anyRequest().authenticated()
            );

        // cierre de configuracion y se entrega el objeto terminado
        return http.build();
    }

    @Bean
    //encripta contraseña
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
