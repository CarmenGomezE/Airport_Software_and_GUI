package aeropuerto.claudia;
import aeropuerto.maria;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class Gestor extends Usuario{

    private List<TipoNotificacion> tiposPermitidosNotificaciones;
    private List<Usuario> usuarios;
    private List<Aeropuerto> aeropuertos;
    private List<ModoEmbarque> modoEmbarques;
    private List<Pista> pistas;
    private List<Hangar> hangares;
    private List<Terminal> terminales;
    private List<Factura> facturas;
    private List<Aerolinea> aerolineas;
    private EstadisticasGestor estadisticas;
    private static int diasAnticipacionMinima = 30;

    public Gestor(String nombre, String contrasena, EstadisticasGestor estadisticas){
        super(nombre, contrasena);
        this.tiposPermitidosNotificaciones = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.hangares = new ArrayList<>();
        this.aeropuertos = new ArrayList<>();
        this.modoEmbarques = new ArrayList<>();
        this.pistas = new ArrayList<>();
        this.terminales = new ArrayList<>();
        this.facturas = new ArrayList<>();
        this.aerolineas = new ArrayList<>();
        this.estadisticas = estadisticas;

    }

    public void generarFacturaYNotificar(Aerolinea aerolinea, SistemaFacturacion sistemaFacturacion) {
         Factura factura = sistemaFacturacion.generarFactura(aerolinea);
        
        if (factura != null) {
            try {
                InvoiceSystem.createInvoice(factura, "./facturas/Factura_" + factura.getInvoiceIdentifier() + ".pdf");
                System.out.println("Factura generada correctamente.");
            } catch (NonExistentFileException | UnsupportedImageTypeException e) {
                System.err.println("Error al generar el PDF: " + e.getMessage());
            }
        } else {
            System.out.println("No se generaron vuelos para la factura.");
        }
    }

    public EstadoFactura verEstadoFactura(Factura factura) {
        return factura.getEstado();
    }

    private UsuarioConDni buscarUsuarioPorDni(String dni) {

        UsuarioConDni usuarioDni;

        for (Usuario usuario: usuarios) {
            if (usuario instanceof UsuarioConDni) {
                usuarioDni = (UsuarioConDni) usuario;
                if (UsuarioConDni.getDni().equals(dni)) {
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
        }

        else {
            System.out.println("Usuario con DNI" + dni + "no encontrado.");
        }

        notificaciones.add(notificacion);

    }

    public void darDeAltaAerolinea(String nombre, String codigo) {

      /**Operador?? */

    }

    public void darDeAltaControlador(String nombre, String dni, String contrasena, Terminal terminal) {

        ControladorAereo controlador;
        controlador = new Controlador(nombre, dni, contrasena); /**Falta terminal */

    }

    public void darDeAltaAeropuertoDestino(String rutaFichero) {
        /**Lo del fichero ni idea */

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
            hangar = new HangarMercancias(numeroMaxAviones, dimPlazaAncho, dimPlazaAlto, dimPlazaLargo, costePorHora, mercaPeligrosa);
        }

        else {
            throw new IllegalArgumentException("Tipo de hangar no válido: " + tipo);
        }

        hangares.add(hangar);
        
    }

    public void darDeAltaTerminal(String codigo, String tipo, int aforo, Map<String, ZonaAparcamiento> puetasEmbarque) {

        Terminal terminal;
        List<PuertaEmbarque> listapuertas;
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
            terminal.añadirPuertaEmbarque(listapuertas.get(i));
        }

        terminales.add(terminal);

    }

    public void darDeAltaFinger (String codigo, double dimAlto, double costePorHora, List<PuertaEmbarque> puertasEmbarque){

        Finger finger;
        Set<PuertaEmbarque> puertasSet;

        if (puertasEmbarque.size() < 1 || puertasEmbarque.size() > 2) {
            throw new IllegalArgumentException("Un finger debe estar conectado a 1 o 2 puertas de embarque");
        }

        puertasSet = new HashSet<>(puertasEmbarque);
        finger = new Finger(codigo, costePorHora, dimAlto, puertasSet);
    

    }

    public void darDeAltaPista (String codigo, double longitud, TipoPista tipoPista){

        Pista pista;
        pista = new Pista(codigo, longitud, tipoPista);
        pistas.add(pista);
    }

    public void gestionarVuelo(VueloProgramado vuelo, Controlador controlador) {
        /**ASIGNAR TERMINAL Y CONTROLADOR AL VUELO */
        /**CONTROLADOR YA LO TENGO */
        /**BUSCAR SI ES POSIBLE TERMINAL */
        /**HACER UN METODO A LO MEJOR EN TERMINAL QUE DEVUELVA LA PRIMERA PUERTA DE EMBARQUE QUE ESTÉ DISPONIBLE O UN BOOLEAN O ALGO DE ESO */
        /**MIRO CAPACIDAD */
        /**ESTAS DOS ULTIMAS HAY QUE TENER EN CUENTA LA HORA DEL VUELO (VALE TAMBIEN 30 MINUTOS) */


    }

    public String verEstadisticasAeropuerto(){

    }

    public void emitirFactura(Aerolinea aerolinea, Date fecha) {

    }

    public Map<String, EstadoFactura> verFacturas(){

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
        return this.diasAnticipacionMinima;
    }

    public void setDiasAnticipacionMinima(int diasAnticipacionMinima) {
        this.diasAnticipacionMinima = diasAnticipacionMinima;
    }

    public List<Aeropuerto> getAeropuerto() {
        return this.aeropuertos;
    }



}

