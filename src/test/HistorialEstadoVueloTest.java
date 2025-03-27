package src.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.util.Map;
import src.airportmanager.vuelo.*;
/**
 * HistorialEstadoVueloTest
 */
public class HistorialEstadoVueloTest {

    private HistorialEstadoVuelo historial;
    private final LocalTime hora1 = LocalTime.of(10, 0);
    private final LocalTime hora2 = LocalTime.of(11, 30);
    private final LocalTime hora3 = LocalTime.of(12, 45);
    /**
	 * Constructor
	 */
	public HistorialEstadoVueloTest() {
		
	}
	
    /**
     * Crea objetos
     */
    @BeforeEach
    void setUp() {
        historial = new HistorialEstadoVuelo();
    }

    /**
     * AgregarCambioEstado
     */
    @Test
    void testAgregarCambioEstado() {
        historial.agregarCambioEstado(EstadoVuelo.ACEPTADO, hora1);
        historial.agregarCambioEstado(EstadoVuelo.A_TIEMPO, hora2);
        
        Map<LocalTime, EstadoVuelo> map = historial.getHistorial();
        assertEquals(2, map.size());
        assertEquals(EstadoVuelo.ACEPTADO, map.get(hora1));
        assertEquals(EstadoVuelo.A_TIEMPO, map.get(hora2));
    }

    /**
     * GetUltimoEstado
     */
    @Test
    void testGetUltimoEstado() {
        assertNull(historial.getUltimoEstado());
        
        historial.agregarCambioEstado(EstadoVuelo.ACEPTADO, hora1);
        assertEquals(EstadoVuelo.ACEPTADO, historial.getUltimoEstado());
        
        historial.agregarCambioEstado(EstadoVuelo.A_TIEMPO, hora2);
        assertEquals(EstadoVuelo.A_TIEMPO, historial.getUltimoEstado());
        
        historial.agregarCambioEstado(EstadoVuelo.VUELO, hora3);
        assertEquals(EstadoVuelo.VUELO, historial.getUltimoEstado());
    }

    /**
     * GetHoraCambio
     */
    @Test
    void testGetHoraCambio() {
        historial.agregarCambioEstado(EstadoVuelo.ACEPTADO, hora1);
        historial.agregarCambioEstado(EstadoVuelo.A_TIEMPO, hora2);
        historial.agregarCambioEstado(EstadoVuelo.VUELO, hora3);
        
        assertEquals(hora1, historial.getHoraCambio(EstadoVuelo.ACEPTADO));
        assertEquals(hora2, historial.getHoraCambio(EstadoVuelo.A_TIEMPO));
        assertEquals(hora3, historial.getHoraCambio(EstadoVuelo.VUELO));
        assertNull(historial.getHoraCambio(EstadoVuelo.RETRASADO)); 
    }

    /**
     * GetHoraCambioConMultiplesCambios
     */
    @Test
    void testGetHoraCambioConMultiplesCambios() {
        LocalTime horaExtra = LocalTime.of(13, 15);
        historial.agregarCambioEstado(EstadoVuelo.ACEPTADO, hora1);
        historial.agregarCambioEstado(EstadoVuelo.A_TIEMPO, hora2);
        historial.agregarCambioEstado(EstadoVuelo.ACEPTADO, hora3);
        historial.agregarCambioEstado(EstadoVuelo.VUELO, horaExtra);
        
        assertEquals(hora3, historial.getHoraCambio(EstadoVuelo.ACEPTADO));
    }

    
    /**
     * OrdenDelHistorial
     */
    @Test
    void testOrdenDelHistorial() {
        historial.agregarCambioEstado(EstadoVuelo.ACEPTADO, hora1);
        historial.agregarCambioEstado(EstadoVuelo.A_TIEMPO, hora2);
        historial.agregarCambioEstado(EstadoVuelo.VUELO, hora3);
        
        LocalTime[] horasEsperadas = {hora1, hora2, hora3};
        int i = 0;
        
        for (LocalTime hora : historial.getHistorial().keySet()) {
            assertEquals(horasEsperadas[i++], hora);
        }
    }
}