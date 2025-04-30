package src.airportmanager.estadisticas;

import java.util.*;


import src.airportmanager.usuario.Operador;
import src.airportmanager.vuelo.Vuelo;

import java.io.Serializable;
import java.time.*;

/**
 * La clase EstadisticasOperador representa las estadísticas que el
 * operador puede ver sobre los vuelos de su aerolínea.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class EstadisticasOperador implements Serializable {

	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;
    /** Operador que ve las estadísticas */
    private Operador operador;
    /** Estadísticas del tiempo medio de retraso por mes */
    private Map<Month, Double> tiempoMedioRetrasoPorMes;
    /** Estadísticas del tiempo medio de retraso por franja horaria */
    private Map<ZoneId, Double> tiempoMedioRetrasoPorFranjaHoraria;
    /** Estadísticas del tiempo medio de retraso por vuelo */
    private Map<Vuelo, Double> tiempoMedioRetrasoPorVuelo;

    /**
     * Constructor de la clase EstadisticasOperador.
     * 
     * @param operador Operador que recibe las estadísticas.
     */
    public EstadisticasOperador(Operador operador) {
        this.operador = operador;
        this.tiempoMedioRetrasoPorMes = new HashMap<>();
        this.tiempoMedioRetrasoPorFranjaHoraria = new HashMap<>();
        this.tiempoMedioRetrasoPorVuelo = new HashMap<>(); 
    }

    /**
     * Método que genera los datos de tiempos medios.
     * 
     */
    public void cargarDatosEstadisticasTiempoMedio() {
        Map<Month, List<Double>> retrasosPorMes = new HashMap<>();
        Map<ZoneId, List<Double>> retrasosPorFranjaHoraria = new HashMap<>();
        Map<Vuelo, List<Double>> retrasosPorVuelo = new HashMap<>();

        for (Vuelo vuelo : operador.getGestor().getAeropuerto().getVuelosAceptados()) {
        	if(operador.getAerolinea().getVuelos().contains(vuelo)) {
        		Month mes = vuelo.getFecha().getMonth();
	            ZoneId franjaHoraria = vuelo.getAeropuertoDestino().getDiferenciaHoraria();
	            double retraso = vuelo.getRetraso();
	            
	            if (retraso >= 0) {
	                if (!retrasosPorMes.containsKey(mes)) {
	                    retrasosPorMes.put(mes, new ArrayList<>());
	                }
	                retrasosPorMes.get(mes).add(retraso);
	
	                if (!retrasosPorFranjaHoraria.containsKey(franjaHoraria)) {
	                    retrasosPorFranjaHoraria.put(franjaHoraria, new ArrayList<>());
	                }
	                retrasosPorFranjaHoraria.get(franjaHoraria).add(retraso);
	
	                if (!retrasosPorVuelo.containsKey(vuelo)) {
	                    retrasosPorVuelo.put(vuelo, new ArrayList<>());
	                }
	                retrasosPorVuelo.get(vuelo).add(retraso);
	            }
        	}
        }

        if (!retrasosPorMes.isEmpty()) {
            for (Month mes : retrasosPorMes.keySet()) {
                tiempoMedioRetrasoPorMes.put(mes, calcularMedia(retrasosPorMes.get(mes)));
            }
        }

        if (!retrasosPorFranjaHoraria.isEmpty()) {
            for (ZoneId franja : retrasosPorFranjaHoraria.keySet()) {
                tiempoMedioRetrasoPorFranjaHoraria.put(franja, calcularMedia(retrasosPorFranjaHoraria.get(franja)));
            }
        } 

        if (!retrasosPorVuelo.isEmpty()) {
            for (Vuelo vuelo : retrasosPorVuelo.keySet()) {
                tiempoMedioRetrasoPorVuelo.put(vuelo, calcularMedia(retrasosPorVuelo.get(vuelo)));
            }
        }
    }

    /**
     * Método que genera los datos de tiempos medios.
     * 
     * @param list Lista de todos los retrasos registrados.
     * @return La media de retraso.
     */
    public double calcularMedia(List<Double> list) {
        if (list.isEmpty()) {
            return 0;
        }

        double suma = 0;
        for (double retraso : list) {
            suma += retraso;
        }
        return (double) suma / list.size();
    }

    /**
     * Método que genera los vuelos en hora.
     * 
     * 
     * @return Número de vuelos en hora.
     */
    public int numeroVuelosEnHora() {
        int count = 0;
        for (Vuelo vuelo : operador.getAerolinea().getVuelos()) {
            if (vuelo.getRetraso() <= 0) {
                count++;
                System.out.println("Vuelo: " + vuelo.getCodigo() + " | Retraso: " + vuelo.getRetraso() + " minutos");
            }
        }
        return count;
    }

    /**
     * Método que genera los vuelos retrasados.
     * 
     * 
     * @return Número de vuelos retrasados.
     */
    public int numeroVuelosRetrasados() {
        int count = 0;
        for (Vuelo vuelo : operador.getAerolinea().getVuelos()) {
            if (vuelo.getRetraso() > 0) {
                count++;
                System.out.println("Vuelo: " + vuelo.getCodigo() + " | Retraso: " + vuelo.getRetraso() + " minutos");
            }
        }
        return count;
    }

    /**
     * Método que calcula el tiempo medio de retraso por mes.
     * 
     * @param mes Mes del que se devuelve el tiempo medio de retraso.
     * @return Tiempo medio de retraso por mes.
     */
    public double getTiempoMedioRetrasoPorMes(Month mes) {
        return tiempoMedioRetrasoPorMes.getOrDefault(mes, 0.0);
    }

    /**
     * Método que calcula el tiempo medio de retraso por franja horaria.
     * 
     * @param franjaHoraria Franja horaria de la que se devuelve el tiempo medio de
     *                      retraso.
     * @return Tiempo medio de retraso por franja horaria.
     */
    public double getTiempoMedioRetrasoPorFranjaHoraria(ZoneId franjaHoraria) {
        return tiempoMedioRetrasoPorFranjaHoraria.getOrDefault(franjaHoraria, 0.0);
    }

    /**
     * Método que calcula el tiempo medio de retraso por vuelo.
     * 
     * @param vuelo Vuelo del que se devuelve el tiempo medio de retraso.
     * @return Tiempo medio de retraso por vuelo.
     */
    public double getTiempoMedioRetrasoPorVuelo(Vuelo vuelo) {
        return tiempoMedioRetrasoPorVuelo.getOrDefault(vuelo, 0.0);
    }

    /**
     * Método que imprime el número de vuelos en hora.
     * 
     */
    public void imprimirNumeroVuelosEnHora() {
        System.out.println("Número de vuelos en hora: " + numeroVuelosEnHora());
    }

    /**
     * Método que imprime el número de vuelos retrasados.
     * 
     */
    public void imprimirNumeroVuelosRetrasados() {
        System.out.println("Número de vuelos retrasados: " + numeroVuelosRetrasados());
    }

    /**
     * Método que imprime el tiempo medio para un mes.
     * @param mes Mes
     * 
     */
    public void imprimirTiempoMedioRetraso(Month mes) {
        System.out.println("Tiempo medio de retraso: " + getTiempoMedioRetrasoPorMes(mes) + " minutos.");
    }

    /**
     * Método que imprime el tiempo medio para una franja horaria.
     * @param franjaHoraria	Franja horaria.
     * 
     */
    public void imprimirTiempoMedioRetraso(ZoneId franjaHoraria) {
        System.out.println("Tiempo medio de retraso: " + getTiempoMedioRetrasoPorFranjaHoraria(franjaHoraria) + " minutos.");
    }
    
    /**
     * Método que imprime el tiempo medio para un vuelo.
     * 
     * @param vuelo Vuelo.
     */
    public void imprimirTiempoMedioRetraso(Vuelo vuelo) {
        System.out.println("Tiempo medio de retraso:" + getTiempoMedioRetrasoPorVuelo(vuelo) + " minutos.");
    }
}
