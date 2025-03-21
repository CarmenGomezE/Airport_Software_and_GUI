package aeropuerto.src.carmen;

import aeropuerto.src.carmen.vuelo.VueloProgramado;
import aeropuerto.src.claudia.*;
import java.util.*;
import java.time.*;

/**
 * La clase EstadisticasOperador representa las estadísticas que el
 * operador puede ver sobre los vuelos de su aerolínea.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class EstadisticasOperador {
    /* Operador que ve las estadísticas */
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
     * @param vuelosEnHora                       Vuelos en hora del aeropuerto.
     * @param vuelosRetrasados                   Vuelos retrasados del aeropuerto.
     * @param tiempoMedioRetrasoPorMes           Tiempo medio de retraso del
     *                                           aeropuerto por mes.
     * @param tiempoMedioRetrasoPorFranjaHoraria Tiempo medio de retraso del
     *                                           aeropuerto por franja horaria.
     * @param tiempoMedioRetrasoPorVuelo         Tiempo medio de retraso del
     *                                           aeropuerto por vuelo.
     */
    public EstadisticasOperador(Operador operador) {
        this.operador = operador;
        this.tiempoMedioRetrasoPorMes = new HashMap<>();
        this.tiempoMedioRetrasoPorFranjaHoraria = new HashMap<>();
        this.tiempoMedioRetrasoPorVuelo = new HashMap<>();

    }

    private void cargarDatosEstadisticasTiempoMedio() {
        Map<Month, List<Integer>> retrasosPorMes = new HashMap<>();
        Map<ZoneId, List<Integer>> retrasosPorFranjaHoraria = new HashMap<>();
        Map<Vuelo, List<Integer>> retrasosPorVuelo = new HashMap<>();

        for (Vuelo vuelo : operador.getVuelos()) {
            Month mes = vuelo.getFecha().getMes();
            ZoneId franjaHoraria = vuelo.getAeropuertoDestino().getDiferenciaHoraria();
            int retraso = vuelo.getRetraso();

            retrasosPorMes.putIfAbsent(mes, new ArrayList<>());
            retrasosPorMes.get(mes).add(retraso);

            retrasosPorFranjaHoraria.putIfAbsent(franjaHoraria, new ArrayList<>());
            retrasosPorFranjaHoraria.get(franjaHoraria).add(retraso);

            retrasosPorVuelo.putIfAbsent(vuelo, new ArrayList<>());
            retrasosPorVuelo.get(vuelo).add(retraso);
        }

        for (Month mes : retrasosPorMes.keySet()) {
            tiempoMedioRetrasoPorMes.put(mes, calcularMedia(retrasosPorMes.get(mes)));
        }

        for (ZoneId franja : retrasosPorFranjaHoraria.keySet()) {
            tiempoMedioRetrasoPorFranjaHoraria.put(franja, calcularMedia(retrasosPorFranjaHoraria.get(franja)));
        }

        for (Vuelo vuelo : retrasosPorVuelo.keySet()) {
            tiempoMedioRetrasoPorVuelo.put(vuelo, calcularMedia(retrasosPorVuelo.get(vuelo)));
        }

    }

    public double numeroVuelosEnHora(Month mes) {
        int count = 0;
        for (Vuelo vuelo : this.operador.getAerolinea().vuelosMensuales(mes)) {
            if (vuelo.getMes().equals(mes) && vuelo.getRetraso() == 0) {
                count++;
            }
        }
        return count;
    }

    public double numeroVuelosRetrasados(Month mes) {
        int count = 0;
        for (Vuelo vuelo : vuelosRetrasados) {
            if (vuelo.getMes().equals(mes) && vuelo.getRetraso() > 0) {
                count++;
            }
        }
        return count;
    }

    public double tiempoMedioRetrasoPorMes(Month mes) {
        double sumaRetrasos = 0;
        int count = 0;
        for (Vuelo vuelo : vuelosRetrasados) {
            if (vuelo.getMes().equals(mes)) {
                sumaRetrasos += vuelo.getRetraso();
                count++;
            }
        }
        return count == 0 ? 0 : sumaRetrasos / count;
    }

    public double tiempoMedioRetrasoPorFranjaHoraria(ZoneId franjaHoraria) {
        double sumaRetrasos = 0;
        int count = 0;
        for (Vuelo vuelo : vuelosRetrasados) {
            if (vuelo.getFranjaHoraria().equals(franjaHoraria)) {
                sumaRetrasos += vuelo.getRetraso();
                count++;
            }
        }
        return count == 0 ? 0 : sumaRetrasos / count;
    }

    public double tiempoMedioRetrasoPorVuelo(Vuelo vuelo) {
        return tiempoMedioRetrasoPorVuelo.getOrDefault(vuelo, 0.0);
    }

    private double calcularMedia(List<Integer> retrasos) {
        if (retrasos.isEmpty())
            return 0;

        double suma = 0;
        for (int retraso : retrasos) {
            suma += retraso;
        }

        return suma / retrasos.size();
    }
}