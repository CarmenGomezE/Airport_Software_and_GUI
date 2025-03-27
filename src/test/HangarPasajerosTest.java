package src.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.*;
import src.airportmanager.avion.*;
import src.airportmanager.exceptions.CargaAvionEquivocadaException;
import src.airportmanager.vuelo.*;
import src.airportmanager.aeropuerto.*;
import src.airportmanager.usuario.*;
import src.airportmanager.hangar.*;
import java.time.*;
import java.util.*;

class HangarPasajerosTest{

    private HangarPasajeros hangar, hangar2;
    private Avion avion, avion2;
    private Calendar calendar;
    private LocalDate fecha;
    private List<Avion> aviones;
    private Vuelo vueloPequeno;
    private LocalTime hora, h;
    private Date revision;
    private AeropuertoDestino aeropuertoDestino;
    private AeropuertoPropio aeropuertoPropio;
    private ZoneOffset zone, zone2;
    private Escala escala;
    private Operador operador;
    private Aerolinea aerolinea;
    private Gestor gestor;
    private AirportManager sistema;

    @BeforeEach
    void setUp() {
        calendar = Calendar.getInstance();
        calendar.set(23, 6, 2023);

        hangar = new HangarPasajeros(2, 45.5, 34.5, 40.8, 5.0);
        hangar2 = new HangarPasajeros(3, 43.8, 46.9, 35.0, 5.5);
        avion = new AvionPasajeros("BOEING", "767-300ER", 35.5, 67.3, 34.8, 90098.5, 2022, calendar.getTime(), "83472PL", 0);
        avion2 = new AvionPasajeros("AIRBUS", "8863-JDID", 67.9, 45.5, 34.5, 78009, 2024, calendar.getTime(), "98378KL", 0);

    }

    @Test
    void testGetAviones() {
        assertTrue(hangar.getAviones().isEmpty(), "Error, la lista de aviones del hangar al principio debería estar vacía");
    }

    @Test
    void testAddAvion() throws CargaAvionEquivocadaException {
        hangar.addAvion(avion);
        assertTrue(hangar.getAviones().contains(avion), "Error la lista de aviones del hangar debería contener un avión");
        hangar.addAvion(avion2);
        aviones = new ArrayList<>();
        aviones.add(avion);
        aviones.add(avion2);
        for(Avion a:aviones) {
        	assertTrue(hangar.comprobarAvionEnHangar(a), "Error la lista de aviones del hangar debería contener dos aviones");
        }
    }

    @Test
    void testSacarAvion() throws CargaAvionEquivocadaException {

        hangar.addAvion(avion);
        hangar.sacarAvion(avion);
        assertTrue(hangar.getAviones().isEmpty(), "Error la lista de aviones del hangar debería estar vacía después de sacar el único avión que había");

    }

    @Test
    void testComprobarAvionHangar() throws CargaAvionEquivocadaException {

        hangar.addAvion(avion);
        assertTrue(hangar.comprobarAvionEnHangar(avion), "Error, el avion debería estar en el hangar");
        assertFalse(hangar.comprobarAvionEnHangar(avion2), "Error, el avión no debería estar en el hangar");
    }

    @Test
    void testHayEspacioEnHangar() throws CargaAvionEquivocadaException {

        hangar.addAvion(avion);
        assertTrue(hangar.hayEspacioEnHangar(), "Error, debería haber espacio para más aviones en el hangar");
        hangar.addAvion(avion2);
        assertFalse(hangar.hayEspacioEnHangar(), "Error, no debería haber espacion para más aviones en el hangar.");
    }

    @Test
    void testGetId() {
        assertNotEquals(hangar.getId(), hangar2.getId(), "Error, los id debería ser distintos");
    }

    @Test
    void testGetNumeroMaxAviones() {
        assertEquals(2, hangar.getNumeroMaxAviones(), "Error el número máximo de aviones no coincide");

    }

    @Test
    void testGetDimPlazaAncho() {
        assertEquals(45.5, hangar.getDimPlazaAncho(), "Error la dimensión de ancho de la plaza no es correcta");

    }

    @Test
    void testGetDimPlazaAlto() {
        assertEquals(34.5, hangar.getDimPlazaAlto(), "Error la dimensión de alto de la plaza no es correcta");
        
    }

    @Test
    void testGetDimPlazaLargo() {
        assertEquals(40.8, hangar.getDimPlazaLargo(), "Error la dimensión de largo de la plaza no es correcta");
        
    }

    @Test
    void testGetCostePorHora() {
        assertEquals(5.0, hangar.getCostePorHora(), "Error el coste por hora del hangar no es correcto");
        
    }

    @Test
    void testGetHorasUso() {
        assertEquals(0, hangar.getHorasUso(), "Error al principio las horas de uso del hangar deberían estar a 0");
        
    }

    @Test
    void testAddHorasUso() {
        hangar.addHorasUso(5);
        assertEquals(5, hangar.getHorasUso(), "Error las horas del uso del hangar no son correctas");
  
    }

    
    @Test
    void testReserHorasUso() {
        hangar.addHorasUso(5);
        hangar.resetHorasUso();
        assertEquals(0, hangar.getHorasUso(), "Error las horas del uso del hangar no se resetearon correctamente");
  
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
        avion = new AvionPasajeros("AIRBUS", "AIR7", 7.0, 7.9, 4.2, 900000, 2021, new Date(), "PSJWU78", 150);
        vueloPequeno = new Vuelo("V123", fecha, null, null, hora, null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avion, aeropuertoDestino, aeropuertoPropio, escala, operador);

        assertTrue(hangar.comprobarTamano(vueloPequeno), "Error. El avion debería caber");
    
    }
    


}
