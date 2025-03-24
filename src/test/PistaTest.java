package aeropuerto.src.claudia;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aeropuerto.src.carmen.*;
import aeropuerto.src.claudia.*;
import aeropuerto.src.maria.*;
import aeropuerto.src.carmen.vuelo.*;
import java.time.*;
import java.util.*;

class PistaTest {

    Pista pista1, pista2;
    Vuelo vuelo1, vuelo2;

    @BeforeEach
    void setUp() {
        pista1 = new Pista("P123", 3500.0, TipoPista.DESPEGUE);
        pista2 = new Pista("P132", 3400.0, TipoPista.ATERRIZAJE);


        LocalDate fechaVuelo = LocalDate.of(2024, 3, 22);
        LocalTime horaVuelo = LocalTime.of(10, 15);
        Avion avion = new AvionPasajeros("Boeing", "737", 4.0, 35.0, 38.0, 6000.0, 2019, new Date(), "EC-XYZ", 160);
        AeropuertoPropio aeropuertoSalida = new AeropuertoPropio("Aeropuerto Madrid", "MAD", "Madrid", "España", 12.5, ZoneOffset.of("+1"), 2, DireccionRelativaCiudad.SUR);
        AeropuertoDestino aeropuertoDestino = new AeropuertoDestino("Aeropuerto París", "CDG", "París", "Francia", 18.0, ZoneOffset.of("+1"), 2, DireccionRelativaCiudad.NORTE);
        Aerolinea aerolinea = new Aerolinea("Iberia", "IB123");
        Gestor gestor = new Gestor("Carlos López", "admin123", aeropuertoSalida);
        Operador operador = new Operador("Juan Pérez", "12345678A", aerolinea, aeropuertoSalida, gestor);

        vuelo1 = new Vuelo("V123", fechaVuelo, null, null, horaVuelo, null, 
            TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            avion, aeropuertoDestino, aeropuertoSalida, null, operador);

        vuelo2 = new Vuelo("V456", fechaVuelo, null, null, horaVuelo, null, 
            TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            avion, aeropuertoDestino, aeropuertoSalida, null, operador);
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

        pista1.quitarVuelo(vuelo1);
        assertEquals(1, pista1.getVuelos().size(), "Error. Debería haber 1 vuelo después de eliminar uno.");
        assertFalse(pista1.getVuelos().contains(vuelo1), "Error. El vuelo eliminado sigue en la pista.");
    }
}







}