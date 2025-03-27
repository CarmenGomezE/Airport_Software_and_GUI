package src.airportmanager.usuario;

import es.uam.eps.padsof.telecard.FailedInternetConnectionException;

import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;
import src.airportmanager.aeropuerto.Aeropuerto;
import src.airportmanager.aeropuerto.AeropuertoPropio;
import src.airportmanager.avion.*;
import src.airportmanager.estadisticas.EstadisticasOperador;
import src.airportmanager.exceptions.AntelacionInvalidaException;
import src.airportmanager.exceptions.AvionNoDisponibleException;
import src.airportmanager.exceptions.AvionNoValidoException;
import src.airportmanager.exceptions.EscalaInvalidaException;
import src.airportmanager.exceptions.EstadoEquivocadoException;
import src.airportmanager.exceptions.EstadoNoDelUsuarioException;
import src.airportmanager.exceptions.OperacionInvalidaException;
import src.airportmanager.exceptions.SolicitudInvalidaExcepcion;
import src.airportmanager.exceptions.VueloNoDelUsuarioException;
import src.airportmanager.factura.*;
import src.airportmanager.notificacion.Notificacion;
import src.airportmanager.notificacion.TipoNotificacion;
import src.airportmanager.vuelo.*;
import src.airportmanager.*;

