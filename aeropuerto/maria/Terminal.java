package aeropuerto;
/**
 * La clase Terminal representa las terminales del aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public abstract class Terminal {
    /** Código de la terminal*/
    private String codigo;
    /** Puertas de embarque de la terminal*/
    private List<PuertaEmbarque> puertasEmbarque;

    /**
     * Constructor de la clase Terminal.
     * 
     * @param codigo            Código de la terminal.
     * @param puertaEmbarque    Una de las puertas de embarque de la terminal.
     */
    public Terminal(String codigo, PuertaEmbarque puertaEmbarque) {
        this.codigo = codigo;
        this.puertasEmbarque.add(puertaEmbarque);
    }

    /**
     * Método para obtener el código de la terminal.
     * 
     * @return Código de la terminal.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Método para obtener las puertas de embarque de la terminal.
     * 
     * @return Lista de las puertas de embarque de la terminal.
     */
    public void getPuertasEmbarque() {
        return puertasEmbarque;
    }

    /**
     * Método para añadir una puerta de embarque a la terminal.
     * 
     * @param puertaEmbarque Nueva puerta de embarque.
     */
    public void añadirPuertaEmbarque(PuertaEmbarque puertaEmbarque) {
        puertasEmbarque.add(puertaEmbarque);
    }
}