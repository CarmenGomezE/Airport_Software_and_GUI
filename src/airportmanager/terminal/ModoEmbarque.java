package src.airportmanager.terminal;

import src.airportmanager.vuelo.Vuelo;

/**
 * La clase abstracta ModoEmbarque representa el modo de embarque.
 * Puede ser o bien finger o bien zona de aparcamiento.
 * 
 * @author Claudia Municio, Carmen Gómez, María Pozo.
 */
public abstract class ModoEmbarque {

    /** Horas de uso de una zona de aparcamiento */
    private double horasUso;
    /** Identificador de una zona de aparcamiento */
    private int id;
    /** Coste por hora del modo de embarque */
    protected double costePorHora;
    /** Vuelo que ocupa el modo de embarque */
    private Vuelo vuelo;

    /** Contador para el identificador de la zona de aparcamiento */
    private static int contador = 1;

    /**
     * Constructor de la clase ModoEmbarque
     * 
     * @param id       Código del modo de embarque.
     * @param costePorHora Coste por hora del modo de embarque.
     */
    public ModoEmbarque(String id, double costePorHora) {
        this.id = id;
        this.costePorHora = costePorHora;
        this.vuelo = null;
        this.horasUso = 0;
        this.id = contador++;
    }

     /**
     * Método que devuelve las horas de uso de la zona de aparcamiento.
     * 
     * @return Horas de uso de la zona de aparcamiento.
     */
    public double getHorasUso() {
        return this.horasUso;
    }

    /**
     * Método que suma horas de uso de la zona de aparcamiento.
     * 
     * @param horas Horas de uso.
     */
    public void addHorasUso(double horas) {
        this.horasUso += horas;
    }

    /**
     * Método que devuelve resetea las horas de uso.
     */
    public void horasUsoReset() {
        this.horasUso = 0;
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
     * Método que devuelve el coste por hora de un modo de embarque.
     * 
     * @return Coste por hora del modo de embarque.
     */
    public double getCostePorHora() {
        return costePorHora;
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