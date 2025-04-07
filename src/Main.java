package src;

import src.airportmanager.*;
import src.airportmanager.gui.*;
import javax.swing.SwingUtilities;

public class Main {
    private static final String ARCHIVO_ESTADO = "resources/estado_airport_manager.dat";

    public static void main(String[] args) {
        try {
            AirportManager manager = AirportManager.cargarEstado(ARCHIVO_ESTADO);
            manager.iniciarAplicacion();

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                    	/** Cuando querais probar una sin pasar por el inicio de sesión ponedle true y false a las demás*/
                        new AirportManagerGUI(manager).setVisible(true);
                        new OperadorGUI(manager).setVisible(false);
                        new GestorGUI(manager).setVisible(false);
                        new ControladorGUI(manager).setVisible(false);
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
