package src.airportmanager.terminal;

import java.util.*;


import src.airportmanager.vuelo.Vuelo;

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

    /**
     * Constructor de la clase Finger
     * 
     * @param id              Código del finger.
     * @param costePorHora    Coste por hora del finger.
     * @param dimAlto         Dimensión de alto del finger.
     * @param puertasEmbarque Puertas de embarque a las que está conectado el
     *                        finger.
     */
    public Finger( double costePorHora, double dimAlto, Set<PuertaEmbarque> puertasEmbarque) {
        super( costePorHora);
        if (puertasEmbarque.size() < 1 || puertasEmbarque.size() > 2) {
            throw new IllegalArgumentException("Un finger debe estar conectado a una o dos puertas de embarque");
        }
        this.dimAlto = dimAlto;
        this.puertasEmbarque = puertasEmbarque;
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