package src.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

import src.airportmanager.aeropuerto.*;
import src.airportmanager.estadisticas.*;
import src.airportmanager.exceptions.TemporadasSolapadasException;
import src.airportmanager.hangar.*;
import src.airportmanager.terminal.*;
import src.airportmanager.usuario.*;

public class EstadisticasGestorTest {

    private EstadisticasGestor estadisticas;
    private Gestor gestor;
    private AeropuertoPropio aeropuerto;
    private Finger finger;
    private PuertaEmbarque puerta;
    private ZonaAparcamiento zona;
    private HangarPasajeros hangar;
    private TerminalPasajeros terminal;
    private Temporada temporada;
    private List<PuertaEmbarque> puertas;

    @BeforeEach
    void setUp() throws TemporadasSolapadasException {
        aeropuerto = new AeropuertoPropio("Test Airport", "TST", "Test City", 
            "Test Country", 10.0, ZoneOffset.UTC, 1, DireccionRelativaCiudad.NORTE);
        
        temporada = new Temporada("01/01", "08:00", "20:00", "31/12");
        aeropuerto.agregarTemporada(temporada);
        
        puertas = new ArrayList<>();
        puerta = new PuertaEmbarque();
        finger = new Finger(10.0, 20.0);
        zona = new ZonaAparcamiento(30.0, 40.0, 50.0);
        puerta.addZonaAparcamiento(zona);
        aeropuerto.addZonaAparcamiento(zona);
        puerta.setFinger(finger);
        puertas.add(puerta);
        
        terminal = new TerminalPasajeros("T1", puertas, 200);
        
        hangar = new HangarPasajeros(5, 50.0, 20.0, 60.0, 100.0);
        aeropuerto.addHangar(hangar);
        aeropuerto.addTerminal(terminal);
        
        finger.addHorasUso(10.0);
        puerta.addHorasUso(10.0);
        zona.addHorasUso(10.0);
        hangar.addHorasUso(10.0);
        
        gestor = new Gestor("Test Gestor", "password", aeropuerto, null);
        
        estadisticas = gestor.getEstadisticasAeropuerto();
    }

    @Test
    void testGetUsoMedioDiarioFinger() {
        // 10 horas de uso / 12 horas de operación = 0.833...
        double expected = 10.0 / 12.0;
        assertEquals(expected, estadisticas.getUsoMedioDiarioFinger(finger), 0.001);
    }

    @Test
    void testGetUsoMedioDiarioPuertaEmbarque() {
        double expected = 10.0 / 12.0;
        assertEquals(expected, estadisticas.getUsoMedioDiarioPuertaEmbarque(puerta), 0.001);
    }

    @Test
    void testGetUsoMedioDiarioZonaAparcamiento() {
        double expected = 10.0 / 12.0;
        assertEquals(expected, estadisticas.getUsoMedioDiarioZonaAparcamiento(zona), 0.001);
    }

    @Test
    void testGetUsoMedioDiarioHangar() {
        double expected = 10.0 / 12.0;
        assertEquals(expected, estadisticas.getUsoMedioDiarioHangar(hangar), 0.001);
    }

    @Test
    void testConHorasOperacionCero() {
        // Configurar aeropuerto sin horas de operación
        AeropuertoPropio aeropuertoSinHoras = new AeropuertoPropio("Empty", "EMP", 
            "Empty", "Empty", 0.0, ZoneOffset.UTC, 0, null);
        Gestor gestorSinHoras = new Gestor("Empty Gestor", "pass", aeropuertoSinHoras, null);
        
        EstadisticasGestor stats = new EstadisticasGestor(gestorSinHoras);
        assertEquals(0.0, stats.getUsoMedioDiarioFinger(new Finger(1, 1)), 0.001);
    }

    @Test
    void testImprimirEstadisticas() {
        assertDoesNotThrow(() -> {
            estadisticas.imprimirUsoMedioFingers();
            estadisticas.imprimirUsoMedioPuertasEmbarque();
            estadisticas.imprimirUsoMedioZonasAparcamiento();
            estadisticas.imprimirUsoMedioHangares();
        });
    }

    @Test
    void testConComponentesNulos() {
        assertEquals(0.0, estadisticas.getUsoMedioDiarioFinger(new Finger(1, 1)), 0.001);
        assertEquals(0.0, estadisticas.getUsoMedioDiarioPuertaEmbarque(new PuertaEmbarque()), 0.001);
        assertEquals(0.0, estadisticas.getUsoMedioDiarioZonaAparcamiento(new ZonaAparcamiento(1, 1, 1)), 0.001);
        assertEquals(0.0, estadisticas.getUsoMedioDiarioHangar(new HangarPasajeros(1, 1, 1, 1, 1)), 0.001);
    }
}