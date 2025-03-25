package src.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.*;
import src.airportmanager.aeropuerto.AeropuertoDestino;
import src.airportmanager.aeropuerto.AeropuertoPropio;
import src.airportmanager.aeropuerto.DireccionRelativaCiudad;
import src.airportmanager.terminal.*;
import src.airportmanager.usuario.Gestor;
import src.airportmanager.usuario.Operador;
import src.airportmanager.avion.*;
import src.airportmanager.vuelo.*;
import java.time.*;
import java.util.*;

class ZonaAparcamientoTest{

    ZonaAparcamiento zona, otraZona;
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

    @BeforeEach
    void setUp() {
        zona = new ZonaAparcamiento(50.0, 60.7, 59.8);
    }

    @Test
    void testGetDimPlazaAncho() {
        assertEquals(60.7, zona.getDimPlazaAncho(), "Error, el método getDimPlazaAncho() no devuelve el valor esperado.");
    }
    
    @Test
    void testGetDimPlazaLargo() {
        assertEquals(59.8, zona.getDimPlazaLargo(), "Error, el método getDimPlazaLargo() no devuelve el valor esperado.");
    }


    @Test
    void testGetId() {
        otraZona = new ZonaAparcamiento(30.0, 5, 6);
        assertNotEquals(zona.getId(), otraZona.getId(), "Error, los IDs de las zonas de aparcamiento deberían ser diferentes.");
    }

    @Test
    void testComprobarTamano() {

        fecha = LocalDate.of(2024, 03, 22);
        hora = LocalTime.of(9,30, 0);
        h = LocalTime.of(11,30, 0);
        calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 22);
        revision = calendar.getTime();
        zone = ZoneOffset.of("+1");
        zone2 = ZoneOffset.of("+2");

        aeropuertoPropio = new AeropuertoPropio("barajas", "MAD", "Madrid", "España", 25.0, zone2, 3, DireccionRelativaCiudad.ESTE);
        sistema = new AirportManager();
        gestor = new Gestor("Miguel", "P8NOKYW", aeropuertoPropio, sistema);
        aerolinea = new Aerolinea("Ryanair", "RT2");
        operador = new Operador("Jesus", "5648274K", aerolinea, aeropuertoPropio, gestor, sistema);
        aeropuertoDestino = new AeropuertoDestino("Fuimicino", "FCO", "Roma", "Italia", 30.0, zone, 2, DireccionRelativaCiudad.NORTE);
        escala = new Escala(aeropuertoPropio, aeropuertoDestino, hora, h);
        avion = new Avion("AIRBUS", "C42", 50.00, 50.0, 50.0, 60.000, 2020, revision, "JLN2000");
        vueloPequeno = new Vuelo("V123", fecha, null, null, hora, null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avion, aeropuertoDestino, aeropuertoPropio, escala, operador);

        assertTrue(zona.comprobarTamano(vueloPequeno), "Error. El avion debería caber");
    
    }

}

