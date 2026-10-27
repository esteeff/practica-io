import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class InformacionArchivo {
    public static void main(String[] args) {
        Path archivo = Path.of("datos", "mensaje.txt");

        System.out.println("Existe: " + Files.exists(archivo));
        System.out.println("Es archivo: " + Files.isRegularFile(archivo));
        System.out.println("Se puede leer: " + Files.isReadable(archivo));

        try {
            System.out.println("Tamaño: " + Files.size(archivo) + " bytes");
            System.out.println("Última modificación: " + Files.getLastModifiedTime(archivo));
        } catch (IOException e) {
            System.err.println("No fue posible consultar el archivo.");
        }
    }
}
