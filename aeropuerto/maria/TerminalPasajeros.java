package aeropuerto;
/**
 * La clase TerminalPasajeros representa las terminales de pasajeros del aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Cludia Municio.
 */
public class TerminalPasajeros extends Terminal {
    /** Número máximo de pasajeros de la terminal de pasajeros*/
    private int numeroMaxPasajeros;

    /**
     * Constructor de la clase TerminalPasajeros.
     * 
     * @param codigo                Código de la terminal de pasajeros.
     * @param puertaEmbarque        Una de las puertas de embarque de la terminal de pasajeros.
     * @param numeroMaxPasajeros    Número máximo de pasajeros de la terminal de pasajeros.
     */
    public Terminal(String codigo, PuertaEmbarque puertaEmbarque, int numeroMaxPasajeros) {
        super(codigo, puertaEmbarque);
        this.numeroMaxPasajeros = numeroMaxPasajeros;
    }

    /**
     * Método para obtener el número máximo de pasajeros de la terminal de pasajeros.
     * 
     * @return Número máximo de la terminal de pasajeros.
     */
    public int getNumeroMaxAviones() {
        return numeroMaxPasajeros;
    }
    
}