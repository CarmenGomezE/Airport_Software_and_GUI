package src;

import src.airportmanager.*;
import src.airportmanager.gui.*;
import javax.swing.SwingUtilities;

public class Main {
	private static final String ARCHIVO_ESTADO = "resources/estado_airport_manager.dat";
    private static final String ARCHIVO_AEDESTINO = "resources/aeropuerto_destino.txt";
    
    public static void main(String[] args) {
        AirportManager manager = AirportManager.cargarEstado(ARCHIVO_ESTADO); 
        manager.iniciarAplicacion();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AirportManagerGUI(manager).setVisible(true);
            }
        });
    }
}
