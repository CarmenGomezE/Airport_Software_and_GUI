package aeropuerto.src.maria;

import aeropuerto.src.claudia.*;
import java.util.*;
/**
 * La clase PuertaEmbarque representa las puertas de embarque del aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class PuertaEmbarque {
    private static int contadorId = 1; 

    /** Código único de la puerta de embarque*/
    private int id;
    /** Zonas de aparcamiento de la puerta de embarque*/
    private List<ZonaAparcamiento> zonasAparcamiento;
    /** Finger conectados a la puerta de embarque*/
    private Finger finger;
    /** Número de horas de uso de la puerta de embarque*/
    private double horasUso;
    /** Vuelo que ocupa la puerta de embarque*/
    private Vuelo vuelo;

    /**
     * Constructor de la clase PuertaEmbarque.
     */
    public PuertaEmbarque () {
        this.id = contadorId++;
        this.zonasAparcamiento = new ArrayList<>();
        this.horasUso = 0;
        this.vuelo = null; 
    }

    /**
     * Método para obtener el código único de una puerta de embarque.
     * 
     * @return El código único de una puerta de embarque.
     */
    public int getId() {
        return id;
    }

    /**
     * Método para obtener las zonas de aparcamiento de una puerta de embarque.
     * 
     * @return Las zonas de aparcamiento de una puerta de embarque.
     */
    public List<ZonaAparcamiento> getZonasAparcamiento() {
        return zonasAparcamiento;
    }

    /**
     * Método para obtener el finger conectado a una puerta de embarque.
     * 
     * @return El finger conectado a la puerta de embarque.
     */
    public Finger getFinger() {
        return finger;
    }

    /**
     * Método para obtener el número de horas de uso de la puerta de embarque.
     * 
     * @return Número de horas de uso de la puerta de embarque.
     */
    public double getHorasUso() {
        return horasUso;
    }

    /**
     * Método para añadir horas de uso a la puerta de embarque.
     * 
     * @param horas Horas que se suman.
     */
    public void addHorasUso(double horas) {
        if (horas <= 0) {
            throw new IllegalArgumentException("Las horas deben ser un número positivo");
        }

        horasUso += horas;
    }

    /**
     * Método para reiniciar a 0 las horas de uso de la puerta de embarque.
     */
    public void resetHorasUso() {
        horasUso = 0;
    }

    /**
     * Método para añadir una zona de aparcamiento a una puerta de embarque.
     * 
     * @param zonaAparcamiento Zona de aparcamiento que se añade.
     */
    public void addZonaAparcamiento(ZonaAparcamiento zonaAparcamiento) {
        if (zonaAparcamiento == null) {
            throw new IllegalArgumentException("La zona de aparcamiento no puede ser nula");
        }

        zonasAparcamiento.add(zonaAparcamiento);
    }

    /**
     * Método para conectar un finger a una puerta de embarque.
     * 
     * @param finger Finger que se conecta.
     */
    public void setFinger(Finger finger) {
        if (finger == null) {
            throw new IllegalArgumentException("El finger no puede ser null");
        }

        this.finger = finger;
    }

    /**
     * Método para obtener el vuelo que ocupa una puerta de embarque.
     * 
     * @return El vuelo que ocupa la puerta de embarque.
     */
    public Vuelo getVuelo() {
        return vuelo;
    }

    /**
     * Método para cambiar el vuelo que ocupa una puerta de embarque.
     * 
     * @param vuelo  El vuelo que ocupa la puerta de embarque.
     */
    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }
}
