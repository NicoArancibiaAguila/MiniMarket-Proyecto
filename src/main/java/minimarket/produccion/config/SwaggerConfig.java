package minimarket.produccion.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(
                        new Info()
                                .title("MiniMarket API - Producción Service")
                                .version("1.0.0")
                                .description("""
                                        
                                        Microservicio encargado de la gestión de producción.
                                        
                                        Funcionalidades:
                                        • Crear lotes de producción
                                        • Consultar lotes
                                        • Buscar lotes por ID
                                        • Eliminar lotes
                                        • Sincronización con Inventario
                                        
                                        Proyecto Full Stack 1
                                        Duoc UC
                                        """)
                )
                //  Habilita el botón "Authorize" globalmente en la interfaz de Swagger
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}