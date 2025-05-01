package src;

import src.airportmanager.AirportManager;
import src.gui.*;

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
    private static final String ARCHIVO_ESTADO = "resources/prueba_interfaz.dat";

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
            AirportManager manager = AirportManager.cargarEstado(ARCHIVO_ESTADO);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    manager.guardarEstado(ARCHIVO_ESTADO);
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
}
