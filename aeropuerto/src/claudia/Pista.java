package aeropuerto;
/**
 * La clase Pista representa una pista de aeropuerto que puede ser de aterrizaje o despegue.
 * @author Claudia Municio, María Pozo, Carmen Gómez.
 */
public class Pista {
    /**Código de la pista */
    private String codigo;
    /**Longitud de la pista */
    private double longitud;
    /**Tipo de la pista */
    private TipoPista tipoPista;
    /**Vuelo si la pista está ocupada */
    private Vuelo vuelo;

    /**
     * Constructor de la clase Pista.
     * @param codigo                Código de la pista
     * @param longitud              Longitud de la pista
     * @param tipoPista             Tipo de pista (aterrizaje o despegue)
     */
    public Pista (String codigo, double longitud, TipoPista tipoPista) {
        this.codigo = codigo;
        this.longitud = longitud;
        this.tipoPista = tipoPista;
        this.vuelo = null;
    }

    /**
     * Método que devuelve el código de la pista.
     * @return Código de la pista.
     */
    public String getCodigo() {
        return this.codigo;
    }

    /**
     * Método que devuelve la longitud de la pista.
     * @return Longitud de la pista.
     */
    public double getLongitud() {
        return this.longitud;
    }

    /**
     * Método que devuelve el tipo de la pista.
     * @return Tipo de la pista.
     */
    public TipoPista getTipoPista() {
        return this.tipoPista;
    }

    /**
     * Método que devuelve el vuelo que ocupa la pista.
     * @return Vuelo de la pista.
     */
    public Vuelo getVuelo() {
        return this.vuelo;
    }

    /**
     * Método que cambia el vuelo que ocupa la pista.
     * @param vuelo Vuelo de la pista.
     */
    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }


}

