package src.airportmanager.vuelo;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Clase que almacena el historial de cambios de estado de un vuelo.
 */
public class HistorialEstadoVuelo implements Serializable {

    /** Número de versión de la clase */
    private static final long serialVersionUID = 1L;
    /** Historial de vuelo */
    private NavigableMap<LocalTime, EstadoVuelo> historial;

    /**
     * Constructor de la clase HistorialEstadoVuelo.
     */
    public HistorialEstadoVuelo() {
        this.historial = new TreeMap<>();
    }

    /**
     * Agrega un nuevo cambio de estado al historial.
     * 
     * @param estado     Nuevo estado del vuelo.
     * @param horaCambio Hora en la que ocurrió el cambio.
     */
    public void agregarCambioEstado(EstadoVuelo estado, LocalTime horaCambio) {
        historial.put(horaCambio, estado);
    }

    /**
     * Devuelve el historial completo de cambios de estado.
     * 
     * @return Mapa con las horas y sus estados correspondientes.
     */
    public Map<LocalTime, EstadoVuelo> getHistorial() {
        return historial;
    }

    /**
     * Obtiene el último estado del vuelo.
     * 
     * @return Último estado registrado o null si no hay registros.
     */
    public EstadoVuelo getUltimoEstado() {
        if (historial.isEmpty()) {
            return null;
        }
        return historial.lastEntry().getValue();
    }

    /**
     * Obtiene el último estado MANUAL del vuelo.
     * 
     * @return Último estado manual registrado o null si no hay.
     */
    public EstadoVuelo getUltimoEstadoManual() {
        if (historial.isEmpty()) {
            return null;
        }
        
        NavigableMap<LocalTime, EstadoVuelo> copia = new TreeMap<>();
        copia=historial;
        for (Map.Entry<LocalTime, EstadoVuelo> entry : copia.descendingMap().entrySet()) {
            if (!entry.getValue().esAutomatico()) {
                return entry.getValue();
            }
        }
        return null; 
    }
    
    /**
     * Obtiene el último estado AUTOMÁTICO del vuelo.
     * 
     * @return Último estado automático registrado o null si no hay.
     */
    public EstadoVuelo getUltimoEstadoAutomatico() {
        if (historial.isEmpty()) {
            return null;
        }
        NavigableMap<LocalTime, EstadoVuelo> copia = new TreeMap<>();
        copia=historial;
        for (Map.Entry<LocalTime, EstadoVuelo> entry : copia.descendingMap().entrySet()) {
            if (entry.getValue().esAutomatico()) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Obtiene la hora exacta en la que se cambió a un estado específico.
     * 
     * @param estado Estado del vuelo a buscar.
     * @return La hora en la que el vuelo cambió a ese estado, o null si no se encontró.
     */
    public LocalTime getHoraCambio(EstadoVuelo estado) {
        LocalTime ultimaHora = null;
        for (Map.Entry<LocalTime, EstadoVuelo> entry : historial.entrySet()) {
            if (entry.getValue() == estado) {
                ultimaHora = entry.getKey(); 
            }
        }
        return ultimaHora;
    }
    
    /**
     * Verifica si un estado de vuelo ya está presente en el historial.
     * 
     * @param vuelo Estado de vuelo a buscar.
     * @return true si el estado está en el historial, false en caso contrario.
     */
    public boolean contains(EstadoVuelo vuelo) {
        return historial.containsValue(vuelo);
    }
}
