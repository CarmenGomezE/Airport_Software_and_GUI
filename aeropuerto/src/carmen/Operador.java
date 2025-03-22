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
        this.vuelosPropuestos = new ArrayList<>();
        this.solicitudesRecibidas = new ArrayList<>();
        this.solicitudesEnviadas = new ArrayList<>();
        this.estadisticas = new EstadisticasOperador(this);
    }

    /**
     * Método que devuelve los vuelos programados del Operador.
     * 
     * @return Vuelos programados del Operador.
     */
    public List<VueloProgramado> getVuelosPropuestos() {
        return this.vuelosPropuestos;
    }
    
    /**
     * Método que añade vuelos programados a los del Operador.
     * 
     * @param vueloProgramado Vuelo a añadir.
     */
    public void proponerVuelo(String codigo, LocalDate fecha, LocalDate fechaInicio, LocalDate fechaFinal, 
            LocalTime hora, List<DayOfWeek> diasRecurrente, TipoCarga tipoCarga, TipoModoEmbarque tipoModoEmbarque, 
            Periodicidad periodicidad, TipoVuelo tipoVuelo, Avion avion, Aeropuerto aeropuertoDestino, 
            Aeropuerto aeropuertoSalida, Escala escala) { 
        
        if (aeropuertoSalida == null || aeropuertoDestino == null) {
            throw new IllegalArgumentException("El aeropuerto de salida y el de llegada no pueden ser nulos.");
        }
        
        if (escala == null) {
            if (aeropuertoSalida.equals(aeropuertoDestino)) {
                throw new IllegalArgumentException("El aeropuerto de salida no puede ser igual al de llegada si no hay escalas.");
            }
        }

        if (tipoVuelo == TipoVuelo.LLEGADA && !aeropuertoDestino.equals(getAeropuertoPropio())) {
            throw new IllegalArgumentException("El aeropuerto destino debe ser el actual si el vuelo es de llegada.");
        }

        if (tipoVuelo == TipoVuelo.SALIDA && !aeropuertoSalida.equals(getAeropuertoPropio())) {
            throw new IllegalArgumentException("El aeropuerto de salida debe ser el actual si el vuelo es de salida.");
        }
     
        if ((avion instanceof AvionMercancias && tipoCarga==TipoCarga.PASAJEROS) || (avion instanceof AvionPasajeros && tipoCarga==TipoCarga.MERCANCIAS)) {
            throw new AvionNoValidoException("El avión seleccionado no concuerda con la carga que transporta.");
        }
        
        if (!avion.estaDisponible(fecha, hora)) {
            throw new AvionNoDisponibleException("El avión seleccionado no está disponible.");
        }

        if (escala.getAeropuertoOrigen().equals(aeropuertoSalida) && !escala.getAeropuertoDestino().equals(aeropuertoDestino)) {
            throw new IllegalArgumentException("No se puede hacer escala en el aeropuerto de salida sin pasar por el de llegada.");
        }
        	 
        if (escala.getHoraSalida().isBefore(escala.getHoraLlegada().plusHours(1))) {
            throw new EscalaInvalidaException("La escala en " + escala.getAeropuertoDestino().getCiudad() + " tiene menos de una hora de permanencia.");
        }

        VueloProgramado vueloPropuesto= new VueloProgramado(codigo,fecha,fechaInicio,fechaFinal,hora,diasRecurrente,tipoCarga,tipoModoEmbarque, 
                periodicidad,tipoVuelo,avion,aeropuertoDestino, aeropuertoSalida,escala);
            	
        this.vuelosPropuestos.add(vueloPropuesto);

        if (periodicidad == Periodicidad.PUNTUAL) {
        	vuelosPropuestos.add(vueloPropuesto);
        } else if (periodicidad == Periodicidad.DIARIO) {
            LocalDate fechaIterativa = fechaInicio;
            while (!fechaIterativa.isAfter(fechaFinal)) {
            	this.vuelosPropuestos.add(new VueloProgramado(codigo + "-" + fechaIterativa, null, fechaIterativa, fechaIterativa,
                        hora, null, tipoCarga, tipoModoEmbarque, periodicidad, tipoVuelo, avion, 
                        aeropuertoDestino, aeropuertoSalida, escala));
                fechaIterativa = fechaIterativa.plusDays(1);
            }
        } else if (periodicidad == Periodicidad.RECURRENTE) {
            LocalDate fechaIterativa = fechaInicio;
            while (!fechaIterativa.isAfter(fechaFinal)) {
                if (diasRecurrente.contains(fechaIterativa.getDayOfWeek())) {
                	this.vuelosPropuestos.add(new VueloProgramado(codigo + "-" + fechaIterativa, null, fechaIterativa, fechaIterativa,
                            hora, diasRecurrente, tipoCarga, tipoModoEmbarque, periodicidad, tipoVuelo, avion, 
                            aeropuertoDestino, aeropuertoSalida, escala));
                }
                fechaIterativa = fechaIterativa.plusDays(1); 
            }
        }
        /*ENVIAR NOTIFICACIÓN AL GESTOR*/
        System.out.println("Vuelo propuesto exitosamente.");
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
                cambiarEstadoEnPreparacionDespegar(vuelo);
            case INICIO_DEL_EMBARQUE:
                cambiarEstadoInicioEmbarque(vuelo);
            case FIN_DEL_EMBARQUE:
                cambiarEstadoFinEmbarque(vuelo);
            case INICIO_DEL_DESEMBARQUE:
                cambiarEstadoInicioDesembarque(vuelo);
            case FIN_DEL_DESEMBARQUE:
                cambiarEstadoFinDesembarque(vuelo);
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

    public void darDeAltaAvionPasajeros(String marca, String modelo, double dimAlto, double dimAncho, double dimLargo,
            double autonomia, int anoCompra, Date fechaUltimaRevision, String matricula,
            int capacidadMaxPasajeros) {
        getAerolinea().agregarAvion(new AvionPasajeros(marca, modelo, dimAlto, dimAncho, dimLargo, autonomia, anoCompra,
                fechaUltimaRevision, matricula, capacidadMaxPasajeros));
    }

    public void darDeAltaAvionMercancias(String marca, String modelo, double dimAlto, double dimAncho, double dimLargo,
            double autonomia, int anoCompra, Date fechaUltimaRevision, String matricula,
            double capacidadMaxCarga, boolean mercaPeligrosa, boolean controlTemperatura) {
    	getAerolinea().agregarAvion(new AvionMercancias(marca, modelo, dimAlto, dimAncho, dimLargo, autonomia, anoCompra,
                fechaUltimaRevision,matricula, capacidadMaxCarga, mercaPeligrosa, controlTemperatura));
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