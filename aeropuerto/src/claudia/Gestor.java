package aeropuerto.src.claudia;
import aeropuerto.src.maria.*;
import es.uam.eps.padsof.invoices.InvoiceSystem;
import es.uam.eps.padsof.invoices.NonExistentFileException;
import es.uam.eps.padsof.invoices.UnsupportedImageTypeException;
import aeropuerto.src.carmen.*;
import aeropuerto.src.carmen.factura.*;
import aeropuerto.src.carmen.vuelo.*;
import java.util.*;
import java.io.*;
import java.time.*;

/**
 * La clase Gestor representa al gestor del AeropuertoPropio.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public class Gestor extends Usuario{

    private List<TipoNotificacion> tiposPermitidosNotificaciones;
    private EstadisticasGestor estadisticas;
    private SistemaFacturacion sistemaFacturacion;
    private List<VueloProgramado> vuelosPropuestos;
    
    private static int diasAnticipacionMinima = 30;
    private static int rangoMinutosMaximoCambioVuelo=30;
    private static int horasMaximasReprogramacion=48;
    private static double recargoAvionMercancias= 105.9;
    private static double costeBaseVueloSalida= 105.9;
    private static double costeBaseVueloLlegada= 105.9;

    /**
     * Constructor de la clase Gestor.
     * 
     * @param nombre                Nombre del controlador aéreo.
     * @param dni                   Dni del controlador aéreo.
     * @param contraseña            Contraseña del controlador aéreo.
     */
    public Gestor(String nombre, String contrasena, AeropuertoPropio aeropuerto){
        super(nombre, contrasena, aeropuerto);
        this.tiposPermitidosNotificaciones = new ArrayList<>();
        this.estadisticas = new EstadisticasGestor(this);
        this.sistemaFacturacion= establecerSistemaFacturacion();
    }

    /**
     * Método para establecer el sistema de facturación.
     * 
     * @return Sistema de facturación.
     */
    public SistemaFacturacion establecerSistemaFacturacion( ) {
    	return new SistemaFacturacion(recargoAvionMercancias,costeBaseVueloSalida, costeBaseVueloLlegada);
    	
    }
    
    /**
     * Método para emitir una nueva factura.
     * 
     * @param Aerolinea		Aerolinea a la que se le emite una nueva factura.
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
     * @param factura 	Factura que se quiere consultar.
     * @return Estado de una factura en concreto.
     */
    public EstadoFactura verEstadoFactura(Factura factura) {
        return factura.getEstado();
    }

    /**
     * Método para buscar un usuarioConDni por dni.
     * 
     * @param dni	Dni del usuario que se busca.
     * @return UsuarioConDni, operador o controlador.
     */
    private UsuarioConDni buscarUsuarioPorDni(String dni) {
        UsuarioConDni usuarioDni;
        for (Usuario usuario: aeropuerto.getUsuarios()) {
            if (usuario instanceof UsuarioConDni) {
                usuarioDni = (UsuarioConDni) usuario;
                if (usuarioDni.getDni().equals(dni)) {
                    return usuarioDni;
                }
            }
        }
        return null;
    }

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
     * @param nombre	Nombre de la nueva aerolinea.
     * @param codigo	Codigo de la nueva aerolinea.
     * @param operador	Operador de la nueva aerolinea.
     */
    public void darDeAltaAerolinea(String nombre, String codigo, Operador operador) {
        Aerolinea aerolinea;
        aerolinea = new Aerolinea(nombre, codigo, operador);
        aeropuerto.addAerolinea(aerolinea);
    }

    /**
     * Método para dar de alta un controlador.
     * 
     * @param nombre		Nombre del nuevo controlador.
     * @param dni    		Dni del nuevo controlador.
     * @param contrasena	Contraseña del nuevo controlador.
     * @param terminal		Terminal del nuevo controlador.
     */
    public void darDeAltaControlador(String nombre, String dni, String contrasena, Terminal terminal) {
        ControladorAereo controlador = new ControladorAereo(nombre, dni, contrasena, aeropuerto, this, terminal);
        aeropuerto.addUsuario(controlador);
        terminal.agregarControlador(controlador);
    }

    /**
     * Método para dar de alta aeropuertos de destino.
     * 
     * @param nombreFichero		Nombre del fichero con la informacion del nuevo aeropuerto de destino.
     * @throws TemporadasSolapadasException 
     */
    public void darDeAltaAeropuertoDestino(String nombreFichero) throws IOException, TemporadasSolapadasException {
    	try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(nombreFichero)))) {
            String linea = reader.readLine();
            
            if (linea != null) {
                String[] campos = linea.split(";");

                if (campos.length >= 9) {
                    String nombre = campos[0];
                    String codigo = campos[1];
                    String ciudadMasCercana = campos[2];
                    String pais = campos[3];
                    double distanciaCiudad = Double.parseDouble(campos[4]);
                    DireccionRelativaCiudad direccionRespectoCiudad =DireccionRelativaCiudad.valueOf(campos[5]);
                    ZoneOffset diferenciaHoraria = ZoneOffset.ofHours(Integer.parseInt(campos[6]));
                    int numeroTemporadas = Integer.parseInt(campos[7]);

                    List<Temporada> temporadas = new ArrayList<>();
                    int i = 8;
                    while (i < campos.length) {
                        String fechaInicio = campos[i];
                        String horarioApertura = campos[i + 1].split("-")[0];
                        String horarioCierre = campos[i + 1].split("-")[1];
                        String fechaFin = campos[i + 2];

                        Temporada temporada = new Temporada(fechaInicio, horarioApertura, horarioCierre,
                                fechaFin);
                        temporadas.add(temporada);

                        i +=3;
                    }
                    
                    AeropuertoDestino aeropuertoDestino = new AeropuertoDestino(nombre, codigo, ciudadMasCercana, pais,
                            distanciaCiudad, diferenciaHoraria, numeroTemporadas, direccionRespectoCiudad);
                    for(Temporada temporada: temporadas) {
                    	aeropuertoDestino.agregarTemporada(temporada);
                    }  	
                    this.aeropuerto.addAeropuertoDestino(aeropuertoDestino);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    /**
     * Método para configurar el aeropuerto.
     * 
     * @param nombre                Nombre del aeropuerto propio.
     * @param codigo                Código del aeropuerto propio.
     * @param ciudad                Ciudad más cercana al aeropuerto propio.
     * @param pais                  País del aeropuerto propio.
     * @param distanciaCiudad       Distancia hasta la ciudad más cercana al aeropuerto propio.
     * @param diferenciaHoraria     Diferencia horaria UTC del aeropuerto propio.
     * @param numTemporadas         Número de temporadas del aeropuerto propio.
     * @param direccionCiudad       Dirección relativa a la ciudad más cercana del aeropuerto propio.
     * @param temporadas			Temporadas del aeropuerto.
     * @throws TemporadasSolapadasException 
     */
    public void configurarAeropuertoPropio(String nombre, String codigo, String ciudadMasCercana, String pais, double distancia, ZoneOffset diferenciaHoraria, int numTemporadas, 
    										DireccionRelativaCiudad direccion, List<Temporada> temporadas) throws TemporadasSolapadasException {
        AeropuertoPropio aeropuertoPropio;
        aeropuertoPropio = new AeropuertoPropio(nombre, codigo, ciudadMasCercana, pais, distancia, diferenciaHoraria, numTemporadas, direccion);
        for(Temporada temporada: temporadas) {
        	aeropuertoPropio.agregarTemporada(temporada);
        }  	
        this.aeropuerto= aeropuertoPropio;
    }

    /**
     * Método para dar de alta hangares.
     * 
     * @param tipo				Mercancias o pasajeros.
     * @param numeroMaxAviones  Número máximo de aviones del hangar.
     * @param dimPlazaAncho     Ancho de cada plaza del hangar.
     * @param dimPlazaAlto      Alto de cada plaza del hangar.
     * @param dimPlazaLargo     Largo de cada plaza del hangar.
     * @param costePorHora      Coste por hora del hangar.
     * @param mercaPeligrosa	Bandera si el hangar acepta aviones de mercancias peligrosas.
     */
    public void darDeAltaHangar(String tipo, int numeroMaximoAviones, double dimPlazaAncho, double dimPlazaAlto, double dimPlazaLargo, double costePorHora, boolean mercaPeligrosa) {
        Hangar hangar;

        if (tipo.equals("Pasajeros")) {
            hangar = new HangarPasajeros(numeroMaximoAviones, dimPlazaAncho, dimPlazaAlto, dimPlazaLargo, costePorHora);
        }

        else if (tipo.equals("Mercancías")) {
            hangar = new HangarMercancias(numeroMaximoAviones, dimPlazaAncho, dimPlazaAlto, dimPlazaLargo, costePorHora, mercaPeligrosa);
        }

        else {
            throw new IllegalArgumentException("Tipo de hangar no válido: " + tipo);
        }
        aeropuerto.addHangar(hangar); 
    }

    /**
     * Método para dar de alta terminales.
     * 
     * @param codigo			Código de la terminal.
     * @param tipo  			Tipo de terminal.
     * @param aforo     		Aforo de la terminal.
     * @param puertasEmbarque   Puertas de embarque de la terminal.
     * 
     */
    public void darDeAltaTerminal(String codigo, String tipo, int aforo, List<PuertaEmbarque> puertasEmbarque) {

        Terminal terminal;
        int i=0;

        if (tipo.equals("Pasajeros")) {
            terminal = new TerminalPasajeros(codigo, puertasEmbarque.get(0), aforo);
        }

        else if (tipo.equals("Mercancías")) {
            terminal = new TerminalMercancias(codigo, puertasEmbarque.get(0));
        }

        else{
            throw new IllegalArgumentException ("Tipo de terminal no válido" + tipo);
        }

        for (i = 1; i < puertasEmbarque.size(); i++) {
            terminal.añadirPuertaEmbarque(puertasEmbarque.get(i));
        }

        aeropuerto.addTerminal(terminal);

    }

    /**
     * Método para dar de alta fingers.
     * 
     * @param codigo			Código del finger.
     * @param dimAlto  			Dimension de alto del finger.
     * @param costePorHora     	Coste por hora del finger.
     * @param puertasEmbarque   Puertas de embarque del finger.
     * @param terminal			Terminal de la que es el finger.
     * 
     */
    public void darDeAltaFinger(String codigo, double dimAlto, double costePorHora, List<PuertaEmbarque> puertasEmbarque){
        Finger finger;
        HashSet<PuertaEmbarque> puertasSet;

        if (puertasEmbarque.size() < 1 || puertasEmbarque.size() > 2) {
            throw new IllegalArgumentException("Un finger debe estar conectado a 1 o 2 puertas de embarque");
        }

        puertasSet = new HashSet<>();
        puertasSet.addAll(puertasEmbarque);
        finger = new Finger(codigo, costePorHora, dimAlto, puertasSet);
        aeropuerto.addFinger(finger);
    }
    
    /**
     * Método para dar de alta zonas de aparcamiento.
     * 
     * @param codigo                    Código de la zona de aparcamiento.
     * @param costePorHora              Coste por hora de la zona de aparcamiento.
     * @param dimPlazaAncho             Dimensión de ancho de la zona de aparcamiento. 
     * @param dimPlazaLargo             dimensión de largo de la zona de aparcamiento..
     * 
     */
    public void darDeAltaZonaAparcamiento(String codigo, double dimPlazaAncho, double costePorHora, double dimPlazaLargo){
    	aeropuerto.addZonaAparcamiento(new ZonaAparcamiento(codigo, dimPlazaAncho, costePorHora, dimPlazaLargo));
    }
    
    /**
     * Método para dar de una puerta de embarque de una terminal.
     * 
     * @param terminal			Terminal en la que se da de alta la puerta de embarque.
     * 
     */
    public void darDeAltaPuertaEmbarque(Terminal terminal){
    	if (terminal == null) {
            throw new IllegalArgumentException("La terminal no puede ser nula.");
        }
    	
    	boolean terminalExiste=false;
    	for(Terminal t: aeropuerto.getTerminales()) {
    		if(t==terminal) {
    			terminalExiste=true;
    		}
    	}
    	if(terminalExiste) {
    		terminal.añadirPuertaEmbarque(new PuertaEmbarque());
    	}   
    	else {
            throw new IllegalArgumentException("La terminal especificada no existe en el aeropuerto.");
        }
    }
    

    /**
     * Método para dar de alta pistas.
     * 
     * @param codigo			Código de la pista.
     * @param longitud 			Longitud de la pista.
     * @param tipoPista     	Tipo de la pista.
     * 
     */
    public void darDeAltaPista (String codigo, double longitud, TipoPista tipoPista){
        Pista pista;
        pista = new Pista(codigo, longitud, tipoPista);
        aeropuerto.addPista(pista);
    }
    
    public void recibirNotificacionProposicionVuelo(List<VueloProgramado> vuelosPropuestosOperador) throws AntelacionInvalidaException {
    	for(VueloProgramado vuelo: vuelosPropuestosOperador) {
    		if(!vuelo.getFecha().isBefore(LocalDate.now().plusDays(diasAnticipacionMinima))) {
    			throw new AntelacionInvalidaException("La propuesta del vuelo " + vuelo.getCodigo() + " no cumple con la antelación mínima de días.");
    		}
    		else{
    			vuelosPropuestos.add(vuelo);
    		}
    	}
    	notificaciones.add(new Notificacion("Nuevo vuelo propuesto", TipoNotificacion.SOLICITUD_VUELO_PROGRAMADO));
    }

    /**
     * Método para gestionar vuelos, este método procesa todos los vuelos que el gestor tiene propuestos(los ha propuesto
     * algún operador) y si se pueden programar los añade, si no intenta reprogramarlos.
     * @throws VueloSolapadoException 
     * 
     */
    public void gestionarVuelos() throws VueloSolapadoException {
    	for(VueloProgramado vuelo: vuelosPropuestos) {
    		if(puedeProgramarse(vuelo)) {
    			aeropuerto.addVueloAceptado((Vuelo)vuelo);
    		}
    		else{
    			vuelo.getAvion().getAerolinea().getOperador().recibirNotificacionOpcionesReprogramacion();
    		}
    	}

    }
    
    /**
     * Método para comprobar si un vuelo puede programarse.
     * 
     * @param vuelo			Vuelo que se quiere comprobar.
     * @return True si se puede, false si no.
     * @throws VueloSolapadoException 
     * 
     */
    public boolean puedeProgramarse(VueloProgramado vuelo) throws VueloSolapadoException {
    	boolean puedeProgramarse=false;
    	for (Vuelo vueloExistente : aeropuerto.getVuelosAceptados()) {
            
            for (Terminal terminal : aeropuerto.getTerminales()) {
            	if ((vuelo.getTipoCarga() == TipoCarga.MERCANCIAS && !(terminal instanceof TerminalMercancias)) ||
                        (vuelo.getTipoCarga() == TipoCarga.PASAJEROS && !(terminal instanceof TerminalPasajeros))) {
                        continue;
                }
                for (ControladorAereo controlador : terminal.getControladores()) {
                    if (vuelosSeSolapan(vuelo, vueloExistente, terminal, controlador)) {
                        if (!moverVuelo(vuelo, terminal, controlador)) {
                        	buscarAlternativas(vuelo,vueloExistente, terminal, controlador);
                        } else {
                            System.out.println("Vuelo movido exitosamente para evitar solapamiento.");
                        }
                        puedeProgramarse = true;
                        break; 
                    }
                }
            }
        }

        return puedeProgramarse;
    }
    
    /**
     * Método para comprobar si un vuelo puede moverse en el rango definido.
     * 
     * @param vuelo			Vuelo que se quiere comprobar.
     * @param terminal		Terminal donde se quiere asignar el vuelo.
     * @param controlador	Controlador al que se le quiere asignar el vuelo.
     * @return True si se puede, false si no.
     * 
     */
    private boolean moverVuelo(VueloProgramado vuelo, Terminal terminal, ControladorAereo controlador) {
    	LocalDate nuevaFecha = vuelo.getFecha();
        LocalTime nuevaHora = vuelo.getHora().plusMinutes(rangoMinutosMaximoCambioVuelo);  

        if (puedeProgramarseEnNuevaHora(vuelo, nuevaFecha, nuevaHora, terminal, controlador)) {
            vuelo.setFecha(nuevaFecha);
            vuelo.setHora(nuevaHora);
            
            System.out.println("Vuelo movido a " + nuevaFecha + " " + nuevaHora);
            return true; 
        }

        return false;
	}
    
    /**
     * Método para comprobar si un vuelo puede programarse en una nueva hora en el rango definido.
     * 
     * @param vuelo			Vuelo que se quiere comprobar.
     * @param nuevaFecha	Nueva fecha.
     * @param nuevaHora		Nueva hora.
     * @param terminal		Terminal donde se quiere asignar el vuelo.
     * @param controlador	Controlador al que se le quiere asignar el vuelo.
     * @return True si se puede, false si no.
     * 
     */
    private boolean puedeProgramarseEnNuevaHora(VueloProgramado vuelo, LocalDate nuevaFecha, LocalTime nuevaHora, Terminal terminal, ControladorAereo controlador) {
        for (Vuelo vueloExistente : aeropuerto.getVuelosAceptados()) {
            if (vuelosSeSolapan(vuelo, vueloExistente, terminal, controlador)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Método para buscar alternativas a un vuelo en un rango de reprogramación.
     * 
     * @param vuelo			Vuelo que se quiere comprobar.
     * @param nuevaFecha	Nueva fecha.
     * @param nuevaHora		Nueva hora.
     * @param terminal		Terminal donde se quiere asignar el vuelo.
     * @param controlador	Controlador al que se le quiere asignar el vuelo.
     * @return True si se puede, false si no.
     * @throws VueloSolapadoException 
     * 
     */    
    private void buscarAlternativas(VueloProgramado vuelo, Vuelo vueloExistente, Terminal terminal, ControladorAereo controlador) throws VueloSolapadoException {
    	LocalDate fechaLimite = LocalDate.now().plusDays(horasMaximasReprogramacion / 24); 
        
        List<VueloProgramado> alternativas = new ArrayList<>();
        
        for (int i = 0; i < horasMaximasReprogramacion; i++) {
            LocalDate nuevaFecha = vuelo.getFecha().plusDays(i); 
     
            if (nuevaFecha.isAfter(fechaLimite)) {
                break;
            }

            for (int j = 0; j < 24 * 60 / rangoMinutosMaximoCambioVuelo; j++) {
                LocalTime nuevaHora = LocalTime.of((j * rangoMinutosMaximoCambioVuelo) / 60, (j * rangoMinutosMaximoCambioVuelo) % 60);
                vuelo.setFecha(nuevaFecha);
                vuelo.setHora(nuevaHora);
  
                if (!vuelosSeSolapan(vuelo, vueloExistente, terminal, controlador)) {
                    VueloProgramado alternativa = new VueloProgramado(vuelo.getCodigo(),nuevaFecha,vuelo.getFechaInicio(),vuelo.getFechaFinal(), nuevaHora,  
                    		vuelo.getDiasRecurrente(),vuelo.getTipoCarga(),vuelo.getTipoModoEmbarque(),vuelo.getPeriodicidad(), vuelo.getTipoVuelo(), vuelo.getAvion(),  
                    		vuelo.getAeropuertoDestino(),vuelo.getAeropuertoSalida(),vuelo.getEscala());
                    alternativas.add(alternativa);
                }
            }
        }
        if (!alternativas.isEmpty()) {
        	/*ENVIAR NOTIFICACION*/
            
        } else {
            throw new VueloSolapadoException("No se pudo programar el vuelo. No hay disponibilidad dentro de las siguientes " + horasMaximasReprogramacion + " horas.");
        }
	}
		

    /**
     * Método para comprobar si dos vuelos se solapan.
     * 
     * @param vuelo				Vuelo que se quiere comprobar.
     * @param vueloExistente	Vuelo de aeropuerto.
     * @param terminal			Terminal donde se quiere asignar el vuelo.
     * @param controlador		Controlador al que se le quiere asignar el vuelo.
     * 
     */    
	private boolean vuelosSeSolapan(VueloProgramado vuelo, Vuelo vueloExistente, Terminal terminal,
			ControladorAereo controlador) {
		
		if (vuelo.getFecha().equals(vueloExistente.getFecha()) && vuelo.getHora().equals(vueloExistente.getHora()) 
		            && terminal.equals(vueloExistente.getTerminal()) && controlador.equals(vueloExistente.getControlador())) {
	        return true; 
	    }
	    return false; 
	}

	/**
     * Método para ver las estadísticas del gestor.
     * 
     * @return Las estadísticas del gestor.
     * 
     */
    public EstadisticasGestor getEstadisticasAeropuerto(){
    	return this.estadisticas;
    }
    
    /**
     * Método para cargar las facturas que ha emitido el gestor.
     * 
     * @return Mapa de código de factura y estado.
     * 
     */
    public Map<String, EstadoFactura> cargarFacturas(){
    	Map<String, EstadoFactura> facturasEstado= new HashMap<String, EstadoFactura>();
    	
    	for(Aerolinea aerolinea: this.aeropuerto.getAerolineas()) {
    		for(Factura factura: aerolinea.getFacturas()) {
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
    	Map<String, EstadoFactura> facturasEstado= cargarFacturas();
    	
	    for (Map.Entry<String, EstadoFactura> entry : facturasEstado.entrySet()) {
	        System.out.println("Factura ID: " + entry.getKey() + " | Estado: " + entry.getValue());
	    }
    }

    public void configurarNotificaciones(List<TipoNotificacion> tipos){
        this.tiposPermitidosNotificaciones = new ArrayList<>(tipos);

    }

    public void recibirNotificaciones(Notificacion notificacion) {
        notificaciones.add(notificacion);
        System.out.println("Nueva notificación: "+ notificacion.getMensaje());

    }

    /**
     * Método que inicia sesión del gestor.
     * 
     * @param contrasena	Contraseña.
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
     * @param nombre		Nombre.
     * @param contrasena	Contraseña nueva.
     * @param usuario		UsuarioConDni que ha olvidado la contraseña.
     * 
     */
    public void modificarUsuario(String nombre, String contrasena, UsuarioConDni usuario) {

        if (usuario != null){
            usuario.setNombre(nombre);
            usuario.setContrasena(contrasena);
            System.out.println("Usuario modificado correctamente");
        }

        else {
            System.out.println("Error. El usuario no existe");
        }
    }
    
    /**
     * Método que establece el número mínimo de días de anticipación para las reservas.
     * 
     * @param nuevosDiasAnticipacionMinima 	El nuevo número de días de anticipación mínima que se va a configurar.
     */
    public void setDiasAnticipacionMinima(int nuevosDiasAnticipacionMinima) {
        diasAnticipacionMinima = nuevosDiasAnticipacionMinima;
    }

    /**
     * Método que establece el coste base de los vuelos de salida desde el aeropuerto.
     * 
     * @param nuevocosteBaseVueloSalida 	El nuevo coste base para los vuelos de salida.
     */
    public void setCosteBaseVueloSalida(double nuevocosteBaseVueloSalida) {
        costeBaseVueloSalida = nuevocosteBaseVueloSalida;
    }

    /**
     * Método que establece el coste base de los vuelos de llegada al aeropuerto.
     * 
     * @param nuevocosteBaseVueloLlegada 	El nuevo coste base para los vuelos de llegada.
     */
    public void setCosteBaseVueloLlegada(double nuevocosteBaseVueloLlegada) {
        costeBaseVueloLlegada = nuevocosteBaseVueloLlegada;
    }

    /**
     * Método que establece el recargo adicional para los vuelos de aviones de mercancías.
     * 
     * @param nuevorecargoAvionMercancias 	El nuevo recargo que se aplicará a los vuelos de aviones de mercancías.
     */
    public void setRecargoAvionMercancias(double nuevorecargoAvionMercancias) {
        recargoAvionMercancias = nuevorecargoAvionMercancias;
    }

}

