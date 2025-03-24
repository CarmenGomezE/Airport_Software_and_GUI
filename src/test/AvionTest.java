package src.test;

import src.airportmanager.avion.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.aeropuerto.Aeropuerto;
import src.airportmanager.aeropuerto.AeropuertoDestino;
import src.airportmanager.aeropuerto.AeropuertoPropio;
import src.airportmanager.aeropuerto.DireccionRelativaCiudad;
import src.airportmanager.avion.Aerolinea;
import src.airportmanager.usuario.Operador;
import src.airportmanager.vuelo.*;

import java.time.*;
import java.util.*;

class AvionTest {
    private Avion avion;
    private Aerolinea aerolinea;
    private Vuelo vuelo;
    private Operador operador;
    private Aeropuerto aeropuertoSalida, aeropuertoDestino;
    private Escala escala;
    private Date fechaRevision;

    @BeforeEach
    void setUp() {
        fechaRevision = new Date();
        avion = new Avion("Boeing", "737", 12.0, 35.8, 39.5, 5600.0, 2020, fechaRevision, "ABC123");

        aeropuertoSalida = new AeropuertoPropio("Madrid-Barajas", "MAD", "Madrid", "España", 12.5, ZoneOffset.of("+1"),
                2, DireccionRelativaCiudad.NORTE);
        aeropuertoDestino = new AeropuertoDestino("Barcelona-El Prat", "BCN", "Barcelona", "España", 10.0,
                ZoneOffset.of("+1"), 2, DireccionRelativaCiudad.SUR);

        aerolinea = new Aerolinea("AeroTest", "AT123", null); // Operador aún no asignado
        operador = new Operador("Juan Pérez", "12345678A", aerolinea, null, null);
        aerolinea = new Aerolinea("AeroTest", "AT123", operador); // Ahora asignamos operador a la aerolínea

        escala = new Escala(aeropuertoSalida, aeropuertoDestino, LocalTime.of(12, 0), LocalTime.of(13, 0));

        vuelo = new Vuelo("V123", LocalDate.of(2025, 5, 10), LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 30),
                LocalTime.of(10, 30), List.of(DayOfWeek.MONDAY), TipoCarga.PASAJEROS,
                TipoModoEmbarque.FINGER, Periodicidad.RECURRENTE, TipoVuelo.SALIDA,
                avion, aeropuertoDestino, aeropuertoSalida, escala, operador);
    }

    @Test
    void testConstructor() {
        assertEquals("Boeing", avion.getMarca());
        assertEquals("737", avion.getModelo());
        assertEquals(12.0, avion.getDimAlto());
        assertEquals(35.8, avion.getDimAncho());
        assertEquals(39.5, avion.getDimLargo());
        assertEquals(5600.0, avion.getAutonomia());
        assertEquals(2020, avion.getAnoCompra());
        assertEquals(fechaRevision, avion.getFechaUltimaRevision());
        assertEquals("ABC123", avion.getMatricula());
        assertNull(avion.getAerolinea());
    }

    @Test
    void testSetAerolinea() {
        avion.setAerolinea(aerolinea);
        assertEquals(aerolinea, avion.getAerolinea());
    }

    @Test
    void testAgregarVuelo() {
        avion.agregarVuelo(vuelo);
        assertFalse(avion.estaDisponible(LocalDate.of(2025, 5, 10), LocalTime.of(10, 30)));
    }

    @Test
    void testEstaDisponible() {
        avion.agregarVuelo(vuelo);

        assertFalse(avion.estaDisponible(LocalDate.of(2025, 5, 10), LocalTime.of(10, 30)));
        assertTrue(avion.estaDisponible(LocalDate.of(2025, 5, 10), LocalTime.of(15, 30)));
    }
}
