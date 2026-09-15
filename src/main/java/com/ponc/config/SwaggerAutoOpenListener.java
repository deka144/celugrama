package com.ponc.config;




import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

// Desktop y URI son clases del propio JDK: no hace falta agregar
// ninguna dependencia nueva en el pom.xml para usarlas
import java.awt.Desktop;
import java.net.URI;

// Escucha "ApplicationReadyEvent": Spring Boot lo dispara automaticamente
// apenas la app termina de arrancar y ya puede recibir peticiones.
// En ese momento abre el navegador del sistema en la pagina de Swagger.
@Component
public class SwaggerAutoOpenListener {

    // Environment es el objeto de Spring que da acceso a las propiedades
    // del application.properties (por ejemplo server.port)
    private final Environment environment;

    // Lee la propiedad "app.abrir-swagger" del application.properties.
    // El ":true" despues de los dos puntos es el valor por defecto si esa
    // propiedad no existe todavia, por eso funciona sin que agregues nada.
    // Para desactivarlo (ej: en un servidor sin entorno grafico), agrega
    // app.abrir-swagger=false  en el application.properties de ese entorno
    @Value("${app.abrir-swagger:true}")
    private boolean abrirSwagger;

    // Constructor: Spring lo llama automaticamente al crear este
    // componente y le inyecta el Environment (igual que inyecta
    // los repos en tus clases *ServiceImpl)
    public SwaggerAutoOpenListener(Environment environment) {
        this.environment = environment;
    }

    // @EventListener + ApplicationReadyEvent.class = este metodo se
    // ejecuta SOLO, apenas la aplicacion queda lista para recibir
    // peticiones. No hay que llamarlo desde ningun otro lado.
    @EventListener(ApplicationReadyEvent.class)
    public void abrirSwaggerEnElNavegador() {

        // Si la bandera quedo en false, corta aqui y no hace nada mas
        if (!abrirSwagger) {
            return;
        }

        // Arma la misma URL que usas manualmente en el Paso 5,
        // leyendo el puerto real (usa 8080 si no hay otro configurado)
        String port = environment.getProperty("server.port", "8080");
        String url = "http://localhost:" + port + "/swagger-ui/index.html";

        // Todo el intento de abrir el navegador queda protegido: si algo
        // falla, no se cae la aplicacion (se atrapa en el catch de abajo)
        try {

            // Camino normal: le pregunta al sistema operativo si puede
            // abrir una URL con el navegador por defecto
            // (en Mac y Windows normalmente responde que si)
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            } else {

                // Respaldo para cuando Desktop no esta soportado
                // (pasa en algunos entornos Linux sin interfaz grafica)
                String os = System.getProperty("os.name").toLowerCase();

                // Mismo comando que usarias a mano en la terminal de Mac
                if (os.contains("mac")) {
                    Runtime.getRuntime().exec(new String[]{"open", url});

                    // Comando equivalente en CMD de Windows
                } else if (os.contains("win")) {
                    Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", url});

                    // Comando estandar en Linux
                } else {
                    Runtime.getRuntime().exec(new String[]{"xdg-open", url});
                }
            }
        } catch (Exception e) {

            // Si algo fallo (ej: servidor sin entorno grafico), solo se
            // imprime un aviso en consola; la app sigue funcionando normal
            System.out.println("No se pudo abrir el navegador automaticamente: " + e.getMessage());
        }
    }
}
