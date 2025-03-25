package src.airportmanager.usuario;

import es.uam.eps.padsof.invoices.InvoiceSystem;

import es.uam.eps.padsof.invoices.NonExistentFileException;
import es.uam.eps.padsof.invoices.UnsupportedImageTypeException;
import src.airportmanager.AirportManager;
import src.airportmanager.aeropuerto.AeropuertoDestino;
import src.airportmanager.aeropuerto.AeropuertoPropio;
import src.airportmanager.aeropuerto.DireccionRelativaCiudad;
import src.airportmanager.aeropuerto.Temporada;
import src.airportmanager.avion.Aerolinea;
import src.airportmanager.estadisticas.EstadisticasGestor;
import src.airportmanager.exceptions.AntelacionInvalidaException;
import src.airportmanager.exceptions.ContrasenaInvalidaException;
import src.airportmanager.exceptions.TemporadasSolapadasException;
import src.airportmanager.exceptions.UsuarioNoEncontradoException;
import src.airportmanager.factura.*;
import src.airportmanager.hangar.Hangar;
import src.airportmanager.hangar.HangarMercancias;
import src.airportmanager.hangar.HangarPasajeros;
import src.airportmanager.notificacion.Notificacion;
import src.airportmanager.notificacion.TipoNotificacion;
import src.airportmanager.pista.Pista;
import src.airportmanager.pista.TipoPista;
import src.airportmanager.terminal.Finger;
import src.airportmanager.terminal.PuertaEmbarque;
import src.airportmanager.terminal.Terminal;
import src.airportmanager.terminal.TerminalMercancias;
import src.airportmanager.terminal.TerminalPasajeros;
import src.airportmanager.terminal.ZonaAparcamiento;
import src.airportmanager.vuelo.*;

import java.util.*;
import java.io.*;
import java.time.*;

