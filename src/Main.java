package src;

import src.airportmanager.*;
import src.airportmanager.app.AirportManager;
import src.gui.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.SwingUtilities;

/**
 * La clase principal del programa. Es el punto de entrada de la aplicación.
 * 
 * Se encarga de cargar el estado del aeropuerto desde un archivo, iniciar
 * la aplicación y mostrar la interfaz gráfica de usuario. También asegura
 * que el estado del aeropuerto se guarde automáticamente cuando la aplicación
 * se cierra.
 * 
 * @author Carmen Gómez, Claudia Municio, María Pozo.
 */
public class Main {
    /** Ruta al archivo donde se guarda el estado del aeropuerto */
    private static final String ARCHIVO_ESTADO = "resources/estado_airport_manager.dat";

    /**
     * Constructor de la clase principal.
     */
    public Main() {
        /** Constructor vacío */
    }

    /**
     * Método principal que ejecuta la aplicación.
     * 
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        try {
            /** vaciarArchivo(ARCHIVO_ESTADO); */
            AirportManager manager = AirportManager.cargarEstado(ARCHIVO_ESTADO);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    manager.guardarEstado(ARCHIVO_ESTADO);
                    System.out.println("Estado guardado automáticamente al cerrar.");
                } catch (Exception e) {
                    System.err.println("Error al guardar el estado al cerrar: " + e.getMessage());
                }
            }));
            manager.iniciarAplicacion();

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                        new AirportManagerGUI(manager).setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println("Error al iniciar la interfaz gráfica: " + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al cargar el estado del aeropuerto o iniciar la aplicación: " + e.getMessage());
        }
    }

    /**
     * Método para vaciar el archivo de estado del aeropuerto.
     * 
     * Este método elimina el contenido del archivo especificado, pero no
     * elimina el archivo en sí. Útil para reiniciar el estado de la aplicación.
     * 
     * @param archivo Ruta del archivo que se desea vaciar.
     */
    public static void vaciarArchivo(String archivo) {
        try {
            File file = new File(archivo);
            System.out.println("\nIntentando vaciar el archivo: " + file.getAbsolutePath());

            file.getParentFile().mkdirs();

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.close();
                System.out.println("El archivo ha sido vaciado correctamente.");
            }
        } catch (IOException e) {
            System.err.println("Error al vaciar el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
