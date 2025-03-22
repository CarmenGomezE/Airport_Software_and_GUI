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


public class Gestor extends Usuario{

    private List<TipoNotificacion> tiposPermitidosNotificaciones;
    private List<Usuario> usuarios;
    private List<Aeropuerto> aeropuertos;
    private List<ModoEmbarque> modoEmbarques;
    private List<Pista> pistas;
    private List<Hangar> hangares;
    private List<Terminal> terminales;
    private AeropuertoPropio aeropuerto;
    private List<Aerolinea> aerolineas;
    private EstadisticasGestor estadisticas;
    private SistemaFacturacion sistemaFacturacion;
    private List<VueloProgramado> vuelosPropuestos;
    private static int diasAnticipacionMinima = 30;
    private static int rangoMinutosMaximoCambioVuelo=30;
    private static int horasMaximasReprogramacion=48;
    private static double recargoAvionMercancias= 105.9;
    private static double costeBaseVueloSalida= 105.9;
    private static double costeBaseVueloLlegada= 105.9;

    public Gestor(String nombre, String contrasena, EstadisticasGestor estadisticas){
        super(nombre, contrasena);
        this.tiposPermitidosNotificaciones = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.hangares = new ArrayList<>();
        this.aeropuertos = new ArrayList<>();
        this.modoEmbarques = new ArrayList<>();
        this.pistas = new ArrayList<>();
        this.terminales = new ArrayList<>();
        this.aerolineas = new ArrayList<>();
        this.estadisticas = estadisticas;
        this.sistemaFacturacion= establecerSistemaFacturacion();
    }

    public SistemaFacturacion establecerSistemaFacturacion( ) {
    	return new SistemaFacturacion(recargoAvionMercancias,costeBaseVueloSalida, costeBaseVueloLlegada);
    	
    }
    
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

    public EstadoFactura verEstadoFactura(Factura factura) {
        return factura.getEstado();
    }

