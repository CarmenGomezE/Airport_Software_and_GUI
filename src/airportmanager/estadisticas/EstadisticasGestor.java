package src.airportmanager.estadisticas;

import java.io.Serializable;
import java.util.*;

import src.airportmanager.hangar.Hangar;
import src.airportmanager.terminal.Finger;
import src.airportmanager.terminal.PuertaEmbarque;
import src.airportmanager.terminal.ZonaAparcamiento;
import src.airportmanager.usuario.Gestor;

/**
 * La clase EstadisticasGestor representa las estadísticas que el
 * gestor puede ver sobre la infraestructura del aeropuerto.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class EstadisticasGestor implements Serializable {

	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;
    /** Gestor que ve las estadísticas */
    private Gestor gestor;
    /** Estadísticas de los fingers del aeropuerto */
    private Map<Finger, Double> horasUsoFinger;
    /** Estadísticas de las puerta de embarque del aeropuerto */
    private Map<PuertaEmbarque, Double> horasUsoPuertaEmbarque;
    /** Estadísticas de las zonas de aparcamiento del aeropuerto */
    private Map<ZonaAparcamiento, Double> horasUsoZonaAparcamiento;
    /** Estadísticas de los hangares del aeropuerto */
    private Map<Hangar, Double> horasUsoHangar;

    /**
     * Constructor de la clase EstadisticasGestor.
     * 
     * @param getsor Gestor que recibe las estadísticas.
     */
    public EstadisticasGestor(Gestor gestor) {
        this.gestor = gestor;
        this.horasUsoFinger = new HashMap<>();
        this.horasUsoPuertaEmbarque = new HashMap<>();
        this.horasUsoZonaAparcamiento = new HashMap<>();
        this.horasUsoHangar = new HashMap<>();
        cargarDatosEstadisticas();
    }

    /**
     * Método que carga los datos necesarios para las estadísticas del gestor.
     * 
     */
    private void cargarDatosEstadisticas() {
        for (Finger finger : gestor.getAeropuerto().getFingers()) {
            horasUsoFinger.put(finger, finger.getHorasUso() / gestor.getAeropuerto().getHorasOperacion());
        }

        for (PuertaEmbarque puerta : gestor.getAeropuerto().getPuertasEmbarque()) {
            horasUsoPuertaEmbarque.put(puerta, puerta.getHorasUso() / gestor.getAeropuerto().getHorasOperacion());
        }

        for (ZonaAparcamiento zona : gestor.getAeropuerto().getZonasAparcamiento()) {
            horasUsoZonaAparcamiento.put(zona, zona.getHorasUso() / gestor.getAeropuerto().getHorasOperacion());
        }

        for (Hangar hangar : gestor.getAeropuerto().getHangares()) {
            horasUsoHangar.put(hangar, hangar.getHorasUso() / gestor.getAeropuerto().getHorasOperacion());
        }
    }

    /**
     * Método que devuelve las horas de uso medio diario de un finger.
     * 
     * @param finger Finger.
     * @return Horas de uso medio de un finger.
     */
    public double getUsoMedioDiarioFinger(Finger finger) {
        return horasUsoFinger.getOrDefault(finger, 0.0);
    }

    /**
     * Método que devuelve las horas de uso medio diario de una puerta de embarque.
     * 
     * @param puerta PuertaEmbarque.
     * @return Horas de uso medio de una puerta de embarque.
     */
    public double getUsoMedioDiarioPuertaEmbarque(PuertaEmbarque puerta) {
        return horasUsoPuertaEmbarque.getOrDefault(puerta, 0.0);
    }

    /**
     * Método que devuelve las horas de uso medio diario de una zona de
     * aparcamiento.
     * 
     * @param zona ZonaAparcamiento.
     * @return Horas de uso medio de una zona de aparcamiento.
     */
    public double getUsoMedioDiarioZonaAparcamiento(ZonaAparcamiento zona) {
        return horasUsoZonaAparcamiento.getOrDefault(zona, 0.0);
    }

    /**
     * Método que devuelve las horas de uso medio diario de un hangar.
     * 
     * @param hangar Hangar.
     * @return Horas de uso medio de un hangar.
     */
    public double getUsoMedioDiarioHangar(Hangar hangar) {
        return horasUsoHangar.getOrDefault(hangar, 0.0);
    }

    /**
     * Método que imprime los tiempos medios de cada finger.
     * 
     */
    public void imprimirUsoMedioFingers() {
        System.out.println("Estadísticas de uso de los fingers:");
        for (Finger finger : gestor.getAeropuerto().getFingers()) {
            System.out.println("Finger: " + finger.getId() + ", Tiempo de uso medio diario: "
                    + getUsoMedioDiarioFinger(finger) + " horas.");
        }
    }

    /**
     * Método que imprime los tiempos medios de cada puerta de embarque.
     * 
     */
    public void imprimirUsoMedioPuertasEmbarque() {
        System.out.println("Estadísticas de uso de las puertas de embarque:");
        for (PuertaEmbarque puerta : gestor.getAeropuerto().getPuertasEmbarque()) {
            System.out.println("Puerta de embarque: " + puerta.getId() + ", Tiempo de uso medio diario: "
                    + getUsoMedioDiarioPuertaEmbarque(puerta) + " horas.");
        }
    }

    /**
     * Método que imprime los tiempos medios de cada zona de aparcamiento.
     * 
     */
    public void imprimirUsoMedioZonasAparcamiento() {
        System.out.println("Estadísticas de uso de las zonas de aparcamiento:");
        for (ZonaAparcamiento zona : gestor.getAeropuerto().getZonasAparcamiento()) {
            System.out.println("Zona de aparcamiento: " + zona.getId() + ", Tiempo de uso medio diario: "
                    + getUsoMedioDiarioZonaAparcamiento(zona) + " horas.");
        }
    }

    /**
     * Método que imprime los tiempos medios de cada hangar.
     * 
     */
    public void imprimirUsoMedioHangares() {
        System.out.println("Estadísticas de uso de los hangares:");
        for (Hangar hangar : gestor.getAeropuerto().getHangares()) {
            System.out.println("Hangar: " + hangar.getId() + ", Tiempo de uso medio diario: "
                    + getUsoMedioDiarioHangar(hangar) + " horas.");
        }
    }
}