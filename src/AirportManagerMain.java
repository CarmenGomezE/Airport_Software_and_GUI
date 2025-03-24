package src;

import java.util.*;


import src.airportmanager.AirportManager;
import src.airportmanager.avion.*;
import src.airportmanager.factura.*;
import src.airportmanager.vuelo.*;

import java.util.*;

/**
 * Clase que implementa un demostrador básico de la aplicación.
 * 
 * @autor Carmen Gómez, Claudia Municio, María Pozo.
 */
public class AirportManagerMain {
	
	/**Nombre del fichero desde el que se carga y donde se guarda el estado de la aplicación*/
    private static final String ARCHIVO_ESTADO = "estado_airport_manager.dat";

    /**
     * Método principal
     * 
     * @param args		Argumentos recibidos.
     */
    public static void main(String[] args) {
        System.out.println("Iniciando Airport Manager...");

        /**Se inicia cargando la aplicación o inicializandola*/
        AirportManager airportManager = AirportManager.cargarEstado(ARCHIVO_ESTADO);

        if (airportManager == null) {
            System.out.println("No se encontró un estado previo. Creando nuevo sistema.");
            airportManager = new AirportManager();
        } else {
            System.out.println("Estado cargado correctamente.");
        }

        
        

  
        
        
        
        
        
        
        /**Se termina guardando el estado de la aplicación en un fichero*/
        airportManager.guardarEstado(ARCHIVO_ESTADO);
        System.out.println("Estado guardado. Cerrando aplicación.");
    }

}
