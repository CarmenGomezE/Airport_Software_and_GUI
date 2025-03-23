package aeropuerto.src.carmen;

import aeropuerto.src.carmen.vuelo.*;
import aeropuerto.src.carmen.factura.*;
import aeropuerto.src.carmen.avion.*;
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
    /** Alternativas que le propone el gestor a sus propuestas de vuelo*/
	private List<VueloProgramado> vuelosAlternativas;
	private Timer timer;

    /**
     * Constructor de la clase SolicitudVueloCompartido.
     * 
     * @param vuelosPorgramados Vuelos programados asignados al operador.
     * @param aerolinea         Aerolinea a la que pertenece.
     * @param estadisticas      Estadísticas sobre vuelos.
     * @param aeropuerto        Aeropuerto propio del operador.
     * @param gestor            Gestor del operador.
     */
    public Operador(String nombre, String dni, Aerolinea aerolinea, AeropuertoPropio aeropuerto, Gestor gestor) {
    	super(nombre, dni, aeropuerto, gestor);
        this.aerolinea = aerolinea;
        this.solicitudesRecibidas = new ArrayList<>();
        this.solicitudesEnviadas = new ArrayList<>();
        this.estadisticas = new EstadisticasOperador(this);
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
     * @throws VueloNoDelUsuarioException 
     * @throws EstadoNoDelUsuarioException 
     */
    @Override
    public void cambiarEstadoVuelo(Vuelo vuelo, EstadoVuelo estado) throws VueloNoDelUsuarioException, EstadoNoDelUsuarioException {
        if (vuelo == null || estado == null) {
            throw new IllegalArgumentException("El vuelo y el nuevo estado no pueden ser null");
        }

        if (getVuelos().contains(vuelo) == false) {
            throw new VueloNoDelUsuarioException("Este vuelo no está asignado a este usuario");
        }

        switch (estado) {
            case EN_PREPARACION_PARA_DESPEGAR:
                this.cambiarEstadoEnPreparacionDespegar(vuelo);
                break;
            case INICIO_DEL_EMBARQUE:
                this.cambiarEstadoInicioEmbarque(vuelo);
                break;
            case FIN_DEL_EMBARQUE:
                this.cambiarEstadoFinEmbarque(vuelo);
                break;
            case INICIO_DEL_DESEMBARQUE:
                this.cambiarEstadoInicioDesembarque(vuelo);
                break;
            case FIN_DEL_DESEMBARQUE:
                this.cambiarEstadoFinDesembarque(vuelo);
                break;
            default:
                throw new EstadoNoDelUsuarioException("Este usuario no puede cambiar a ese estado");
        }
    }

    /**
     * Método para cambiar el estado de un vuelo a en preparación para despegar.
     * 
     * @param vuelo     Vuelo al que se le cambia el estado.
     */
    public void cambiarEstadoEnPreparacionDespegar(Vuelo vuelo) {
        if (vuelo.getEstado() == EstadoVuelo.OPERATIVO) {
            vuelo.cambiarEstado(EstadoVuelo.EN_PREPARACION_PARA_DESPEGAR);
        }
    }

    /**
     * Método para cambiar el estado de un vuelo a inicio de embarque.
     * 
     * @param vuelo     Vuelo al que se le cambia el estado.
     */
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

    /**
     * Método para cambiar el estado de un vuelo a fin de embarque.
     * 
     * @param vuelo     Vuelo al que se le cambia el estado.
     */
    public void cambiarEstadoFinEmbarque(Vuelo vuelo) {
        if (vuelo.getEstado() == EstadoVuelo.INICIO_DEL_EMBARQUE) {
            vuelo.cambiarEstado(EstadoVuelo.FIN_DEL_EMBARQUE);
        }
    }

    /**
     * Método para cambiar el estado de un vuelo a inicio de desembarque.
     * 
     * @param vuelo     Vuelo al que se le cambia el estado.
     */
    public void cambiarEstadoInicioDesembarque(Vuelo vuelo) {
        if (vuelo.getEstado() == EstadoVuelo.ATERRIZAJE) {
            vuelo.cambiarEstado(EstadoVuelo.INICIO_DEL_DESEMBARQUE);
        }
    }

    /**
     * Método para cambiar el estado de un vuelo a fin de desembarque.
     * 
     * @param vuelo     Vuelo al que se le cambia el estado.
     */
    public void cambiarEstadoFinDesembarque(Vuelo vuelo) {
        if (vuelo.getEstado() == EstadoVuelo.INICIO_DEL_DESEMBARQUE) {
            vuelo.cambiarEstado(EstadoVuelo.FIN_DEL_DESEMBARQUE);
        }
    }

    /**
     * Método para dar de alta aviones en la aerolinea.
     * 
     * @param marca                 Marca del avión.
     * @param modelo                Modelo del avión.
     * @param dimAlto               Dimensión de alto del avión.
     * @param dimAncho              Dimensión de ancho del avión.
     * @param dimLargo              Dimensión de largo del avión.
     * @param autonomia             Autonomía del avión.
     * @param anoCompra             Año de compra del avión.
     * @param fechaUltimaRevision   Fecha de la última revisión del avión.
     * @param matricula             Matrícula del avión.
     * @param capacidadMaxPasajeros Capacidad máxima del avión.
     */
    public void darDeAltaAvionPasajeros(String marca, String modelo, double dimAlto, double dimAncho, double dimLargo,
            double autonomia, int anoCompra, Date fechaUltimaRevision, String matricula,
            int capacidadMaxPasajeros) {
        
    	Avion nuevoAvion= new AvionPasajeros(marca, modelo, dimAlto, dimAncho, dimLargo, autonomia, anoCompra, fechaUltimaRevision, 
    										matricula, capacidadMaxPasajeros);
    	
    	this.getAerolinea().agregarAvion(nuevoAvion);
    	nuevoAvion.setAerolinea(this.getAerolinea());
    }

    /**
     * Método para dar de alta aviones en la aerolinea.
     * 
     * @param marca                 Marca del avión.
     * @param modelo                Modelo del avión.
     * @param dimAlto               Dimensión de alto del avión.
     * @param dimAncho              Dimensión de ancho del avión.
     * @param dimLargo              Dimensión de largo del avión.
     * @param autonomia             Autonomía del avión.
     * @param anoCompra             Año de compra del avión.
     * @param fechaUltimaRevision   Fecha de la última revisión del avión.
     * @param matricula             Matrícula del avión.
     * @param capacidadMaxPasajeros Capacidad máxima del avión.
     */
    public void darDeAltaAvionMercancias(String marca, String modelo, double dimAlto, double dimAncho, double dimLargo,
            double autonomia, int anoCompra, Date fechaUltimaRevision, String matricula,
            double capacidadMaxCarga, boolean mercaPeligrosa, boolean controlTemperatura) {
    	
    	Avion nuevoAvion= new AvionMercancias(marca, modelo, dimAlto, dimAncho, dimLargo, autonomia, anoCompra, fechaUltimaRevision,matricula,
    											capacidadMaxCarga, mercaPeligrosa, controlTemperatura);
    	
    	this.getAerolinea().agregarAvion(nuevoAvion);
    	nuevoAvion.setAerolinea(this.getAerolinea());
    }

    public void recibirNotificacionOpcionesReprogramacion(List<VueloProgramado> alternativas) {
        vuelosAlternativas = alternativas;
        System.out.println("Recibidas " + alternativas.size() + " alternativas de vuelos para su aceptación.");
        
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                eliminarAlternativas();
            }
        }, gestor.getTiempoAceptarPropuesta() * 60 * 1000); 
    }

    // Método para aceptar una alternativa
    public void aceptarAlternativa(VueloProgramado vuelo) throws AvionNoDisponibleException, AvionNoValidoException, EscalaInvalidaException, AntelacionInvalidaException {
        if (vuelosAlternativas.contains(vuelo)) {
            vuelosAlternativas.remove(vuelo);
            System.out.println("Vuelo " + vuelo.getCodigo() + " aceptado.");
            timer.cancel();
            proponerVuelo(vuelo.getCodigo(),vuelo.getFecha(),vuelo.getFechaInicio(),vuelo.getFechaFinal(), vuelo.getHora(),  
                    		vuelo.getDiasRecurrente(),vuelo.getTipoCarga(),vuelo.getTipoModoEmbarque(),vuelo.getPeriodicidad(), vuelo.getTipoVuelo(), vuelo.getAvion(),  
                    		vuelo.getAeropuertoDestino(),vuelo.getAeropuertoSalida(),vuelo.getEscala());
        }
    }

    // Método para rechazar una alternativa
    public void rechazarAlternativa(VueloProgramado vuelo) {
        if (vuelosAlternativas.contains(vuelo)) {
            vuelosAlternativas.remove(vuelo);
        }
    }

    private void eliminarAlternativas() {
        if (!vuelosAlternativas.isEmpty()) {
            System.out.println("El tiempo para aceptar las alternativas ha pasado. Se eliminan las propuestas.");
            vuelosAlternativas.clear();
        }
    }
    
    /**
     * Método que añade vuelos propuestos del Operador.
     * 
     * @param vueloProgramado Vuelo a añadir.
     * @throws AvionNoDisponibleException 
     * @throws AvionNoValidoException 
     * @throws EscalaInvalidaException 
     * @throws AntelacionInvalidaException 
     */
    public void proponerVuelo(String codigo, LocalDate fecha, LocalDate fechaInicio, LocalDate fechaFinal, 
            LocalTime hora, List<DayOfWeek> diasRecurrente, TipoCarga tipoCarga, TipoModoEmbarque tipoModoEmbarque, 
            Periodicidad periodicidad, TipoVuelo tipoVuelo, Avion avion, Aeropuerto aeropuertoDestino, 
            Aeropuerto aeropuertoSalida, Escala escala) throws AvionNoDisponibleException, AvionNoValidoException, EscalaInvalidaException, AntelacionInvalidaException { 
        
    	List<VueloProgramado> vueloPropuestos=new ArrayList<>();
    	
        if (aeropuertoSalida == null || aeropuertoDestino == null) {
            throw new IllegalArgumentException("El aeropuerto de salida y el de llegada no pueden ser nulos.");
        }
        
        if (escala == null) {
            if (aeropuertoSalida.equals(aeropuertoDestino)) {
                throw new IllegalArgumentException("El aeropuerto de salida no puede ser igual al de llegada si no hay escalas.");
            }
        }

        if (tipoVuelo == TipoVuelo.LLEGADA && !aeropuertoDestino.equals(getAeropuerto())) {
            throw new IllegalArgumentException("El aeropuerto destino debe ser el actual si el vuelo es de llegada.");
        }

        if (tipoVuelo == TipoVuelo.SALIDA && !aeropuertoSalida.equals(getAeropuerto())) {
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
            	
        vueloPropuestos.add(vueloPropuesto);

        if (periodicidad == Periodicidad.PUNTUAL) {
        	vueloPropuestos.add(vueloPropuesto);
        } else if (periodicidad == Periodicidad.DIARIO) {
            LocalDate fechaIterativa = fechaInicio;
            while (!fechaIterativa.isAfter(fechaFinal)) {
            	if (!avion.estaDisponible(fechaIterativa, hora)) {
                    throw new AvionNoDisponibleException("El avión no está disponible para el vuelo diario en la fecha " + fechaIterativa);
                }
            	
            	vueloPropuestos.add(new VueloProgramado(codigo + "-" + fechaIterativa, null, fechaIterativa, fechaIterativa,
                        hora, null, tipoCarga, tipoModoEmbarque, periodicidad, tipoVuelo, avion, 
                        aeropuertoDestino, aeropuertoSalida, escala));
                fechaIterativa = fechaIterativa.plusDays(1);
            }
        } else if (periodicidad == Periodicidad.RECURRENTE) {
            LocalDate fechaIterativa = fechaInicio;
            while (!fechaIterativa.isAfter(fechaFinal)) {
            	if (!avion.estaDisponible(fechaIterativa, hora)) {
                    throw new AvionNoDisponibleException("El avión no está disponible para el vuelo diario en la fecha " + fechaIterativa);
                }
            	
                if (diasRecurrente.contains(fechaIterativa.getDayOfWeek())) {
                	vueloPropuestos.add(new VueloProgramado(codigo + "-" + fechaIterativa, null, fechaIterativa, fechaIterativa,
                            hora, diasRecurrente, tipoCarga, tipoModoEmbarque, periodicidad, tipoVuelo, avion, 
                            aeropuertoDestino, aeropuertoSalida, escala));
                }
                fechaIterativa = fechaIterativa.plusDays(1); 
            }
        }
        getGestor().recibirNotificacionProposicionVuelo(vueloPropuestos);
        
        System.out.println("Vuelo propuesto exitosamente.");
    }

    public void recibirNotificacionFacturaEmitida(Factura factura) {
    	getAerolinea().addFactura(factura);
        new Notificacion("Factura emitida", TipoNotificacion.FACTURA_EMITIDA);
    }
  
    /**
     * Método que añade vuelos propuestos del Operador.
     * 
     * @param factura	 		Factura que se va a pagar. 
     * @param numeroDeTarjeta 	Tarjeta con la que se va a pagar. 
     */
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
    
    /**
     * Método que solicita compartir vuelos.
     * 
     * @param vuelo		           Vuelo que se solicita compartir.
     * @param operadorReceptor	   Aerolinea que ha realizado la solicitud.
     * @param porcentaje		   Aerolinea que ha recibido la solicitud.
     * @throws OperacionInvalidaException 
     */
    public void solicitarCompartirVuelo(Vuelo vuelo, Operador operadorReceptor, double porcentajeCarga) throws SolicitudInvalidaExcepcion, OperacionInvalidaException {
    	 
    	if (vuelo.getOperadoresAsignados().containsKey(this)) {
            throw new OperacionInvalidaException("No puedes solicitar compartir un vuelo en el que ya participas.");
        }

    	    SolicitudVueloCompartido solicitud = new SolicitudVueloCompartido(vuelo, this,operadorReceptor, porcentajeCarga);
    	    operadorReceptor.recibirSolicitud(solicitud);
    	    solicitudesEnviadas.add(solicitud);
    	    enviarNotificacionCompartirVuelo(operadorReceptor);
    }

    /**
     * Método para recibir solicitudes de compartir vuelos.
     * 
     * @param solicitud		         Solicitud que se recibe.
     */
    public void recibirSolicitud(SolicitudVueloCompartido solicitud) {
        this.solicitudesRecibidas.add(solicitud);
        System.out.println("Nueva solicitud recibida para compartir vuelo " + solicitud.getVueloOriginal().getCodigo());
    }

    /**
     * Método para aceptar solicitudes de compartir vuelos.
     * 
     * @param solicitud		         Solicitud que se quiere aceptar.
     */
    public void aceptarSolicitud(SolicitudVueloCompartido solicitud) {
        Vuelo vuelo = solicitud.getVueloOriginal();
        
        if (!vuelo.getOperadorPrincipal().equals(this)) {
            throw new IllegalArgumentException("Solo el operador principal del vuelo puede aceptar solicitudes.");
        }

        try {
            if (solicitud.esSolicitudValida()) {
                solicitud.aceptar();
                vuelo.agregarOperadorSecundario(solicitud.getOperadorSolicitante(), solicitud.getPorcentaje());

                System.out.println("Solicitud aceptada: " + solicitud.getOperadorSolicitante().getNombre() + " ahora comparte el vuelo " + vuelo.getCodigo());
            }
        } catch (SolicitudInvalidaExcepcion e) {
            System.out.println("No se pudo aceptar la solicitud: " + e.getMessage());
        }
    }

    /**
     * Método para rechazar solicitudes de compartir vuelos.
     * 
     * @param solicitud		         Solicitud que se quiere aceptar.
     */
    public void rechazarSolicitud(SolicitudVueloCompartido solicitud) {
        solicitud.rechazar();
        System.out.println("Solicitud rechazada por " + this.getNombre());
    }

    /**
     * Método que muestra las solicitudes recibidas.
     * 
     */
    public void verSolicitudes() {
        if (solicitudesRecibidas.isEmpty()) {
            System.out.println("No hay solicitudes de vuelo compartido.");
        } else {
            System.out.println("Solicitudes de vuelo compartido recibidas:");
            for (SolicitudVueloCompartido solicitud : solicitudesRecibidas) {
                System.out.println("Vuelo: " + solicitud.getVueloOriginal().getCodigo() +
                        " | Solicitante: " + solicitud.getOperadorSolicitante().getNombre() +
                        " | Estado: " + solicitud.getEstadoSolicitud());
            }
        }
    }

    /**
     * Método para enviar solicitudes de compartir vuelos.
     * 
     * @param operadorReceptor		         Operador al que se envia la solicitud.
     */
    private void enviarNotificacionCompartirVuelo(Operador operadorReceptor) {
    	operadorReceptor.recibirNotificacionSolicitudVueloCompartido();
    }

    /**
     * Método para recibir solicitudes de compartir vuelos.
     * 
     */
	private void recibirNotificacionSolicitudVueloCompartido() {
		recibirNotificacion(new Notificacion("Factura emitida", TipoNotificacion.SOLICITUD_COMPARTIR_VUELO));
	}
}