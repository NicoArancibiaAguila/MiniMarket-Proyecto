import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	http
			.csrf(AbstractHttpConfigurer::disable) // Desactiva CSRF para pruebas locales
			.authorizeHttpRequests(auth -> auth
					.anyRequest().permitAll() // Permite el tráfico libre en desarrollo
			);
	return http.build();
}


void main() {
}