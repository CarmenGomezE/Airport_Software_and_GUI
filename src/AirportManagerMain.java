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
    private static final String ARCHIVO_ESTADO = "resources/estado_airport_manager.dat";
    private static final String ARCHIVO_AEDESTINO = "resources/aeropuerto_destino.txt";
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
    
    
    public void darDeAlta() {
    	Aerolinea aerolineaIberia= new Aerolinea("Iberia", "IBE");
    	Operador operadorIberia = new Operador("Francisco Gonzalez", "12345678A", aerolineaIberia, airportManager.getAeropuerto(), airportManager.getGestor(), airportManager);
    	airportManager.getGestor().darDeAltaAerolinea("Iberia", "IB", operadorIberia);
    	airportManager.getGestor().darDeAltaOperador("Francisco Gonzalez", "12345678A", aerolineaIberia);
    	operadorIberia.darDeAltaAvionPasajeros("Boeing", "737", 12.5, 30.5, 40.0, 3500, 2015, new Date(), "EC-HGR", 180);
    	operadorIberia.darDeAltaAvionPasajeros("Airbus", "A319", 12.5, 30.5, 40.0, 3500, 2016, new Date(), "EC-HGS", 180);
    	operadorIberia.darDeAltaAvionPasajeros("ATR72", "600", 12.5, 30.5, 40.0, 3500, 2020, new Date(), "EC-HGT", 180);
    	operadorIberia.darDeAltaAvionMercancias("Airbus", "A300", 16.0, 45.0, 60.0, 8000, 2010, new Date(), "EC-KBX", 10000, true, true);
    	
    	Aerolinea aerolineaAirFrance= new Aerolinea("Air France", "AFR");
    	airportManager.getGestor().darDeAltaOperador("Danielle Abbadie", "87654321B",aerolineaAirFrance);
    	airportManager.getGestor().darDeAltaAerolinea("Air France","AFR",aerolineaAirFrance.getOperador());
    	aerolineaAirFrance.getOperador().darDeAltaAvionPasajeros("Boeing", "787", 14.0, 40.0, 50.0, 7000, 2018, new Date(), "F-GKXC", 250);
    	aerolineaAirFrance.getOperador().darDeAltaAvionMercancias("Airbus", "A330", 18.0, 50.0, 70.0, 10000, 2017, new Date(), "F-GKXG", 15000, false, true);
    	aerolineaAirFrance.getOperador().darDeAltaAvionMercancias("Airbus", "A220", 18.0, 50.0, 70.0, 10000, 2017, new Date(), "F-GKXL", 15000, false, true);
        
    
        List<PuertaEmbarque> puertasT1 = new ArrayList<>();
        List<PuertaEmbarque> puertasT2 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {  
            puertasT1.add(new PuertaEmbarque());
            puertasT2.add(new PuertaEmbarque());
        }
        Terminal terminalT1 = new TerminalPasajeros("T1", puertasT1.get(0), 200);
        Terminal terminalT2 = new TerminalMercancias("T2", puertasT2.get(0));
        airportManager.getGestor().darDeAltaTerminal("T1", "Pasajeros", 500, puertasT1);
        airportManager.getGestor().darDeAltaTerminal("T2", "Pasajeros", 500, puertasT2);
        
        airportManager.getGestor().darDeAltaControlador("Ana Gómez", "123456789L", terminalT1);
        airportManager.getGestor().darDeAltaControlador("Victoria Ramirez", "123356789C", terminalT1);
        airportManager.getGestor().darDeAltaControlador("Alfonso Ruiz", "123456239T", terminalT2);
        airportManager.getGestor().darDeAltaControlador("Charles Green", "174456789F", terminalT2);
        airportManager.getGestor().darDeAltaControlador("Marta Sanz", "123126789K", terminalT2);
        
        airportManager.getGestor().darDeAltaHangar("Pasajeros", 6, 25.0, 15.0, 40.0, 1000.0, false);
        airportManager.getGestor().darDeAltaHangar("Mercancías", 3, 28.0, 17.0, 43.0, 1000.0, true);

        airportManager.getGestor().darDeAltaFinger(5.0, 200.0, List.of(puertasT1.get(0)));
        airportManager.getGestor().darDeAltaFinger(5.0, 200.0, List.of(puertasT1.get(2))); 
        airportManager.getGestor().darDeAltaFinger(5.0, 200.0, List.of(puertasT1.get(0), puertasT1.get(1)));


        airportManager.getGestor().darDeAltaZonaAparcamiento(2.5, 5.0, 5.0);
        airportManager.getGestor().darDeAltaZonaAparcamiento(3.0, 6.0, 6.0);
   
        airportManager.getGestor().darDeAltaPista("P1", 3000, TipoPista.ATERRIZAJE);
        airportManager.getGestor().darDeAltaPista("P2", 3000, TipoPista.DESPEGUE);
        
        try {
        	 airportManager.getGestor().darDeAltaAeropuertoDestino(ARCHIVO_AEDESTINO);
        } catch (IOException | TemporadasSolapadasException e) {
            System.err.println("Error al dar de alta aeropuerto destino: " + e.getMessage());
        }
        List<Temporada> temporadas = new ArrayList<>();
        temporadas.add(new Temporada("2025-06-01", "10:00", "20:00", "2025-09-30"));
        try {
        	 airportManager.getGestor().configurarAeropuertoPropio("Aeropuerto Adolfo Suárez Madrid-Barajas", "MAD", "Madrid", "España", 
        			 												10.5, ZoneOffset.ofHours(0), 2, DireccionRelativaCiudad.ESTE, temporadas);
        } catch (TemporadasSolapadasException e) {
            System.err.println("Error al configurar el aeropuerto: " + e.getMessage());
        }

        
        
    }

}
