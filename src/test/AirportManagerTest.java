package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.*;
import java.util.*;
import src.airportmanager.exceptions.*;
import src.airportmanager.terminal.PuertaEmbarque;
import src.airportmanager.terminal.TerminalPasajeros;
import src.airportmanager.usuario.Operador;
import src.airportmanager.vuelo.*;
import src.airportmanager.AirportManager;
import src.airportmanager.avion.*;

/**
 * AirporManagerTest
 */
public class AirportManagerTest {

    private AirportManager airportManager;
    private Vuelo vueloSalida, vueloLlegada;
    private AvionPasajeros avionPasajeros;
    private TerminalPasajeros terminal;
    private PuertaEmbarque puerta;
    private List<PuertaEmbarque> puertas;
    private Operador operador;
    private Aerolinea aerolinea;

    /**
	 * Constructor
	 */
	public AirportManagerTest() {
		
	}
	
    /**
     * CreaObjetis
     * @throws TemporadasSolapadasException
     */
    @BeforeEach
    void setUp() throws TemporadasSolapadasException {
    	airportManager = new AirportManager();
        avionPasajeros = new AvionPasajeros("Boeing", "747", 70.6, 19.4, 70.6, 
            397000, 2020, new Date(), "ABC123", 416);
        
        operador = new Operador("nombre", "dni", aerolinea, airportManager.getAeropuerto(), airportManager.getGestor(), airportManager);
        
        vueloSalida = new Vuelo("IB123", LocalDate.now(), null, null, 
            LocalTime.now().plusHours(1), null, TipoCarga.PASAJEROS, 
            TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            avionPasajeros, null, airportManager.getAeropuerto(), null, operador);
            
        vueloLlegada = new Vuelo("IB456", LocalDate.now(), null, null, 
            LocalTime.now().plusHours(2), null, TipoCarga.PASAJEROS, 
            TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, 
            avionPasajeros, airportManager.getAeropuerto(), null, null, operador);
        
        puerta = new PuertaEmbarque();
        puertas = new ArrayList<>();
        puertas.add(puerta);
        
        terminal = new TerminalPasajeros("CODE", puertas, 100);
        vueloSalida.setTerminal(terminal);
    	vueloLlegada.setTerminal(terminal);
    }

    /**
     * IniciarAplicacion
     */
    @Test
    void testIniciarAplicacion() {
        airportManager.iniciarAplicacion();
       
        assertNotNull(airportManager.getAeropuerto());
        assertNotNull(airportManager.getGestor());
        assertEquals(1, airportManager.getUsuarios().size());
    }   

    /**
     * GuardarYCargarEstado
     */
    @Test
    void testGuardarYCargarEstado() {
        String tempDir = System.getProperty("java.io.tmpdir");
        String archivo = tempDir + File.separator + "airport_manager_test" + File.separator + "test_state.dat";
        
        new File(tempDir + File.separator + "airport_manager_test").mkdirs();
        
        try {
            airportManager.guardarEstado(archivo);
            assertTrue(new File(archivo).exists(), "El archivo debería existir después de guardar");
            
            AirportManager cargado = AirportManager.cargarEstado(archivo);
            assertNotNull(cargado, "El objeto cargado no debería ser null");
            
        } finally {
            if (new File(archivo).exists()) {
                new File(archivo).delete();
            }
            new File(tempDir + File.separator + "airport_manager_test").delete();
        }
    }

    /**
     * GnerarContrasena
     */
    @Test
    void testGenerarContrasena() {
        String contrasena = airportManager.generarContrasena();
        assertNotNull(contrasena);
        assertEquals(12, contrasena.length());
    }

    /**
     * ActualizarEstadoVueloAutomatico
     */
    @Test
    void testActualizarEstadoVueloAutomatico() {
    	airportManager.iniciarAplicacion();
        
        aerolinea = new Aerolinea("IBERIA", "IBE");
        aerolinea.agregarAvion(avionPasajeros);
        avionPasajeros.setAerolinea(aerolinea);
        vueloSalida.cambiarEstado(EstadoVuelo.ESPERANDO_DESPEGUE);
        airportManager.actualizarEstadoVueloAutomatico(vueloSalida);
        assertTrue(vueloSalida.getEstado() == EstadoVuelo.RETRASADO || 
                 vueloSalida.getEstado() == EstadoVuelo.VUELO);
        
        vueloLlegada.cambiarEstado(EstadoVuelo.ACEPTADO);
        airportManager.actualizarEstadoVueloAutomatico(vueloLlegada);
        assertTrue(vueloLlegada.getEstado() == EstadoVuelo.A_TIEMPO || 
                 vueloLlegada.getEstado() == EstadoVuelo.RETRASADO);
    }

    /**
     * AsignarContrasena
     * @throws UsuarioNoEncontradoException
     * @throws ContrasenaInvalidaException
     */
    @Test
    void testAsignarContrasena() throws UsuarioNoEncontradoException, ContrasenaInvalidaException {
        airportManager.asignarContrasena("87654321B", "nuevaContrasena");
        assertTrue(airportManager.getCredenciales().containsKey("87654321B"));
    }

