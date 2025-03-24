package src.airportmanager.vuelo;

import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Clase que almacena el historial de cambios de estado de un vuelo.
 */
public class HistorialEstadoVuelo {
    private Map<LocalTime, EstadoVuelo> historial;

    /**
     * Constructor de la clase HistorialEstadoVuelo.
     */
    public HistorialEstadoVuelo() {
        this.historial = new LinkedHashMap<>();
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
        if (historial.isEmpty())
            return null;
        return historial.values().stream().reduce((first, second) -> second).orElse(null);
    }

    /**
     * Obtiene la hora exacta en la que se cambió a un estado específico.
     * 
     * @param estado Estado del vuelo a buscar.
     * @return La hora en la que el vuelo cambió a ese estado, o null si no se
     *         encontró.
     */
    public LocalTime getHoraCambio(EstadoVuelo estado) {
        for (Map.Entry<LocalTime, EstadoVuelo> entry : historial.entrySet()) {
            if (entry.getValue() == estado) {
                return entry.getKey();
            }
        }
        return null;
    }
}
