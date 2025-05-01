package src.airportmanager.terminal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import src.airportmanager.vuelo.Vuelo;

/**
 * La clase abstracta ModoEmbarque representa el modo de embarque.
 * Puede ser o bien finger o bien zona de aparcamiento.
 * 
 * @author Claudia Municio, Carmen Gómez, María Pozo.
 */
public abstract class ModoEmbarque implements Serializable {

    /** Número de versión de la clase */
    private static final long serialVersionUID = 1L;

    /** Identificador de una zona de aparcamiento */
    private int id;
    /** Coste por hora del modo de embarque */
    protected double costePorHora;
    /** Vuelo que ocupa el modo de embarque */
    private Vuelo vuelo;

    /** Contador para el identificador de la zona de aparcamiento */
    private static int contador = 1;
    /** Guarda el contador para el identificador de la zona de aparcamiento */
    private int contadorGuardado;

    /**
     * Constructor de la clase ModoEmbarque
     * 
     * @param costePorHora Coste por hora del modo de embarque.
     */
    public ModoEmbarque(double costePorHora) {
        this.costePorHora = costePorHora;
        this.vuelo = null;
        this.id = contador++;
    }

    /**
     * Método para guardar el contador.
     * 
     * @param oos Output Stream.
     * @throws IOException Excepcion al leer.
     */
    private void writeObject(ObjectOutputStream oos) throws IOException {
        contadorGuardado = contador;
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
        contador = contadorGuardado;
    }

    /**
     * Método que devuelve el identificador de la zona de aparcamiento.
     * 
     * @return Identificador de la zona de aparcamiento.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Método que devuelve el vuelo que ocupa un modo de embarque.
     * 
     * @return Vuelo que ocupa el modo de embarque.
     */
    public Vuelo getVuelo() {
        return vuelo;
    }

    /**
     * Método que cambia el vuelo que ocupa un modo de embarque.
     * 
     * @param vuelo Vuelo que ocupa el modo de embarque.
     */
    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    /**
     * Método para comprobar si un avion entra en la zona de aparcamiento.
     * 
     * @param vuelo El vuelo que tiene el avion que va a entrar en la zona de
     *              aparcamiento.
     * 
     * @return true si entra, false si no.
     */
    public abstract boolean comprobarTamano(Vuelo vuelo);
}