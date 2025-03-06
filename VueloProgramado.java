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

}