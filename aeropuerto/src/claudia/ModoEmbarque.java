package aeropuerto.src.claudia;
/**
 * La clase abstracta ModoEmbarque representa el modo de embarque. 
 * Puede ser o bien finger o bien zona de aparcamiento.
 * @author Claudia Municio, Carmen Gómez, María Pozo.
 */
public abstract class ModoEmbarque {
	
    /**Código del modo de embarque */
    protected String codigo;
    /**Coste por hora del modo de embarque */
    protected double costePorHora;
    /**Vuelo que ocupa el modo de embarque */
    private Vuelo vuelo;

    /**
     * Constructor de la clase ModoEmbarque
     * @param codigo                Código del modo de embarque.
     * @param costePorHora          Coste por hora del modo de embarque.
     */
    public ModoEmbarque (String codigo, double costePorHora) {
        this.codigo = codigo;
        this.costePorHora = costePorHora;
        this.vuelo = null;
    }

    /**
     * Método que devuelve el código de un modo de embarque.
     * @return Código del modo de embarque.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Método que devuelve el coste por hora de un modo de embarque.
     * @return Coste por hora del modo de embarque.
     */
    public double getCostePorHora() {
        return costePorHora;
    }

    /**
     * Método que devuelve el vuelo que ocupa un modo de embarque.
     * @return Vuelo que ocupa el modo de embarque.
     */
    public Vuelo getVuelo() {
        return vuelo;
    }

    /**
     * Método que cambia el vuelo que ocupa un modo de embarque.
     * @param vuelo  Vuelo que ocupa el modo de embarque.
     */
    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }
}