package src.airportmanager.terminal;

import src.airportmanager.vuelo.Vuelo;

/**
 * La clase ZonaAparcamiento representa una zona de aparcamiento en un
 * aeropuerto.
 * 
 * @author Claudia Municio, Carmen Gómez, María Pozo.
 */
public class ZonaAparcamiento extends ModoEmbarque {
    /** La dimensión de ancho de la plaza de aparcamiento */
    private double dimPlazaAncho;
    /** La dimensión de largo de la plaza de aparcamiento */
    private double dimPlazaLargo;
    /** Horas de uso de una zona de aparcamiento */
    private double horasUso;
    /** Identificador de una zona de aparcamiento */
    private int id;
    /** Contador para el identificador de la zona de aparcamiento */
    private static int contador = 1;

    /**
     * Constructor de la clase ZonaAparcamiento
     * 
     * @param codigo        Código de la zona de aparcamiento.
     * @param costePorHora  Coste por hora de la zona de aparcamiento.
     * @param dimPlazaAncho Dimensión de ancho de la zona de aparcamiento.
     * @param dimPlazaLargo dimensión de largo de la zona de aparcamiento.
     */
    public ZonaAparcamiento(String codigo, double costePorHora, double dimPlazaAncho, double dimPlazaLargo) {
        super(codigo, costePorHora);
        this.dimPlazaAncho = dimPlazaAncho;
        this.dimPlazaLargo = dimPlazaLargo;
        this.horasUso = 0;
        this.id = contador++;
    }

    /**
     * Método que devuelve la dimensión de ancho de la plaza de aparcamiento.
     * 
     * @return Dimensión de ancho de la plaza.
     */
    public double getDimPlazaAncho() {
        return this.dimPlazaAncho;
    }

    /**
     * Método que devuelve la dimensión de largo de la plaza de aparcamiento.
     * 
     * @return Dimensión de largo de la plaza.
     */
    public double getDimPlazaLargo() {
        return this.dimPlazaLargo;
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
     * Método para comprobar si un avion entra en la zona de aparcamiento.
     * 
     * @param vuelo El vuelo que tiene el avion que va a entrar en la zona de
     *              aparcamiento.
     * 
     * @return true si entra, false si no.
     */
    public boolean comprobarTamano(Vuelo vuelo) {
        if (vuelo.getAvion().getDimAncho() <= dimPlazaAncho && vuelo.getAvion().getDimLargo() <= dimPlazaLargo) {
            return true;
        }

        return false;
    }
}