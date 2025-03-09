/**
 * La clase VueloProgramado representa los vuelos que se pretenden añadir a los
 * vuelos del aeropuerto. Este tendrá un tipo de vuelo, de modo de embarque y de 
 * carga, y una periodicidad.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class EstadisticasOperador {
    /** Estadísticas de los vuelos en hora en promedio */
    private String vuelosEnHora;
    /** Estadísticas de los vuelos retrasados en promedio */
    private String vuelosRetrasados;
    /** Estadísticas del tiempo medio de retraso */
    private String tiempoMedioRetraso;

    /**
     * Constructor de la clase EstadisticasOperador.
     * 
     * @param vuelosEnHora              Vuelos en hora del aeropuerto.
     * @param vuelosRetrasados          Vuelos retrasados del aeropuerto.
     * @param tiempoMedioRetraso        Tiempo medio de retraso del aeropuerto.
     */
    public EstadisticasOperador(String vuelosEnHora, String vuelosRetrasados, String tiempoMedioRetraso){
        this.vuelosEnHora=vuelosEnHora;
        this.vuelosRetrasados=vuelosRetrasados;
        this.tiempoMedioRetraso=tiempoMedioRetraso;
    }

}