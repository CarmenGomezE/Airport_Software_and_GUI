package src.aeropuerto.claudia;
/**
 * La clase abstracta ModoEmbarque representa el modo de embarque. 
 * Puede ser o bien finger o bien zona de aparcamiento.
 * @author Claudia Municio, Carmen Gómez, María Pozo.
 */
public abstract class ModoEmbarque {
    /**Código del modo de embarque */
    private String codigo;
    /**Coste por hora del modo de embarque */
    private double costePorHora;

    /**
     * Constructor de la clase ModoEmbarque
     * @param codigo                Código del modo de embarque.
     * @param costePorHora          Coste por hora del modo de embarque.
     */
    public ModoEmbarque (String codigo, double costePorHora) {
        this.codigo = codigo;
        this.costePorHora = costePorHora;
    }

    /**
     * Método que devuelve el código de un modo de embarque.
     * @return Código del modo de embarque.
     */
    public String getCodigo() {
        this.codigo = codigo;
    }

     /**
     * Método que devuelve el coste por hora de un modo de embarque.
     * @return Coste por hora del modo de embarque.
     */
    public double getCostePorHora() {
        this.costePorHora = costePorHora;
    }

}