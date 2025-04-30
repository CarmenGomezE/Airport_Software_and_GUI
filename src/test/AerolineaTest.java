package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
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
import src.airportmanager.usuario.*;
import src.airportmanager.vuelo.Periodicidad;
import src.airportmanager.vuelo.TipoCarga;
import src.airportmanager.vuelo.TipoModoEmbarque;
import src.airportmanager.vuelo.TipoVuelo;
import src.airportmanager.vuelo.Vuelo;
import src.airportmanager.factura.*;;

/**
 * Clase de test de aerolinea.
 */
public class AerolineaTest {
    Aerolinea aerolinea;
    Operador operador;
    AeropuertoPropio aeropuerto;
    AeropuertoDestino aeropuertoDestino;
    ZoneOffset zone;
    Gestor gestor;
    AirportManager sistema;
    Factura factura;
    SistemaFacturacion sistemaFacturacion;
    List<Factura> facturas;
    Vuelo vuelo;
    Avion avion;
    Date fechaAvion;
    Calendar calendar;

    /**
     * Constructor
     */
    public AerolineaTest() {

    }

    @BeforeEach
    void setUp() throws FormatoDniIncorrectoException {

        calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 22);
        fechaAvion = calendar.getTime();
        zone = ZoneOffset.ofHours((+1));
        aerolinea = new Aerolinea("Iberia", "IB2");
        aeropuerto = new AeropuertoPropio("Barajas", "MAD", "Madrid", "España", 3.5, zone, 3,
                DireccionRelativaCiudad.ESTE);
        aeropuertoDestino = new AeropuertoDestino("Hearthwo", "LHR", "Londres", "Reino Unido", 15.5, zone, 3,
                DireccionRelativaCiudad.SUR);
        sistema = new AirportManager();
        gestor = new Gestor("Raúl", "Rauul90", aeropuerto, sistema);
        operador = new Operador("Pedro", "57487643T", aerolinea, aeropuerto, gestor, sistema);
        aerolinea.setOperador(operador);
        gestor.establecerSistemaFacturacion();
        facturas = gestor.getSistemaFacturacion().generarFactura(aerolinea);
        avion = new AvionPasajeros("AIRBUS", "A3", 5.0, 5.5, 5.5, 600.000, 2020, null, null, 0);
        avion.setAerolinea(aerolinea);
        aerolinea.agregarAvion(avion);
        vuelo = new Vuelo("V123", LocalDate.of(2025, 2, 10), null, null, LocalTime.of(9, 30, 0), null,
                TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avion,
                aeropuertoDestino, aeropuerto, null, operador);
        avion.agregarVuelo(vuelo);
        operador.recibirAlternativas(vuelo, List.of(vuelo));
        operador.aceptarVuelo(vuelo);
        aeropuerto.addVueloAceptado(vuelo);
        aeropuerto.addUsuario(operador);

    }

    @Test
    void testGetNombre() {
        assertEquals("Iberia", aerolinea.getNombre(), "Error, el nombre de la aerolínea no es correcto");
    }

    @Test
    void testGetOperador() {
        assertEquals(operador, aerolinea.getOperador(), "Error, el operador de la aerolínea no es correcto");
    }

    @Test
    void testSetOperador() {
        aerolinea.setOperador(operador);
        assertEquals(operador, aerolinea.getOperador(), "Error, el operador de la aerolínea no es correcto");

    }

    @Test
    void testGetCodigo() {
        assertEquals("IB2", aerolinea.getCodigo(), "Error el código de la aerolínea no es correcto");
    }

    @Test
    void testGetFactura() {
        assertTrue(aerolinea.getFacturas().isEmpty(), "Error, al principio la lista de facturas debería estar vacía");
    }

    @Test
    void testAddFactura() {
        gestor.emitirFactura(aerolinea);
        assertTrue(!aerolinea.getFacturas().contains(factura), "Error la aerolínea debería tener la factura");

    }

    @Test
    void testGetVuelos() {
        assertTrue(aerolinea.getVuelos().contains(vuelo), "Error, la aerolínea debería tener el vuelo.");

    }

    @Test
    void testVuelosMensuales() {
        assertTrue(aerolinea.vuelosMensuales(Month.FEBRUARY).contains(vuelo),
                "Error, en el mes de febrero la aerolínea debería tener un vuelo");
    }

    @Test
    void testGetAviones() {
        assertTrue(aerolinea.getAviones().contains(avion),
                "Error, al principio la lista de aviones de la aerolínea debería estar vacía");
    }

    @Test
    void testAgregarAviones() {
        aerolinea.agregarAvion(avion);
        assertTrue(aerolinea.getAviones().contains(avion), "Error, la aerolínea debería tener un avión");
    }
}
