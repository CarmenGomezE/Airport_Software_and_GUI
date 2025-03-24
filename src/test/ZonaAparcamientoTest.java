package src.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.*;
import src.airportmanager.terminal.*;
import src.airportmanager.avion.*;
import src.airportmanager.vuelo.*;
import java.time.*;
import java.util.*;

class ZonaAparcamientoTest{

    ZonaAparcamiento zona;

    @BeforeEach
    void setUp() {
        zona = new ZonaAparcamiento("Z123", 50.0, 3, 5);
    }

    @Test
    void testGetDimPlazaAncho() {
        assertEquals(3, zona.getDimPlazaAncho(), "Error, el método getDimPlazaAncho() no devuelve el valor esperado.");
    }

    @Test
    void testGetHorasUso() {
        assertEquals(0.0, zona.getHorasUso(), "Error, al principio la zona de aparcamiento debería tener 0 horas de uso.");
    }

    @Test
    void testAddHorasUso() {
        zona.addHorasUso(3.5);
        assertEquals(3.5, zona.getHorasUso(), "Error, las horas de uso no se actualizaron correctamente.");    
        zona.addHorasUso(2.0);
        assertEquals(5.5, zona.getHorasUso(), "Error, la suma de horas de uso no es correcta.");
    }

    @Test
    void testAddHorasUsoNegativo() {
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            zona.addHorasUso(-2.0);
        });

        assertEquals("Las horas deben ser positivas", excepcion.getMessage(), "Error, no se lanzó la excepción esperada.");
    }

    @Test
    void testHorasUsoReset() {
        zona.addHorasUso(5.0);
        zona.horasUsoReset();
        assertEquals(0.0, zona.getHorasUso(), "Error, las horas de uso de la zona de aparcamiento no se resetearon correctamente.");
    }

    @Test
    void testGetId() {
        ZonaAparcamiento otraZona = new ZonaAparcamiento("Z124", 30.0, 5, 6);
        assertNotEquals(zona.getId(), otraZona.getId(), "Error, los IDs de las zonas de aparcamiento deberían ser diferentes.");
    }

    /**AQUI TENGO QUE CAMBIAR COSAS */
    @Test
    void testComprobarTamano() {
        Avion avionPequeno;
        Vuelo vueloPequeno;
        LocalDate fechaVuelo = LocalDate.of(2024, 3, 22);
        LocalTime horaVuelo = LocalTime.of(10, 15);

        /** Crear fecha de última revisión del avión */
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 1); 
        Date fechaRevision = calendar.getTime();

        /** Crear el avión pequeño */
        avionPequeno = new AvionPasajeros("Airbus", "A320", 4.5, 35.8, 37.6, 6100.0, 2018, fechaRevision, "EC-XYZ", 180);

        /** Crear aeropuertos */
        AeropuertoPropio aeropuertoSalida = new AeropuertoPropio("Aeropuerto Madrid", "MAD", "Madrid", "España", 12.5, ZoneOffset.of("+1"), 2, DireccionRelativaCiudad.SUR);
        AeropuertoDestino aeropuertoDestino = new AeropuertoDestino("Aeropuerto París", "CDG", "París", "Francia", 18.0, ZoneOffset.of("+1"), 2, DireccionRelativaCiudad.NORTE);

        /** Crear operador, necesita aerolínea y gestor */
        Aerolinea aerolinea = new Aerolinea("Iberia", "IB123");
        Gestor gestor = new Gestor("Carlos López", "admin123", aeropuertoSalida);
        Operador operador = new Operador("Juan Pérez", "12345678A", aerolinea, aeropuertoSalida, gestor);

        /** Crear el vuelo con todos los parámetros */
        vueloPequeno = new Vuelo("V123", fechaVuelo, null, null, horaVuelo, null, 
            TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            avionPequeno, aeropuertoDestino, aeropuertoSalida, null, operador);

        assertTrue(zona.comprobarTamano(vueloPequeno), "ERROR: El avión pequeño debería caber en la zona de aparcamiento.");
    }
}
