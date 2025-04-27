package src.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.time.*;
import java.util.*;
import src.airportmanager.*;
import src.airportmanager.aeropuerto.*;
import src.airportmanager.avion.*;
import src.airportmanager.exceptions.*;
import src.airportmanager.factura.EstadoFactura;
import src.airportmanager.factura.Factura;
import src.airportmanager.notificacion.*;
import src.airportmanager.terminal.PuertaEmbarque;
import src.airportmanager.terminal.TerminalPasajeros;
import src.airportmanager.usuario.*;
import src.airportmanager.vuelo.*;
/**
 * GestorTest
 */
public class GestorTest {

    private Gestor gestor;
    private AeropuertoPropio aeropuerto;
    private AirportManager sistema;
    private Aerolinea aerolinea;
    private Operador operador;
    private ControladorAereo controlador;
    private TerminalPasajeros terminal;
    private VueloProgramado vueloProgramado;
    private AvionPasajeros avion;
    private PuertaEmbarque puerta;
    private List<PuertaEmbarque> puertas;
    private Vuelo vuelo;

    /**
	 * Constructor
	 */
	public GestorTest() {
		
	}
	
    /**
     * CreaObjetos
     * @throws TemporadasSolapadasException
     * @throws FormatoDniIncorrectoException 
     */
    @BeforeEach
    void setUp() throws TemporadasSolapadasException, FormatoDniIncorrectoException {
        sistema = new AirportManager();
        aeropuerto = new AeropuertoPropio("Barajas", "MAD", "Madrid", "España", 
            10.5, ZoneOffset.UTC, 2, DireccionRelativaCiudad.ESTE);
        gestor = new Gestor("Admin", "password123", aeropuerto, sistema);
        
        aerolinea = new Aerolinea("Iberia", "IB");
        operador = new Operador("Operador1", "12345678A", aerolinea, aeropuerto, gestor, sistema);
        aerolinea.setOperador(operador);
        
        puerta = new PuertaEmbarque();
        puertas = new ArrayList<>();
        puertas.add(puerta);
        
        terminal = new TerminalPasajeros("T1", puertas, 200);
        controlador = new ControladorAereo("Controlador1", "87654321B", "pass123", aeropuerto, gestor, terminal, sistema);
        
        avion = new AvionPasajeros("Boeing", "747", 70.6, 19.4, 70.6, 397000, 2020, new Date(), "ABC123", 416);
        aerolinea.agregarAvion(avion);
        vueloProgramado = new VueloProgramado("IB123", LocalDate.now().plusDays(31), null, null, 
            LocalTime.now(), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER, 
            Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avion, null, aeropuerto, null);
    }

    /**
     * EmitirFactura
     */
    @Test
    void testEmitirFactura() {
    	vuelo = new Vuelo("VY123", LocalDate.now(), null, null, 
                LocalTime.now(), null, TipoCarga.PASAJEROS, 
                TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
                avion, null, null, null, operador);
    	aeropuerto.addVueloAceptado(vuelo);
        gestor.emitirFactura(aerolinea);

        assertFalse(aerolinea.getOperador().getNotificaciones().isEmpty());
    }

    /**
     * VerEstadoFactyra
     */
    @Test
    void testVerEstadoFactura() {
        Factura factura = new Factura(aerolinea, LocalDate.now(), 1000.0, 0, null, null, EstadoFactura.PENDIENTE);
        EstadoFactura estado = gestor.verEstadoFactura(factura);
        assertEquals(EstadoFactura.PENDIENTE, estado);
    }

    /**
     * BuscarUsuarioPorDNI
     */
    @Test
    void testBuscarUsuarioPorDni() {
        aeropuerto.addUsuario(operador);
        UsuarioConDni encontrado = gestor.buscarUsuarioPorDni("12345678A");
        assertEquals(operador, encontrado);
        
        assertNull(gestor.buscarUsuarioPorDni("DNI_INEXISTENTE"));
    }

