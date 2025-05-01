package src.airportmanager.terminal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import src.airportmanager.vuelo.Vuelo;

/**
 * La clase PuertaEmbarque representa las puertas de embarque del aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class PuertaEmbarque implements Serializable {

    /** Número de versión de la clase */
    private static final long serialVersionUID = 1L;
    /** Contador para el identificador */
    private static int contadorId = 1;
    /** Código único de la puerta de embarque */
    private int id;
    /** Zonas de aparcamiento de la puerta de embarque */
    private List<ZonaAparcamiento> zonasAparcamiento;
    /** Finger conectados a la puerta de embarque */
    private Finger finger;
    /** Vuelo que ocupa la puerta de embarque */
    private Vuelo vuelo;
    /** Guarda el contador para el identificador de la zona de aparcamiento */
    private int contadorGuardado;

    /**
     * Constructor de la clase PuertaEmbarque.
     */
    public PuertaEmbarque() {
        this.id = contadorId++;
        this.zonasAparcamiento = new ArrayList<>();
        this.vuelo = null;
        this.finger = null;
    }

    /**
     * Método para guardar el contador.
     * 
     * @param oos Output Stream.
     * @throws IOException Excepcion al leer.
     */
    private void writeObject(ObjectOutputStream oos) throws IOException {
        contadorGuardado = contadorId;
        oos.defaultWriteObject();
    }

    /**
     * Método para cargar el contador.
     * 
     * @param ois Input Stream.
     * @throws IOException            Excepcion al leer.
     * @throws ClassNotFoundException Clase no encontrada.
     */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        contadorId = contadorGuardado;
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
     * @param vuelo El vuelo que ocupa la puerta de embarque.
     */
    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    /**
     * Método para ver si contiene una zona de aparcamiento.
     * 
     * @param zona Zona de aparcamiento.
     * @return True si la contiene, false si no.
     */
    public boolean contieneZonaAparcamiento(ZonaAparcamiento zona) {
        return zonasAparcamiento.contains(zona);
    }

    /**
     * Método to string.
     * 
     * @return cadena que respresenta una puerta de embarque.
     */
    @Override
    public String toString() {
        return "Puerta: " + id;
    }
}
