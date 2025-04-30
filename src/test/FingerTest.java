package src.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.AirportManager;
import src.airportmanager.aeropuerto.*;
import src.airportmanager.avion.*;
import src.airportmanager.exceptions.FormatoDniIncorrectoException;
import src.airportmanager.terminal.*;
import src.airportmanager.vuelo.*;
import src.airportmanager.usuario.*;
import java.time.*;
import java.util.*;

/**
 * FingerTest
 */
class FingerTest {

    Finger finger, otroFinger;
    PuertaEmbarque puerta1, puerta2, p1, p2;
    Set<PuertaEmbarque> puertas, puertasEsperadas, p;

    /**
     * Constructor
     */
    public FingerTest() {

    }

    @BeforeEach
    void setUp() {

        puerta1 = new PuertaEmbarque();
        puerta2 = new PuertaEmbarque();

        puertas = new HashSet<PuertaEmbarque>();
        puertas.add(puerta1);
        puertas.add(puerta2);

        finger = new Finger(50.0, 70.0);

    }

    /**
     * GetDimAlto
     */
    @Test
    void testGetDimAlto() {
        assertEquals(70.0, finger.getDimAlto(), "Error, el método getDimAlto() no devuelve el valor esperado.");
    }

    /**
     * GetId
     */
    @Test
    void testGetId() {
        otroFinger = new Finger(40.0, 4.0);
        assertNotEquals(finger.getId(), otroFinger.getId(), "Error, los id de los fingers deberían ser diferentes");
    }

    /**
     * ComprobarTamano
     */
    @Test
    void testComprobarTamano() {

        Vuelo vueloPequeno;
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
            operador = new Operador("Jesus", "56482714K", aerolinea, aeropuertoPropio, gestor, sistema);

            aeropuertoDestino = new AeropuertoDestino("Fuimicino", "FCO", "Roma", "Italia", 30.0, zone, 2,
                    DireccionRelativaCiudad.NORTE);
            escala = new Escala(aeropuertoPropio, aeropuertoDestino, hora, h);
            avion = new AvionPasajeros("AIRBUS", "AIR7", 5.5, 6.9, 4.2, 900000, 2021, new Date(), "PSJWU78", 150);
            vueloPequeno = new Vuelo("V123", fecha, null, null, hora, null, TipoCarga.PASAJEROS,
                    TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avion, aeropuertoDestino,
                    aeropuertoPropio, escala, operador);

            assertTrue(finger.comprobarTamano(vueloPequeno), "Error. El avion debería caber");
        } catch (FormatoDniIncorrectoException e) {
            fail();
        }
    }
}