    /**
     * NotificarPorblemaContraseña
     */
    @Test
    void testNotificarProblemaContrasena() {
        aeropuerto.addUsuario(operador);
        gestor.notificarProblemaContrasena("12345678A");

        assertEquals(1, operador.getNotificaciones().size());
    }

    /**
     * DarDeAltaAerolinea
     */
    
    @Test
    void testDarDeAltaAerolinea() {
        try {
			gestor.darDeAltaAerolinea("Vueling", "VY");
		} catch (AerolineaYaExisteException e) {
			fail("No debería lanzar excepción.");
		}
        assertEquals(1, aeropuerto.getAerolineas().size());
    }

   /**
    * DarDeAltaControlador
    */
    @Test
    void testDarDeAltaControlador() {
        try {
			gestor.darDeAltaControlador("Controlador2", "11111111A", terminal);
		} catch (UsuarioYaExisteException | FormatoDniIncorrectoException e) {
			fail("No debería lanzar excepción.");
		}
        assertEquals(1, terminal.getControladores().size());
        assertTrue(sistema.getCredenciales().containsKey("11111111A"));
    }

    /**
     * DarDeAltaOperador
     */
    @Test
    void testDarDeAltaOperador() {
        try {
			gestor.darDeAltaOperador("Operador2", "22222222B", aerolinea);
		} catch (UsuarioYaExisteException | FormatoDniIncorrectoException e) {
			fail("No debería lanzar excepción.");
		}
        assertEquals(aerolinea.getOperador().getDni(), "22222222B");
        assertTrue(sistema.getCredenciales().containsKey("22222222B"));
    }

    /**
     * DarDeAltaAeropuertodestino
     * @throws IOException
     * @throws TemporadasSolapadasException
     */
    @Test
    void testDarDeAltaAeropuertoDestino() throws IOException, TemporadasSolapadasException {
        File tempFile = File.createTempFile("aeropuertos", ".txt");
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("Heathrow;LHR;Londres;Reino Unido;22.5;NORTE;0;2;01/04;08:00-22:00;30/09;01/10;07:00-23:00;31/03");
        }
        
        try {
			gestor.darDeAltaAeropuertoDestino(tempFile.getAbsolutePath());
		} catch (IOException | TemporadasSolapadasException | AeropuertoYaExisteException e) {
			fail("No debería lanzar excepción.");
		}
        assertEquals(1, aeropuerto.getAeropuertosDestino().size());
        
