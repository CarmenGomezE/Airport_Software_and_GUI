package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.AirportManager;
import src.airportmanager.avion.Aerolinea;
import src.airportmanager.exceptions.SolicitudInvalidaExcepcion;
import src.airportmanager.usuario.Operador;
import src.airportmanager.vuelo.*;

public class SolicitudVueloCompartidoTest {

    private Vuelo vuelo;
    private Operador operadorPrincipal;
    private Operador operadorSolicitante;
    private SolicitudVueloCompartido solicitud;
    private AirportManager sistema;
    private Aerolinea aerolinea1, aerolinea2;

    @BeforeEach
    void setUp() {
    	sistema = new AirportManager();
    	sistema.iniciarAplicacion();
    	
    	aerolinea1 = new Aerolinea("Iberia", "IBE");
    	aerolinea2 = new Aerolinea("AirFrance", "AFR");
    	
        operadorPrincipal = new Operador("Operador Principal", "12345678A", aerolinea1, sistema.getAeropuerto(), sistema.getGestor(), sistema);
        operadorSolicitante = new Operador("Operador Solicitante", "87654321B", aerolinea2, sistema.getAeropuerto(), sistema.getGestor(), sistema);
        
        vuelo = new Vuelo("VY123", LocalDate.now(), null, null, 
            LocalTime.now(), null, TipoCarga.PASAJEROS, 
            TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            null, null, null, null, operadorPrincipal);
        
        solicitud = new SolicitudVueloCompartido(vuelo, operadorSolicitante, operadorPrincipal, 30.0);
    }

    @Test
    void testConstructor() {
        assertEquals(vuelo, solicitud.getVueloOriginal());
        assertEquals(operadorSolicitante, solicitud.getOperadorSolicitante());
        assertEquals(operadorPrincipal, solicitud.getOperadorReceptor());
        assertEquals(30.0, solicitud.getPorcentaje(), 0.001);
        assertEquals(EstadoSolicitud.PENDIENTE, solicitud.getEstadoSolicitud());
    }

    @Test
    void testAceptarSolicitudValida() throws SolicitudInvalidaExcepcion {
        solicitud.aceptar();
        assertEquals(EstadoSolicitud.ACEPTADA, solicitud.getEstadoSolicitud());
        assertTrue(vuelo.getOperadores().contains(operadorSolicitante));
    }

    @Test
    void testAceptarSolicitudInvalidaPorcentajeExcedido() {
        SolicitudVueloCompartido solicitudInvalida = new SolicitudVueloCompartido(
            vuelo, operadorSolicitante, operadorPrincipal, 80.0);
        
        assertThrows(SolicitudInvalidaExcepcion.class, () -> solicitudInvalida.aceptar());
        assertEquals(EstadoSolicitud.RECHAZADA, solicitudInvalida.getEstadoSolicitud());
    }

    @Test
    void testAceptarSolicitudInvalidaPorcentajeMinimo() {
        SolicitudVueloCompartido solicitudInvalida = new SolicitudVueloCompartido(
            vuelo, operadorSolicitante, operadorPrincipal, 60.0);
        
        assertThrows(SolicitudInvalidaExcepcion.class, () -> solicitudInvalida.aceptar());
        assertEquals(EstadoSolicitud.RECHAZADA, solicitudInvalida.getEstadoSolicitud());
    }

    @Test
    void testRechazar() {
        solicitud.rechazar();
        assertEquals(EstadoSolicitud.RECHAZADA, solicitud.getEstadoSolicitud());
        assertFalse(vuelo.getOperadores().contains(operadorSolicitante));
    }

    @Test
    void testEsSolicitudValida() throws SolicitudInvalidaExcepcion {
        assertTrue(solicitud.esSolicitudValida());
        
        SolicitudVueloCompartido solicitudInvalida = new SolicitudVueloCompartido(
            vuelo, operadorSolicitante, operadorPrincipal, 80.0);
        
        assertThrows(SolicitudInvalidaExcepcion.class, () -> solicitudInvalida.esSolicitudValida());
    }

    @Test
    void testGetVueloOriginal() {
        assertEquals(vuelo, solicitud.getVueloOriginal());
    }

    @Test
    void testGetPorcentaje() {
        assertEquals(30.0, solicitud.getPorcentaje(), 0.001);
    }

    @Test
    void testGetOperadorSolicitante() {
        assertEquals(operadorSolicitante, solicitud.getOperadorSolicitante());
    }

    @Test
    void testGetOperadorReceptor() {
        assertEquals(operadorPrincipal, solicitud.getOperadorReceptor());
    }

    @Test
    void testGetEstadoSolicitud() {
        assertEquals(EstadoSolicitud.PENDIENTE, solicitud.getEstadoSolicitud());
    }
}