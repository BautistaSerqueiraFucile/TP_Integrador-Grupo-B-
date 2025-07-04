package org.example.chatbot.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.chatbot.client.OllamaClient;
import org.example.chatbot.dtos.RespuestaApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OllamaService {

    @PersistenceContext // 👉 Inyecta EntityManager para ejecutar consultas SQL nativas.
    private EntityManager entityManager;

    @Autowired
    private OllamaClient ollamaChatClient;

    private final String CONTEXTO_SQL; // 👉 Guarda el esquema SQL como texto.

    private static final Logger log = LoggerFactory.getLogger(OllamaService.class); // 👉 Logger para debug.

    public OllamaService() {
        this.CONTEXTO_SQL = cargarEsquemaSQL("viaje.sql"); // 👉 Constructor: lee el archivo esquema_completo.sql al inicializar.
    }

    private String cargarEsquemaSQL(String nombreArchivo) { // 👉 Método para leer el archivo del classpath como String.
        try (InputStream inputStream = new ClassPathResource(nombreArchivo).getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el archivo SQL desde resources: " + e.getMessage(), e);
        }
    }

    public ResponseEntity<?> procesarPrompt(String promptUsuario) {
        try {
            String promptFinal = """
                Este es el esquema de mi base de datos MySQL:                
                %s                
                Basándote exclusivamente en este esquema, devolveme únicamente una consulta SQL completa y válida (sin explicaciones, sin markdown, sin texto adicional), que responda a lo siguiente:
                %s
                """.formatted(CONTEXTO_SQL, promptUsuario);

            System.out.println(promptFinal);
//            log.info("==== PROMPT ENVIADO A LA IA ====");
//            log.info("\n{}", promptFinal);

            String respuestaIa = ollamaChatClient.preguntar(promptFinal);
            //log.info("==== PROMPT COMPLETO ====\n" + CONTEXTO_SQL + "\n---\n" + promptFinal);
            log.info("==== RESPUESTA IA ====\n" + respuestaIa);

            // Extraer SQL de la respuesta
            String sql = extraerConsultaSQL(respuestaIa);  // 👉 Intenta extraer la consulta SQL de la respuesta. Si no la encuentra, devuelve 400.
            if (sql == null || sql.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new RespuestaApi<>(false, "No se encontró una consulta SQL válida en la respuesta de la IA.", null));
            }

            System.out.println("-----------------------");
            System.out.println("consulta extraida: \n" +sql);
            System.out.println("-----------------------");

            // Ejecutar la consulta
            try {
                List<Object[]> resultados = entityManager.createNativeQuery(sql).getResultList(); // 👉 Ejecuta la consulta SQL con JPA nativo:
                return ResponseEntity.ok(new RespuestaApi<>(true, "Consulta ejecutada con éxito", resultados));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new RespuestaApi<>(false, "Error al ejecutar la consulta: " + e.getMessage(), null));
            }

        } catch (Exception e) {
            return new ResponseEntity<>(new RespuestaApi<>(false, "Error al procesar el prompt: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String extraerConsultaSQL(String respuesta) { // 👉 Usa regex para extraer solo SELECT
        Pattern pattern = Pattern.compile("(?i)(SELECT\\s+.*?;)", Pattern.DOTALL); // solo acepta consultas que empiecen con SELECT
        Matcher matcher = pattern.matcher(respuesta);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }

}
