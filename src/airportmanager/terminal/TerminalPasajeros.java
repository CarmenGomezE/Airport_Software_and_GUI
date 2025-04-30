package src.airportmanager.terminal;

import java.util.List;

/**
 * La clase TerminalPasajeros representa las terminales de pasajeros del
 * aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Cludia Municio.
 */
public class TerminalPasajeros extends Terminal {
	/**Número de versión de clase*/
    private static final long serialVersionUID = 1L;
	/** Número máximo de pasajeros de la terminal de pasajeros */
    private int numeroMaxPasajeros;

    /**
     * Constructor de la clase TerminalPasajeros.
     * 
     * @param codigo             Código de la terminal de pasajeros.
     * @param puertaEmbarque     Una de las puertas de embarque de la terminal de
     *                           pasajeros.
     * @param numeroMaxPasajeros Número máximo de pasajeros de la terminal de
     *                           pasajeros.
     */
    public TerminalPasajeros(String codigo, List<PuertaEmbarque> puertaEmbarque, int numeroMaxPasajeros) {
        super(codigo, puertaEmbarque);
        this.numeroMaxPasajeros = numeroMaxPasajeros;
    }
}