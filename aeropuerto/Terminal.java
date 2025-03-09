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
     * @param codigo Código de la terminal.
     */
    public Terminal(String codigo) {
        this.codigo = codigo;
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