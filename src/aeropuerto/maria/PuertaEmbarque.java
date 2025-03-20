package src.aeropuerto.maria;
/**
 * La clase PuertaEmbarque representa las puertas de embarque del aeropuerto.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class PuertaEmbarque {
    /** Código de la puerta de embarque*/
    private String codigo;
    /** Zonas de aparcamiento de la puerta de embarque*/
    private List<ZonaAparcamiento> zonasAparcamiento;

    /**
     * Constructor de la clase PuertaEmbarque.
     * 
     * @param codigo            Código de la puerta de embarque.
     * @param zonasAparcamiento Zonas de aparcamiento de la puerta de embarque.
     */
    public PuertaEmbarque (String codigo, List<ZonaAparcamiento> zonasAparcamiento) {
        this.codigo = codigo;
        this.zonasAparcamiento = zonasAparcamiento;
    }
}