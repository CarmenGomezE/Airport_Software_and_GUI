
/**
 * Representa una pista de aeropuerto que puede ser de aterrizaje o despegue.
 */
public class Pista {
    private String codigo;
    private double longitud;
    private TipoPista tipopista;


    /**
     * Crea una pista de aterrizaje o de despegue.
     * @param codigo el código de la pista
     * @param longitud la longitud de la pista
     * @param tipopista el tipo de pista (aterrizaje o despegue)
     */
    public Pista (String codigo, double longitud, TipoPista tipopista) {
        this.codigo = codigo;
        this.longitud = longitud;
        this.tipopista = tipopista;
    }
}