/**
 * La clase Gestor representa al gestor del AeropuertoPropio.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class Gestor extends Usuario {
	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;
    /** Tipos permitidos de notificaciones. */
    private List<TipoNotificacion> tiposPermitidosNotificaciones;
    /** Estadisticas del gestor sobre la infraestructura. */
    private EstadisticasGestor estadisticas;
    /** Sistema de facturación que utiliza el gestor. */
    private SistemaFacturacion sistemaFacturacion;
    private List<VueloProgramado> vuelosPropuestos;
    
    /** Dias de anticipación mínima para una propuesta de Vuelo. */
    private static int diasAnticipacionMinima = 30;
    /** Rango de minutos que como máximo se puede modificar una propuesta de Vuelo.*/
    public static int rangoMinutosMaximoCambioVuelo = 30;
    /** Horas máximas que se puede mover la reprogramación de una propuesta de Vuelo.*/
    private static int horasMaximasReprogramacion = 48;
    /** Recargo de un avión de mercancías. */
    private static double recargoAvionMercancias = 105.9;
    /** Coste base de un vuelo de salida. */
    private static double costeBaseVueloSalida = 105.9;
    /** Coste base de un vuelo de llegada. */
    private static double costeBaseVueloLlegada = 105.9;
    /** Tiempo que tiene el Operador para aceptar una propuesta de Vuelo. */
    private static int tiempoAceptarPropuesta = 60;

    /**
     * Constructor de la clase Gestor.
     * 
     * @param nombre     Nombre del controlador aéreo.
     * @param dni        Dni del controlador aéreo.
     * @param contraseña Contraseña del controlador aéreo.
     * @param nombre     Nombre del gestor.
     * @param dni        Dni del gestor.
     * @param contraseña Contraseña del gestor.
     * @param aeropuerto Aeropuerto del gestor.
     * @param sistema    Sistema.
     */
    public Gestor(String nombre, String contrasena, AeropuertoPropio aeropuerto, AirportManager sistema) {
        super(nombre, contrasena, aeropuerto, sistema);
        /** Se inicializa a que reciba todas */
        this.tiposPermitidosNotificaciones = new ArrayList<>(Arrays.asList(TipoNotificacion.values()));
        this.estadisticas = new EstadisticasGestor(this);
        this.vuelosPropuestos= new ArrayList<>();
        establecerSistemaFacturacion();
    }

    /**
     * Método para establecer el sistema de facturación.
     * 
     * @return Sistema de facturación.
     */
    public SistemaFacturacion establecerSistemaFacturacion() {
        return new SistemaFacturacion(recargoAvionMercancias, costeBaseVueloSalida, costeBaseVueloLlegada);

    }

    /**
     * Método para emitir una nueva factura.
     * 
     * @param Aerolinea Aerolinea a la que se le emite una nueva factura.
     */
    public void emitirFactura(Aerolinea aerolinea) {
        Factura factura = this.sistemaFacturacion.generarFactura(aerolinea);

        if (factura == null) {
            System.out.println("No se generaron vuelos para la factura.");
            return;
        }

        try {
            File directorio = new File("./facturas/");
            if (!directorio.exists() && !directorio.mkdirs()) {
                System.err.println("Error: No se pudo crear el directorio de facturas.");
                return;
            }

            String filePath = "./facturas/Factura_" + factura.getInvoiceIdentifier() + ".pdf";
            InvoiceSystem.createInvoice(factura, filePath);

            System.out.println("Factura generada correctamente: " + filePath);
        } catch (NonExistentFileException e) {
            System.err.println("Error: El archivo o directorio no existe. " + e.getMessage());
        } catch (UnsupportedImageTypeException e) {
            System.err.println("Error: Tipo de imagen no soportado en la factura. " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al generar la factura: " + e.getMessage());
        }

        aerolinea.getOperador().recibirNotificacionFacturaEmitida(factura);
    }

    /**
     * Método para consultar el estado de una factura.
     * 
     * @param factura Factura que se quiere consultar.
     * @return Estado de una factura en concreto.
     */
    public EstadoFactura verEstadoFactura(Factura factura) {
        return factura.getEstado();
    }

    /**
     * Método para buscar un usuarioConDni por dni.
     * 
     * @param dni Dni del usuario que se busca.
     * @return UsuarioConDni, operador o controlador.
     */
    public UsuarioConDni buscarUsuarioPorDni(String dni) {
        UsuarioConDni usuarioDni;
        for (Usuario usuario : aeropuerto.getUsuarios()) {
            if (usuario instanceof UsuarioConDni) {
                usuarioDni = (UsuarioConDni) usuario;
                if (usuarioDni.getDni().equals(dni)) {
                    return usuarioDni;
                }
            }
        }
        return null;
    }

    /**
     * Método para notificar de un problema con la contraseña del usuario del que se
     * recive el dni.
     * 
     * @param dni Dni del usuario que tiene problemas.
     * 
     */
    public void notificarProblemaContrasena(String dni) {
        Usuario usuario;
        Notificacion notificacion;
        usuario = buscarUsuarioPorDni(dni);

        if (usuario != null) {
            notificacion = new Notificacion("Problema con la contraseña. Contacta con el gestor", TipoNotificacion.OLVIDO_CONTRASENA);
            notificaciones.add(notificacion);
        }

        else {
            System.out.println("Usuario con DNI" + dni + "no encontrado.");
        }
    }

    /**
     * Método para dar de alta una aerolinea.
     * 
     * @param nombre   Nombre de la nueva aerolinea.
     * @param codigo   Codigo de la nueva aerolinea.
     * @param operador Operador de la nueva aerolinea.
     */
    public void darDeAltaAerolinea(String nombre, String codigo) {
        Aerolinea aerolinea;
        aerolinea = new Aerolinea(nombre, codigo);
        aeropuerto.addAerolinea(aerolinea);
    }

    /**
     * Método para dar de alta un controlador.
     * 
     * @param nombre     Nombre del nuevo controlador.
     * @param dni        Dni del nuevo controlador.
     * @param contrasena Contraseña del nuevo controlador.
     * @param terminal   Terminal del nuevo controlador.
     */
    public void darDeAltaControlador(String nombre, String dni, Terminal terminal) {
        ControladorAereo controlador = new ControladorAereo(nombre, dni, sistema.generarContrasena(), aeropuerto, this, terminal, sistema);
        aeropuerto.addUsuario(controlador);
        terminal.agregarControlador(controlador);
        sistema.addCredencial(dni, controlador.getContrasena());
    }

    /**
     * Método para dar de alta un operador.
     * 
     * @param nombre Nombre del nuevo controlador.
     * @param dni    Dni del nuevo controlador.
     */
    public void darDeAltaOperador(String nombre, String dni, Aerolinea aerolinea) {
        Operador operador = new Operador(nombre, dni, aerolinea, aeropuerto, this, sistema);
        aerolinea.setOperador(operador);
        aeropuerto.addUsuario(operador);
        sistema.addCredencial(dni, operador.getContrasena());
    }

    /**
     * Método para dar de alta aeropuertos de destino.
     * 
     * @param nombreFichero Nombre del fichero con la informacion del nuevo
     *                      aeropuerto de destino.
     * @throws TemporadasSolapadasException
     */
    public void darDeAltaAeropuertoDestino(String nombreFichero) throws IOException, TemporadasSolapadasException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(nombreFichero)))) {
            String linea;

            // Leer cada línea del archivo
            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(";");

                // Asegurarse de que la línea tiene suficientes campos
                if (campos.length >= 9) {
                    String nombre = campos[0];
                    String codigo = campos[1];
                    String ciudadMasCercana = campos[2];
                    String pais = campos[3];
                    double distanciaCiudad = Double.parseDouble(campos[4]);
                    DireccionRelativaCiudad direccionRespectoCiudad = DireccionRelativaCiudad.valueOf(campos[5]);
                    ZoneOffset diferenciaHoraria = ZoneOffset.ofHours(Integer.parseInt(campos[6]));
                    int numeroTemporadas = Integer.parseInt(campos[7]);

                    // Lista para almacenar las temporadas
                    List<Temporada> temporadas = new ArrayList<>();
                    int i = 8;

                    // Leer cada temporada y procesarla
                    while (i < campos.length) {
                        String fechaInicio = campos[i];
                        // Dividir el horario en apertura y cierre
                        String[] horarios = campos[i + 1].split("-"); // Se divide el horario por '-'
                        String horarioApertura = horarios[0];
                        String horarioCierre = horarios[1];
                        String fechaFin = campos[i + 2];

                        // Crear objeto Temporada
                        Temporada temporada = new Temporada(fechaInicio, horarioApertura, horarioCierre, fechaFin);
                        temporadas.add(temporada);

                        i += 3; // Avanzar al siguiente conjunto de temporada
                    }

                    // Crear y poblar el objeto AeropuertoDestino
                    AeropuertoDestino aeropuertoDestino = new AeropuertoDestino(nombre, codigo, ciudadMasCercana, pais,
                            distanciaCiudad, diferenciaHoraria, numeroTemporadas, direccionRespectoCiudad);

                    // Añadir todas las temporadas al objeto AeropuertoDestino
                    for (Temporada temporada : temporadas) {
                        aeropuertoDestino.agregarTemporada(temporada);
                    }

                    // Añadir el AeropuertoDestino al aeropuerto
                    this.aeropuerto.addAeropuertoDestino(aeropuertoDestino);
                    System.out.println("Aeropuerto " + nombre + " dado de alta correctamente.");
                } else {
                    System.err.println("Error: la línea no contiene suficientes datos para procesar.");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
 
    /**
     * Método para configurar el nombre del aeropuerto.
     * @param nombre	Nuevo nombre.
     */
    public void configurarAeropuertoPropioNombre(String nombre) {
    	if (nombre == null) {
    		throw new IllegalArgumentException("El nombre no puede ser nulo");
    	}
    	aeropuerto.setNombre(nombre);
    }
    
    /**
     * Método para configurar el código del aeropuerto.
     * @param codigo	Nuevo código.
     */
    public void configurarAeropuertoPropioCodigo(String codigo) {
    	if (codigo == null) {
    		throw new IllegalArgumentException("El código no puede ser nulo");
    	}
    	aeropuerto.setCodigo(codigo);
    }
    
    /**
     * Método para configurar la ciudad más cercana del aeropuerto.
     * @param ciudad	Nueva ciudad.
     */
    public void configurarAeropuertoPropioCiudad(String ciudad) {
    	if (ciudad == null) {
    		throw new IllegalArgumentException("La ciudad no puede ser nula");
    	}
    	aeropuerto.setCiudad(ciudad);
    }
        
    /**
     * Método para configurar el país del aeropuerto.
     * @param pais	Nuevo país.
     */
    public void configurarAeropuertoPropioPais(String pais) {
    	if (pais == null) {
    		throw new IllegalArgumentException("El país no puede ser nulo");
    	}
    	aeropuerto.setPais(pais);
    }
    
    /**
     * Método para configurar la distancia a la ciudad más cercana del aeropuerto.
     * @param distancia	Nueva distancia.
     */
    public void configurarAeropuertoPropioDistanciaCiudad(double distancia) {
    	if (distancia< 0) {
    		throw new IllegalArgumentException("La distancia a la ciudad más cercana no puede ser nula");
    	}
    	aeropuerto.setDistanciaCiudad(distancia);
    }
    
    /**
     * Método para configurar la diferencia horaria del aeropuerto.
     * @param diferencia	Nueva diferencia horaria.
     */
    public void configurarAeropuertoPropioDiferenciaHoraria(ZoneOffset diferencia) {
    	if (diferencia == null) {
    		throw new IllegalArgumentException("La diferencia horaria no puede ser nula");
    	}
    	aeropuerto.setDiferenciaHoraria(diferencia);
    }
   
    /**
     * Método para configurar el número de temporadas del aeropuerto.
     * @param num	Nuevo número de temporadas.
     */
    public void configurarAeropuertoPropioNumTemporadas(int num) {
    	if (num <= 0) {
    		throw new IllegalArgumentException("El número de temporadas tiene que ser positivo");
    	}
    	aeropuerto.setNumTemporadas(num);
    }
    
    /**
     * Método para configurar la dirección relativa a la ciudad más cercana del aeropuerto.
     * @param direccion	Nueva dirección relativa a la ciudad.
     */
    public void configurarAeropuertoPropioDireccionCiudad(DireccionRelativaCiudad direccion) {
    	if (direccion == null) {
    		throw new IllegalArgumentException("La dirección relativa a la ciudad no puede ser nula");
    	}
    	aeropuerto.setDireccionCiudad(direccion);
    }
    
    /**
     * Método para configurar las temporadas del aeropuerto.
     * @param temporadas	Nuevas temporadas.
     */
    public void configurarAeropuertoPropioTemporadas(List<Temporada> temporadas) {
    	if (temporadas == null) {
    		throw new IllegalArgumentException("Las temporadas no pueden ser nulas");
    	}
    	aeropuerto.setTemporadas(temporadas);
    }

    /**
     * Método para dar de alta hangares.
     * 
     * @param tipo             Mercancias o pasajeros.
     * @param numeroMaxAviones Número máximo de aviones del hangar.
     * @param dimPlazaAncho    Ancho de cada plaza del hangar.
     * @param dimPlazaAlto     Alto de cada plaza del hangar.
     * @param dimPlazaLargo    Largo de cada plaza del hangar.
     * @param costePorHora     Coste por hora del hangar.
     * @param mercaPeligrosa   Bandera si el hangar acepta aviones de mercancias
     *                         peligrosas.
     */
    public void darDeAltaHangar(String tipo, int numeroMaximoAviones, double dimPlazaAncho, double dimPlazaAlto,
            double dimPlazaLargo, double costePorHora, boolean mercaPeligrosa) {
        Hangar hangar;

        if (tipo.equals("Pasajeros")) {
            hangar = new HangarPasajeros(numeroMaximoAviones, dimPlazaAncho, dimPlazaAlto, dimPlazaLargo, costePorHora);
        }

        else if (tipo.equals("Mercancías")) {
            hangar = new HangarMercancias(numeroMaximoAviones, dimPlazaAncho, dimPlazaAlto, dimPlazaLargo, costePorHora,
                    mercaPeligrosa);
        }

        else {
            throw new IllegalArgumentException("Tipo de hangar no válido: " + tipo);
        }
        aeropuerto.addHangar(hangar);
    }

    /**
     * Método para dar de alta terminales.
     * 
     * @param codigo          Código de la terminal.
     * @param tipo            Tipo de terminal.
     * @param aforo           Aforo de la terminal.
     * @param puertasEmbarque Puertas de embarque de la terminal.
     * 
     */
    public void darDeAltaTerminal(String codigo, String tipo, int aforo, List<PuertaEmbarque> puertasEmbarque) {

        Terminal terminal;
        if(puertasEmbarque.size()<1) {
        	 throw new IllegalArgumentException("La terminal debe tener mínimo una puerta.");
        }

        if (tipo.equals("Pasajeros")) {
            terminal = new TerminalPasajeros(codigo, puertasEmbarque.get(0), aforo);
        }

        else if (tipo.equals("Mercancías")) {
            terminal = new TerminalMercancias(codigo, puertasEmbarque.get(0));
        }

        else {
            throw new IllegalArgumentException("Tipo de terminal no válido" + tipo);
        }

        for (PuertaEmbarque puerta: puertasEmbarque) {
            terminal.addPuertaEmbarque(puerta);
        }

        aeropuerto.addTerminal(terminal);

    }

    /**
     * Método para dar de alta fingers.
     * 
     * @param codigo          Código del finger.
     * @param dimAlto         Dimension de alto del finger.
     * @param costePorHora    Coste por hora del finger.
     * @param puertasEmbarque Puertas de embarque del finger.
     * @param terminal        Terminal de la que es el finger.
     * 
     */
    public void darDeAltaFinger( double dimAlto, double costePorHora,
            List<PuertaEmbarque> puertasEmbarque) {
        Finger finger;
        HashSet<PuertaEmbarque> puertasSet;

        if (puertasEmbarque.size() < 1 || puertasEmbarque.size() > 2) {
            throw new IllegalArgumentException("Un finger debe estar conectado a 1 o 2 puertas de embarque");
        }

        puertasSet = new HashSet<>();
        puertasSet.addAll(puertasEmbarque);
        finger = new Finger( costePorHora, dimAlto, puertasSet);
        
        for (PuertaEmbarque puerta : puertasEmbarque) {
            puerta.setFinger(finger);
        }
        aeropuerto.addFinger(finger);
    }

    /**
     * Método para dar de alta zonas de aparcamiento.
     * 
     * @param codigo        Código de la zona de aparcamiento.
     * @param costePorHora  Coste por hora de la zona de aparcamiento.
     * @param dimPlazaAncho Dimensión de ancho de la zona de aparcamiento.
     * @param dimPlazaLargo dimensión de largo de la zona de aparcamiento..
     * 
     */
    public void darDeAltaZonaAparcamiento(double dimPlazaAncho, double costePorHora,
            double dimPlazaLargo) {
        aeropuerto.addZonaAparcamiento(new ZonaAparcamiento(dimPlazaAncho, costePorHora, dimPlazaLargo));
    }

    /**
     * Método para dar de una puerta de embarque de una terminal.
     * 
     * @param terminal Terminal en la que se da de alta la puerta de embarque.
     * 
     */
    public void darDeAltaPuertaEmbarque(PuertaEmbarque puerta, Terminal terminal) {
        if (terminal == null) {
            throw new IllegalArgumentException("La terminal no puede ser nula.");
        }

        boolean terminalExiste = false;
        for (Terminal t : aeropuerto.getTerminales()) {
            if (t == terminal) {
                terminalExiste = true;
            }
        }
        if (terminalExiste && puerta!=null) {
            terminal.addPuertaEmbarque(puerta);
        } else {
            throw new IllegalArgumentException("La terminal especificada no existe en el aeropuerto.");
        }
    }

    /**
     * Método para dar de alta pistas.
     * 
     * @param codigo    Código de la pista.
     * @param longitud  Longitud de la pista.
     * @param tipoPista Tipo de la pista.
     * 
     */
    public void darDeAltaPista(String codigo, double longitud, TipoPista tipoPista) {
        Pista pista;
        pista = new Pista(codigo, longitud, tipoPista);
        aeropuerto.addPista(pista);
    }
    
    public void recibirProposicionVuelo(List<VueloProgramado> vuelosPropuestosOperador) throws AntelacionInvalidaException {
        
        recibirNotificacion(new Notificacion("Nuevo vuelo propuesto", TipoNotificacion.SOLICITUD_VUELO_PROGRAMADO));
        List<VueloProgramado> vuelosPropuestos = new ArrayList<>();
        
    	for (VueloProgramado vuelo : vuelosPropuestosOperador) {
        	if(vuelo!=null) {
        		if (!vuelo.getFecha().isBefore(LocalDate.now().plusDays(Gestor.diasAnticipacionMinima))) {
                throw new AntelacionInvalidaException(
                        "La propuesta del vuelo " + vuelo.getCodigo() + " no cumple con la antelación mínima de días.");
	            } else {
	                vuelosPropuestos.add(vuelo);
	            }
        	}
           
        }
    	if(vuelosPropuestos!=null) {
    		this.vuelosPropuestos=vuelosPropuestosOperador;
    	}
    }
    
    public void gestionarVuelos(List<VueloProgramado> vuelosGestionar) {
    	
    	for(VueloProgramado vuelo: vuelosGestionar) {
    		for(Vuelo vueloAceptado: getAeropuerto().getVuelosAceptados()) {
    			 for (Terminal terminal : aeropuerto.getTerminales()) {
	                if ((vuelo.getTipoCarga() == TipoCarga.MERCANCIAS && !(terminal instanceof TerminalMercancias)) ||
	                        (vuelo.getTipoCarga() == TipoCarga.PASAJEROS && !(terminal instanceof TerminalPasajeros))) {
	                    continue;
	                }
	                for (ControladorAereo controlador : terminal.getControladores()) {
	                	/** El nuevo vuelo coincide en terminal, controlador y hora con alguno existente*/
	                	if (vuelo.getFecha().equals(vueloAceptado.getFecha()) && vuelo.getHora().equals(vueloAceptado.getHora())
	                	&& terminal.equals(vueloAceptado.getTerminal()) && controlador.equals(vueloAceptado.getControlador())) {
	                		if(sePuedeMover(vuelo, terminal, controlador)) {
	                			aceptarVuelo(vuelo, terminal, controlador);
	                        }
	                		else {
	                			List<Vuelo> alternativas= generarAlternativas(vuelo, terminal, controlador);
	                			vuelo.getAvion().getAerolinea().getOperador().recibirAlternativas(alternativas);
	                		}
	                    }
	                	else {
                			aceptarVuelo(vuelo, terminal, controlador);
	                	}
	                }
    			 }
    		}
    	}
    	this.vuelosPropuestos=null;
    	
    }
   
    private boolean sePuedeMover(VueloProgramado vuelo, Terminal terminal, ControladorAereo controlador) {
    	LocalDateTime fechaHoraOriginal = LocalDateTime.of(vuelo.getFecha(), vuelo.getHora());
        LocalDateTime nuevaFechaHoraSumada = fechaHoraOriginal.plusMinutes(Gestor.rangoMinutosMaximoCambioVuelo);
        LocalDateTime nuevaFechaHoraRestada = fechaHoraOriginal.minusMinutes(Gestor.rangoMinutosMaximoCambioVuelo);

        if (esHoraLibre(vuelo, nuevaFechaHoraSumada)) {
            moverVuelo(vuelo, nuevaFechaHoraSumada, terminal, controlador);
            return true;
        } 
        else if (esHoraLibre(vuelo, nuevaFechaHoraRestada)) {
            moverVuelo(vuelo, nuevaFechaHoraRestada, terminal, controlador);
            return true;
        }
        return false; 
    }
    
    private void moverVuelo(VueloProgramado vuelo, LocalDateTime nuevaFechaHora, Terminal terminal, ControladorAereo controlador) {
        vuelo.setHora(nuevaFechaHora.toLocalTime());
        vuelo.setFecha(nuevaFechaHora.toLocalDate());
        aceptarVuelo(vuelo, terminal, controlador);
        System.out.println("Vuelo " + vuelo.getCodigo() + " ha sido movido a la nueva hora: " + nuevaFechaHora);
    }

    private boolean esHoraLibre(VueloProgramado vuelo, LocalDateTime nuevaFechaHora) {
 
        VueloProgramado vueloAlternativo = new VueloProgramado(vuelo.getCodigo(),nuevaFechaHora.toLocalDate(),vuelo.getFechaInicio(),vuelo.getFechaFinal(),
        		nuevaFechaHora.toLocalTime(),vuelo.getDiasRecurrente(),vuelo.getTipoCarga(),vuelo.getTipoModoEmbarque(),vuelo.getPeriodicidad(),
                vuelo.getTipoVuelo(),vuelo.getAvion(),vuelo.getAeropuertoDestino(),vuelo.getAeropuertoSalida(),vuelo.getEscala());

        for (Vuelo vueloAceptado : getAeropuerto().getVuelosAceptados()) {
            for (Terminal terminal : aeropuerto.getTerminales()) {
                if ((vueloAlternativo.getTipoCarga() == TipoCarga.MERCANCIAS && !(terminal instanceof TerminalMercancias)) ||
                    (vueloAlternativo.getTipoCarga() == TipoCarga.PASAJEROS && !(terminal instanceof TerminalPasajeros))) {
                    continue;
                }

                for (ControladorAereo controlador : terminal.getControladores()) {
                    if (vueloAlternativo.getFecha().equals(vueloAceptado.getFecha()) &&
                        vueloAlternativo.getHora().equals(vueloAceptado.getHora()) &&
                        terminal.equals(vueloAceptado.getTerminal()) &&
                        controlador.equals(vueloAceptado.getControlador())) {
                        return false;
                    }
                }
            }
        }

        return true; 
    }
	public List<Vuelo> generarAlternativas(VueloProgramado vueloMover, Terminal terminal, ControladorAereo controlador) {
    	LocalDateTime fechaHoraOriginal = LocalDateTime.of(vueloMover.getFecha(), vueloMover.getHora());
        LocalDateTime fechaHoraLimite = fechaHoraOriginal.plusHours(Gestor.horasMaximasReprogramacion);
        List<Vuelo> alternativas = new ArrayList<>();
        int alternativasEncontradas = 0;

        /**Buscamos 5 alternativas máximo en periodos de 30 minutos*/
        for (LocalDateTime nuevaFechaHora = fechaHoraOriginal.plusMinutes(30); nuevaFechaHora.isBefore(fechaHoraLimite) && alternativasEncontradas < 5; 
        nuevaFechaHora = nuevaFechaHora.plusMinutes(30)) {
        	
            Vuelo vueloAlternativo = crearVueloAlternativo(vueloMover, nuevaFechaHora);
            if (esAlternativaValida(vueloAlternativo)) {
                alternativas.add(vueloAlternativo);
                alternativasEncontradas++;
                vueloAlternativo.setTerminal(terminal);
                vueloAlternativo.setControlador(controlador);
            }
        }
        if (!alternativas.isEmpty()) {
            System.out.println("Se encontraron " + alternativas.size() + " alternativas para el vuelo " + vueloMover.getCodigo());
            return alternativas;
        } else {
           return null;
        }
    }
    
    private Vuelo crearVueloAlternativo(VueloProgramado vueloMover, LocalDateTime nuevaFechaHora) {
        return new Vuelo( vueloMover.getCodigo(),nuevaFechaHora.toLocalDate(),vueloMover.getFechaInicio(),
                vueloMover.getFechaFinal(),nuevaFechaHora.toLocalTime(),vueloMover.getDiasRecurrente(),vueloMover.getTipoCarga(),
                vueloMover.getTipoModoEmbarque(), vueloMover.getPeriodicidad(), vueloMover.getTipoVuelo(),vueloMover.getAvion(), 
                vueloMover.getAeropuertoDestino(), vueloMover.getAeropuertoSalida(),vueloMover.getEscala(), vueloMover.getAvion().getAerolinea().getOperador());
    }

    private boolean esAlternativaValida(VueloProgramado vueloAlternativo) {
        for (Vuelo vueloAceptado : getAeropuerto().getVuelosAceptados()) {
            for (Terminal terminal : aeropuerto.getTerminales()) {
                if ((vueloAlternativo.getTipoCarga() == TipoCarga.MERCANCIAS && !(terminal instanceof TerminalMercancias)) ||
                    (vueloAlternativo.getTipoCarga() == TipoCarga.PASAJEROS && !(terminal instanceof TerminalPasajeros))) {
                    continue;
                }
                for (ControladorAereo controlador : terminal.getControladores()) {
                    if (vueloAlternativo.getFecha().equals(vueloAceptado.getFecha()) &&
                        vueloAlternativo.getHora().equals(vueloAceptado.getHora()) &&
                        terminal.equals(vueloAceptado.getTerminal()) &&
                        controlador.equals(vueloAceptado.getControlador())) {
                        return false;
                    }
                }
            }
        }
        return true; 
    }
    
    public void aceptarVuelo(VueloProgramado vueloAceptar, Terminal terminal, ControladorAereo controlador) {
    	Vuelo nuevoVuelo= new Vuelo(vueloAceptar.getCodigo(), vueloAceptar.getFecha(), vueloAceptar.getFechaInicio(), vueloAceptar.getFechaFinal(),
    			vueloAceptar.getHora(), vueloAceptar.getDiasRecurrente(), vueloAceptar.getTipoCarga(), vueloAceptar.getTipoModoEmbarque(), vueloAceptar.getPeriodicidad()
    			,vueloAceptar.getTipoVuelo(), vueloAceptar.getAvion(), vueloAceptar.getAeropuertoDestino(), vueloAceptar.getAeropuertoSalida(), vueloAceptar.getEscala(),
    			vueloAceptar.getAvion().getAerolinea().getOperador());
    	getAeropuerto().addVueloAceptado(nuevoVuelo);
    	nuevoVuelo.setTerminal(terminal);
    	nuevoVuelo.setControlador(controlador);
    }

    /**
     * Método para ver las estadísticas del gestor.
     * 
     * @return Las estadísticas del gestor.
     * 
     */
    public EstadisticasGestor getEstadisticasAeropuerto() {
        return this.estadisticas;
    }

    /**
     * Método para cargar las facturas que ha emitido el gestor.
     * 
     * @return Mapa de código de factura y estado.
     * 
     */
    public Map<String, EstadoFactura> cargarFacturas() {
        Map<String, EstadoFactura> facturasEstado = new HashMap<String, EstadoFactura>();

        for (Aerolinea aerolinea : this.aeropuerto.getAerolineas()) {
            for (Factura factura : aerolinea.getFacturas()) {
                facturasEstado.put(factura.getInvoiceIdentifier(), factura.getEstado());
            }
        }
        return facturasEstado;
    }

    /**
     * Método que imprime las facturas que ha emitido el gestor.
     * 
     */
    public void verFacturas() {
        Map<String, EstadoFactura> facturasEstado = cargarFacturas();
        for (Map.Entry<String, EstadoFactura> entry : facturasEstado.entrySet()) {
            System.out.println("Factura ID: " + entry.getKey() + " | Estado: " + entry.getValue());
        }
    }

    /**
     * Método para añadir tipos de notificacion a las que puede recibir el gestor.
     * 
     * @param tipos Tipos de notificaciones que se añaden.
     * 
     */
    public void configurarNotificaciones(List<TipoNotificacion> tipos) {
        this.tiposPermitidosNotificaciones = new ArrayList<>(tipos);

    }

    /**
     * Método para recibir notificaciones.
     * 
     * @param notificacion Notificación que se recibe.
     * 
     */
    public void recibirNotificaciones(Notificacion notificacion) {
    	for(TipoNotificacion tipo: getTiposPermitidosNotificaciones() )	{
    		if(notificacion.getTipoNotificacion()==tipo) {
    			 notificaciones.add(notificacion);
    			 System.out.println("Nueva notificación: " + notificacion.getMensaje());
    		}
    	}
    }

    /**
     * Método que inicia sesión del gestor.
     * 
     * @param contrasena Contraseña.
     * 
     */
    public void iniciarSesion(String contrasena) {
        if (this.contrasena.equals(contrasena)) {
            System.out.println("Inicio de sesión exitoso");
        }

        else {
            System.out.println("Contraseña incorrecta. Inténtelo de nuevo");
        }
    }

    /**
     * Método que modifica la contraseña de un usuario con dni que la ha olvidado.
     * 
     * @param nombre     Nombre.
     * @param contrasena Contraseña nueva.
     * @param usuario    UsuarioConDni que ha olvidado la contraseña.
     * @throws ContrasenaInvalidaException 
     * @throws UsuarioNoEncontradoException 
     * 
     */
    public void modificarUsuario(String nombre, UsuarioConDni usuario) throws UsuarioNoEncontradoException, ContrasenaInvalidaException {

        if (usuario != null) {
            usuario.setNombre(nombre);
            String nuevaContrasena=sistema.generarContrasena();
            usuario.setContrasena(nuevaContrasena);
            sistema.asignarContrasena(usuario.getDni(), nuevaContrasena);
            System.out.println("Usuario modificado correctamente");
        }

        else {
            System.out.println("Error. El usuario no existe");
        }
    }
    public List<VueloProgramado> getVuelosPropuestos() {
		if(this.vuelosPropuestos==null) {
			System.out.println("No hay aternativas propuestas.");
			return null;
		}
		else {
			return this.vuelosPropuestos;
		}
		
	}
    
    /**
     * Método que establece el coste base de los vuelos de salida desde el
     * aeropuerto.
     * 
     * @param nuevocosteBaseVueloSalida El nuevo coste base para los vuelos de
     *                                  salida.
     */
    public static void setCosteBaseVueloSalida(double nuevocosteBaseVueloSalida) {
        costeBaseVueloSalida = nuevocosteBaseVueloSalida;
    }

    /**
     * Método que establece el coste base de los vuelos de llegada al aeropuerto.
     * 
     * @param nuevocosteBaseVueloLlegada El nuevo coste base para los vuelos de
     *                                   llegada.
     */
    public static void setCosteBaseVueloLlegada(double nuevocosteBaseVueloLlegada) {
        costeBaseVueloLlegada = nuevocosteBaseVueloLlegada;
    }

    /**
     * Método que establece el recargo adicional para los vuelos de aviones de
     * mercancías.
     * 
     * @param nuevorecargoAvionMercancias El nuevo recargo que se aplicará a los
     *                                    vuelos de aviones de mercancías.
     */
    public static void setRecargoAvionMercancias(double nuevorecargoAvionMercancias) {
        recargoAvionMercancias = nuevorecargoAvionMercancias;
    }

    /**
     * Método que devuelve los tipos de notificación que puede recibir el gestor.
     * 
     * @return Lista de tipos de notificación.
     * 
     */
    public List<TipoNotificacion> getTiposPermitidosNotificaciones() {
        return tiposPermitidosNotificaciones;
    }

    /**
     * Método para añadir un tipo de notificación a los que puede recibir.
     * 
     * @param tiposPermitidosNotificaciones Tipo de notificación.
     * 
     */
    public void addTiposPermitidosNotificaciones(TipoNotificacion tiposPermitidosNotificaciones) {
        this.tiposPermitidosNotificaciones.add(tiposPermitidosNotificaciones);
    }

    /**
     * Método que devuelve el tiempo que tiene el Operador para aceptar una
     * propuesta de Vuelo.
     * 
     * @return Tiempo que tiene el Operador para aceptar una propuesta de Vuelo.
     * 
     */
    public static int getTiempoAceptarPropuesta() {
        return tiempoAceptarPropuesta;
    }

    /**
     * Método para establecer el tiempo que tiene el Operador para aceptar una
     * propuesta de Vuelo.
     * 
     * @param nuevoTiempo Tiempo.
     * 
     */
    public void setTiempoAceptarPropuesta(int nuevoTiempo) {
        tiempoAceptarPropuesta = nuevoTiempo;
    }

	public void heOlvidadoContrasena() throws UsuarioNoEncontradoException, ContrasenaInvalidaException {
		String nuevaContrasena=sistema.generarContrasena();
		sistema.asignarContrasena(this.getNombre(), nuevaContrasena);
		
	}

	public static void setRangoMinutosMaximoCambioVuelo(int i) {
		rangoMinutosMaximoCambioVuelo=i;
		
	}

}
