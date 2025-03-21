package aeropuerto.src.carmen;

import aeropuerto.src.claudia.*;
import aeropuerto.src.carmen.vuelo.*;
import java.util.*;

/**
 * La clase Operador representa un tipo de usuario con dni, este usuario es el
 * encargado de cambiar los estados de vuelo de embarque y desembarque, así como
 * el de en preparación para despegar. Además podrá realizar otras acciones como
 * dar de alta aviones, programar y compartir vuelos, recibir notificaciones o
 * pagar facturas.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class SolicitudVueloCompartido {
    /** Porcentaje de vuelo que se solicita compartir */
    private VueloProgramado vueloOriginal;  
    private List<Operador> operadoresCompartidos; 
    private List<Double> porcentajesCarga;  
    /** Estado de la solicitud */
    private EstadoSolicitud estadoSolicitud;
    /** Aerolinea que solicita compartir vuelo */
    private Operador operadorSolicita;

    /**
     * Constructor de la clase SolicitudVueloCompartido.
     * 
     * @param porcentaje           Porcentaje de vuelo solicitado.
     * @param estadoSolicitud      Estado de la solicitud.
     * @param aerolineaQueSolicita Aerolinea que ha realizado la solicitud.
     * @param aerolineaOriginal    Aerolinea que ha recibido la solicitud.
     */
    public SolicitudVueloCompartido(VueloProgramado vuelo, Operador operadorSolicitante) {
        this.vueloOriginal = vuelo;
        this.operadorSolicita = operadorSolicitante;
        this.operadoresCompartidos = new ArrayList<>();
        this.porcentajesCarga = new ArrayList<>();
        this.estadoSolicitud = EstadoSolicitud.PENDIENTE;
    }

    /**
     * Método que devuelve el porcentaje que se solicita compartir.
     * 
     * @return Porcentaje.
     */
    public List<Double> getPorcentajes() {
        return this.porcentajes;
    }

    /**
     * Método que devuelve el estado de la solicitud.
     * 
     * @return Estado de la solicitud.
     */
    public EstadoSolicitud getEstadoSolicitud() {
        return this.estadoSolicitud;
    }

    public void agregarOperadorCompartido(Operador operador, double porcentajeCarga) {
        this.operadoresCompartidos.add(operador);
        this.porcentajesCarga.add(porcentajeCarga);
    }

    public void aceptar() {
        try {
            if (esSolicitudValida()) {
                this.estadoSolicitud = EstadoSolicitud.ACEPTADA;
            }
        } catch (SolicitudInvalidaExcepcion e) {
            this.estadoSolicitud = EstadoSolicitud.RECHAZADA;
            System.out.println("Error al aceptar la solicitud: " + e.getMessage());
        }
    }

    public void rechazar() {
        this.estadoSolicitud = EstadoSolicitud.RECHAZADA;
    }
    
    private boolean esSolicitudValida() throws SolicitudInvalidaExcepcion {
        double totalPorcentaje = 0.0;
        for (Double porcentaje : porcentajesCarga) {
            totalPorcentaje += porcentaje;
        }
        if (totalPorcentaje > 100.0 || porcentajesCarga.get(0) < 50.0) {
            throw new SolicitudInvalidaExcepcion("La solicitud de vuelo compartido es inválida: la aerolínea original debe tener al menos el 50% de la carga.");
        }
        return true;
    }

	public VueloProgramado getVueloOriginal() {
		return this.vueloOriginal;
	}
}