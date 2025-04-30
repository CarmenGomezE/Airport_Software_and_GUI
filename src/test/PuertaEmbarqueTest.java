package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.AirportManager;
import src.airportmanager.aeropuerto.AeropuertoDestino;
import src.airportmanager.aeropuerto.AeropuertoPropio;
import src.airportmanager.aeropuerto.DireccionRelativaCiudad;
import src.airportmanager.avion.Aerolinea;
import src.airportmanager.avion.Avion;
import src.airportmanager.avion.AvionPasajeros;
import src.airportmanager.exceptions.FormatoDniIncorrectoException;
import src.airportmanager.terminal.*;
import src.airportmanager.usuario.*;
import src.airportmanager.vuelo.Escala;
import src.airportmanager.vuelo.Periodicidad;
import src.airportmanager.vuelo.TipoCarga;
import src.airportmanager.vuelo.TipoModoEmbarque;
import src.airportmanager.vuelo.TipoVuelo;
import src.airportmanager.vuelo.Vuelo;

/**
 * PuertaEmbarqueTest
 */
public class PuertaEmbarqueTest {
    /**
     * Constructor
     */
    public PuertaEmbarqueTest() {

    }

    PuertaEmbarque puerta1, puerta2;
    ZonaAparcamiento zona1, zona2;
    List<ZonaAparcamiento> zonas;
    Finger finger;
    Set<PuertaEmbarque> puertas;
    Exception excepcion;
    Vuelo vuelo;
    LocalDate fecha;
    LocalTime hora, h;
    Date revision;
    Calendar calendar;
    Avion avion;
    AeropuertoDestino aeropuertoDestino;
    AeropuertoPropio aeropuertoPropio;
    ZoneOffset zone, zone2;
    Escala escala;
    Operador operador;
    Aerolinea aerolinea;
    Gestor gestor;
    AirportManager sistema;

    /**
     * Crea objetos
     */
    @BeforeEach
    void setUp() {
        puerta1 = new PuertaEmbarque();
        puerta2 = new PuertaEmbarque();

        zona1 = new ZonaAparcamiento(15.0, 25, 25);
        zona2 = new ZonaAparcamiento(20.0, 30, 30);

        puertas = new HashSet<>();
        puertas.add(puerta1);

        finger = new Finger(10, 5);

    }

    /**
     * GetId
     */
    @Test
    void testGetId() {
        assertNotEquals(puerta1.getId(), puerta2.getId(),
                "Error, los id de las puertas de embarque deberían ser diferentes");
    }

    /**
     * GetZonaAparcamiento
     */
    @Test
    void testGetZonaAparcamiento() {
        assertTrue(puerta1.getZonasAparcamiento().isEmpty(),
                "Error, la lista de zonas de aparcamiento al principio debería estar vacía");
    }

    /**
     * AddZonaAparcamiento
     */
    @Test
    void testAddZonaAparcamiento() {

        puerta1.addZonaAparcamiento(zona1);
        assertTrue(puerta1.getZonasAparcamiento().contains(zona1),
                "Error, la puerta de embarque debería tener una zona de aparcamiento");
        zonas = new ArrayList<>();
        zonas.add(zona1);
        zonas.add(zona2);
        puerta1.addZonaAparcamiento(zona2);
        assertTrue(puerta1.getZonasAparcamiento().containsAll(zonas),
                "Error, la puerta de embarque debería tener dos zonas de aparcamiento");
    }

    /**
     * GetFinger
     */
    @Test
    void testGetFinger() {
        assertEquals(null, puerta1.getFinger(), "Error al principio puerta de embarque no tiene finger");
    }

    /**
     * SetFinger
     */
    @Test
    void testSetFinger() {
        puerta1.setFinger(finger);
        assertEquals(finger, puerta1.getFinger(), "Error, el finger no coincide");

    }

    
    /**
     * ContieneZonaAparcamiento
     */
    @Test
    void testContieneZonaAparcamiento() {
        puerta1.addZonaAparcamiento(zona1);
        assertTrue(puerta1.contieneZonaAparcamiento(zona1),
                "La puerta de embarque debería contener la zona de aparcamiento");
        assertFalse(puerta2.contieneZonaAparcamiento(zona1),
                "La puerta de embarque no debería contener la zona de aparcamiento");
    }

    /**
     * GetVuelo
     */
    @Test
    void testGetVuelo() {
        assertEquals(null, puerta1.getVuelo(), "Error, la puerta de embarque al principio no debería tener vuelos");
    }

    /**
     * SetVuelo
     */
    @Test
    void testSetVuelo() {
        fecha = LocalDate.of(2024, 03, 22);
        hora = LocalTime.of(9, 30, 0);
        h = LocalTime.of(11, 30, 0);
        calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 22);
        revision = calendar.getTime();
        zone = ZoneOffset.of("+1");
        zone2 = ZoneOffset.of("+2");

        aeropuertoPropio = new AeropuertoPropio("barajas", "MAD", "Madrid", "España", 25.0, zone2, 3,
                DireccionRelativaCiudad.ESTE);
        sistema = new AirportManager();
        gestor = new Gestor("Miguel", "P8NOKYW", aeropuertoPropio, sistema);
        aerolinea = new Aerolinea("Ryanair", "RT2");
        try {
            operador = new Operador("Jesus", "56482784K", aerolinea, aeropuertoPropio, gestor, sistema);
        } catch (FormatoDniIncorrectoException e) {
            fail();
        }
        aeropuertoDestino = new AeropuertoDestino("Fuimicino", "FCO", "Roma", "Italia", 30.0, zone, 2,
                DireccionRelativaCiudad.NORTE);
        escala = new Escala(aeropuertoPropio, aeropuertoDestino, hora, h);
        avion = new AvionPasajeros("AIRBUS", "C42", 50.00, 50.0, 50.0, 60.000, 2020, revision, "JLN2000", 0);
        vuelo = new Vuelo("V123", fecha, null, null, hora, null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER,
                Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avion, aeropuertoDestino, aeropuertoPropio, escala, operador);

        puerta1.setVuelo(vuelo);
        assertEquals(vuelo, puerta1.getVuelo(), "Error, el vuelo no coincide");
    }
}
