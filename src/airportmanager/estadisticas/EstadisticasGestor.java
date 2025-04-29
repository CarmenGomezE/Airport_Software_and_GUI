package src.airportmanager.estadisticas;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

import src.airportmanager.hangar.Hangar;
import src.airportmanager.terminal.Finger;
import src.airportmanager.terminal.PuertaEmbarque;
import src.airportmanager.terminal.ZonaAparcamiento;
import src.airportmanager.usuario.Gestor;
import src.airportmanager.vuelo.*;

/**
 * La clase EstadisticasGestor representa las estadísticas de uso de las diferentes
 * infraestructuras del aeropuerto, tales como los fingers, las puertas de embarque,
 * las zonas de aparcamiento y los hangares.
 * Permite calcular y mostrar el tiempo de uso de cada infraestructura.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class EstadisticasGestor implements Serializable {

    /** Número de versión de la clase */
    private static final long serialVersionUID = 1L;

    /** Gestor que accede a las estadísticas */
    private Gestor gestor;

    /** Mapa que almacena el tiempo de uso de cada finger (en minutos) */
    private Map<Finger, Double> horasUsoFinger;

    /** Mapa que almacena el tiempo de uso de cada puerta de embarque (en minutos) */
    private Map<PuertaEmbarque, Double> horasUsoPuertaEmbarque;

    /** Mapa que almacena el tiempo de uso de cada zona de aparcamiento (en minutos) */
    private Map<ZonaAparcamiento, Double> horasUsoZonaAparcamiento;

    /** Mapa que almacena el tiempo de uso de cada hangar (en minutos) */
    private Map<Hangar, Double> horasUsoHangar;
    
    /** Horas diarias que opera el aeropuerto */
    double horasOperacionDiarias;
    
    /** Dias del mes al que se le generan estadísticas */
    int diasDelMes;
    
    /** Horas mensuales que opera el aeropuerto */
    double totalHorasOperacionMes;

    /**
     * Constructor de la clase EstadisticasGestor.
     * Inicializa los mapas para almacenar las horas de uso de las infraestructuras
     * y carga los datos necesarios para las estadísticas.
     *
     * @param gestor Gestor que recibirá las estadísticas.
     */
    public EstadisticasGestor(Gestor gestor) {
        this.gestor = gestor;
        this.horasUsoFinger = new HashMap<>();
        this.horasUsoPuertaEmbarque = new HashMap<>();
        this.horasUsoZonaAparcamiento = new HashMap<>();
        this.horasUsoHangar = new HashMap<>();
        this.horasOperacionDiarias = gestor.getAeropuerto().getHorasOperacion();
        YearMonth mesActual = YearMonth.now();
        this.diasDelMes = mesActual.lengthOfMonth();
        this.totalHorasOperacionMes = horasOperacionDiarias * diasDelMes;
        cargarDatosEstadisticas();
    }

    /**
     * Carga los datos necesarios para las estadísticas del gestor.
     * Este método calcula las horas de uso de cada infraestructura (finger, 
     * puerta de embarque, zona de aparcamiento y hangar) y las guarda en los 
     * mapas correspondientes.
     */
    /**
     * Carga los datos necesarios para las estadísticas del gestor.
     * Este método calcula las horas de uso de cada infraestructura (finger, 
     * puerta de embarque, zona de aparcamiento y hangar) y las guarda en los 
     * mapas correspondientes.
     */
    public void cargarDatosEstadisticas() {
        YearMonth mesActual = YearMonth.now();

        for (PuertaEmbarque puerta : gestor.getAeropuerto().getPuertasEmbarque()) {
            double horasUso = calcularTiempoUsoPuertaEmbarque(puerta, mesActual);
            horasUsoPuertaEmbarque.put(puerta, horasUso);
        }

        for (ZonaAparcamiento zona : gestor.getAeropuerto().getZonasAparcamiento()) {
            double horasUso = calcularTiempoUsoZonaAparcamiento(zona, mesActual);
            horasUsoZonaAparcamiento.put(zona, horasUso);
        }

        for (Hangar hangar : gestor.getAeropuerto().getHangares()) {
            double horasUso = calcularTiempoUsoHangar(hangar, mesActual);
            horasUsoHangar.put(hangar, horasUso);
        }

        for (PuertaEmbarque puerta : gestor.getAeropuerto().getPuertasEmbarque()) {
            Finger finger = puerta.getFinger();
            if (finger != null) {
                double horasUso = calcularTiempoUsoFinger(finger, mesActual);
                horasUsoFinger.put(finger, horasUso);
            }
        }
    }

    /**
     * Calcula el tiempo total de uso de un finger, basado en los cambios de estado
     * entre INICIO_EMBARQUE y FIN_EMBARQUE, filtrando solo los vuelos del mes actual.
     *
     * @param finger El finger cuya estadística de uso se va a calcular.
     * @return El tiempo total de uso del finger (en minutos).
     */
    private double calcularTiempoUsoFinger(Finger finger, YearMonth mesActual) {
        double totalTiempoUso = 0;

        for (Vuelo vuelo : gestor.getAeropuerto().getVuelosAceptados()) {
            if (!mesActual.equals(YearMonth.from(vuelo.getFecha()))) continue;

            HistorialEstadoVuelo historial = vuelo.getHistorialEstadoVuelo();
            LocalTime inicioEmbarque = historial.getHoraCambio(EstadoVuelo.INICIO_DEL_EMBARQUE);
            LocalTime finEmbarque = historial.getHoraCambio(EstadoVuelo.FIN_DEL_EMBARQUE);
            if (inicioEmbarque != null && finEmbarque != null) {
                Duration duracion = Duration.between(inicioEmbarque, finEmbarque);
                if (duracion.isNegative() || duracion.isZero()) {
                    continue; 
                }
                totalTiempoUso += duracion.toMinutes();
            }
        }

        return totalTiempoUso;
    }

    /**
     * Calcula el tiempo total de uso de una puerta de embarque, filtrando los vuelos
     * solo por el mes actual.
     *
     * @param puerta La puerta de embarque cuya estadística de uso se va a calcular.
     * @return El tiempo total de uso de la puerta de embarque (en minutos).
     */
    private double calcularTiempoUsoPuertaEmbarque(PuertaEmbarque puerta, YearMonth mesActual) {
        double totalTiempoUso = 0;

        for (Vuelo vuelo : gestor.getAeropuerto().getVuelosAceptados()) {
            if (!mesActual.equals(YearMonth.from(vuelo.getFecha()))) continue;

            HistorialEstadoVuelo historial = vuelo.getHistorialEstadoVuelo();
            LocalTime inicio = null;
            LocalTime fin = null;
            if (vuelo.getTipoVuelo() == TipoVuelo.SALIDA) {
                inicio = historial.getHoraCambio(EstadoVuelo.EN_PREPARACION_PARA_DESPEGAR);
                fin = historial.getHoraCambio(EstadoVuelo.ESPERANDO_PISTA_DE_DESPEGUE);
            } else {
                inicio = historial.getHoraCambio(EstadoVuelo.ATERRIZAJE);
                fin = historial.getHoraCambio(EstadoVuelo.FIN_DEL_DESEMBARQUE);
            }

            if (inicio != null && fin != null) {
                Duration duracion = Duration.between(inicio, fin);
                if (duracion.isNegative() || duracion.isZero()) {
                    continue; 
                }
                totalTiempoUso += duracion.toMinutes();
            }
        }

        return totalTiempoUso;
    }

    /**
     * Calcula el tiempo total de uso de una zona de aparcamiento, filtrando los vuelos
     * solo por el mes actual.
     *
     * @param zona La zona de aparcamiento cuya estadística de uso se va a calcular.
     * @return El tiempo total de uso de la zona de aparcamiento (en minutos).
     */
    private double calcularTiempoUsoZonaAparcamiento(ZonaAparcamiento zona, YearMonth mesActual) {
        double totalTiempoUso = 0;

        for (Vuelo vuelo : gestor.getAeropuerto().getVuelosAceptados()) {
            if (!mesActual.equals(YearMonth.from(vuelo.getFecha()))) continue;

            HistorialEstadoVuelo historial = vuelo.getHistorialEstadoVuelo();
            LocalTime inicioEmbarque = historial.getHoraCambio(EstadoVuelo.INICIO_DEL_EMBARQUE);
            LocalTime finEmbarque = historial.getHoraCambio(EstadoVuelo.FIN_DEL_EMBARQUE);
            if (inicioEmbarque != null && finEmbarque != null) {
                Duration duracion = Duration.between(inicioEmbarque, finEmbarque);
                if (duracion.isNegative() || duracion.isZero()) {
                    continue; 
                }
                totalTiempoUso += duracion.toMinutes();
            }
        }

        return totalTiempoUso;
    }

    /**
     * Calcula el tiempo total de uso de un hangar, filtrando los vuelos
     * solo por el mes actual.
     *
     * @param hangar El hangar cuya estadística de uso se va a calcular.
     * @return El tiempo total de uso del hangar (en minutos).
     */
    private double calcularTiempoUsoHangar(Hangar hangar, YearMonth mesActual) {
        double totalTiempoUso = 0.0;

        for (Vuelo vuelo : gestor.getAeropuerto().getVuelosAceptados()) {
            if (!mesActual.equals(YearMonth.from(vuelo.getFecha()))) continue;

            HistorialEstadoVuelo historial = vuelo.getHistorialEstadoVuelo();
            LocalTime inicioEmbarque = historial.getHoraCambio(EstadoVuelo.AVION_A_HANGAR);

            if (inicioEmbarque == null) {
                continue;
            }

            LocalDate fechaVuelo = vuelo.getFecha();
            LocalDateTime inicioEmbarqueDateTime = LocalDateTime.of(fechaVuelo, inicioEmbarque);
            List<Vuelo> vuelosDelAvion = vuelo.getAvion().getAerolinea().getVuelos();

            vuelosDelAvion = vuelosDelAvion.stream()
                .filter(v -> v.getAvion().equals(vuelo.getAvion()))
                .sorted(Comparator.comparing(v -> LocalDateTime.of(v.getFecha(), v.getHora())))
                .collect(Collectors.toList());

            boolean found = false;
            for (int i = 0; i < vuelosDelAvion.size(); i++) {
                Vuelo proximoVuelo = vuelosDelAvion.get(i);

                if (found) {
                    LocalDate fechaProximoVuelo = proximoVuelo.getFecha();
                    LocalTime horaFin = proximoVuelo.getHora();

                    LocalDateTime fin = LocalDateTime.of(fechaProximoVuelo, horaFin);

                    if (inicioEmbarqueDateTime != null && fin != null) {
                        Duration duracion = Duration.between(inicioEmbarqueDateTime, fin);
                        if (duracion.isNegative() || duracion.isZero()) {
                            continue; 
                        }
                        totalTiempoUso += duracion.toMinutes();
                    }
                    break;
                }

                if (vuelosDelAvion.get(i).equals(vuelo)) {
                    found = true;
                }
            }
        }

        return totalTiempoUso;
    }

    /**
     * Devuelve el tiempo total de uso mensual de un finger.
     *
     * @param finger El finger cuya estadística de uso mensual se desea obtener.
     * @return Tiempo total de uso en el mes (en horas).
     */
    public double getUsoMensualFinger(Finger finger) {
    	if(horasUsoFinger.getOrDefault(finger, 0.0)==0.0) {
    		return 0.0;
    	}
        return horasUsoFinger.getOrDefault(finger, 0.0)/totalHorasOperacionMes;
    }

    /**
     * Devuelve el tiempo medio diario de uso de una puerta de embarque.
     *
     * @param puerta La puerta de embarque cuyo tiempo medio diario de uso se va a obtener.
     * @return El tiempo medio diario de uso de la puerta de embarque (en horas).
     */
    public double getUsoMensualPuertaEmbarque(PuertaEmbarque puerta) {
    	if(horasUsoPuertaEmbarque.getOrDefault(puerta, 0.0)==0.0) {
    		return 0.0;
    	}
        return horasUsoPuertaEmbarque.getOrDefault(puerta, 0.0)/totalHorasOperacionMes;
    }
    /**
     * Devuelve el tiempo medio diario de uso de una zona de aparcamiento.
     *
     * @param zona La zona de aparcamiento cuyo tiempo medio diario de uso se va a obtener.
     * @return El tiempo medio diario de uso de la zona de aparcamiento (en horas).
     */
    public double getUsoMensualZonaAparcamiento(ZonaAparcamiento zona) {
    	if(horasUsoZonaAparcamiento.getOrDefault(zona, 0.0)==0.0) {
    		return 0.0;
    	}
        return horasUsoZonaAparcamiento.getOrDefault(zona, 0.0)/totalHorasOperacionMes;
    }


    /**
     * Devuelve el tiempo medio diario de uso de un hangar.
     *
     * @param hangar El hangar cuyo tiempo medio diario de uso se va a obtener.
     * @return El tiempo medio diario de uso del hangar (en horas).
     */
    public double getUsoMensualHangar(Hangar hangar) {
    	if(horasUsoHangar.getOrDefault(hangar, 0.0)==0.0) {
    		return 0.0;
    	}
        return horasUsoHangar.getOrDefault(hangar, 0.0)/totalHorasOperacionMes;
    }

    /**
     * Imprime las estadísticas de uso medio diario de los fingers del aeropuerto.
     */
    public void imprimirUsoMedioFingers() {
        System.out.println("Estadísticas de uso de los fingers:");
        for (PuertaEmbarque puerta : gestor.getAeropuerto().getPuertasEmbarque()) {
            Finger finger = puerta.getFinger();
            if (finger != null) {
                System.out.println("Finger:" + finger.getId() + " Tiempo de uso medio diario: " + getUsoMensualFinger(finger) + " horas.");
            }
        }
    }

    /**
     * Imprime las estadísticas de uso medio diario de las puertas de embarque del aeropuerto.
     */
    public void imprimirUsoMedioPuertasEmbarque() {
        System.out.println("Estadísticas de uso de las puertas de embarque:");
        for (PuertaEmbarque puerta : gestor.getAeropuerto().getPuertasEmbarque()) {
            System.out.println("Puerta de embarque:" + puerta.getId() + " Tiempo de uso medio diario: " + getUsoMensualPuertaEmbarque(puerta) + " horas.");
        }
    }

    /**
     * Imprime las estadísticas de uso medio diario de las zonas de aparcamiento del aeropuerto.
     */
    public void imprimirUsoMedioZonasAparcamiento() {
        System.out.println("Estadísticas de uso de las zonas de aparcamiento:");
        for (ZonaAparcamiento zona : gestor.getAeropuerto().getZonasAparcamiento()) {
            System.out.println("Zona de aparcamiento:" + zona.getId() + " Tiempo de uso medio diario: " + getUsoMensualZonaAparcamiento(zona) + " horas.");
        }
    }

    /**
     * Imprime las estadísticas de uso medio diario de los hangares del aeropuerto.
     */
    public void imprimirUsoMedioHangares() {
        System.out.println("Estadísticas de uso de los hangares:");
        for (Hangar hangar : gestor.getAeropuerto().getHangares()) {
            System.out.println("Hangar:" + hangar.getId() + " Tiempo de uso medio diario: " + getUsoMensualHangar(hangar) + " horas.");
        }
    }
}
