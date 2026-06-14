package Minimarket.ventas.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

// @Component le indica a Spring que registre esta clase como un componente del sistema
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Este método se ejecuta automáticamente en cada petición que recibe el microservicio
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        // 1. Extrae la cabecera llamada "Authorization" de la petición HTTP
        String authHeader = request.getHeader("Authorization");

        // 2. Verifica si la cabecera existe y si comienza con la palabra "Bearer " (formato estándar de tokens)
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Recorta la palabra "Bearer " para quedarse únicamente con el texto puro del Token JWT
            String token = authHeader.substring(7);
            
            // -------------------------------------------------------------------
            // NOTA DE DESARROLLO LOCAL: 
            // Aquí simulamos la aprobación del token copiado de 'loginauth'
            // para que no requiera validar firmas complejas en tu PC.
            // -------------------------------------------------------------------
            if (!token.isBlank()) {
                // Creamos una credencial de autenticación ficticia ("user") con roles vacíos
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        "user", null, Collections.emptyList());
                
                // Le inyectamos esta credencial al "contenedor de seguridad" de Spring. 
                // Al ver esto, Spring sabe que el usuario ya está logueado y es válido.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 3. Permite que la petición continúe su viaje hacia el Controlador (Controller) sin detenerse
        filterChain.doFilter(request, response);
    }
}