    /**
     * ValidarDNI
     */
    @Test
    void testValidarDni() {
    	airportManager.iniciarAplicacion();
        assertFalse(airportManager.validarDni("12345678A"));
    }

    /**
     * ValidarContrasena
     * @throws UsuarioNoEncontradoException
     */
    @Test
    void testValidarContrasena() throws UsuarioNoEncontradoException {
    	airportManager.iniciarAplicacion();
        assertTrue(airportManager.validarContrasena("Gestor", airportManager.getGestor().getContrasena()));
        assertFalse(airportManager.validarContrasena("Gestor", "contrasenaIncorrecta"));
    }

    /**
     * ValidarUsuario
     */
    @Test
    void testValidarUsuario() {
    	airportManager.iniciarAplicacion();
    	try {
    	    airportManager.validarUsuario("Gestor", airportManager.getGestor().getContrasena());
    	} catch (Exception e) {
    	    fail("No debería haber lanzado excepción con credenciales correctas");
    	}
    	try {
    	    airportManager.validarUsuario("DNI_INEXISTENTE", "password123");
    	    fail("Debería haber lanzado UsuarioNoEncontradoException");
    	} catch (UsuarioNoEncontradoException e) {
    	} catch (Exception e) {
    	    fail("Debería haber lanzado UsuarioNoEncontradoException pero lanzó " + e.getClass());
    	}
    	try {
    	    airportManager.validarUsuario("Gestor", "contrasenaIncorrecta");
    	    fail("Debería haber lanzado ContrasenaInvalidaException");
    	} catch (ContrasenaInvalidaException e) {
    	} catch (Exception e) {
    	    fail("Debería haber lanzado ContrasenaInvalidaException pero lanzó " + e.getClass());
    	}
    }

    /**
     * AddCREDENCIAL
     */
    @Test
    void testAddCredencial() {
        airportManager.addCredencial("11111111A", "test123");
        Map<String, String> credenciales = airportManager.getCredenciales();
        assertTrue(credenciales.containsKey("11111111A"));
        assertEquals("test123", credenciales.get("11111111A"));
    }

    /**
     * OrdenarColaVuelos
     */
    @Test
    void testOrdenarColaVuelos() {
    	airportManager.iniciarAplicacion();
        Vuelo vuelo1 = new Vuelo("IB123", LocalDate.now(), null, null, 
            LocalTime.now(), null, TipoCarga.PASAJEROS, 
            TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            avionPasajeros, null, airportManager.getAeropuerto(), null, null);
        vuelo1.setRetraso(30);
        
        Vuelo vuelo2 = new Vuelo("IB456", LocalDate.now(), null, null, 
            LocalTime.now(), null, TipoCarga.PASAJEROS, 
            TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            avionPasajeros, null, airportManager.getAeropuerto(), null, null);
        vuelo2.setRetraso(15);
        
        airportManager.getAeropuerto().getVuelosAceptados().add(vuelo1);
        airportManager.getAeropuerto().getVuelosAceptados().add(vuelo2);
        
        vuelo1.cambiarEstado(EstadoVuelo.ESPERANDO_DESPEGUE);
        vuelo2.cambiarEstado(EstadoVuelo.ESPERANDO_DESPEGUE);
        
        Vuelo vueloOrdenado = airportManager.ordenarColaVuelos();
        assertEquals(vuelo1, vueloOrdenado);
    }

    /**
     * CompararVuelos
     */
    @Test
    void testCompararVuelos() {
        Vuelo vuelo1 = new Vuelo("IB123", LocalDate.now(), null, null, 
            LocalTime.now(), null, TipoCarga.PASAJEROS, 
            TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            avionPasajeros, null, airportManager.getAeropuerto(), null, null);
        vuelo1.setRetraso(30);
        
        Vuelo vuelo2 = new Vuelo("IB456", LocalDate.now(), null, null, 
            LocalTime.now(), null, TipoCarga.PASAJEROS, 
            TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            avionPasajeros, null, airportManager.getAeropuerto(), null, null);
        vuelo2.setRetraso(15);
        
        assertTrue(airportManager.compararVuelos(vuelo1, vuelo2) < 0);
    }

    /**
     * GetAeropuerto
     */
    @Test
    void testGetAeropuerto() {
        assertNull(airportManager.getAeropuerto());
    }

    /**
     * GetGESTOR
     */
    @Test
    void testGetGestor() {
        assertNull(airportManager.getGestor());
    }
    
    /**
     * GetCredenciales
     */
    @Test
    void testGetCredenciales() {
    	airportManager.iniciarAplicacion();
        Map<String, String> credenciales = airportManager.getCredenciales();
        assertNotNull(credenciales);
        assertEquals(1, credenciales.size());
        assertEquals(airportManager.getGestor().getContrasena(), credenciales.get("Gestor"));
        assertNull(credenciales.get("56437432B"));
    } 
}
