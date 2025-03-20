package src.aeropuerto.claudia;
import java.util.*;

import src.aeropuerto.maria;

/**
 * La clase EstadisiticasGestor representa las estadisticas que puede 
 * ver el gestor acerca del tiempo medio de uso de finger, zona de aparcamiento
 * puerta de embarque y hangar.
 * 
 * @author Claudia Municio, María Pozo, Carmen Gómez 
 */
public class EstadisticasGestor {
    /**El tiempo medio de uso */
    String tiempoMedio;

    /**Constructor de la clase EstadisticasGestor.
     * @param tiempoMedio       Tiempo medio de uso.
     */
    public EstadisticasGestor(String tiempoMedio) {
        this.tiempoMedio = tiempoMedio;
    }

    /**
     * Método que devuelve el tiempo medio de uso de un finger.
     * @param f            Finger del que se ve el tiempo medio de uso.
     */
    public int tiempoMedioFinger(Finger f) {

    }

    /**
     * Método que devuelve el tiempo medio de uso de una zona de aparcamiento.
     * @param  z          Zona de aparcamiento de la que se ve el tiempo medio de uso.
     */
    public int tiempoMedioZonaAparcamiento(ZonaAparcamiento z) {

    }

    /**
     * Método que devuelve el tiempo medio de uso de una puerta de embarque.
     * @param p          Puerta de embarque de la que se ve el tiempo medio de uso.
     */
    public int tiempoMedioPuertaEmbarque (PuertaEmbarque p) {

    }

    /**
     * Método que devuelve el tiempo medio de uso de un hangar.
     * @param h           Hangar del que se ve el tiempo medio de uso.
     */
    public int tiempoMedioHangar (Hangar h) {

    }
}