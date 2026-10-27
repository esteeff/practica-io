import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ManipularDirectorios {
    public static void main(String[] args) {
        try {
            
            Path directorio = Path.of("salida", "reportes", "procesados");
            Files.createDirectories(directorio);
            System.out.println("Directorio creado: " + directorio);

            
            Path archivo = directorio.resolve("resultado.txt");
            if (!Files.exists(archivo)) {
                Files.createFile(archivo);
                System.out.println("Archivo creado: " + archivo);
            }

            
            Path temporal = Files.createTempFile("dsi-", ".tmp");
            System.out.println("Archivo temporal: " + temporal);

            Path directorioTemporal = Files.createTempDirectory("dsi-");
            System.out.println("Directorio temporal: " + directorioTemporal);

        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }
    }
}