        tempFile.delete();
    }

    /**
     * ConfigurarAeropuertoPropio
     */
    @Test
    void testConfigurarAeropuertoPropio() {
        gestor.configurarAeropuertoPropioNombre("Nuevo Barajas");
        assertEquals("Nuevo Barajas", aeropuerto.getNombre());
        
        gestor.configurarAeropuertoPropioCodigo("NBA");
        assertEquals("NBA", aeropuerto.getCodigo());
        
        assertThrows(IllegalArgumentException.class, () -> gestor.configurarAeropuertoPropioNombre(null));
    }

    /**
     * DarDeAltaHangar
     */
    @Test
    void testDarDeAltaHangar() {
        gestor.darDeAltaHangar("Pasajeros", 5, 50.0, 20.0, 60.0, 100.0, false);
        assertEquals(1, aeropuerto.getHangares().size());
        
        gestor.darDeAltaHangar("Mercancías", 3, 60.0, 25.0, 70.0, 150.0, true);
        assertEquals(2, aeropuerto.getHangares().size());
        
        assertThrows(IllegalArgumentException.class, () -> 
            gestor.darDeAltaHangar("TipoInvalido", 1, 1, 1, 1, 1, false));
    }

    /**
     * DarDeAltaTerminal
     */
    @Test
    void testDarDeAltaTerminal() {
        List<PuertaEmbarque> puertas = new ArrayList<>();
        puertas.add(new PuertaEmbarque());
        
        try {
			gestor.darDeAltaTerminal("T1", "Pasajeros", 200, puertas);
		} catch (TerminalYaExisteException e) {
			fail("No debería lanzar excepción.");
		}
        assertEquals(1, aeropuerto.getTerminales().size());
        
        assertThrows(IllegalArgumentException.class, () -> 
            gestor.darDeAltaTerminal("T2", "TipoInvalido", 100, puertas));
    }

    /**
     * RecibirPrporsicionVuelo
     */
    @Test
    void testRecibirProposicionVuelo() {
        List<VueloProgramado> propuestas = new ArrayList<>();
        propuestas.add(vueloProgramado);
        
        assertDoesNotThrow(() -> gestor.recibirProposicionVuelo(propuestas));
        assertEquals(1, gestor.getVuelosPropuestos().size());
        
       
        VueloProgramado vueloCortoPlazo = new VueloProgramado("IB456", LocalDate.now().plusDays(20), null, null, 
            LocalTime.now(), null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER, 
            Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avion, null, aeropuerto, null);
        propuestas.add(vueloCortoPlazo);
        
        assertThrows(AntelacionInvalidaException.class, () -> gestor.recibirProposicionVuelo(propuestas));
    }

    /**
     * GestionarVuelos
     * @throws AntelacionInvalidaException
     */
    @Test
    void testGestionarVuelos() throws AntelacionInvalidaException {
        List<VueloProgramado> propuestas = new ArrayList<>();
        propuestas.add(vueloProgramado);
        
        aeropuerto.addTerminal(terminal);
        terminal.agregarControlador(controlador);
        
        List<VueloProgramado> vuelosAGestionar = new ArrayList<>(gestor.getVuelosPropuestos());
	    for (VueloProgramado v : vuelosAGestionar) {
	        gestor.gestionarVuelos(v);
	    }
        
        assertEquals(0, aeropuerto.getVuelosAceptados().size());
        assertTrue(gestor.getVuelosPropuestos().isEmpty());
    }

    /**
     * Modicarususarios
     * @throws UsuarioNoEncontradoException
     * @throws ContrasenaInvalidaException
     */
    @Test
    void testModificarUsuario() throws UsuarioNoEncontradoException, ContrasenaInvalidaException {
        aeropuerto.addUsuario(operador);
        gestor.modificarUsuario("NuevoNombre", operador);
        assertEquals("NuevoNombre", operador.getNombre());
        assertNotNull(operador.getContrasena());
    }

    /**
     * ConfigurarNotificaciones
     */
    @Test
    void testConfigurarNotificaciones() {
        List<TipoNotificacion> nuevosTipos = Arrays.asList(TipoNotificacion.OLVIDO_CONTRASENA);
        gestor.configurarNotificaciones(nuevosTipos);
        assertEquals(1, gestor.getTiposPermitidosNotificaciones().size());
    }

    /**
     * recibirNotificacion
     */
    @Test
    void testRecibirNotificacion() {
        Notificacion notificacion = new Notificacion("Mensaje", TipoNotificacion.OLVIDO_CONTRASENA);
        gestor.recibirNotificacion(notificacion);
        assertEquals(1, gestor.getNotificaciones().size());
        
        gestor.configurarNotificaciones(new ArrayList<>());
        gestor.recibirNotificacion(notificacion);
        assertEquals(1, gestor.getNotificaciones().size()); 
    }

    /**
     * SetCostos
     */
    @Test
    void testSetCostos() {
        gestor.setCosteBaseVueloSalida(200.0);
        gestor.setCosteBaseVueloLlegada(180.0);
        gestor.setRecargoAvionMercancias(50.0);
        
        assertEquals(200.0, gestor.getSistemaFacturacion().getCosteBaseVueloSalida());
        assertEquals(180.0, gestor.getSistemaFacturacion().getCosteBaseVueloLlegada());
        assertEquals(50.0, gestor.getSistemaFacturacion().getRecargoAvionMercancias());
    }
}
