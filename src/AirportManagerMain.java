package src;

import java.util.*;


import src.airportmanager.AirportManager;
import src.airportmanager.avion.*;
import src.airportmanager.pista.*;
import src.airportmanager.factura.*;
import src.airportmanager.vuelo.*;
import src.airportmanager.aeropuerto.*;
import src.airportmanager.exceptions.*;
import src.airportmanager.usuario.*;
import src.airportmanager.terminal.*;
import src.airportmanager.hangar.*;
import src.airportmanager.notificacion.*;
import src.airportmanager.estadisticas.*;

import java.util.*;
import java.io.IOException;
import java.time.*;

/**
 * Clase que implementa un demostrador básico de la aplicación.
 * 
 * @autor Carmen Gómez, Claudia Municio, María Pozo.
 */
public class AirportManagerMain {
	
	/**Nombre del fichero desde el que se carga y donde se guarda el estado de la aplicación*/
    private static final String ARCHIVO_ESTADO = "estado_airport_manager.dat";
    private AirportManager airportManager;
    /**
     * Método principal
     * 
     * @param args		Argumentos recibidos.
     */
    public static void main(String[] args) {
    	AirportManager airportManager = AirportManager.cargarEstado(ARCHIVO_ESTADO); // Intenta cargar el estado

        if (airportManager == null) {
            System.out.println("No se encontró un estado previo. Creando nuevo sistema.");
            airportManager = new AirportManager();
        } else {
            System.out.println("Estado cargado correctamente.");
        }

        airportManager.iniciarAplicacion();

        airportManager.guardarEstado(ARCHIVO_ESTADO);
        System.out.println("Estado guardado. Cerrando aplicación.");
    }
    
    
    public void darDeAltaGestor() {
    	Operador operador = new Operador("Juan Pérez", "12345678A", new Aerolinea("Iberia", "IBE"), airportManager.getAeropuerto(), airportManager.getGestor(), airportManager);
    	 airportManager.getGestor().darDeAltaAerolinea("Iberia", "IB");

        // Dar de alta un operador
    	 airportManager.getGestor().darDeAltaOperador("Carlos López", "87654321B", new Aerolinea("Air France", "AFR"));

        // Dar de alta un controlador
        Terminal terminal = new TerminalPasajeros("T1", new PuertaEmbarque(), 200);
        airportManager.getGestor().darDeAltaControlador("Ana Gómez", "123456789C", terminal);

        // Dar de alta un aeropuerto de destino desde archivo
        try {
        	 airportManager.getGestor().darDeAltaAeropuertoDestino("aeropuerto_destino.txt"); // Este archivo debe existir con datos válidos
        } catch (IOException | TemporadasSolapadasException e) {
            System.err.println("Error al dar de alta aeropuerto destino: " + e.getMessage());
        }

        // Configurar aeropuerto propio
        List<Temporada> temporadas = new ArrayList<>();
        temporadas.add(new Temporada("2025-06-01", "10:00", "20:00", "2025-09-30"));
        try {
        	 airportManager.getGestor().configurarAeropuertoPropio("Aeropuerto Adolfo Suárez Madrid-Barajas", "MAD", "Madrid", "España", 
                    10.5, ZoneOffset.ofHours(2), 2, DireccionRelativaCiudad.ESTE, temporadas);
        } catch (TemporadasSolapadasException e) {
            System.err.println("Error al configurar el aeropuerto: " + e.getMessage());
        }

        // Dar de alta hangares
        airportManager.getGestor().darDeAltaHangar("Pasajeros", 10, 25.0, 15.0, 40.0, 1000.0, false);

        // Dar de alta una terminal
        List<PuertaEmbarque> puertas = new ArrayList<>();
        puertas.add(new PuertaEmbarque());
        airportManager.getGestor().darDeAltaTerminal("T1", "Pasajeros", 500, puertas);

        // Dar de alta un finger
        Set<PuertaEmbarque> puertasFinger = new HashSet<>();
        puertasFinger.add(new PuertaEmbarque());
        puertasFinger.add(new PuertaEmbarque());
        airportManager.getGestor().darDeAltaFinger("F1", 5.0, 200.0, new ArrayList<>(puertasFinger));

        // Dar de alta zonas de aparcamiento
        airportManager.getGestor().darDeAltaZonaAparcamiento("ZAP01", 2.5, 5.0, 5.0);

        // Dar de alta pista
        airportManager.getGestor().darDeAltaPista("P1", 3000, TipoPista.ATERRIZAJE);
        airportManager.getGestor().darDeAltaPista("P2", 3000, TipoPista.DESPEGUE);
        
    }

}
