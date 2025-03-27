package src.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.*;
import java.util.*;
import src.airportmanager.exceptions.*;
import src.airportmanager.vuelo.*;
import src.airportmanager.AirportManager;
import src.airportmanager.avion.*;

public class AirportManagerTest {

    private AirportManager airportManager;
    private Vuelo vueloSalida, vueloLlegada;
    private AvionPasajeros avionPasajeros;

    @BeforeEach
    void setUp() throws TemporadasSolapadasException {
        airportManager = new AirportManager();
        
        avionPasajeros = new AvionPasajeros("Boeing", "747", 70.6, 19.4, 70.6, 
            397000, 2020, new Date(), "ABC123", 416);
        
        vueloSalida = new Vuelo("IB123", LocalDate.now(), null, null, 
            LocalTime.now().plusHours(1), null, TipoCarga.PASAJEROS, 
            TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            avionPasajeros, null, airportManager.getAeropuerto(), null, null);
            
        vueloLlegada = new Vuelo("IB456", LocalDate.now(), null, null, 
            LocalTime.now().plusHours(2), null, TipoCarga.PASAJEROS, 
            TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, 
            avionPasajeros, airportManager.getAeropuerto(), null, null, null);
    }

    @Test
    void testIniciarAplicacion() {
        airportManager.iniciarAplicacion();
       
        assertNotNull(airportManager.getAeropuerto());
        assertNotNull(airportManager.getGestor());
        assertEquals(1, airportManager.getUsuarios().size());
    }   

    @Test
    void testGuardarYCargarEstado() {
        String archivo = "test_state.dat";
        airportManager.guardarEstado(archivo);
        
        AirportManager cargado = AirportManager.cargarEstado(archivo);
        assertNotNull(cargado);
        
        new File(archivo).delete();
    }

    @Test
    void testGenerarContrasena() {
        String contrasena = airportManager.generarContrasena();
        assertNotNull(contrasena);
        assertEquals(12, contrasena.length());
    }

    @Test
    void testActualizarEstadosVuelosAutomaticamente() {
    	airportManager.iniciarAplicacion();
        airportManager.getAeropuerto().getVuelosAceptados().add(vueloSalida);
        airportManager.getAeropuerto().getVuelosAceptados().add(vueloLlegada);
        
        vueloSalida.cambiarEstado(EstadoVuelo.ESPERANDO_DESPEGUE);
        vueloLlegada.cambiarEstado(EstadoVuelo.ACEPTADO);
        
        airportManager.actualizarEstadosVuelosAutomaticamente();
        
        assertNotEquals(EstadoVuelo.ESPERANDO_DESPEGUE, vueloSalida.getEstado());
        assertNotEquals(EstadoVuelo.ACEPTADO, vueloLlegada.getEstado());
    }

    @Test
    void testActualizarEstadoVueloAutomatico() {
        vueloSalida.cambiarEstado(EstadoVuelo.ESPERANDO_DESPEGUE);
        airportManager.actualizarEstadoVueloAutomatico(vueloSalida);
        assertTrue(vueloSalida.getEstado() == EstadoVuelo.RETRASADO || 
                 vueloSalida.getEstado() == EstadoVuelo.VUELO);
        
        vueloLlegada.cambiarEstado(EstadoVuelo.ACEPTADO);
        airportManager.actualizarEstadoVueloAutomatico(vueloLlegada);
        assertTrue(vueloLlegada.getEstado() == EstadoVuelo.A_TIEMPO || 
                 vueloLlegada.getEstado() == EstadoVuelo.RETRASADO);
    }

    @Test
    void testAsignarContrasena() throws UsuarioNoEncontradoException, ContrasenaInvalidaException {
        airportManager.asignarContrasena("87654321B", "nuevaContrasena");
        assertTrue(airportManager.getCredenciales().containsKey("87654321B"));
    }

    @Test
    void testValidarDni() {
    	airportManager.iniciarAplicacion();
        assertFalse(airportManager.validarDni("12345678A"));
    }

    @Test
    void testValidarContrasena() throws UsuarioNoEncontradoException {
    	airportManager.iniciarAplicacion();
        assertTrue(airportManager.validarContrasena("Gestor", airportManager.getGestor().getContrasena()));
        assertFalse(airportManager.validarContrasena("Gestor", "contrasenaIncorrecta"));
    }

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

    @Test
    void testAddCredencial() {
        airportManager.addCredencial("11111111A", "test123");
        Map<String, String> credenciales = airportManager.getCredenciales();
        assertTrue(credenciales.containsKey("11111111A"));
        assertEquals("test123", credenciales.get("11111111A"));
    }

    @Test
    void testOrdenarColaVuelos() {
    	airportManager.iniciarAplicacion();
        Vuelo vuelo1 = new Vuelo("IB123", LocalDate.now(), null, null, 
            LocalTime.now(), null, TipoCarga.PASAJEROS, 
            TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            avionPasajeros, null, airportManager.getAeropuerto(), null, null);
        vuelo1.addRetraso(30);
        
        Vuelo vuelo2 = new Vuelo("IB456", LocalDate.now(), null, null, 
            LocalTime.now(), null, TipoCarga.PASAJEROS, 
            TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            avionPasajeros, null, airportManager.getAeropuerto(), null, null);
        vuelo2.addRetraso(15);
        
        airportManager.getAeropuerto().getVuelosAceptados().add(vuelo1);
        airportManager.getAeropuerto().getVuelosAceptados().add(vuelo2);
        
        vuelo1.cambiarEstado(EstadoVuelo.ESPERANDO_DESPEGUE);
        vuelo2.cambiarEstado(EstadoVuelo.ESPERANDO_DESPEGUE);
        
        List<Vuelo> ordenados = airportManager.ordenarColaVuelos();
        assertEquals(vuelo1, ordenados.get(0));
        assertEquals(vuelo2, ordenados.get(1));
    }

    @Test
    void testCompararVuelos() {
        Vuelo vuelo1 = new Vuelo("IB123", LocalDate.now(), null, null, 
            LocalTime.now(), null, TipoCarga.PASAJEROS, 
            TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            avionPasajeros, null, airportManager.getAeropuerto(), null, null);
        vuelo1.addRetraso(30);
        
        Vuelo vuelo2 = new Vuelo("IB456", LocalDate.now(), null, null, 
            LocalTime.now(), null, TipoCarga.PASAJEROS, 
            TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            avionPasajeros, null, airportManager.getAeropuerto(), null, null);
        vuelo2.addRetraso(15);
        
        assertTrue(airportManager.compararVuelos(vuelo1, vuelo2) < 0);
    }

    @Test
    void testGetAeropuerto() {
        assertNull(airportManager.getAeropuerto());
    }

    @Test
    void testGetGestor() {
        assertNull(airportManager.getGestor());
    }
    
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