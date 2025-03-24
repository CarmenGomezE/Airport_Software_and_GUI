package src;

import java.util.*;


import src.airportmanager.AirportManager;
import src.airportmanager.avion.*;
import src.airportmanager.factura.*;
import src.airportmanager.vuelo.*;
import src.airportmanager.aeropuerto.*;

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

    /**
     * Método principal
     * 
     * @param args		Argumentos recibidos.
     */
    public static void main(String[] args) {
        System.out.println("Iniciando Airport Manager...");

        /**Se inicia cargando la aplicación o inicializandola*/
        AirportManager airportManager = AirportManager.cargarEstado(ARCHIVO_ESTADO);

        String nombre = "Aeropuerto Internacional Ejemplo";
        String codigo = "AIE";
        String ciudad = "Ciudad Ejemplo";
        String pais = "País Ejemplo";
        double distanciaCiudad = 10.5; // Distancia en km
        ZoneOffset diferenciaHoraria = ZoneOffset.ofHours(2); // Diferencia horaria de +2 horas
        int numTemporadas = 4; // Ejemplo: 4 temporadas de vuelos

        AeropuertoPropio aeropuerto = new AeropuertoPropio(nombre, codigo, ciudad, pais, distanciaCiudad, ZoneOffset.ofHours(2), 2, DireccionRelativaCiudad.ESTE);

        aeropuerto.agregarTemporada(new Temporada("2025-06-01", "10:00", "20:00", "2025-09-30"));
        aeropuerto.agregarTemporada(new Temporada("2025-10-01", "08:00", "24:00", "2025-05-30"));

        // 4. Configurar el aeropuerto propio con las temporadas
        try {
            aeropuerto.configurarAeropuertoPropio(
                nombre, 
                codigo, 
                ciudad, 
                pais, 
                distanciaCiudad, 
                diferenciaHoraria, 
                numTemporadas, 
                direccionCiudad,
                temporadas
            );
        } catch (TemporadasSolapadasException e) {
            System.out.println("Error al configurar el aeropuerto: " + e.getMessage());
        }
        if (airportManager == null) {
            System.out.println("No se encontró un estado previo. Creando nuevo sistema.");
            airportManager = new AirportManager(null, null);
        } else {
            System.out.println("Estado cargado correctamente.");
        }

        
        

  
        
        
        
        
        
        
        /**Se termina guardando el estado de la aplicación en un fichero*/
        airportManager.guardarEstado(ARCHIVO_ESTADO);
        System.out.println("Estado guardado. Cerrando aplicación.");
    }

}