    private UsuarioConDni buscarUsuarioPorDni(String dni) {

        UsuarioConDni usuarioDni;

        for (Usuario usuario: usuarios) {
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

    public void darDeAltaAerolinea(String nombre, String codigo) {

      /**Operador?? */

    }

    public void darDeAltaControlador(String nombre, String dni, String contrasena, Terminal terminal) {

        ControladorAereo controlador;
        controlador = new Controlador(nombre, dni, contrasena); /**Falta terminal */

    }

    public void darDeAltaAeropuertoDestino(String nombreFichero) throws IOException {
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
                    this.aeropuerto.agregarAeropuertoDestino(aeropuertoDestino);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public void configurarAeropuertoPropio(String nombre, String codigo, String ciudadMasCercana, String pais, double distancia, int diferenciaHoraria, int numTemporadas, String horarioVerano, String horarioInvierno, DireccionRelativaCiudad direccion) {
        AeropuertoPropio aeropuertoPropio;
        aeropuertoPropio = new AeropuertoPropio(nombre, codigo, ciudadMasCercana, pais, distancia, diferenciaHoraria, numTemporadas, horarioVerano, horarioInvierno, direccion);
        aeropuertos.add(aeropuertoPropio);
    }

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

        hangares.add(hangar);
        
    }

    public void darDeAltaTerminal(String codigo, String tipo, int aforo, Map<String, ZonaAparcamiento> puertasEmbarque) {

        Terminal terminal;
        List<PuertaEmbarque> listaPuertas;
        String codigoPuerta;
        ZonaAparcamiento zona;
        PuertaEmbarque puerta;
        int i;

        for (Map.Entry<String, ZonaAparcamiento> entry : puertasEmbarque.entrySet()) {

            codigoPuerta = entry.getKey();
            zona = entry.getValue();
            puerta = new PuertaEmbarque(codigoPuerta, zona);
            listaPuertas.add(puerta);
        }

        if (tipo.equals("Pasajeros")) {
            terminal = new TerminalPasajeros(codigo, listaPuertas.get(0), aforo);
        }

        else if (tipo.equals("Mercancías")) {
            terminal = new TerminalMercancias(codigo, listaPuertas.get(0));
        }

        else{
            throw new IllegalArgumentException ("Tipo de terminal no válido" + tipo);
        }

        for (i = 1; i < listaPuertas.size(); i++) {
            terminal.añadirPuertaEmbarque(listaPuertas.get(i));
        }

        terminales.add(terminal);

    }

    public void darDeAltaFinger (String codigo, double dimAlto, double costePorHora, List<PuertaEmbarque> puertasEmbarque){
        Finger finger;
        HashSet<PuertaEmbarque> puertasSet;

        if (puertasEmbarque.size() < 1 || puertasEmbarque.size() > 2) {
            throw new IllegalArgumentException("Un finger debe estar conectado a 1 o 2 puertas de embarque");
        }

        puertasSet = new HashSet<PuertaEmbarque>(puertasEmbarque);
        finger = new Finger(codigo, costePorHora, dimAlto, puertasSet);
    

    }

    public void darDeAltaPista (String codigo, double longitud, TipoPista tipoPista){

        Pista pista;
        pista = new Pista(codigo, longitud, tipoPista);
        pistas.add(pista);
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

    public void gestionarVuelos() {
    	for(VueloProgramado vuelo: vuelosPropuestos) {
    		if(puedeProgramarse(vuelo)) {
    			aeropuerto.agregarVueloAceptado(vuelo);
    		}
    		else{
    			vuelo.getAvion().getAerolinea().getOperador().recibirNotificacionOpcionesReprogramacion();
    		}
    	}

    }
    
    public boolean puedeProgramarse(VueloProgramado vuelo) {
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
    
    private boolean moverVuelo(VueloProgramado vuelo, Terminal terminal, ControladorAereo controlador) {
    	LocalDate nuevaFecha = vuelo.getFecha();
        LocalTime nuevaHora = vuelo.getHora().plusMinutes(rangoMinutosMaximoCambioVuelo);  

        if (puedeProgramarseEnNuevaHora(vuelo, nuevaFecha, nuevaHora, terminal, controlador)) {
            // Si se puede mover, actualizamos el vuelo con la nueva fecha y hora
            vuelo.setFecha(nuevaFecha);
            vuelo.setHora(nuevaHora);
            
            System.out.println("Vuelo movido a " + nuevaFecha + " " + nuevaHora);
            return true; 
        }

        return false;
	}
    
    private boolean puedeProgramarseEnNuevaHora(VueloProgramado vuelo, LocalDate nuevaFecha, LocalTime nuevaHora, Terminal terminal, ControladorAereo controlador) {
        for (Vuelo vueloExistente : aeropuerto.getVuelosAceptados()) {
            if (vuelosSeSolapan(vuelo, vueloExistente, terminal, controlador)) {
                return false;
            }
        }
        return true;
    }

    private void buscarAlternativas(VueloProgramado vuelo, Vuelo vueloExistente, Terminal terminal, ControladorAereo controlador) {
		LocalDate fechaLimite = LocalDate.now().plusDays(horasMaximasReprogramacion%24);

	    for (int i = 0; i < horasMaximasReprogramacion; i++) {
	        LocalDate nuevaFecha = vuelo.getFecha().plusDays(i);
	        
	        for  (int j = 0; j < 24 * 60 / rangoMinutosMaximoCambioVuelo; j++) {
	            LocalTime nuevaHora = LocalTime.of((j * rangoMinutosMaximoCambioVuelo) / 60, (j * rangoMinutosMaximoCambioVuelo) % 60);
	            
	            vuelo.setFecha(nuevaFecha);
	            vuelo.setHora(nuevaHora);
	            
	            if (!vuelosSeSolapan(vuelo, vueloExistente, terminal, controlador)) {
	                System.out.println("Alternativa encontrada: " + nuevaFecha + " " + nuevaHora);
	                // Aquí se podrían enviar las opciones alternativas al gestor u operador
	                return;
	            }
	        }
	    }
	    
	    // Si no encontramos alternativas dentro de las 48 horas
	    throw new VueloSolapadoException("No se pudo programar el vuelo. No hay disponibilidad dentro de las siguientes 48 horas.");
	}
		

	private boolean vuelosSeSolapan(VueloProgramado vuelo, Vuelo vueloExistente, Terminal terminal,
			ControladorAereo controlador) {
		
		if (vuelo.getFecha().equals(vueloExistente.getFecha()) && vuelo.getHora().equals(vueloExistente.getHora()) 
		            && terminal.equals(vueloExistente.getTerminal()) && controlador.equals(vueloExistente.getControlador())) {
	        return true; 
	    }
	    return false; 
	}

    public EstadisticasGestor getEstadisticasAeropuerto(){
    	return this.estadisticas;
    }
    
    public Map<String, EstadoFactura> cargarFacturas(){
    	Map<String, EstadoFactura> facturasEstado= new HashMap<String, EstadoFactura>();
    	
    	for(Aerolinea aerolinea: this.aerolineas) {
    		for(Factura factura: aerolinea.getFacturas()) {
    			facturasEstado.put(factura.getInvoiceIdentifier(), factura.getEstado());
    		}
    	}
    	return facturasEstado;
    }
    
    public void verFacturas() {
    	Map<String, EstadoFactura> facturasEstado= cargarFacturas();
    	
	    for (Map.Entry<String, EstadoFactura> entry : facturasEstado.entrySet()) {
	        System.out.println("Factura ID: " + entry.getKey() + " | Estado: " + entry.getValue());
	    }
    }

    public void configurarNotificaciones(List<TipoNotificacion> tipos){

    }

    public void recibirNotificaciones(Notificacion notificacion) {

    }

    public void iniciarSesion(String contrasena) {

    }

    public void modificarUsuario(String nombre, String contrasena, UsuarioConDni usuario) {

    }

    public static int getDiasAnticipacionMinima(){
        return diasAnticipacionMinima;
    }

    public void setDiasAnticipacionMinima(int nuevosDiasAnticipacionMinima) {
        diasAnticipacionMinima = nuevosDiasAnticipacionMinima;
    }
    
    public void setCosteBaseVueloSalida(double nuevocosteBaseVueloSalida) {
        costeBaseVueloSalida = nuevocosteBaseVueloSalida;
    }
    
    public void setCosteBaseVueloLlegada(double nuevocosteBaseVueloLlegada) {
        costeBaseVueloLlegada = nuevocosteBaseVueloLlegada;
    }
    
    public void setRecargoAvionMercancias(double nuevorecargoAvionMercancias) {
        recargoAvionMercancias = nuevorecargoAvionMercancias;
    }

    public List<Aeropuerto> getAeropuerto() {
        return this.aeropuertos;
    }



}

