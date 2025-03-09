package aeropuerto;
/**
 * Clase abstracta que representa el modo de embarque. 
 * Puede ser o bien finger o bien zona de aparcamiento.
 */
public abstract class ModoEmbarque {
    private String codigo;
    private double costePorHora;

    /**
     * Crea un modo de embarque
     * @param codigo el código del modo de embarque.
     * @param costePorHora el coste por hora del modo de embarque
     */
    public ModoEmbarque (String codigo, double costePorHora) {
        this.codigo = codigo;
        this.costePorHora = costePorHora;
    }


}