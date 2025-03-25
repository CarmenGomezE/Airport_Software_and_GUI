package src.airportmanager.vuelo;

import java.io.Serializable;
import java.time.LocalTime;

import src.airportmanager.aeropuerto.Aeropuerto;
import src.airportmanager.aeropuerto.DireccionRelativaCiudad;

/**
 * La clase Escala representa las escalas de un vuelo.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class Escala implements Serializable {

	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;
    /** Origen de la escala */
    private Aeropuerto aeropuertoOrigen;
    /** Destino de la escala */
    private Aeropuerto aeropuertoDestino;
    /** Hora de salida de la escala */
    private LocalTime horaSalida;
    /** Hora de llegada de la escala */
    private LocalTime horaLlegada;

    /**
     * Constructor de la clase Escala.
     * 
     * @param origen      Origen de la escala.
     * @param destino     Destino de la escala.
     * @param horaSalida  Hora de salida de la escala.
     * @param horaLlegada Hora de llegada de la escala.
     */
    public Escala(Aeropuerto aeropuertoOrigen, Aeropuerto aeropuertoDestino, LocalTime horaLlegada,
            LocalTime horaSalida) {
        this.aeropuertoOrigen = aeropuertoOrigen;
        this.aeropuertoDestino = aeropuertoDestino;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
    }

    /**
     * Método que devuelve el aeropuerto de destino.
     * 
     * @return Aeropuerto de destino.
     */
    public Aeropuerto getAeropuertoDestino() {
        return this.aeropuertoDestino;
    }

    /**
     * Método que devuelve el aeropuerto de origen.
     * 
     * @return Aeropuerto de origen.
     */
    public Aeropuerto getAeropuertoOrigen() {
        return this.aeropuertoOrigen;
    }

    /**
     * Método que devuelve el la hora de llegada.
     * 
     * @return Hora de llegada.
     */
    public LocalTime getHoraLlegada() {
        return this.horaSalida;
    }

    /**
     * Método que devuelve el la hora de salida.
     * 
     * @return Hora de salida.
     */
    public LocalTime getHoraSalida() {
        return this.horaLlegada;
    }


    public void configurarNombreAeropuerto() {

        System.out.println("Nombre del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getNombre());
        airportManager.getGestor().configurarAeropuertoPropioNombre("Heathrow");
        System.out.println("Nombre del aeropuerto después de la configuración: "+ airportManager.getAeropuerto().getNombre());

    }

    public void configurarCodigoAeropuerto() {

        System.out.println("Código del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getCodigo());
        airportManager.getGestor().configurarAeropuertoPropioCodigo("LHR");
        System.out.println("Código del aeropuerto después de la configuración: "+ airportManager.getAeropuerto().getCodigo());

    }

    public void configurarCiudadAeropuerto() {

        System.out.println("Ciudad del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getCiudad());
        airportManager.getGestor().configurarAeropuertoPropioCiudad("Londres");
        System.out.println("Ciudad del aeropuerto después de la configuración: "+ airportManager.getAeropuerto().getCiudad());

    }

    public void configurarPaisAeropuerto() {
        System.out.println("País del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getPais());
        airportManager.getGestor().configurarAeropuertoPropioPais("Reino Unido");
        System.out.println("País del aeropuerto después de la configuración: "+ airportManager.getAeropuerto().getPais());

    }

    public void configurarDistanciaCiudadAeropueto() {

        System.out.println("Distancia ciudad del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getDistanciaCiudad());
        airportManager.getGestor().configurarAeropuertoPropioDistanciaCiudad(24);
        System.out.println("Distancia ciudad del aeropuerto después de la configuración: "+ airportManager.getAeropuerto().getDistanciaCiudad());

    }

    public void configurarDiferenciaHorariaAeropuerto() {
        System.out.println("Diferencia horaria del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getDiferenciaHoraria());
        airportManager.getGestor().configurarAeropuertoPropioDiferenciaHoraria(ZoneOffset.ofHours(+1));
        System.out.println("Diferencia horaria del aeropuerto después de la configuración: "+ airportManager.getAeropuerto().getDiferenciaHoraria());

    }

    public void configurarNumTemporadasAeropuerto() {
        System.out.println("Número de temporadas del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getTemporadas().size());
        airportManager.getGestor().configurarAeropuertoPropioNumTemporadas(2);
        System.out.println("Número de temporadas del aeropuerto después de la configuración: "+ airportManager.getAeropuerto().getTemporadas().size);
    }

    public void configurarDireccionCiudadAeropuerto() {
        System.out.println("Dirección ciudad del aeropuerto antes de la configuración:" + airportManager.getAeropuerto().getDireccionCiudad());
        airportManager.getGestor().configurarAeropuertoPropioDireccionCiudad(DireccionRelativaCiudad.SUR);
        System.out.println("Dirección ciudad  del aeropuerto después de la configuración: "+ airportManager.getAeropuerto().getDireccionCiudad());
    }


}
