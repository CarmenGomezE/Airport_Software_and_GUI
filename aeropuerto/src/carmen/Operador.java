package aeropuerto.src.carmen;

import aeropuerto.src.carmen.vuelo.*;
import aeropuerto.src.carmen.factura.*;
import aeropuerto.src.carmen.avion.*;
import aeropuerto.src.carmen.*;
import aeropuerto.src.claudia.*;
import aeropuerto.src.maria.*;
import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;

import java.util.*;
import java.time.*;

/**
 * La clase Operador representa un tipo de usuario con dni, este usuario es el
 * encargado
 * de cambiar los estados de vuelo de embarque y desembarque, así como el de en
 * preparación
 * para despegar. Además podrá realizar otras acciones como dar de alta aviones,
 * programar
 * y compartir vuelos, recibir notificaciones o pagar facturas.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class Operador extends UsuarioConDni {
    /** Lista de los vuelos que tiene programados */
    private List<VueloProgramado> vuelosProgramados;
    /** Lista de solicitudes recibidas de vuelos compartidos de la aerolínea */
    private List<SolicitudVueloCompartido> solicitudesRecibidas;
    /** Lista de solicitudes enviadas de vuelos compartidos de la aerolínea */
    private List<SolicitudVueloCompartido> solicitudesEnviadas;
    /** Aerolinea para la que opera */
    private Aerolinea aerolinea;
    /** Estadísticas del operador sobre vuelos de su aerolinea */
    private EstadisticasOperador estadisticas;
    
    private List<Vuelo> vuelos;

    /**
     * Constructor de la clase SolicitudVueloCompartido.
     * 
     * @param vuelosPorgramados Vuelos programados asignados al operador.
     * @param aerolinea         Aerolinea a la que pertenece.
     * @param estadisticas      Estadísticas sobre vuelos.
     */
    public Operador(String nombre, String dni, Aerolinea aerolinea) {
    	super(nombre, dni);
        this.aerolinea = aerolinea;
        this.vuelosProgramados = new ArrayList<>();
        this.solicitudesRecibidas = new ArrayList<>();
        this.solicitudesEnviadas = new ArrayList<>();
        this.estadisticas = new EstadisticasOperador(this);
    }

    /**
     * Método que devuelve los vuelos programados del Operador.
     * 
     * @return Vuelos programados del Operador.
     */
    public List<VueloProgramado> getVuelosProgramados() {
        return this.vuelosPorgramados;
    }

    /**
     * Método que devuelve los vuelos programados del Operador.
     * 
     * @return Vuelos programados del Operador.
     */
    public List<Vuelo> getVuelos() {
        return this.vuelos;
    }

    /**
     * Método que añade vuelos programados a los del Operador.
     * 
     * @param vueloProgramado Vuelo a añadir.
     */
    public void addVueloProgramado(VueloProgramado vueloPorgramado) {
        this.vuelosProgramados.add(vueloPorgramado);
    }

    /**
     * Método que devuelve la Aerolinea del Operador.
     * 
     * @return Aerolinea del Operador.
     */
    public Aerolinea getAerolinea() {
        return this.aerolinea;
    }

    /**
     * Método que devuelve las estadísticas del Operador.
     * 
     * @return Estadísticas el Operador.
     */
    public EstadisticasOperador getEstadisticasOperador() {
        return this.estadisticas;
    }

    /**
     * Método para cambiar el estado de un vuelo.
     * 
     * @param vuelo     Vuelo al que se le cambia el estado.
     * @param estado    Nuevo estado del vuelo.
     */
    @Override
    public void cambiarEstadoVuelo(Vuelo vuelo, EstadoVuelo estado) {
        if (vuelo == null || estado == null) {
            throw new IllegalArgumentException("El vuelo y el nuevo estado no pueden ser null");
        }

        if (vuelos.contains(vuelo) == false) {
            throw new VueloNoDelUsuarioException("Este vuelo no está asignado a este usuario");
        }

        switch (estado) {
            case EN_PREPARACION_PARA_DESPEGAR:
                return cambiarEstadoEnPreparacionDespegar(vuelo);
            case INICIO_DEL_EMBARQUE:
                return cambiarEstadoInicioEmbarque(vuelo);
            case FIN_DEL_EMBARQUE:
                return cambiarEstadoFinEmbarque(vuelo);
            case INICIO_DEL_DESEMBARQUE:
                return cambiarEstadoInicioDesembarque(vuelo);
            case FIN_DEL_DESEMBARQUE:
                return cambiarEstadoFinDesembarque(vuelo);
            default:
                throw new EstadoNoDelUsuarioException("Este usuario no puede cambiar a ese estado");
        }
    }

    public void cambiarEstadoEnPreparacionDespegar(Vuelo vuelo) {
        if (vuelo.getEstado() == EstadoVuelo.OPERATIVO) {
            vuelo.cambiarEstado(EstadoVuelo.EN_PREPARACION_PARA_DESPEGAR);
        }
    }

    public void cambiarEstadoInicioEmbarque(Vuelo vuelo) {
        LocalTime horaActual = LocalTime.now();
        long minutosDeDiferencia = Duration.between(horaActual, vuelo.getHora()).toMinutes();

        if (vuelo.getAvion() instanceof AvionMercancias) {
            AvionMercancias avion = (AvionMercancias) vuelo.getAvion();
            double capacidadCarga = avion.getCapacidadMaxCarga();
            if (minutosDeDiferencia <= 30 && (capacidadCarga < 1000)
                    || (minutosDeDiferencia <= 45 && capacidadCarga > 1000)) {
                if (vuelo.getEstado() == EstadoVuelo.EN_PREPARACION_PARA_DESPEGAR) {
                    vuelo.cambiarEstado(EstadoVuelo.INICIO_DEL_EMBARQUE);
                }
            }
        } else if (vuelo.getAvion() instanceof AvionPasajeros) {
            AvionPasajeros avion = (AvionPasajeros) vuelo.getAvion();
            int numeroPasajeros = avion.getCapacidadMaxPasajeros();
            if ((minutosDeDiferencia <= 30 && numeroPasajeros < 100)
                    || (minutosDeDiferencia <= 45 && numeroPasajeros > 100)) {
                if (vuelo.getEstado() == EstadoVuelo.EN_PREPARACION_PARA_DESPEGAR) {
                    vuelo.cambiarEstado(EstadoVuelo.INICIO_DEL_EMBARQUE);
                }
            }
        }
    }

    public void cambiarEstadoFinEmbarque(Vuelo vuelo) {
        if (vuelo.getEstado() == EstadoVuelo.INICIO_DEL_EMBARQUE) {
            vuelo.cambiarEstado(EstadoVuelo.FIN_DEL_EMBARQUE);
        }
    }

    public void cambiarEstadoInicioDesembarque(Vuelo vuelo) {
        if (vuelo.getEstado() == EstadoVuelo.ATERRIZAJE) {
            vuelo.cambiarEstado(EstadoVuelo.INICIO_DEL_DESEMBARQUE);
        }
    }

    public void cambiarEstadoFinDesembarque(Vuelo vuelo) {
        if (vuelo.getEstado() == EstadoVuelo.INICIO_DEL_DESEMBARQUE) {
            vuelo.cambiarEstado(EstadoVuelo.FIN_DEL_DESEMBARQUE);
        }
    }

    public AvionPasajeros darDeAltaAvionPasajeros(String marca, String modelo, double dimAlto, double dimAncho, double dimLargo,
            double autonomia, int anoCompra, Date fechaUltimaRevision, String matricula,
            int capacidadMaxPasajeros) {
        return new AvionPasajeros(marca, modelo, dimAlto, dimAncho, dimLargo, autonomia, anoCompra,
                fechaUltimaRevision, matricula, capacidadMaxPasajeros);
    }

    public AvionMercancias darDeAltaAvionMercancias(String marca, String modelo, double dimAlto, double dimAncho, double dimLargo,
            double autonomia, int anoCompra, Date fechaUltimaRevision, String matricula,
            double capacidadMaxCarga, boolean mercaPeligrosa, boolean controlTemperatura) {
        return new AvionMercancias(marca, modelo, dimAlto, dimAncho, dimLargo, autonomia, anoCompra,
                fechaUltimaRevision,
                matricula, capacidadMaxCarga, mercaPeligrosa, controlTemperatura);
    }

    public void recibirNotificacionOpcionesReprogramacion() {

    }

    public void recibirNotificacionFacturaEmitida(Factura factura) {
    	getAerolinea().addFactura(factura);
        System.out.println("Operador notificado: Nueva factura generada para " + factura.getAirline()+ " por un total de " + factura.getPrice() + "€.");
    }

    public void pagarFactura(Factura factura, String numeroDeTarjeta) {
        try {
            TeleChargeAndPaySystem.isValidCardNumber(numeroDeTarjeta);
            TeleChargeAndPaySystem.charge(numeroDeTarjeta, factura.getInvoiceIdentifier(), factura.getPrice());
            factura.marcarComoPagada();

        } catch (InvalidCardNumberException e) {
            System.err.println("Error: Número de tarjeta inválido.");
        } catch (FailedInternetConnectionException e) {
            System.err.println("Error: Conexión a Internet fallida.");
        } catch (OrderRejectedException e) {
            System.err.println("Error: El pago fue rechazado.");
        } catch (Exception e) {
            System.err.println("Error desconocido: " + e.getMessage());
        }
    }
    
   
        public boolean programarVuelo(String codigoVuelo, Avion avion, String aeropuertoSalida,
                                      String aeropuertoLlegada, LocalDateTime horarioSalida,
                                      LocalDateTime horarioLlegada, List<Escala> escalas,
                                      TipoVuelo tipo, Periodicidad periodicidad, LocalDate fechaInicio,
                                      LocalDate fechaFin, List<Integer> diasRecurrentes) {

        if (ChronoUnit.DAYS.between(LocalDate.now(), fechaInicio) < DIAS_ANTICIPACION_MINIMA) {
            throw new AnticipacionInsuficienteException("Los vuelos deben programarse con al menos " 
                    + DIAS_ANTICIPACION_MINIMA + " días de anticipación.");
        }

        // Verifica que el avión esté disponible
        if (!avion.estaDisponible()) {
            throw new AvionNoDisponibleException("El avión seleccionado no está disponible.");
        }

        // Verifica que la hora de llegada sea posterior a la de salida
        if (horarioLlegada.isBefore(horarioSalida)) {
            throw new HorarioInvalidoException("La hora de llegada no puede ser anterior a la de salida.");
        }

        // Verifica que las escalas sean válidas
        for (Escala escala : escalas) {
            if (escala.getHoraSalida().isBefore(escala.getHoraLlegada().plusHours(1))) {
                throw new EscalaInvalidaException("La escala en " + escala.getDestino() + 
                    " tiene menos de una hora de permanencia.");
            }
        }

        // Creación y programación del vuelo según el tipo de periodicidad
        if (periodicidad == Periodicidad.PUNTUAL) {
            vuelosProgramados.add(new Vuelo(codigoVuelo, avion, aeropuertoSalida, aeropuertoLlegada,
                    horarioSalida, horarioLlegada, escalas, tipo, periodicidad));
        } else if (periodicidad == Periodicidad.DIARIO) {
            LocalDate fecha = fechaInicio;
            while (!fecha.isAfter(fechaFin)) {
                vuelosProgramados.add(new Vuelo(codigoVuelo + "-" + fecha, avion, aeropuertoSalida,
                        aeropuertoLlegada, horarioSalida.withDayOfMonth(fecha.getDayOfMonth()),
                        horarioLlegada.withDayOfMonth(fecha.getDayOfMonth()), escalas, tipo, periodicidad));
                fecha = fecha.plusDays(1);
            }
        } else if (periodicidad == Periodicidad.RECURRENTE) {
            LocalDate fecha = fechaInicio;
            while (!fecha.isAfter(fechaFin)) {
                if (diasRecurrentes.contains(fecha.getDayOfWeek().getValue())) {
                    vuelosProgramados.add(new Vuelo(codigoVuelo + "-" + fecha, avion, aeropuertoSalida,
                            aeropuertoLlegada, horarioSalida.withDayOfMonth(fecha.getDayOfMonth()),
                            horarioLlegada.withDayOfMonth(fecha.getDayOfMonth()), escalas, tipo, periodicidad));
                }
                fecha = fecha.plusDays(1);
            }
        }

        System.out.println("Vuelo programado exitosamente.");
        return true;
    }

    public void solicitarCompartirVuelo(VueloProgramado vuelo, Operador operadorReceptor, double porcentajeCarga) {
        SolicitudVueloCompartido solicitud = new SolicitudVueloCompartido(vuelo, this);
        solicitud.agregarOperadorCompartido(operadorReceptor, porcentajeCarga);
        
        operadorReceptor.getSolicitudesRecibidas().add(solicitud);
        
        System.out.println("Solicitud de compartir vuelo enviada por " + this.getName() + " a " + operadorReceptor.getName());
    }

    private List<SolicitudVueloCompartido> getSolicitudesRecibidas() {
		return this.solicitudesRecibidas;
	}

    public void aceptarSolicitud(SolicitudVueloCompartido solicitud) {
            solicitud.aceptar();
            System.out.println("Solicitud aceptada por " + this.getName());
    }

    public void rechazarSolicitud(SolicitudVueloCompartido solicitud) {
        solicitud.rechazar();
        System.out.println("Solicitud rechazada por " + this.getName());
    }

    public void verSolicitudes() {
        if (this.solicitudesRecibidas.isEmpty()) {
            System.out.println("No hay solicitudes de vuelo compartido.");
        } else {
            for (SolicitudVueloCompartido solicitud : solicitudesRecibidas) {
                System.out.println("Solicitud de vuelo compartido: " + solicitud.getVueloOriginal().getCodigo());
            }
        }
    }

}