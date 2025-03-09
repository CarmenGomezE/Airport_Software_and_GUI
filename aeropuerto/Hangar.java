/**
 * La clase Hangar representa los hangares del aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public abstract class Hangar {
    /** Número máximo de aviones del hangar*/
    private int numeroMaxAviones;
    /** Ancho de cada plaza del hangar*/
    private double dimPlazaAncho;
    /** Alto de cada plaza del hangar*/
    private double dimPlazaAlto;
    /** Largo de cada plaza del hangar*/
    private double dimPlazaLargo;
    /** Coste por hora del hangar*/
    private double costePorHora;

    /**
     * Constructor de la clase Hangar.
     * 
     * @param numeroMaxAviones  Número máximo de aviones del hangar.
     * @param dimPlazaAncho     Ancho de cada plaza del hangar.
     * @param dimPlazaAlto      Alto de cada plaza del hangar.
     * @param dimPlazaLargo     Largo de cada plaza del hangar.
     * @param costePorHora      Coste por hora del hangar.
     */
    public Hangar(int numeroMaxAviones, double dimPlazaAncho, double dimPlazaAlto, double dimPlazaLargo, double costePorHora) {
        this.numeroMaxAviones = numeroMaxAviones;
        this.dimPlazaAncho = dimPlazaAncho;
        this.dimPlazaAlto = dimPlazaAlto;
        this.dimPlazaLargo = dimPlazaLargo;
        this.costePorHora = costePorHora;
    } 
}