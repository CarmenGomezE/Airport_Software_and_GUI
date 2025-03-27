package src.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.*;
import src.airportmanager.avion.*;
import src.airportmanager.aeropuerto.*;
import src.airportmanager.pista.*;
import src.airportmanager.usuario.*;
import src.airportmanager.vuelo.*;

import java.time.*;
import java.util.*;

class PistaTest {

    Pista pista1, pista2;
    Vuelo vuelo1, vuelo2;
    LocalDate fecha, fecha2;
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

        pista1 = new Pista("P123", 3500.0, TipoPista.DESPEGUE);
        pista2 = new Pista("P132", 3400.0, TipoPista.ATERRIZAJE);
    

        fecha = LocalDate.of(2024, 03, 22);
        fecha2 = LocalDate.of(2024, 04, 25);
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
        avion = new AvionPasajeros("AIRBUS", "AIR7", 87.0, 67.9, 54.2, 900000, 2021, new Date(), "PSJWU78", 150);

        vuelo1 = new Vuelo("V123", fecha, null, null, hora, null, 
            TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            avion, aeropuertoDestino, aeropuertoPropio, null, operador);

        vuelo2 = new Vuelo("V456", fecha2, null, null, h, null, 
            TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            avion, aeropuertoDestino, aeropuertoPropio, escala, operador);
    }

    @Test
    void testGetCodigo() {
        assertEquals("P123", pista1.getCodigo(), "Error. El código de la pista no es el esperado");
        assertEquals("P132", pista2.getCodigo(), "Error. El código de la pista no es el esperado");
    }

    @Test
    void testGetLongitud() {
        assertEquals(3500.0, pista1.getLongitud(), "Error. La longitud de la pista no es la esperada");
        assertEquals(3400.0, pista2.getLongitud(), "Error. La longitud de la pista no es la esperada");
    }

    @Test
    void testGetTipoPista() {
        assertEquals(TipoPista.DESPEGUE, pista1.getTipoPista(), "Error. El tipo de pista no coincide.");
        assertEquals(TipoPista.ATERRIZAJE, pista2.getTipoPista(), "Error. El tipo de pista no coincide.");
    }

    @Test
    void testGetVuelos() {
    
        assertTrue(pista1.getVuelos().isEmpty(), "Error. La pista debería empezar sin vuelos.");

        pista1.addVuelo(vuelo1);
        assertEquals(1, pista1.getVuelos().size(), "Error. Debería haber 1 vuelo en la pista.");
        assertTrue(pista1.getVuelos().contains(vuelo1), "Error. El vuelo no está en la pista.");

      
        pista1.addVuelo(vuelo2);
        assertEquals(2, pista1.getVuelos().size(), "Error. Debería haber 2 vuelos en la pista.");

        pista1.removeVuelo(vuelo1);
        assertEquals(1, pista1.getVuelos().size(), "Error. Debería haber 1 vuelo después de eliminar uno.");
        assertFalse(pista1.getVuelos().contains(vuelo1), "Error. El vuelo eliminado sigue en la pista.");
    }
}







