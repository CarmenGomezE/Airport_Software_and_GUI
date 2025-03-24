package src.airportmanager.terminal;

import java.util.*;

import src.airportmanager.vuelo.Vuelo;
import src.maria.*;

/**
 * La clase Finger representa un finger de un aeropuerto.
 * 
 * @author Claudia Municio, María Pozo, Carmen Gómez
 */
public class Finger extends ModoEmbarque {

    /** La dimensión de alto del finger. */
    private double dimAlto;
    /** Las puertas de embarque a las que está conectado (solo puede ser 1 o 2) */
    private Set<PuertaEmbarque> puertasEmbarque;
    /** Horas de uso del finger */
    private double horasUso;
    /** Id del finger */
    private int id;
    /** Contador para el id del finger */
    private static int contador = 1;

    /**
     * Constructor de la clase Finger
     * 
     * @param codigo          Código del finger.
     * @param costePorHora    Coste por hora del finger.
     * @param dimAlto         Dimensión de alto del finger.
     * @param puertasEmbarque Puertas de embarque a las que está conectado el
     *                        finger.
     */
    public Finger(String codigo, double costePorHora, double dimAlto, Set<PuertaEmbarque> puertasEmbarque) {
        super(codigo, costePorHora);
        if (puertasEmbarque.size() < 1 || puertasEmbarque.size() > 2) {
            throw new IllegalArgumentException("Un finger debe estar conectado a una o dos puertas de embarque");
        }
        this.dimAlto = dimAlto;
        this.puertasEmbarque = new HashSet<>(puertasEmbarque);
        this.horasUso = 0;
        this.id = contador++;

    }

    /**
     * Método que devuelve la dimensión de alto del finger.
     * 
     * @return Dimensión de alto del finger.
     */
    public double getDimAlto() {
        return this.dimAlto;
    }

    /**
     * Método que devuelve las puertas de embarque a las que está conectado el
     * finger.
     * 
     * @return Puertas de embarque a las que está conectado el finger.
     */
    public Set<PuertaEmbarque> getPuertasEmbarque() {
        return this.puertasEmbarque;
    }

    /**
     * Método que devuelve las horas de uso del finger.
     * 
     * @return Horas de uso del finger.
     */
    public double getHorasUso() {
        return this.horasUso;
    }

    /**
     * Método que suma horas de uso a las horas de uso del finger.
     * 
     * @param horas Horas de uso.
     */
    public void addHorasUso(double horas) {
        if (horas < 0) {
            throw new IllegalArgumentException("Las horas deben ser positivas");
        }
        this.horasUso += horas;
    }

    /**
     * Método para resetear las horas de uso del finger.
     * 
     */
    public void horasUsoReset() {
        this.horasUso = 0;
    }

    /**
     * Método que devuelve el id del finger.
     * 
     * @return Id del finger.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Método para comprobar si un avion entra en el finger.
     * 
     * @param vuelo El vuelo que tiene el avion que va a conectarse al finger.
     * 
     * @return true si entra, false si no.
     */
    public boolean comprobarTamano(Vuelo vuelo) {
        if (vuelo.getAvion().getDimAlto() <= dimAlto) {
            return true;
        }

        return false;
    }

}