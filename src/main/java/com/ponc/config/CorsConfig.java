package com.ponc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Habilita CORS: sin esto, el navegador bloquea las peticiones que tu
// frontend (Angular, u otro) le haga a esta API cuando corren en distinto
// dominio/puerto, aunque el backend responda bien.
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    // Lista de dominios de frontend permitidos, separados por coma.
    // El valor por defecto vive en application.properties, no aqui,
    // igual que hicimos con la conexion a la BD y el abrir-swagger
    @Value("${app.frontend-urls}")
    private String frontendUrls;

    // Spring Boot llama a addCorsMappings() al arrancar PARA PREGUNTARLE
    // A ESTA CLASE que origenes/dominios quiere permitir.
    // @Override verifica que el metodo ya exista en la interfaz, PARA QUE
    // el compilador avise si hay un error de tipeo (en vez de dejarlo pasar)
    @Override
    // "registry" = el formulario en blanco que llenamos aqui abajo
    public void addCorsMappings(CorsRegistry registry) {
        // "http://a,http://b" -> ["http://a", "http://b"]
        String[] origenesPermitidos = frontendUrls.split(",");

        // aplica a todos los endpoints de la API (/personas, /reportes, etc.)
        registry.addMapping("/**")
                // Solo estos origenes pueden llamar a la API
                .allowedOrigins(origenesPermitidos)
                // Verbos permitidos; OPTIONS es obligatorio (el navegador lo
                // manda solo, para "preguntar" si tiene permiso antes)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                // "*" = acepta cualquier cabecera (Content-Type, etc.)
                .allowedHeaders("*")
                // Permite mandar cookies/autenticacion junto con la peticion
                .allowCredentials(true);
    }
}
