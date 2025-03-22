package aeropuerto.src.maria;
/** 
 * La clase TerminalMercancias representa las terminales de mercancias del aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class TerminalMercancias extends Terminal {
    /**
     * Constructor de la clase TerminalMercancias.
     * 
     * @param codigo            Código de la terminal de mercancías.
     * @param puertaEmbarque    Una de las puertas de embarque de la terminal de mercancías.
     */
    public Terminal(String codigo, PuertaEmbarque puertaEmbarque) {
        super(codigo, puertaEmbarque);
    }
}