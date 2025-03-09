package aeropuerto;
/**
 * La clase VueloProgramado representa los vuelos que se pretenden añadir a los
 * vuelos del aeropuerto. Este tendrá un tipo de vuelo, de modo de embarque y de 
 * carga, y una periodicidad.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class VueloProgramado{

    /** Codigo del vuelo */
    private String codigo;
    /** Fecha del vuelo */
    private Date fecha;
    /** Hora del vuelo */
    private LocalTime hora;
    /** Dias en los que se repite el vuelo */
    private List <DayOfWeek> diasRecurrente;

    /** Tipo de carga que transporta el vuelo */
    private TipoCarga tipoCarga;
     /** Tipo de embarque del vuelo */
    private TipoModoEmbarque tipoModoEmbarque;
     /** Tipo de periodicidad del vuelo */
    private Periodicidad periodicidad;
     /** Tipo de vuelo */
    private TipoVuelo tipoVuelo;


    /**
     * Constructor de la clase VueloProgramado.
     * 
     * @param codigo            Código del vuelo.
     * @param fecha             Fecha del vuelo.
     * @param hora              Hora del vuelo.
     * @param diasRecurrente    Días en los que es recurrente del vuelo.
     * @param tipoCarga         Tipo de carga del vuelo.
     * @param tipoModoEmbarque  Modo de embarque del vuelo.
     * @param periodicidad      Periodicidad del vuelo.
     * @param tipoVuelo         Tipo de vuelo.
     */
    public VueloProgramado(String codigo, Date fecha, LocalTime hora, List<DayOfWeek> diasRecurrente, TipoCarga tipoCarga, 
                            TipoModoEmbarque tipoModoEmbarque, Periodicidad periodicidad, TipoVuelo tipoVuelo) {
        this.codigo=codigo;
        this.fecha=fecha;
        this.hora=hora;
        this.diasRecurrente=diasRecurrente;
        this.tipoCarga=tipoCarga;
        this.tipoModoEmbarque=tipoModoEmbarque;
        this.periodicidad=periodicidad;
        this.tipoVuelo= tipoVuelo;
    }

     /**
     * Método que devuelve el código del VueloProgramado.
     * 
     * @return Código del vuelo.
     */
    public String getCodigo(){
        return this.codigo;
    }

     /**
     * Método que devuelve la fecha del VueloProgramado.
     * 
     * @return Fecha del vuelo.
     */
    public Date getFecha(){
        return this.capacidadMaxCarga;
    }

     /**
     * Método que devuelve la hora del VueloProgramado.
     * 
     * @return Hora del vuelo.
     */
    public LocalTime getHora(){
        return this.hora;
    }

     /**
     * Método que devuelve los dias de la semana en los que se repite el VueloProgramado.
     * 
     * @return Días de la semana en los que se repite el vuelo.
     */
    public List<DayOfWee> getDiasRecurrente(){
        return this.diasRecurrente;
    }

     /**
     * Método que devuelve el tipo de carga del VueloProgramado.
     * 
     * @return El tipo de carga.
     */
    public TipoCarga getTipoCarga(){
        return this.tipoCarga;
    }

     /**
     * Método que devuelve el modo de embarque del VueloProgramado.
     * 
     * @return El tipo de modo de embarque.
     */
    public TipoModoEmbarque getTipoModoEmbarque(){
        return this.tipoModoEmbarque;
    }

     /**
     * Método que devuelve la periodicidad del VueloProgramado.
     * 
     * @return La periodicidad.
     */
    public Periodicidad getPeriodicidad(){
        return this.periodicidad;
    }

     /**
     * Método que devuelve el tipo de VueloProgramado.
     * 
     * @return Tipo de vuelo.
     */
    public TipoVuelo getTipoVuelo(){
        return this.tipoVuelo;
    }

}