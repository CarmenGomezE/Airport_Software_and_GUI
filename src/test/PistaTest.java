/**
 * 
 */
package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

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
import src.airportmanager.pista.Pista;
import src.airportmanager.pista.TipoPista;
import src.airportmanager.usuario.Gestor;
import src.airportmanager.usuario.Operador;
import src.airportmanager.vuelo.Escala;
import src.airportmanager.vuelo.Periodicidad;
import src.airportmanager.vuelo.TipoCarga;
import src.airportmanager.vuelo.TipoModoEmbarque;
import src.airportmanager.vuelo.TipoVuelo;
import src.airportmanager.vuelo.Vuelo;

/**
 * PistaTest
 */
class PistaTest {

    private Pista pista1, pista2;
    private Vuelo vuelo1, vuelo2;
    private LocalDate fecha, fecha2;
    private LocalTime hora, h;
    private Date revision;
    private Calendar calendar;
    private Avion avion;
    private AeropuertoDestino aeropuertoDestino;
    private AeropuertoPropio aeropuertoPropio;
    private ZoneOffset zone, zone2;
    private Escala escala;
    private Operador operador;
    private Aerolinea aerolinea;
    private Gestor gestor;
    private AirportManager sistema;

    /**
     * Constructor
     */
    public PistaTest() {

    }

    /**
     * Crea objetos necesarios
     * 
     * @throws FormatoDniIncorrectoException
     */
    @BeforeEach
    void setUp() throws FormatoDniIncorrectoException {

        pista1 = new Pista("P123", 3500.0, TipoPista.DESPEGUE);
        pista2 = new Pista("P132", 3400.0, TipoPista.ATERRIZAJE);

        fecha = LocalDate.of(2024, 03, 22);
        fecha2 = LocalDate.of(2024, 04, 25);
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
        operador = new Operador("Jesus", "56438274K", aerolinea, aeropuertoPropio, gestor, sistema);
        aeropuertoDestino = new AeropuertoDestino("Fuimicino", "FCO", "Roma", "Italia", 30.0, zone, 2,
                DireccionRelativaCiudad.NORTE);
        escala = new Escala(aeropuertoPropio, aeropuertoDestino, hora, h);
        avion = new AvionPasajeros("AIRBUS", "AIR7", 87.0, 67.9, 54.2, 900000, 2021, new Date(), "PSJWU78", 150);

        vuelo1 = new Vuelo("V123", fecha, null, null, hora, null,
                TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA,
                avion, aeropuertoDestino, aeropuertoPropio, null, operador);

        vuelo2 = new Vuelo("V456", fecha2, null, null, h, null,
                TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA,
                avion, aeropuertoDestino, aeropuertoPropio, escala, operador);
    }

    /**
     * Test method for {@link src.airportmanager.pista.Pista#getCodigo()}.
     */
    @Test
    void testGetCodigo() {
        assertEquals("P123", pista1.getCodigo(), "Error. El código de la pista no es el esperado");
        assertEquals("P132", pista2.getCodigo(), "Error. El código de la pista no es el esperado");
    }

   

    /**
     * Test method for {@link src.airportmanager.pista.Pista#getTipoPista()}.
     */
    @Test
    void testGetTipoPista() {
        assertEquals(TipoPista.DESPEGUE, pista1.getTipoPista(), "Error. El tipo de pista no coincide.");
        assertEquals(TipoPista.ATERRIZAJE, pista2.getTipoPista(), "Error. El tipo de pista no coincide.");
    }

    /**
     * Test method for {@link src.airportmanager.pista.Pista#getVuelos()}.
     */
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
