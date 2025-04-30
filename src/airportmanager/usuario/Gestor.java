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
import src.airportmanager.exceptions.AerolineaYaExisteException;
import src.airportmanager.exceptions.AeropuertoYaExisteException;
import src.airportmanager.exceptions.AntelacionInvalidaException;
import src.airportmanager.exceptions.ContrasenaInvalidaException;
import src.airportmanager.exceptions.FormatoDniIncorrectoException;
import src.airportmanager.exceptions.PistaYaExisteException;
import src.airportmanager.exceptions.TemporadasSolapadasException;
import src.airportmanager.exceptions.TerminalYaExisteException;
import src.airportmanager.exceptions.UsuarioNoEncontradoException;
import src.airportmanager.exceptions.UsuarioYaExisteException;
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
import java.time.format.DateTimeFormatter;

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
    /** Vuelos propuestos por el Operador. */
    private List<VueloProgramado> vuelosPropuestos;
    /** Mensaje de la interfaz. */
    private String mensajeGUI;
    /** Formateador de local date */
    private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
    /** Formateador de local time */
    private transient DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
    
    /** Dias de anticipación mínima para una propuesta de Vuelo. */
    private static int diasAnticipacionMinima = 0;
    /** Rango de minutos que como máximo se puede modificar una propuesta de Vuelo.*/
    public static int rangoMinutosMaximoCambioVuelo = 30;
    /** Horas máximas que se puede mover la reprogramación de una propuesta de Vuelo.*/
    private static int horasMaximasReprogramacion = 48;
    /** Recargo de un avión de mercancías. */
    private static double recargoAvionMercancias = 40.5;
    /** Coste base de un vuelo de salida. */
    private static double costeBaseVueloSalida = 99.9;
    /** Coste base de un vuelo de llegada. */
    private static double costeBaseVueloLlegada = 105.3;
    /** Tiempo que tiene el Operador para aceptar una propuesta de Vuelo. */
    private static int tiempoAceptarPropuesta = 60;
    /** Número de alternativa */
    private static int contadorAlternativa=0;

    /**
     * Constructor de la clase Gestor.
     * 
     * @param nombre     Nombre del gestor.
     * @param contrasena Contraseña del gestor.
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
        this.mensajeGUI="";
    }

    /**
     * Método para establecer el sistema de facturación.
     */
    public void establecerSistemaFacturacion() {
        this.sistemaFacturacion= new SistemaFacturacion(recargoAvionMercancias, costeBaseVueloSalida, costeBaseVueloLlegada);

    }
    
    /**
     * Método para obtener el mensaje de la interfaz.
     * 
     * @return El mensaje de la interfaz.
     */
    public String getMensajeGUI() {
    	return mensajeGUI;
    }

    /**
     * Método para emitir una nueva factura.
     * 
     * @param aerolinea Aerolinea a la que se le emite una nueva factura.
     */
    public void emitirFactura(Aerolinea aerolinea) {
        List<Factura> facturas = this.sistemaFacturacion.generarFactura(aerolinea);
        
        if (facturas == null || facturas.isEmpty()) {
            System.out.println("No hay facturas para la aerolínea.");
            return;
        }
        
        for (Factura factura : facturas) {
            if (factura == null) {
                System.out.println("No se generaron facturas para la aerolínea.");
                return;
            }

            try {
                String facturaDir = "/Users/carmen/git/padsoft/facturas/";
                File directorio = new File(facturaDir);
                
                if (!directorio.exists() && !directorio.mkdirs()) {
                    System.err.println("Error: No se pudo crear el directorio de facturas.");
                    return;
                }

                String filePath = facturaDir + "Factura_" + factura.getInvoiceIdentifier() + ".pdf";
                InvoiceSystem.createInvoice(factura, filePath);
                System.out.println("Factura generada en: " + new File(filePath).getAbsolutePath());
            } catch (NonExistentFileException e) {
                System.err.println("Error: El archivo o directorio no existe. " + e.getMessage());
            } catch (UnsupportedImageTypeException e) {
                System.err.println("Error: Tipo de imagen no soportado en la factura. " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error inesperado al generar la factura: " + e.getMessage());
            }

            if (!factura.getAirline().equals(aerolinea.getCodigo())) {
                continue;
            }

            aerolinea.getOperador().recibirNotificacionFacturaEmitida(factura);

            aerolinea.addFactura(factura);
        }
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
     * Método para dar de alta una aerolinea.
     * 
     * @param nombre   Nombre de la nueva aerolinea.
     * @param codigo   Codigo de la nueva aerolinea.
     * @throws AerolineaYaExisteException Ya existe una aerolínea con ese código.
     */
    public void darDeAltaAerolinea(String nombre, String codigo) throws AerolineaYaExisteException {
    	for (Aerolinea a: aeropuerto.getAerolineas()) {
    		if (a.getCodigo().equals(codigo)) {
    			throw new AerolineaYaExisteException("Ya existe una aerolínea con ese código");
    		}
    	}
        Aerolinea aerolinea;
        aerolinea = new Aerolinea(nombre, codigo);
        aeropuerto.addAerolinea(aerolinea);
    }

    /**
     * Método para dar de alta un controlador.
     * 
     * @param nombre     Nombre del nuevo controlador.
     * @param dni        Dni del nuevo controlador.
     * @param terminal   Terminal del nuevo controlador.
     * @throws UsuarioYaExisteException Ya existe un usuario con ese dni.
     * @throws FormatoDniIncorrectoException Formato del dni incorrecto.
     */
    public void darDeAltaControlador(String nombre, String dni, Terminal terminal) throws UsuarioYaExisteException, FormatoDniIncorrectoException {
    	if (sistema.getCredenciales().containsKey(dni)) {
    		throw new UsuarioYaExisteException("Ya existe un usuario con ese dni.");
    	}
    	
        ControladorAereo controlador = new ControladorAereo(nombre, dni, "SoyControlador", aeropuerto, this, terminal, sistema);
        aeropuerto.addUsuario(controlador);
        terminal.agregarControlador(controlador);
        sistema.addCredencial(dni, controlador.getContrasena());
    }

    /**
     * Método para dar de alta un operador.
     * 
     * @param nombre Nombre del nuevo controlador.
     * @param dni    Dni del nuevo controlador. 
     * @param aerolinea	Aerolinea.
     * @throws UsuarioYaExisteException Ya existe un usuario con ese dni.
     * @throws FormatoDniIncorrectoException Formato dni incorrecto.
     */
    public void darDeAltaOperador(String nombre, String dni, Aerolinea aerolinea) throws UsuarioYaExisteException, FormatoDniIncorrectoException {
    	if (sistema.getCredenciales().containsKey(dni)) {
    		throw new UsuarioYaExisteException("Ya existe un usuario con ese dni.");
    	}
    	
        Operador operador = new Operador(nombre, dni, aerolinea, aeropuerto, this, sistema);
        aerolinea.setOperador(operador);
        aeropuerto.addUsuario(operador);
        sistema.addCredencial(dni, ""); 
    }

    /**
     * Método para dar de alta aeropuertos de destino.
     * 
     * @param nombreFichero Nombre del fichero con la informacion del nuevo
     *                      aeropuerto de destino.
     * @throws TemporadasSolapadasException si las temporadas se solpan.
     * @throws IOException si hay errores con el fichero.
     * @throws AeropuertoYaExisteException Ya existe un aeropuerto de destino con ese código.
     */
    public void darDeAltaAeropuertoDestino(String nombreFichero) throws IOException, TemporadasSolapadasException, AeropuertoYaExisteException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(nombreFichero)))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(";");

                if (campos.length >= 9) {
                    String nombre = campos[0];
                    String codigo = campos[1];
                    for (AeropuertoDestino a: aeropuerto.getAeropuertosDestino()) {
                    	if (a.getCodigo().equals(codigo)) {
                    		throw new AeropuertoYaExisteException("Ya existe un aeropuerto de destino con ese código.");
                    	}
                    }
                    String ciudadMasCercana = campos[2];
                    String pais = campos[3];
                    double distanciaCiudad = Double.parseDouble(campos[4]);
                    DireccionRelativaCiudad direccionRespectoCiudad = DireccionRelativaCiudad.valueOf(campos[5]);
                    ZoneOffset diferenciaHoraria = ZoneOffset.ofHours(Integer.parseInt(campos[6]));
                    int numeroTemporadas = Integer.parseInt(campos[7]);

                    List<Temporada> temporadas = new ArrayList<>();
                    int i = 8;

                    while (i < campos.length) {
                        String fechaInicio = campos[i];
                        String[] horarios = campos[i + 1].split("-"); 
                        String horarioApertura = horarios[0];
                        String horarioCierre = horarios[1];
                        String fechaFin = campos[i + 2];

                        Temporada temporada = new Temporada(fechaInicio, horarioApertura, horarioCierre, fechaFin);
                        temporadas.add(temporada);

                        i += 3; 
                    }

                    AeropuertoDestino aeropuertoDestino = new AeropuertoDestino(nombre, codigo, ciudadMasCercana, pais,
                            distanciaCiudad, diferenciaHoraria, numeroTemporadas, direccionRespectoCiudad);

                    for (Temporada temporada : temporadas) {
                        aeropuertoDestino.agregarTemporada(temporada);
                    }

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
     * @param tipo             		Mercancias o pasajeros.
     * @param numeroMaximoAviones 	Número máximo de aviones del hangar.
     * @param dimPlazaAncho    		Ancho de cada plaza del hangar.
     * @param dimPlazaAlto     		Alto de cada plaza del hangar.
     * @param dimPlazaLargo    		Largo de cada plaza del hangar.
     * @param costePorHora     		Coste por hora del hangar.
     * @param mercaPeligrosa   		Bandera si el hangar acepta aviones de mercancias peligrosas.
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
     * @throws TerminalYaExisteException Ya existe una terminal con ese código.
     * 
     */
    public void darDeAltaTerminal(String codigo, String tipo, int aforo, List<PuertaEmbarque> puertasEmbarque) throws TerminalYaExisteException {
    	for (Terminal t: aeropuerto.getTerminales()) {
    		if (t.getCodigo().equals(codigo)) {
    			throw new TerminalYaExisteException("Ya existe una terminal con ese código.");
    		}
    	}
    	
        Terminal terminal;
        if (puertasEmbarque == null || puertasEmbarque.isEmpty()) {
            throw new IllegalArgumentException("La terminal debe tener al menos una puerta.");
        }

        if (tipo.equals("Pasajeros")) {
            terminal = new TerminalPasajeros(codigo, puertasEmbarque, aforo);
        }

        else if (tipo.equals("Mercancías")) {
            terminal = new TerminalMercancias(codigo, puertasEmbarque);
        }

        else {
            throw new IllegalArgumentException("Tipo de terminal no válido" + tipo);
        }
        aeropuerto.addTerminal(terminal);
    }

    /**
     * Método para dar de alta fingers.
     * 
     * @param dimAlto         Dimension de alto del finger.
     * @param costePorHora    Coste por hora del finger.
     * @param puertasEmbarque Puertas de embarque del finger.
     * 
     */
    public void darDeAltaFinger(double dimAlto, double costePorHora, List<PuertaEmbarque> puertasEmbarque) {
    	if (puertasEmbarque.isEmpty()|| puertasEmbarque.size() > 2) {
            throw new IllegalArgumentException("Un finger debe estar conectado a 1 o 2 puertas de embarque");
        }

        Finger finger = new Finger(costePorHora, dimAlto);

        for (PuertaEmbarque puerta : puertasEmbarque) {
            puerta.setFinger(finger);
        }
    }

    /**
     * Método para dar de alta zonas de aparcamiento.
     * 
     * @param puertasEmbarque       Puertas a las que da servicio.
     * @param costePorHora  		Coste por hora de la zona de aparcamiento.
     * @param dimPlazaAncho 		Dimensión de ancho de la zona de aparcamiento.
     * @param dimPlazaLargo 		dimensión de largo de la zona de aparcamiento..
     * 
     */
    public void darDeAltaZonaAparcamiento(double dimPlazaAncho, double costePorHora, double dimPlazaLargo, List<PuertaEmbarque> puertasEmbarque) {
    	ZonaAparcamiento zonaAp= new ZonaAparcamiento(dimPlazaAncho, costePorHora, dimPlazaLargo);
        aeropuerto.addZonaAparcamiento(zonaAp);
        
       for(PuertaEmbarque puerta: puertasEmbarque) {
    	   puerta.addZonaAparcamiento(zonaAp);
       }
    }

    /**
     * Método para dar de una puerta de embarque de una terminal.
     * 
     * @param terminal Terminal en la que se da de alta la puerta de embarque.
     * @param puerta Puerta a dar de alta en la terminal.
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
     * @throws PistaYaExisteException Ya existe una pista con ese código.
     * 
     */
    public void darDeAltaPista(String codigo, double longitud, TipoPista tipoPista) throws PistaYaExisteException {  
    	for (Pista p: aeropuerto.getPistas()) {
    		if (p.getCodigo().equals(codigo)) {
    			throw new PistaYaExisteException("Ya existe una pista con ese código.");
    		}
    	}
    	
        Pista pista;
        pista = new Pista(codigo, longitud, tipoPista);
        aeropuerto.addPista(pista);
    }
    
    /**
     * Método para recibir proposiciones de vuelo.
     * 
     * @param vuelosPropuestosOperador    Vuelos que le propone el Operador.
     * @throws AntelacionInvalidaException si la antelación es menor a la que debería.
     * 
     */
    public void recibirProposicionVuelo(List<VueloProgramado> vuelosPropuestosOperador) throws AntelacionInvalidaException {
        
        recibirNotificacion(new Notificacion("Nuevo vuelo propuesto", TipoNotificacion.SOLICITUD_VUELO_PROGRAMADO));
        if(vuelosPropuestos==null) {
        	this.vuelosPropuestos=new ArrayList<>();
        }
        
    	for (VueloProgramado vuelo : vuelosPropuestosOperador) {
        	if(vuelo!=null) {
        		if (vuelo.getFecha().isBefore(LocalDate.now().plusDays(Gestor.diasAnticipacionMinima))) {
                throw new AntelacionInvalidaException(
                        "La propuesta del vuelo " + vuelo.getCodigo() + " no cumple con la antelación mínima de días.");
	            } else {
	            	vuelosPropuestos.add(vuelo);
	            }
        	}
        }
    }
    
    /**
     * Método que devuelve los vuelos aceptados y las alternativas.
     * 
     * @param vuelo    Vuelo del que son las alternativas.
     * @return Lista de vuelos.
     */
    public List<Vuelo> getVuelosAceptadosMasAlternativas(VueloProgramado vuelo){
    	List<Vuelo> vuelos= new ArrayList<>();
    	vuelos.addAll(getAeropuerto().getVuelosAceptados());
    	vuelos.addAll(getAeropuerto().getVuelosAceptados());
    	vuelos.addAll(vuelo.getAvion().getAerolinea().getOperador().getTodasAlternativas());
    	
    	return vuelos;
    }
    
    /**
     * Método para gestionar vuelos.
     * 
     * @param vueloGestionar    Vuelos que le propone el Operador.
     */
    public void gestionarVuelos(VueloProgramado vueloGestionar) {
	    if (getVuelosAceptadosMasAlternativas(vueloGestionar).isEmpty()) {
	        for (Terminal terminal : aeropuerto.getTerminales()) {
	            if ((vueloGestionar.getTipoCarga() == TipoCarga.MERCANCIAS && terminal instanceof TerminalMercancias) ||
	                (vueloGestionar.getTipoCarga() == TipoCarga.PASAJEROS && terminal instanceof TerminalPasajeros)) {
	                
	                ControladorAereo controladorDisponible = terminal.getControladores().get(0);
	                aceptarVuelo(vueloGestionar, terminal, controladorDisponible);
                    mensajeGUI="El vuelo " + vueloGestionar.getCodigo() + " ha sido aceptado con el controlador: " + controladorDisponible.getNombre();
	                break;
	            }
	        }
	    }

	    boolean gestionado = false;
        Terminal terminalAsignada = null;
        ControladorAereo controladorAsignado = null;
        
        for (Vuelo vueloAceptado : getVuelosAceptadosMasAlternativas(vueloGestionar)) {
            for (Terminal terminal : aeropuerto.getTerminales()) {
                if ((vueloGestionar.getTipoCarga() == TipoCarga.MERCANCIAS && !(terminal instanceof TerminalMercancias)) ||
                    (vueloGestionar.getTipoCarga() == TipoCarga.PASAJEROS && !(terminal instanceof TerminalPasajeros))) {
                    continue;
                }

                for (ControladorAereo controlador : terminal.getControladores()) {
                    if (vueloGestionar.getFecha().equals(vueloAceptado.getFecha()) &&
                    	vueloGestionar.getHora().equals(vueloAceptado.getHora()) &&
                        terminal.equals(vueloAceptado.getTerminal()) &&
                        controlador.equals(vueloAceptado.getControlador())) {

                        if (!sePuedeMover(vueloGestionar, terminal, controlador)) {
                            List<Vuelo> alternativas = generarAlternativas(vueloGestionar, terminal, controlador);
                            vueloGestionar.getAvion().getAerolinea().getOperador().recibirAlternativas(vueloGestionar, alternativas);
                        }

                        gestionado = true;
                        break;
                    }
                }
                if (gestionado) break;
            }
            if (gestionado) break;
        }
        if (!gestionado) {
            for (Terminal terminal : aeropuerto.getTerminales()) {
                if ((vueloGestionar.getTipoCarga() == TipoCarga.MERCANCIAS && terminal instanceof TerminalMercancias) ||
                    (vueloGestionar.getTipoCarga() == TipoCarga.PASAJEROS && terminal instanceof TerminalPasajeros)) {
                    
                    for (ControladorAereo controlador : terminal.getControladores()) {
                        terminalAsignada = terminal;
                        controladorAsignado = controlador;
                        break; 
                    }

                    if (terminalAsignada != null && controladorAsignado != null) {
                        aceptarVuelo(vueloGestionar, terminalAsignada, controladorAsignado);
                        mensajeGUI="El vuelo " + vueloGestionar.getCodigo() + " ha sido aceptado con el controlador: " + controladorAsignado.getNombre();
                    }
                    break;
                }
            }
        }
        
        Iterator<VueloProgramado> iterator = vuelosPropuestos.iterator();
        while (iterator.hasNext()) {
            VueloProgramado vuelo = iterator.next();
            if (vuelo.equals(vueloGestionar)) {
                iterator.remove();
            }
        }
	}
   
    /**
     * Método para ver si se pueden mover vuelos.
     * 
     * @param vuelo    		Vuelo a comprobar.
     * @param terminal    	Terminal.
     * @param controlador   Controlador.
     * @return True si se puede, false si no.
     * 
     */
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
    
    /**
     * Método para mover vuelos.
     * 
     * @param vuelo    			Vuelo a comprobar.
     * @param terminal    		Terminal.
     * @param controlador   	Controlador.
     * @param nuevaFechaHora 	Nueva fecha y hora.
     * 
     */
    private void moverVuelo(VueloProgramado vuelo, LocalDateTime nuevaFechaHora, Terminal terminal, ControladorAereo controlador) {
        vuelo.setHora(nuevaFechaHora.toLocalTime());
        vuelo.setFecha(nuevaFechaHora.toLocalDate());
        aceptarVuelo(vuelo, terminal, controlador);
        mensajeGUI="El vuelo " + vuelo.getCodigo() + " ha sido movido, nueva fecha y hora: " + nuevaFechaHora +".";
    }

    /**
     * Método para verificar si un vuelo está libre.
     * 
     * @param vuelo    			Vuelo a comprobar.
     * @param nuevaFechaHora 	Nueva fecha y hora.
     * @return True si esta libre, false si no.
     * 
     */
    private boolean esHoraLibre(VueloProgramado vuelo, LocalDateTime nuevaFechaHora) {
 
        VueloProgramado vueloAlternativo = new VueloProgramado(vuelo.getCodigo(),nuevaFechaHora.toLocalDate(),vuelo.getFechaInicio(),vuelo.getFechaFinal(),
        		nuevaFechaHora.toLocalTime(),vuelo.getDiasRecurrente(),vuelo.getTipoCarga(),vuelo.getTipoModoEmbarque(),vuelo.getPeriodicidad(),
                vuelo.getTipoVuelo(),vuelo.getAvion(),vuelo.getAeropuertoDestino(),vuelo.getAeropuertoSalida(),vuelo.getEscala());

        for (Vuelo vueloAceptado : getVuelosAceptadosMasAlternativas(vuelo)) {
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
    
    /**
     * Método para generar alternativas.
     * 
     * @param vueloMover   		Vuelo a proponer alternativas.
     * @param terminal    		Terminal.
     * @param controlador   	Controlador.
     * @return Lista de alternativas.
     * 
     */
	public List<Vuelo> generarAlternativas(VueloProgramado vueloMover, Terminal terminal, ControladorAereo controlador) {
    	LocalDateTime fechaHoraOriginal = LocalDateTime.of(vueloMover.getFecha(), vueloMover.getHora());
        LocalDateTime fechaHoraLimite = fechaHoraOriginal.plusHours(Gestor.horasMaximasReprogramacion);
        List<Vuelo> alternativas = new ArrayList<>();
        int alternativasEncontradas = 0;
        contadorAlternativa=0;

        /**Buscamos 5 alternativas máximo en periodos de 5 minutos*/
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
            mensajeGUI="El vuelo " + vueloMover.getCodigo() + " no ha podido ser movido, se han generado alternativas:";
            for(Vuelo v:alternativas) {
            	mensajeGUI+= "\nAlternativa al vuelo: "+v.getCodigo()+"\nFecha/Hora: "+v.getFecha().format(formatter)+"/"+v.getHora().format(format)+" Terminal: "+v.getTerminal()+ " Controlador: "+ v.getControlador().getNombre();
            }
            return alternativas;
        } else {
           return null;
        }
    }
    
	/**
     * Método para crear una alternativa a un vuelo.
     * 
     * @param vueloMover    	Vuelo a proponer alternativa.
     * @param nuevaFechaHora 	Nueva fecha y hora.
     * @return Vuelo alternativo.
     * 
     */
    private Vuelo crearVueloAlternativo(VueloProgramado vueloMover, LocalDateTime nuevaFechaHora) {
    	String codigo=vueloMover.getCodigo()+"A"+contadorAlternativa++;
        return new Vuelo(codigo,nuevaFechaHora.toLocalDate(),vueloMover.getFechaInicio(),
                vueloMover.getFechaFinal(),nuevaFechaHora.toLocalTime(),vueloMover.getDiasRecurrente(),vueloMover.getTipoCarga(),
                vueloMover.getTipoModoEmbarque(), vueloMover.getPeriodicidad(), vueloMover.getTipoVuelo(),vueloMover.getAvion(), 
                vueloMover.getAeropuertoDestino(), vueloMover.getAeropuertoSalida(),vueloMover.getEscala(), vueloMover.getAvion().getAerolinea().getOperador());
    }

    /**
     * Método para verificar si es una alternativa válida a un vuelo.
     * 
     * @param vueloAlternativo    	Vuelo alternativa. 
     * @return True si es una alternativa, false si no.
     * 
     */
    private boolean esAlternativaValida(VueloProgramado vueloAlternativo) {
        for (Vuelo vueloAceptado : getVuelosAceptadosMasAlternativas(vueloAlternativo)) {
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
    
    /**
     * Método para aceptar un vuelo.
     * 
     * @param vueloAceptar    	Vuelo a proponer alternativas.
     * @param terminal    		Terminal.
     * @param controlador   	Controlador.
     * 
     */
    public void aceptarVuelo(VueloProgramado vueloAceptar, Terminal terminal, ControladorAereo controlador) {
    	Vuelo nuevoVuelo= new Vuelo(vueloAceptar.getCodigo(), vueloAceptar.getFecha(), vueloAceptar.getFechaInicio(), vueloAceptar.getFechaFinal(),
    			vueloAceptar.getHora(), vueloAceptar.getDiasRecurrente(), vueloAceptar.getTipoCarga(), vueloAceptar.getTipoModoEmbarque(), vueloAceptar.getPeriodicidad()
    			,vueloAceptar.getTipoVuelo(), vueloAceptar.getAvion(), vueloAceptar.getAeropuertoDestino(), vueloAceptar.getAeropuertoSalida(), vueloAceptar.getEscala(),
    			vueloAceptar.getAvion().getAerolinea().getOperador());
    	aeropuerto.addVueloAceptado(nuevoVuelo);
    	nuevoVuelo.cambiarEstado(EstadoVuelo.ACEPTADO);
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
    @Override 
    public void recibirNotificacion(Notificacion notificacion) {
    	for(TipoNotificacion tipo: getTiposPermitidosNotificaciones() )	{
    		if(notificacion.getTipoNotificacion()==tipo) {
    			 notificaciones.add(notificacion);
    			 System.out.println("Nueva notificación: " + notificacion.getMensaje());
    		}
    	}
    }
    
    /**
     * Método que devuelve el sistema de facturación.
     * 
     * @return Sistema de Facturaicón
     * 
     */
	public SistemaFacturacion getSistemaFacturacion() {
		return sistemaFacturacion;
	}

    /**
     * Método que inicia sesión del gestor.
     * 
     * @param contrasena Contraseña.
     * @throws ContrasenaInvalidaException contrasena invalida.
     */
    public void iniciarSesion(String contrasena)  throws ContrasenaInvalidaException {
        if (!this.contrasena.equals(contrasena)) {
            throw new ContrasenaInvalidaException("La contraseña es inválida.");
        }
    }

    /**
     * Método que modifica la contraseña de un usuario con dni que la ha olvidado.
     * 
     * @param nombre     Nombre.
     * @param usuario    UsuarioConDni que ha olvidado la contraseña.
     * @throws ContrasenaInvalidaException si la contraseña es inválida
     * @throws UsuarioNoEncontradoException si no existe el usuario.
     * 
     */
    public void modificarUsuario(String nombre, UsuarioConDni usuario) throws UsuarioNoEncontradoException, ContrasenaInvalidaException {
        if (usuario != null) {
            usuario.setNombre(nombre);
            String nuevaContrasena="UsuarioNuevo";
            usuario.setContrasena(nuevaContrasena);
            sistema.asignarContrasena(usuario.getDni(), nuevaContrasena);
            System.out.println("Usuario modificado correctamente");
        }

        else {
            System.out.println("Error. El usuario no existe");
        }
    }
    
    /**
     * Método que devuelve los vuelos propuestos por el operador.
     * 
     * @return Lista de vuelos propuestos por el operador
     * 
     */
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
     * Método que devuelve los tipos de notificación que puede recibir el gestor.
     * 
     * @return Lista de tipos de notificación.
     * 
     */
    public List<TipoNotificacion> getTiposPermitidosNotificaciones() {
        return tiposPermitidosNotificaciones;
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

    /**
     * Método para reestablecer la contraseña.
     * 
     * @throws UsuarioNoEncontradoException si no existe el usuario.
     * @throws ContrasenaInvalidaException si la contraseña no es válida.
     */
	public void heOlvidadoContrasena() throws UsuarioNoEncontradoException, ContrasenaInvalidaException {
		String nuevaContrasena="SoyNuevoGestor";
		sistema.asignarContrasena(this.getNombre(), nuevaContrasena);
		this.contrasena=nuevaContrasena;
		
	}

	/**
     * Método para establecer el rango en el que se puede mover un vuelo.
     * 
     * @param i Tiempo.
     * 
     */
	public static void setRangoMinutosMaximoCambioVuelo(int i) {
		rangoMinutosMaximoCambioVuelo=i;
	}

	/**
     * Método para establecer la antelación mínima de propuesta de días.
     * 
     * @param i 	Nueva antelación.
     * 
     */
	public static void setAntelacionMinimaDias(int i) {
		diasAnticipacionMinima=i;
	}

	/**
	 * Busca un vuelo propuesto en la lista de vuelos programados a partir de su código.
	 * 
	 * @param codigo El código del vuelo que se busca.
	 * @return El vuelo programado con el código especificado, o null si no se encuentra.
	 */
	public VueloProgramado buscarVueloPropuestoPorCodigo(String codigo) {
	    VueloProgramado vueloBuscado = null;
	    for (VueloProgramado v: vuelosPropuestos) {
	        if (v.getCodigo().equals(codigo)) {
	            vueloBuscado = v; 
	            break; 
	        }
	    }
	    return vueloBuscado; 
	}

	/**
	 * Busca una aerolínea en el aeropuerto por su nombre.
	 * 
	 * @param nombreAerolinea El nombre de la aerolínea a buscar.
	 * @return La aerolínea con el nombre especificado, o null si no se encuentra.
	 */
	public Aerolinea buscarAerolineaPorNombre(String nombreAerolinea) {        
	    Aerolinea aerolinea = null;
	    for (Aerolinea a : getAeropuerto().getAerolineas()) {
	        if (a.getNombre().equals(nombreAerolinea)) {
	            aerolinea = a; 
	            break; 
	        }
	    }
	    return aerolinea;
	}

	/**
	 * Busca una terminal en el aeropuerto por su código.
	 * 
	 * @param codigo El código de la terminal que se busca.
	 * @return La terminal con el código especificado, o null si no se encuentra.
	 */
	public Terminal buscarTerminalPorCodigo(String codigo) {
	    Terminal terminal = null;
	    for (Terminal t: getAeropuerto().getTerminales()) {
	        if (t.getCodigo().equals(codigo)) {
	            terminal = t; 
	            break; 
	        }
	    }
	    return terminal; 
	}

	/**
	 * Obtiene una puerta de embarque a partir de su ID.
	 * 
	 * @param id El ID de la puerta de embarque que se busca.
	 * @return La puerta de embarque con el ID especificado, o null si no se encuentra.
	 */
	public PuertaEmbarque obtenerPuertaEmbarquePorId(int id) {
	    PuertaEmbarque puerta = null;
	    for (PuertaEmbarque p: getAeropuerto().getPuertasEmbarque()) {
	        if (p.getId() == id) {
	            puerta = p;
	            break; 
	        }
	    }
	    return puerta; 
	}

	/**
	 * Establece las horas máximas permitidas para la reprogramación de un vuelo.
	 * 
	 * @param i El número de horas máximas para la reprogramación de vuelos.
	 */
	public static void setHorasMaxReprogramacionVuelo(int i) {
	    horasMaximasReprogramacion = i; 
	}


}
