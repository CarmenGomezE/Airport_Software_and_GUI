package src.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.*;
import java.util.*;

import src.airportmanager.AirportManager;
import src.airportmanager.avion.*;
import src.airportmanager.aeropuerto.*;
import src.airportmanager.avion.Aerolinea;
import src.airportmanager.avion.Avion;
import src.airportmanager.estadisticas.*;
import src.airportmanager.exceptions.FormatoDniIncorrectoException;
import src.airportmanager.exceptions.TemporadasSolapadasException;
import src.airportmanager.usuario.*;
import src.airportmanager.vuelo.*;
/**
 * EstadisticasOperadorTest
 */
public class EstadisticasOperadorTest {

    private EstadisticasOperador estadisticas;
    private Operador operador;
    private Aerolinea aerolinea;
    private AeropuertoPropio aeropuertoPropio;
    private Avion avion1, avion2;
    private Vuelo vuelo1, vuelo2, vuelo3;
    private AirportManager sistema;

    /**
	 * Constructor
	 */
	public EstadisticasOperadorTest() {
		
	}
	
    /**
     * Crea objetos
     * @throws TemporadasSolapadasException
     * @throws FormatoDniIncorrectoException 
     */
    @BeforeEach
    void setUp() throws TemporadasSolapadasException, FormatoDniIncorrectoException {
    	sistema = new AirportManager();
    	sistema.iniciarAplicacion();
    	
        aeropuertoPropio = new AeropuertoPropio("Test Airport", "TST", "Test City", 
            "Test Country", 10.0, ZoneOffset.UTC, 1, DireccionRelativaCiudad.NORTE);
        
        aerolinea = new Aerolinea("TestAir", "TA");
        operador = new Operador("op1", "12345678A", aerolinea, aeropuertoPropio, sistema.getGestor(), sistema);
        aerolinea.setOperador(operador);
        
        avion1 = new AvionPasajeros("Boeing", "737", 10.0, 20.0, 30.0, 
            5000, 2020, new Date(), "ABC123", 150);
        avion2 = new AvionMercancias("Airbus", "A300", 12.0, 25.0, 35.0, 
            6000, 2019, new Date(), "XYZ789", 20000, false, false);
        
        aerolinea.agregarAvion(avion1);
        aerolinea.agregarAvion(avion2);
        
        AeropuertoDestino madrid = new AeropuertoDestino("Adolfo Suárez Madrid-Barajas", "MAD", 
            "Madrid", "Spain", 15.0, ZoneOffset.ofHours(1), 2, DireccionRelativaCiudad.NORTE);
        
        AeropuertoDestino ny = new AeropuertoDestino("John F. Kennedy", "JFK", 
            "New York", "USA", 25.0, ZoneOffset.ofHours(-5), 3, DireccionRelativaCiudad.ESTE);
        
        AeropuertoDestino paris = new AeropuertoDestino("Charles de Gaulle", "CDG", 
            "Paris", "France", 20.0, ZoneOffset.ofHours(1), 2, DireccionRelativaCiudad.OESTE);
        
        vuelo1 = new Vuelo("V001", LocalDate.of(2023, Month.JANUARY, 1), 
            null, null, LocalTime.of(10, 0), null, TipoCarga.PASAJEROS, 
            TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            avion1, madrid, aeropuertoPropio, null, operador);
        vuelo1.setRetraso(15);
        
        vuelo2 = new Vuelo("V002", LocalDate.of(2023, Month.JANUARY, 15), 
            null, null, LocalTime.of(12, 0), null, TipoCarga.PASAJEROS, 
            TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            avion1, ny, aeropuertoPropio, null, operador);
        vuelo2.setRetraso(30); 
        
        vuelo3 = new Vuelo("V003", LocalDate.of(2023, Month.FEBRUARY, 1), 
            null, null, LocalTime.of(14, 0), null, TipoCarga.MERCANCIAS, 
            TipoModoEmbarque.ZONA_DE_APARCAMIENTO, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            avion2, paris, aeropuertoPropio, null, operador);
       
        sistema.getGestor().getAeropuerto().getVuelosAceptados().add(vuelo1);
        sistema.getGestor().getAeropuerto().getVuelosAceptados().add(vuelo2);
        sistema.getGestor().getAeropuerto().getVuelosAceptados().add(vuelo3);
        
        estadisticas = new EstadisticasOperador(operador);
    }
    
    /**
     * CalcularMedia
     */
    @Test
    void testCalcularMedia() {
        assertEquals(0.0, estadisticas.calcularMedia(Collections.emptyList()));
        assertEquals(10.0, estadisticas.calcularMedia(List.of(10.0)));
        assertEquals(15.0, estadisticas.calcularMedia(List.of(10.0, 20.0)));
        assertEquals(20.0, estadisticas.calcularMedia(List.of(10.0, 20.0, 30.0)));
    }

    /**
     * CargarDatosEstadisticasSinVuelos
     */
    @Test
    void testCargarDatosEstadisticasSinVuelos() {
    	sistema.getGestor().getAeropuerto().getVuelosAceptados().clear();
        
        estadisticas.cargarDatosEstadisticasTiempoMedio();
        
        assertEquals(0, estadisticas.numeroVuelosEnHora());
        assertEquals(0, estadisticas.numeroVuelosRetrasados());
        assertEquals(0.0, estadisticas.getTiempoMedioRetrasoPorMes(Month.JANUARY));
        assertEquals(0.0, estadisticas.getTiempoMedioRetrasoPorFranjaHoraria(ZoneId.of("Europe/Madrid")));
        assertEquals(0.0, estadisticas.getTiempoMedioRetrasoPorVuelo(vuelo1));
    }

    /**
     * CargarDatosEstadisticasConVuelos
     */
    @Test
    void testCargarDatosEstadisticasConVuelos() {
        estadisticas.cargarDatosEstadisticasTiempoMedio();
        
        assertEquals(0.0, estadisticas.getTiempoMedioRetrasoPorVuelo(vuelo1), 0.001);
        assertEquals(0.0, estadisticas.getTiempoMedioRetrasoPorVuelo(vuelo2), 0.001);
        assertEquals(0.0, estadisticas.getTiempoMedioRetrasoPorVuelo(vuelo3), 0.001);
        
        assertEquals(0.0, estadisticas.getTiempoMedioRetrasoPorMes(Month.JANUARY), 0.001); 
        assertEquals(0.0, estadisticas.getTiempoMedioRetrasoPorMes(Month.FEBRUARY), 0.001);
    }
    
    /**
     * Impresionestadisticas
     */
    @Test
    void testImpresionEstadisticas() {
        estadisticas.cargarDatosEstadisticasTiempoMedio();
        
        assertDoesNotThrow(() -> {
            estadisticas.imprimirNumeroVuelosEnHora();
            estadisticas.imprimirNumeroVuelosRetrasados();
            estadisticas.imprimirTiempoMedioRetraso(Month.JANUARY);
            estadisticas.imprimirTiempoMedioRetraso(ZoneId.of("Europe/Madrid"));
            estadisticas.imprimirTiempoMedioRetraso(vuelo1);
        });
    }

}
