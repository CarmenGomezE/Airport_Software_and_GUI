package src.airportmanager.terminal;

/**
 * La clase TerminalMercancias representa las terminales de mercancias del
 * aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class TerminalMercancias extends Terminal {
	/**Número de versión de clase*/
    private static final long serialVersionUID = 1L;

	/**
     * Constructor de la clase TerminalMercancias.
     * 
     * @param codigo         Código de la terminal de mercancías.
     * @param puertaEmbarque Una de las puertas de embarque de la terminal de
     *                       mercancías.
     */
    public TerminalMercancias(String codigo, PuertaEmbarque puertaEmbarque) {
        super(codigo, puertaEmbarque);
    }
}