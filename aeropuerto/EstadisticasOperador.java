package aeropuerto;
/**
 * La clase EstadisticasOperador representa las estadísticas que el 
 * operador puede ver sobre los vuelos de su aerolínea.
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