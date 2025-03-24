package src;

import java.util.*;


import src.airportmanager.AirportManager;
import src.airportmanager.avion.*;
import src.airportmanager.exceptions.TemporadasSolapadasException;
import src.airportmanager.factura.*;
import src.airportmanager.vuelo.*;
import src.airportmanager.aeropuerto.*;
import src.airportmanager.exceptions.*;

import java.util.*;
import java.time.*;

/**
 * Clase que implementa un demostrador básico de la aplicación.
 * 
 * @autor Carmen Gómez, Claudia Municio, María Pozo.
 */
public class AirportManagerMain {
	
	/**Nombre del fichero desde el que se carga y donde se guarda el estado de la aplicación*/
    private static final String ARCHIVO_ESTADO = "estado_airport_manager.dat";
    private static AeropuertoPropio aeropuerto;

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
    

}
