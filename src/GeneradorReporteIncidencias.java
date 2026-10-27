import java.io.*;
import java.nio.file.*;

public class GeneradorReporteIncidencias {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java GeneradorReporteIncidencias <ruta_carpeta_entrada>");
            return;
        }

        // 2. Crear Path de la carpeta de entrada
        Path carpetaEntrada = Path.of(args[0]);

        // 3. Comprobar existencia
        if (!Files.exists(carpetaEntrada) || !Files.isDirectory(carpetaEntrada)) {
            System.out.println("Error: La carpeta no existe o no es válida: " + carpetaEntrada.toAbsolutePath());
            return;
        }

        try {
            // 5. Crear directorio de salida
            Path directorioSalida = Path.of("mesa-ayuda", "salida", "reportes-consolidados");
            Files.createDirectories(directorioSalida);

            Path rutaReporte = directorioSalida.resolve("reporte-consolidado.txt");
            Path rutaAltas = directorioSalida.resolve("todas-las-altas.txt");

            int totalGlobal = 0;
            int altasGlobal = 0;
            int mediasGlobal = 0;
            int bajasGlobal = 0;
            int archivosProcesados = 0;

            try (
                PrintWriter reporte = new PrintWriter(Files.newBufferedWriter(rutaReporte));
                PrintWriter reporteAltas = new PrintWriter(Files.newBufferedWriter(rutaAltas))
            ) {
                reporte.println("REPORTE CONSOLIDADO DE INCIDENCIAS");
                reporte.println("==================================\n");

                // Buscar solo archivos *.txt usando un filtro glob
                DirectoryStream<Path> stream = Files.newDirectoryStream(carpetaEntrada, "*.txt");
                
                for (Path archivo : stream) {
                    archivosProcesados++;
                    reporte.println(">> Procesando archivo: " + archivo.getFileName());

                    try (BufferedReader lector = Files.newBufferedReader(archivo)) {
                        String linea;
                        while ((linea = lector.readLine()) != null) {
                            if (linea.trim().isEmpty()) continue;
                            
                            String[] partes = linea.split("\\|");
                            if (partes.length == 3) {
                                totalGlobal++;
                                String id = partes[0];
                                String descripcion = partes[1];
                                String prioridad = partes[2];

                                if (prioridad.equals("ALTA")) {
                                    altasGlobal++;
                                    reporteAltas.println(id + "|" + descripcion + "|ALTA|" + archivo.getFileName());
                                } else if (prioridad.equals("MEDIA")) {
                                    mediasGlobal++;
                                } else if (prioridad.equals("BAJA")) {
                                    bajasGlobal++;
                                }
                            }
                        }
                    }
                }

                reporte.println("\n--- RESUMEN GLOBAL ---");
                reporte.println("Archivos procesados: " + archivosProcesados);
                reporte.println("Total de incidencias: " + totalGlobal);
                reporte.println("Prioridad ALTA: " + altasGlobal);
                reporte.println("Prioridad MEDIA: " + mediasGlobal);
                reporte.println("Prioridad BAJA: " + bajasGlobal);
            }

            System.out.println("Reporte consolidado generado en: " + rutaReporte.toAbsolutePath());
            System.out.println("Archivo global de altas generado en: " + rutaAltas.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }
    }
}