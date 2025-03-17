package aeropuerto;

import java.time.Month;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
/**
 * La clase EstadisticasOperador representa las estadísticas que el 
 * operador puede ver sobre los vuelos de su aerolínea.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class EstadisticasOperador {
    /** Estadísticas de los vuelos en hora en promedio */
    private double vuelosEnHora;
    /** Estadísticas de los vuelos retrasados en promedio */
    private double vuelosRetrasados;
    /** Estadísticas del tiempo medio de retraso por mes */
    private Map<Month, double> tiempoMedioRetrasoPorMes;
    /** Estadísticas del tiempo medio de retraso por franja horaria */
    private Map<ZoneId, double> tiempoMedioRetrasoPorFranjaHoraria;
    /** Estadísticas del tiempo medio de retraso por vuelo*/
    private Map<Vuelo, double> tiempoMedioRetrasoPorVuelo;


    /**
     * Constructor de la clase EstadisticasOperador.
     * 
     * @param vuelosEnHora                              Vuelos en hora del aeropuerto.
     * @param vuelosRetrasados                          Vuelos retrasados del aeropuerto.
     * @param tiempoMedioRetrasoPorMes                  Tiempo medio de retraso del aeropuerto por mes.
     * @param tiempoMedioRetrasoPorFranjaHoraria        Tiempo medio de retraso del aeropuerto por franja horaria.
     * @param tiempoMedioRetrasoPorVuelo                Tiempo medio de retraso del aeropuerto por vuelo.
     */
    public EstadisticasOperador(double vuelosEnHora, double vuelosRetrasados, Map<String, Double> tiempoMedioRetrasoPorMes,
                                Map<String, Double> tiempoMedioRetrasoPorFranjaHoraria, Map<String, Double> tiempoMedioRetrasoPorVuelo){
        this.vuelosEnHora=vuelosEnHora;
        this.vuelosRetrasados=vuelosRetrasados;
        
        this.tiempoMedioRetrasoPorMes = (tiempoMedioRetrasoPorMes != null) ? tiempoMedioRetrasoPorMes : new HashMap<>();
        this.tiempoMedioRetrasoPorFranjaHoraria = (tiempoMedioRetrasoPorFranjaHoraria != null) ? tiempoMedioRetrasoPorFranjaHoraria : new HashMap<>();
        this.tiempoMedioRetrasoPorVuelo = (tiempoMedioRetrasoPorVuelo != null) ? tiempoMedioRetrasoPorVuelo : new HashMap<>();
    }

    public double numeroVuelosEnHora(Month mes){

    }

    public double numeroVuelosRetrasados(Month mes){

    }

    public double tiempoMedioRetrasoPorMes(Month mes){

    }

    public double tiempoMedioRetrasoPorFranjaHoraria(ZoneId franjaHoraria){
        
    }

    public double tiempoMedioRetrasoPorVuelo(Vuelo vuelo){
        
    }

}