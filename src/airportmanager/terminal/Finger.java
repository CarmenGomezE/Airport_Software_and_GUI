package src.airportmanager.terminal;

import src.airportmanager.vuelo.Vuelo;

/**
 * La clase Finger representa un finger de un aeropuerto.
 * 
 * @author Claudia Municio, María Pozo, Carmen Gómez
 */
public class Finger extends ModoEmbarque {

    /** Número de versión de clase*/
	private static final long serialVersionUID = 1L;
	/** La dimensión de alto del finger. */
    private double dimAlto;

    /**
     * Constructor de la clase Finger
     * 
     * @param costePorHora    Coste por hora del finger.
     * @param dimAlto         Dimensión de alto del finger.
     */
    public Finger( double costePorHora, double dimAlto) {
        super( costePorHora);
        this.dimAlto = dimAlto;
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