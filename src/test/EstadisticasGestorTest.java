package src.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

import src.airportmanager.app.aeropuerto.*;
import src.airportmanager.app.estadisticas.*;
import src.airportmanager.app.exceptions.TemporadasSolapadasException;
import src.airportmanager.hangar.*;
import src.airportmanager.terminal.*;
import src.airportmanager.usuario.*;

/**
 * EstadisticasgestorTest
 */
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

    /**
     * Constructor
     */
    public EstadisticasGestorTest() {

    }

    /**
     * Crea objetos
     * 
     * @throws TemporadasSolapadasException
     */
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

    /**
     * UsoMedioDiarioFinfer
     */
    @Test
    void testGetUsoMedioDiarioFinger() {
        double expected = 0.0;
        assertEquals(expected, estadisticas.getUsoMensualFinger(finger), 0.001);
    }

    /**
     * UsoMedoDiairoPuertaEmbarque
     */
    @Test
    void testGetUsoMedioDiarioPuertaEmbarque() {
        double expected = 0.0;
        assertEquals(expected, estadisticas.getUsoMensualPuertaEmbarque(puerta), 0.001);
    }

    /**
     * UsoMedioDiarioZonaAparcamiento
     */
    @Test
    void testGetUsoMedioDiarioZonaAparcamiento() {
        double expected = 0.0;
        assertEquals(expected, estadisticas.getUsoMensualZonaAparcamiento(zona), 0.001);
    }

    /**
     * UsoMedioDiarioHangar
     */
    @Test
    void testGetUsoMedioDiarioHangar() {
        double expected = 0.0;
        assertEquals(expected, estadisticas.getUsoMensualHangar(hangar), 0.001);
    }

    /**
     * ConHorasOperacioncero
     */
    @Test
    void testConHorasOperacionCero() {
        AeropuertoPropio aeropuertoSinHoras = new AeropuertoPropio("Empty", "EMP",
                "Empty", "Empty", 0.0, ZoneOffset.UTC, 0, null);
        Gestor gestorSinHoras = new Gestor("Empty Gestor", "pass", aeropuertoSinHoras, null);

        EstadisticasGestor stats = new EstadisticasGestor(gestorSinHoras);
        assertEquals(0.0, stats.getUsoMensualFinger(new Finger(1, 1)), 0.001);
    }

    /**
     * ImprimirEstadisticas
     */
    @Test
    void testImprimirEstadisticas() {
        assertDoesNotThrow(() -> {
            estadisticas.imprimirUsoMedioFingers();
            estadisticas.imprimirUsoMedioPuertasEmbarque();
            estadisticas.imprimirUsoMedioZonasAparcamiento();
            estadisticas.imprimirUsoMedioHangares();
        });
    }

    /**
     * ConComponentesNUlos
     */
    @Test
    void testConComponentesNulos() {
        assertEquals(0.0, estadisticas.getUsoMensualFinger(new Finger(1, 1)), 0.001);
        assertEquals(0.0, estadisticas.getUsoMensualPuertaEmbarque(new PuertaEmbarque()), 0.001);
        assertEquals(0.0, estadisticas.getUsoMensualZonaAparcamiento(new ZonaAparcamiento(1, 1, 1)), 0.001);
        assertEquals(0.0, estadisticas.getUsoMensualHangar(new HangarPasajeros(1, 1, 1, 1, 1)), 0.001);
    }
}