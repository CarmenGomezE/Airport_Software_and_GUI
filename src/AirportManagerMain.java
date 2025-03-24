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
import java.io.*;
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
    private static AirportManager airportManager;
    /**
     * Método principal
     * 
     * @param args		Argumentos recibidos.
     */
    public static void main(String[] args) {
    	/** Inicialización del estado*/
    	vaciarArchivo(ARCHIVO_ESTADO);
    	System.out.println("Iniciando simulación...\n\n1.- Comenzamos cargando la aplicación desde un estado previo si lo tiene o inicalizándolo.");
    	airportManager = AirportManager.cargarEstado(ARCHIVO_ESTADO); 
        if (airportManager == null) {
            System.out.println("No se encontró un estado previo. Creando nuevo sistema.");
            airportManager = new AirportManager();
        } else {
            System.out.println("Estado cargado correctamente.");
        }
        airportManager.iniciarAplicacion();
        
        System.out.println("\n2.- Dar de alta elementos del aeropuerto.");
        darDeAlta();

        airportManager.guardarEstado(ARCHIVO_ESTADO);
        System.out.println("Estado guardado. Cerrando aplicación.");
        System.out.println("\nFinalizando simulación...");
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
    public static void darDeAlta() {
    	System.out.println("Iniciando el proceso de alta de aerolíneas y operadores.");
    	/** Aerolinea y operador*/
    	Aerolinea aerolineaIberia = new Aerolinea("Iberia", "IBE");
    	Operador operadorIberia = new Operador("Francisco Gonzalez", "12345678A", aerolineaIberia, airportManager.getAeropuerto(), airportManager.getGestor(), airportManager);
    	System.out.println("Dando de alta la aerolínea Iberia con código IBE.");
    	airportManager.getGestor().darDeAltaAerolinea("Iberia", "IBE", operadorIberia);
    	System.out.println("Dando de alta el operador Francisco Gonzalez para la aerolínea Iberia.");
    	airportManager.getGestor().darDeAltaOperador("Francisco Gonzalez", "12345678A", aerolineaIberia);

    	/** Aviones*/
    	System.out.println("Dando de alta los aviones para la aerolínea Iberia:");
    	operadorIberia.darDeAltaAvionPasajeros("Boeing", "737", 12.5, 30.5, 40.0, 3500, 2015, new Date(), "EC-HGR", 180);
    	System.out.println("Avión de pasajeros Boeing 737 de Iberia dado de alta con matrícula EC-HGR y capacidad de 180 pasajeros.");
    	operadorIberia.darDeAltaAvionPasajeros("Airbus", "A319", 12.5, 30.5, 40.0, 3500, 2016, new Date(), "EC-HGS", 180);
    	System.out.println("Avión de pasajeros Airbus A319 de Iberia dado de alta con matrícula EC-HGS y capacidad de 180 pasajeros.");
    	operadorIberia.darDeAltaAvionPasajeros("ATR72", "600", 12.5, 30.5, 40.0, 3500, 2020, new Date(), "EC-HGT", 180);
    	System.out.println("Avión de pasajeros ATR72 600 de Iberia dado de alta con matrícula EC-HGT y capacidad de 180 pasajeros.");
    	operadorIberia.darDeAltaAvionMercancias("Airbus", "A300", 16.0, 45.0, 60.0, 8000, 2010, new Date(), "EC-KBX", 10000, true, true);
    	System.out.println("Avión de mercancías Airbus A300 de Iberia dado de alta con matrícula EC-KBX y capacidad de 10,000 kg.");

    	/** Aerolinea y operador*/
    	Aerolinea aerolineaAirFrance = new Aerolinea("Air France", "AFR");
    	airportManager.getGestor().darDeAltaOperador("Danielle Abbadie", "87654321B", aerolineaAirFrance);
    	System.out.println("Dando de alta la aerolínea Air France con código AFR.");
    	airportManager.getGestor().darDeAltaAerolinea("Air France", "AFR", aerolineaAirFrance.getOperador());
    	System.out.println("Dando de alta el operador Danielle Abbadie para la aerolínea Air France.");
    	
    	/** Aviones*/
    	aerolineaAirFrance.getOperador().darDeAltaAvionPasajeros("Boeing", "787", 14.0, 40.0, 50.0, 7000, 2018, new Date(), "F-GKXC", 250);
    	System.out.println("Avión de pasajeros Boeing 787 de Air France dado de alta con matrícula F-GKXC y capacidad de 250 pasajeros.");
    	aerolineaAirFrance.getOperador().darDeAltaAvionMercancias("Airbus", "A330", 18.0, 50.0, 70.0, 10000, 2017, new Date(), "F-GKXG", 15000, false, true);
    	System.out.println("Avión de mercancías Airbus A330 de Air France dado de alta con matrícula F-GKXG y capacidad de 15,000 kg.");
    	aerolineaAirFrance.getOperador().darDeAltaAvionMercancias("Airbus", "A220", 18.0, 50.0, 70.0, 10000, 2017, new Date(), "F-GKXL", 15000, false, true);
    	System.out.println("Avión de mercancías Airbus A220 de Air France dado de alta con matrícula F-GKXL y capacidad de 15,000 kg.");

    	System.out.println("Proceso de alta de aerolíneas y aviones completado.");

    	System.out.println("Iniciando el proceso de alta de la infraestructura del aeropuerto.");

    	/** Puertas de embarque */
    	System.out.println("Creando puertas de embarque para la terminal T1 (Pasajeros) y T2 (Mercancías).");
    	List<PuertaEmbarque> puertasT1 = new ArrayList<>();
    	List<PuertaEmbarque> puertasT2 = new ArrayList<>();
    	for (int i = 0; i < 4; i++) {  
    	    puertasT1.add(new PuertaEmbarque());
    	    puertasT2.add(new PuertaEmbarque());
    	}
    	System.out.println("Puertas de embarque para T1: " + puertasT1.size() + " y para T2: " + puertasT2.size());

    	/** Terminales */
    	airportManager.getGestor().darDeAltaTerminal("T1", "Pasajeros", 500, puertasT1);
    	Terminal terminalT1= airportManager.getAeropuerto().getTerminales().get(0);
    	airportManager.getGestor().darDeAltaTerminal("T2", "Mercancías", 500, puertasT2);
    	Terminal terminalT2=airportManager.getAeropuerto().getTerminales().get(1);
    	System.out.println("Dadas de alta Terminal T1 (Pasajeros) y Terminal T2 (Mercancías).");
    	
    	System.out.println("Dando de alta las puertas de embarque en las terminales.");
    	for (PuertaEmbarque puerta : puertasT1) {
    	    airportManager.getGestor().darDeAltaPuertaEmbarque(puerta, terminalT1);
    	}
    	for (PuertaEmbarque puerta : puertasT2) {
    	    airportManager.getGestor().darDeAltaPuertaEmbarque(puerta, terminalT2);
    	}
    	System.out.println("Puertas de embarque dadas de alta en las terminales T1 y T2.");

    	/** Controladores */
    	System.out.println("Dando de alta los controladores para las terminales:");
    	airportManager.getGestor().darDeAltaControlador("Ana Gómez", "123456789L", terminalT1);
    	airportManager.getGestor().darDeAltaControlador("Victoria Ramirez", "123356789C", terminalT1);
    	airportManager.getGestor().darDeAltaControlador("Alfonso Ruiz", "123456239T", terminalT2);
    	airportManager.getGestor().darDeAltaControlador("Charles Green", "174456789F", terminalT2);
    	airportManager.getGestor().darDeAltaControlador("Marta Sanz", "123126789K", terminalT2);
    	System.out.println("Controladores Ana Gómez y Victoria Ramirez dados de alta para la Terminal T1.");
    	System.out.println("Controladores Alfonso Ruiz, Charles Green y Marta Sanz dados de alta para la Terminal T2.");

    	/** Hangares */
    	System.out.println("Dando de alta hangares para las terminales:");
    	airportManager.getGestor().darDeAltaHangar("Pasajeros", 6, 25.0, 15.0, 40.0, 1000.0, false);
    	airportManager.getGestor().darDeAltaHangar("Mercancías", 3, 28.0, 17.0, 43.0, 1000.0, true);
    	System.out.println("Hangar de Pasajeros y Hangar de Mercancías dados de alta.");

    	/** Fingers */
    	System.out.println("Dando de alta los Finger para la Terminal T1.");
    	airportManager.getGestor().darDeAltaFinger(5.0, 200.0, List.of(puertasT1.get(0)));
    	airportManager.getGestor().darDeAltaFinger(5.0, 200.0, List.of(puertasT1.get(2))); 
    	airportManager.getGestor().darDeAltaFinger(5.0, 200.0, List.of(puertasT1.get(0), puertasT1.get(1)));
    	System.out.println("Fingers dados de alta para las puertas de embarque T1.");

    	/** Zonas de aparcamiento */
    	System.out.println("Dando de alta zonas de aparcamiento en el aeropuerto.");
    	airportManager.getGestor().darDeAltaZonaAparcamiento(2.5, 5.0, 5.0);
    	airportManager.getGestor().darDeAltaZonaAparcamiento(3.0, 6.0, 6.0);
    	System.out.println("Zonas de aparcamiento dadas de alta con éxito.");

    	/** Pistas */
    	System.out.println("Dando de alta las pistas de aterrizaje y despegue.");
    	airportManager.getGestor().darDeAltaPista("P1", 3000, TipoPista.ATERRIZAJE);
    	airportManager.getGestor().darDeAltaPista("P2", 3000, TipoPista.DESPEGUE);
    	System.out.println("Pistas de aterrizaje y despegue dadas de alta.");
    	System.out.println("Proceso de alta de infraestructura del aeropuerto completado con éxito.");
        
    	/**Aeropuertos de destino */
    	System.out.println("Vamos a cargar 9 aeropuertos de un fichero.");
    	try {
    	    airportManager.getGestor().darDeAltaAeropuertoDestino(ARCHIVO_AEDESTINO);
    	    System.out.println("Aeropuerto destino dado de alta correctamente. Nombre del nuevo aeropuerto: "+airportManager.getAeropuerto().getAeropuertosDestino().getLast().getNombre());
    	} catch (IOException | TemporadasSolapadasException e) {
    	    System.err.println("Error al dar de alta aeropuerto destino: " + e.getMessage());
    	}

    	List<Temporada> temporadas = new ArrayList<>();
    	temporadas.add(new Temporada("01/06", "10:00", "20:00", "30/09"));
    	
    	/**Modificar aeropuerto propio */
    	try {
    	    airportManager.getGestor().configurarAeropuertoPropio("Aeropuerto Adolfo Suárez Madrid-Barajas", "MAD", "Madrid", "España", 
    	                                                          10.5, ZoneOffset.ofHours(0), 2, DireccionRelativaCiudad.ESTE, temporadas);
    	    System.out.println("Aeropuerto propio configurado correctamente, se ha añadido una temporada. Nuevo número de temporadas:"+airportManager.getAeropuerto().getTemporadas().size());
    	} catch (TemporadasSolapadasException e) {
    	    System.err.println("Error al configurar el aeropuerto: " + e.getMessage());
    	}
    	
    	
        
        
    }

}
