package aeropuerto.carmen;
import aeropuerto.claudia.*;
import java.util.*;
import java.time.*;
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
    private Map<Month, Double> tiempoMedioRetrasoPorMes;
    /** Estadísticas del tiempo medio de retraso por franja horaria */
    private Map<ZoneId, Double> tiempoMedioRetrasoPorFranjaHoraria;
    /** Estadísticas del tiempo medio de retraso por vuelo*/
    private Map<Vuelo, Double> tiempoMedioRetrasoPorVuelo;


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