import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

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
	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;
    /** Lista de solicitudes recibidas de vuelos compartidos de la aerolínea */
    private List<SolicitudVueloCompartido> solicitudesRecibidas;
    /** Lista de solicitudes enviadas de vuelos compartidos de la aerolínea */
    private List<SolicitudVueloCompartido> solicitudesEnviadas;
    /** Aerolinea para la que opera */
    private Aerolinea aerolinea;
    /** Estadísticas del operador sobre vuelos de su aerolinea */
    private EstadisticasOperador estadisticas;
    /** Alternativas que le propone el gestor a sus propuestas de vuelo */
    private List<Vuelo> vuelosAlternativas;
	private boolean decisionTomada;

    /**
     * Constructor de la clase SolicitudVueloCompartido.
     * 
     * @param vuelosPorgramados Vuelos programados asignados al operador.
     * @param aerolinea         Aerolinea a la que pertenece.
     * @param estadisticas      Estadísticas sobre vuelos.
     * @param aeropuerto        Aeropuerto propio del operador.
     * @param gestor            Gestor del operador.
     */
    public Operador(String nombre, String dni, Aerolinea aerolinea, AeropuertoPropio aeropuerto, Gestor gestor, AirportManager sistema) {
        super(nombre, dni, aeropuerto, gestor, sistema);
        this.contrasena=establecerContrasena();
        this.aerolinea = aerolinea;
        this.solicitudesRecibidas = new ArrayList<>();
        this.solicitudesEnviadas = new ArrayList<>();
        this.estadisticas = new EstadisticasOperador(this);
        this.vuelosAlternativas= new ArrayList<>();
        this.decisionTomada=false;
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
    	this.estadisticas.cargarDatosEstadisticasTiempoMedio();
        return this.estadisticas;
    }

    /**
     * Método para cambiar el estado de un vuelo.
     * 
     * @param vuelo  Vuelo al que se le cambia el estado.
     * @param estado Nuevo estado del vuelo.
     * @throws VueloNoDelUsuarioException
     * @throws EstadoNoDelUsuarioException
     * @throws EstadoEquivocadoException 
     */
    @Override
    public void cambiarEstadoVuelo(Vuelo vuelo, EstadoVuelo estado)
            throws VueloNoDelUsuarioException, EstadoNoDelUsuarioException, EstadoEquivocadoException {
        if (vuelo == null || estado == null) {
            throw new IllegalArgumentException("El vuelo y el nuevo estado no pueden ser null");
        }

        if (vuelo.getOperadorPrincipal()!=this) {
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
     * @param vuelo Vuelo al que se le cambia el estado.
     * @throws EstadoEquivocadoException 
     */
    private void cambiarEstadoEnPreparacionDespegar(Vuelo vuelo) throws EstadoEquivocadoException {
        if (vuelo.getEstado() != EstadoVuelo.OPERATIVO && vuelo.getEstado() != EstadoVuelo.A_TIEMPO && vuelo.getEstado() != EstadoVuelo.RETRASADO 
        		&& vuelo.getEstado() != EstadoVuelo.ACEPTADO) {
        	  throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }
        else {
            vuelo.cambiarEstado(EstadoVuelo.EN_PREPARACION_PARA_DESPEGAR);
            sistema.iniciarActualizacionCada5Min(vuelo);
        }
        for(ControladorAereo controlador: vuelo.getTerminal().getControladores()) {
        	controlador.recibirNotificacionCambioEstado(vuelo);
        }
        
        for (Operador o : vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
        gestor.recibirNotificacion(new Notificacion("El vuelo " + vuelo.getCodigo() + " ha cambiado su estado a " + vuelo.getEstado() + ".", TipoNotificacion.CAMBIO_ESTADO_VUELO));

    }

    /**
     * Método para cambiar el estado de un vuelo a inicio de embarque.
     * 
     * @param vuelo Vuelo al que se le cambia el estado.
     * @throws EstadoEquivocadoException 
     */
    private void cambiarEstadoInicioEmbarque(Vuelo vuelo) throws EstadoEquivocadoException {
        LocalTime horaActual = LocalTime.now();
        long minutosDeDiferencia = Duration.between(horaActual, vuelo.getHora()).toMinutes();

        if (vuelo.getAvion() instanceof AvionMercancias) {
            AvionMercancias avion = (AvionMercancias) vuelo.getAvion();
            double capacidadCarga = avion.getCapacidadMaxCarga();
            if (minutosDeDiferencia <= 30 && (capacidadCarga < 10000)
                    || (minutosDeDiferencia <= 45 && capacidadCarga > 10000)) {
                if (vuelo.getEstado() != EstadoVuelo.EN_PREPARACION_PARA_DESPEGAR && vuelo.getEstado() != EstadoVuelo.A_TIEMPO && vuelo.getEstado() != EstadoVuelo.RETRASADO) {
                	throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
                }
                else {
                    vuelo.cambiarEstado(EstadoVuelo.INICIO_DEL_EMBARQUE);
                }
            }
        } else if (vuelo.getAvion() instanceof AvionPasajeros) {
            AvionPasajeros avion = (AvionPasajeros) vuelo.getAvion();
            int numeroPasajeros = avion.getCapacidadMaxPasajeros();
            if ((minutosDeDiferencia <= 30 && numeroPasajeros < 100)
                    || (minutosDeDiferencia <= 45 && numeroPasajeros > 100)) {
                if (vuelo.getEstado() != EstadoVuelo.EN_PREPARACION_PARA_DESPEGAR && vuelo.getEstado() != EstadoVuelo.A_TIEMPO && vuelo.getEstado() != EstadoVuelo.RETRASADO) {
                	throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
                }
                else {
                    vuelo.cambiarEstado(EstadoVuelo.INICIO_DEL_EMBARQUE);
                }
            }
        }
        for(ControladorAereo controlador: vuelo.getTerminal().getControladores()) {
        	controlador.recibirNotificacionCambioEstado(vuelo);
        }
        
        for (Operador o : vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
        gestor.recibirNotificacion(new Notificacion("El vuelo " + vuelo.getCodigo() + " ha cambiado su estado a " + vuelo.getEstado() + ".", TipoNotificacion.CAMBIO_ESTADO_VUELO));

    }

    /**
     * Método para cambiar el estado de un vuelo a fin de embarque.
     * 
     * @param vuelo Vuelo al que se le cambia el estado.
     * @throws EstadoEquivocadoException 
     */
    private void cambiarEstadoFinEmbarque(Vuelo vuelo) throws EstadoEquivocadoException {
        if (vuelo.getEstado() != EstadoVuelo.INICIO_DEL_EMBARQUE && vuelo.getEstado() != EstadoVuelo.A_TIEMPO && vuelo.getEstado() != EstadoVuelo.RETRASADO) {
        	throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }
        else {
            vuelo.cambiarEstado(EstadoVuelo.FIN_DEL_EMBARQUE);
        }
        for(ControladorAereo controlador: vuelo.getTerminal().getControladores()) {
        	controlador.recibirNotificacionCambioEstado(vuelo);
        }
        
        for (Operador o : vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
        gestor.recibirNotificacion(new Notificacion("El vuelo " + vuelo.getCodigo() + " ha cambiado su estado a " + vuelo.getEstado() + ".", TipoNotificacion.CAMBIO_ESTADO_VUELO));

    }

    /**
     * Método para cambiar el estado de un vuelo a inicio de desembarque.
     * 
     * @param vuelo Vuelo al que se le cambia el estado.
     * @throws EstadoEquivocadoException 
     */
    private void cambiarEstadoInicioDesembarque(Vuelo vuelo) throws EstadoEquivocadoException {
        if (vuelo.getEstado() != EstadoVuelo.ATERRIZAJE && vuelo.getEstado() != EstadoVuelo.A_TIEMPO && vuelo.getEstado() != EstadoVuelo.RETRASADO) {
        	throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }
        else {
            vuelo.cambiarEstado(EstadoVuelo.INICIO_DEL_DESEMBARQUE);
        }
        for(ControladorAereo controlador: vuelo.getTerminal().getControladores()) {
        	controlador.recibirNotificacionCambioEstado(vuelo);
        }
        
        for (Operador o : vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
        gestor.recibirNotificacion(new Notificacion("El vuelo " + vuelo.getCodigo() + " ha cambiado su estado a " + vuelo.getEstado() + ".", TipoNotificacion.CAMBIO_ESTADO_VUELO));

    }

    /**
     * Método para cambiar el estado de un vuelo a fin de desembarque.
     * 
     * @param vuelo Vuelo al que se le cambia el estado.
     * @throws EstadoEquivocadoException 
     */
    private void cambiarEstadoFinDesembarque(Vuelo vuelo) throws EstadoEquivocadoException {
        if (vuelo.getEstado() != EstadoVuelo.INICIO_DEL_DESEMBARQUE && vuelo.getEstado() != EstadoVuelo.A_TIEMPO && vuelo.getEstado() != EstadoVuelo.RETRASADO) {
        	throw new EstadoEquivocadoException("El vuelo no está en el estado correcto para cambiar a este estado");
        }
        else {
            vuelo.cambiarEstado(EstadoVuelo.FIN_DEL_DESEMBARQUE);
        }
        for(ControladorAereo controlador: vuelo.getTerminal().getControladores()) {
        	controlador.recibirNotificacionCambioEstado(vuelo);
        }
        
        for (Operador o : vuelo.getOperadores()) {
            o.recibirNotificacionCambioEstado(vuelo);
        }
        gestor.recibirNotificacion(new Notificacion("El vuelo " + vuelo.getCodigo() + " ha cambiado su estado a " + vuelo.getEstado() + ".", TipoNotificacion.CAMBIO_ESTADO_VUELO));

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

        Avion nuevoAvion = new AvionPasajeros(marca, modelo, dimAlto, dimAncho, dimLargo, autonomia, anoCompra,
                fechaUltimaRevision, matricula, capacidadMaxPasajeros);

        this.getAerolinea().agregarAvion(nuevoAvion);
        System.out.println("Total aviones en aerolínea " + this.getAerolinea().getNombre() + ": " 
                            + this.getAerolinea().getAviones().size());
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

        Avion nuevoAvion = new AvionMercancias(marca, modelo, dimAlto, dimAncho, dimLargo, autonomia, anoCompra,
                fechaUltimaRevision, matricula,
                capacidadMaxCarga, mercaPeligrosa, controlTemperatura);

        this.getAerolinea().agregarAvion(nuevoAvion);
       
        System.out.println("Total aviones en aerolínea " + this.getAerolinea().getNombre() + ": " 
                            + this.getAerolinea().getAviones().size());
    }
   
  
    public void proponerVuelo(String codigo, LocalDate fecha, LocalDate fechaInicio, LocalDate fechaFinal,
            LocalTime hora, List<DayOfWeek> diasRecurrente, TipoCarga tipoCarga, TipoModoEmbarque tipoModoEmbarque,
            Periodicidad periodicidad, TipoVuelo tipoVuelo, Avion avion, Aeropuerto aeropuertoDestino,
            Aeropuerto aeropuertoSalida, Escala escala) throws AvionNoDisponibleException, AvionNoValidoException,
            EscalaInvalidaException, AntelacionInvalidaException {

        List<VueloProgramado> vueloPropuestos = new ArrayList<>();

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
        if ((avion instanceof AvionMercancias && tipoCarga == TipoCarga.PASAJEROS) || (avion instanceof AvionPasajeros && tipoCarga == TipoCarga.MERCANCIAS)) {
            throw new AvionNoValidoException("El avión seleccionado no concuerda con la carga que transporta.");
        }
        if (!avion.estaDisponible(fecha, hora)) {
            throw new AvionNoDisponibleException("El avión seleccionado no está disponible.");
        }
        if(escala!=null) {
        	if (escala.getAeropuertoOrigen().equals(aeropuertoSalida)
                && !escala.getAeropuertoDestino().equals(aeropuertoDestino)) {
            throw new IllegalArgumentException(
                    "No se puede hacer escala en el aeropuerto de salida sin pasar por el de llegada.");
	        }
	
        	if (ChronoUnit.MINUTES.between(escala.getHoraLlegada(), escala.getHoraSalida()) < 60) {
                throw new IllegalArgumentException("La escala debe ser de al menos una hora.");
            }
        }
        
        /** Si cumple todo, se crea un vuelo*/
        VueloProgramado vueloPropuesto = new VueloProgramado(codigo, fecha, fechaInicio, fechaFinal, hora,diasRecurrente, tipoCarga, tipoModoEmbarque,
                											periodicidad, tipoVuelo, avion, aeropuertoDestino, aeropuertoSalida, escala);
        /** Si es puntual, creo un vuelo*/
        if (periodicidad == Periodicidad.PUNTUAL) {
            vueloPropuestos.add(vueloPropuesto);
        } 
        /** Si es diario, creo uno cada día*/
        else if (periodicidad == Periodicidad.DIARIO) {
            LocalDate fechaIterativa = fechaInicio;
            while (!fechaIterativa.isAfter(fechaFinal)) {
                if (!avion.estaDisponible(fechaIterativa, hora)) {
                    throw new AvionNoDisponibleException( "El avión no está disponible para el vuelo diario en la fecha " + fechaIterativa);
                }
                vueloPropuestos.add(new VueloProgramado(codigo + "-" + fechaIterativa, fechaIterativa, fechaInicio, fechaFinal,
                                hora, null, tipoCarga, tipoModoEmbarque, periodicidad, tipoVuelo, avion, aeropuertoDestino, aeropuertoSalida, escala));
                fechaIterativa = fechaIterativa.plusDays(1);
            }
        } 
        /** Si es puntual, creo uno los días indicados*/
        else if (periodicidad == Periodicidad.RECURRENTE) {
            LocalDate fechaIterativa = fechaInicio;
            while (!fechaIterativa.isAfter(fechaFinal)) {
                if (!avion.estaDisponible(fechaIterativa, hora)) {
                    throw new AvionNoDisponibleException("El avión no está disponible para el vuelo diario en la fecha " + fechaIterativa);
                }

                if (diasRecurrente.contains(fechaIterativa.getDayOfWeek())) {
                    vueloPropuestos.add(
                            new VueloProgramado(codigo + "-" + fechaIterativa, fechaIterativa, fechaInicio, fechaFinal,
                                    hora, diasRecurrente, tipoCarga, tipoModoEmbarque, periodicidad, tipoVuelo, avion, aeropuertoDestino, aeropuertoSalida, escala));
                }
                fechaIterativa = fechaIterativa.plusDays(1);
            }
        }
        
        /**Se envian al gestor*/
        getGestor().recibirProposicionVuelo(vueloPropuestos);
        System.out.println("Vuelo propuesto exitosamente.");
    }
    
    public void recibirAlternativas(List<Vuelo> alternativas) {
    	recibirNotificacion(new Notificacion("Nueva alternativa recibida", TipoNotificacion.OPCIONES_REPROGRAMAR_VUELO));
    	iniciarTemporizador();
    	this.vuelosAlternativas=alternativas;
    }
  
    private void iniciarTemporizador() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!decisionTomada) {
                    rechazarVuelo();
                }
            }
        }, Gestor.getTiempoAceptarPropuesta() * 60 * 1000);
    }
    
    public void aceptarVuelo(Vuelo alternativa) {
        if (!decisionTomada) {
        	this.getGestor().aceptarVuelo(alternativa, alternativa.getTerminal(), alternativa.getControlador());
            decisionTomada = true;
            System.out.println("Vuelo aceptado.");
        }
    }

    public void rechazarVuelo() {
		if (!decisionTomada) {
            decisionTomada = true;
            System.out.println("Vuelo rechazado.");
            this.vuelosAlternativas=null;
        }
    }

    public Gestor getGestor() {
        return gestor;
    }

    /**
     * Método para recibir una factura emitida.
     * 
     * @param factura Factura que se recibe.
     */
    public void recibirNotificacionFacturaEmitida(Factura factura) {
        getAerolinea().addFactura(factura);
        recibirNotificacion(new Notificacion("Factura " + factura.getInvoiceIdentifier() + " emitida.", TipoNotificacion.FACTURA_EMITIDA));
    }

    /**
     * Método que añade vuelos propuestos del Operador.
     * 
     * @param factura         Factura que se va a pagar.
     * @param numeroDeTarjeta Tarjeta con la que se va a pagar.
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
     * @param vuelo            Vuelo que se solicita compartir.
     * @param operadorReceptor Aerolinea que ha realizado la solicitud.
     * @param porcentaje       Aerolinea que ha recibido la solicitud.
     * @throws OperacionInvalidaException
     */
    public void solicitarCompartirVuelo(Vuelo vuelo, Operador operadorReceptor, double porcentajeCarga)
            throws SolicitudInvalidaExcepcion, OperacionInvalidaException {

        if (vuelo.getOperadoresAsignados().containsKey(this)) {
            throw new OperacionInvalidaException("No puedes solicitar compartir un vuelo en el que ya participas.");
        }
        if (!vuelo.getOperadorPrincipal().equals(operadorReceptor)) {
            throw new OperacionInvalidaException("Solo el operador principal del vuelo puede recibir solicitudes.");
        }
        if (porcentajeCarga <= 0) {
            throw new SolicitudInvalidaExcepcion("El porcentaje de carga debe ser mayor que 0.");
        }

        SolicitudVueloCompartido solicitud = new SolicitudVueloCompartido(vuelo, this, operadorReceptor,
                porcentajeCarga);
        
        for (SolicitudVueloCompartido solicitudEnviada : solicitudesEnviadas) {
            if (solicitud.getVueloOriginal().equals(vuelo) && solicitudEnviada.getEstadoSolicitud() == EstadoSolicitud.PENDIENTE) {
                throw new OperacionInvalidaException("Ya tienes una solicitud pendiente para este vuelo.");
            }
        }
        
        operadorReceptor.recibirSolicitud(solicitud);
        solicitudesEnviadas.add(solicitud);
        enviarNotificacionCompartirVuelo(operadorReceptor, vuelo);
    }

    /**
     * Método para recibir solicitudes de compartir vuelos.
     * 
     * @param solicitud Solicitud que se recibe.
     */
    public void recibirSolicitud(SolicitudVueloCompartido solicitud) {
        if (!solicitudesRecibidas.contains(solicitud)) {
            this.solicitudesRecibidas.add(solicitud);
            System.out.println("Nueva solicitud recibida para compartir vuelo " + solicitud.getVueloOriginal().getCodigo());
        }
    }

    /**
     * Método para aceptar solicitudes de compartir vuelos.
     * 
     * @param solicitud Solicitud que se quiere aceptar.
     */
    public void aceptarSolicitud(SolicitudVueloCompartido solicitud) {
        Vuelo vuelo = solicitud.getVueloOriginal();

        if (!vuelo.getOperadorPrincipal().equals(this)) {
            throw new IllegalArgumentException("Solo el operador principal del vuelo puede aceptar solicitudes.");
        }

        try {
            solicitud.aceptar();
        } catch (SolicitudInvalidaExcepcion e) {
            System.out.println("No se pudo aceptar la solicitud: " + e.getMessage());
        }
    }

    /**
     * Método para rechazar solicitudes de compartir vuelos.
     * 
     * @param solicitud Solicitud que se quiere aceptar.
     */
    public void rechazarSolicitud(SolicitudVueloCompartido solicitud) {
        solicitud.rechazar();
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
     * @param operadorReceptor Operador al que se envia la solicitud.
     * @param vuelo            Vuelo que se solicita compartir.
     */
    private void enviarNotificacionCompartirVuelo(Operador operadorReceptor, Vuelo vuelo) {
        operadorReceptor.recibirNotificacionSolicitudVueloCompartido(vuelo);
    }

    /**
     * Método para recibir solicitudes de compartir vuelos.
     * 
     * @param vuelo Vuelo que se solicita compartir.
     */
    private void recibirNotificacionSolicitudVueloCompartido(Vuelo vuelo) {
        recibirNotificacion(new Notificacion("Solicitud para compartir el vuelo con código " + vuelo.getCodigo() + ".", TipoNotificacion.SOLICITUD_COMPARTIR_VUELO));
    }
    
    private String establecerContrasena() {
    	return sistema.generarContrasena();
    }

	public List<Vuelo> getAlternativas() {
		if(this.vuelosAlternativas.isEmpty()) {
			System.out.println("No hay aternativas propuestas.");
		}
		return this.vuelosAlternativas;
		
	}

	public List<SolicitudVueloCompartido> getSolicitudesRecibidas() {
		return this.solicitudesRecibidas;
	}
	
	@Override
    public void recibirNotificacion(Notificacion notificacion) {
    	
		if(notificacion.getTipoNotificacion()==TipoNotificacion.CAMBIO_ESTADO_VUELO || notificacion.getTipoNotificacion()==TipoNotificacion.FACTURA_EMITIDA 
				|| notificacion.getTipoNotificacion()==TipoNotificacion.OPCIONES_REPROGRAMAR_VUELO || notificacion.getTipoNotificacion()==TipoNotificacion.SOLICITUD_COMPARTIR_VUELO) {
			 notificaciones.add(notificacion);
			 System.out.println("Nueva notificación: " + notificacion.getMensaje());
		}
    }

	 /**
     * Método para recibir una notificación de cambio de estado de un vuelo.
     * 
     * @param vuelo El vuelo del que trata la notificación.
     */
	@Override
    public void recibirNotificacionCambioEstado(Vuelo vuelo) {
		String mensaje = "El vuelo " + vuelo.getCodigo() + " ha cambiado su estado a " + vuelo.getEstado() + ".";
        Notificacion notificacion = new Notificacion(mensaje, TipoNotificacion.CAMBIO_ESTADO_VUELO);
        
		for(Operador o: vuelo.getOperadores()) {
			if(o==this) {
				recibirNotificacion(notificacion); 
			}
		}
    }
}
