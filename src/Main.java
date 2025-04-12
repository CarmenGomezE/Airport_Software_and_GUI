package src;

import src.airportmanager.*;
import src.airportmanager.gui.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.SwingUtilities;

public class Main {
    private static final String ARCHIVO_ESTADO = "resources/estado_airport_manager.dat";

    public static void main(String[] args) {
        try {
        	vaciarArchivo(ARCHIVO_ESTADO);
            AirportManager manager = AirportManager.cargarEstado(ARCHIVO_ESTADO);
            manager.iniciarAplicacion();

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                    	/** Cuando querais probar una sin pasar por el inicio de sesión ponedle true y false a las demás*/
                        new AirportManagerGUI(manager).setVisible(false);
                        new OperadorGUI(manager).setVisible(true);
                        new GestorGUI(manager).setVisible(false);
                        new ControladorGUI(manager).setVisible(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println("Error al iniciar la interfaz gráfica: " + e.getMessage());
                    }
                }
            });
            
            manager.guardarEstado(ARCHIVO_ESTADO);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al cargar el estado del aeropuerto o iniciar la aplicación: " + e.getMessage());
        }
    }
    
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
