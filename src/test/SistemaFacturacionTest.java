package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.AirportManager;
import src.airportmanager.aeropuerto.AeropuertoDestino;
import src.airportmanager.aeropuerto.AeropuertoPropio;
import src.airportmanager.aeropuerto.DireccionRelativaCiudad;
import src.airportmanager.avion.Aerolinea;
import src.airportmanager.avion.AvionPasajeros;
import src.airportmanager.exceptions.FormatoDniIncorrectoException;
import src.airportmanager.factura.*;
import src.airportmanager.terminal.PuertaEmbarque;
import src.airportmanager.terminal.TerminalPasajeros;
import src.airportmanager.usuario.ControladorAereo;
import src.airportmanager.usuario.Gestor;
import src.airportmanager.usuario.Operador;
import src.airportmanager.vuelo.*;
/**
 * SistemaFacturacionTest
 */
class SistemaFacturacionTest {

    private SistemaFacturacion sistemaFacturacion;
    private Aerolinea aerolinea;
    private Operador operador;
    private AvionPasajeros avion;
    private AeropuertoDestino aeropuertoDestino;
    private AeropuertoPropio aeropuertoPropio;
    private AirportManager sistema;
    private Gestor gestor;

    private Aerolinea aerolinea1;

    private PuertaEmbarque p1, p2;
    private List<PuertaEmbarque> puertasList;

    /**
	 * Constructor
	 */
	public SistemaFacturacionTest() {
		
	}
    /**
     * Crea objetos
     * @throws FormatoDniIncorrectoException 
     */
    @BeforeEach
    void setUp() throws FormatoDniIncorrectoException {
        
        sistemaFacturacion = new SistemaFacturacion(1.6, 20.7, 43.9);

       
        aerolinea = new Aerolinea("AA", "AeroTest");
        puertasList = new ArrayList<>();
        p1 = new PuertaEmbarque();
        p2 = new PuertaEmbarque();
        
        puertasList.add(p1);
        puertasList.add(p2);

        avion = new AvionPasajeros("AIRBUS", "AIR7", 87.0, 67.9, 54.2, 900000, 2021, new Date(), "PSJWU78", 150);
        aeropuertoDestino = new AeropuertoDestino("Hearthrow", "LHR", "Londres", "Reino Unido", 56.9, ZoneOffset.of("+1"), 3, DireccionRelativaCiudad.SUR);
        aeropuertoPropio = new AeropuertoPropio("Barajas", "MAD", "Madrid", "España", 64.9, ZoneOffset.UTC, 2, DireccionRelativaCiudad.ESTE);
        sistema = new AirportManager();
        gestor = new Gestor("Lorena", "Lore76", aeropuertoPropio, sistema);
  
        aerolinea1 = new Aerolinea("IBERIA", "IBE1");
        operador = new Operador("Miguel", "56098976M", aerolinea1, aeropuertoPropio, gestor, sistema);
       
    }

    /**
     * GenerarFactura
     */
    @Test
    void testGenerarFactura() {
       
        Operador operadorAeroTest;
		try {
			operadorAeroTest = new Operador("Test Operator", "12345678T", aerolinea, aeropuertoPropio, gestor, sistema);
			aerolinea.setOperador(operadorAeroTest);
	        aerolinea.agregarAvion(avion);
	        
	      
	        LocalDate now = LocalDate.now();
	        Vuelo vueloTest1 = new Vuelo("V123", now.withDayOfMonth(1), 
	            null, null, LocalTime.of(7, 30), null, TipoCarga.PASAJEROS, 
	            TipoModoEmbarque.ZONA_DE_APARCAMIENTO, Periodicidad.PUNTUAL, 
	            TipoVuelo.SALIDA, avion, aeropuertoDestino, aeropuertoPropio, 
	            null, operadorAeroTest);
	        
	        operadorAeroTest.getAeropuerto().getVuelosAceptados().add(vueloTest1);
	        
	        
	        List<Factura> facturas = sistemaFacturacion.generarFactura(aerolinea);
	        
	       
	        System.out.println("Airline code: " + aerolinea.getCodigo());
	        if (!facturas.isEmpty()) {
	            System.out.println("First invoice code: " + facturas.get(0).getInvoiceIdentifier());
	        }
	       
	        assertNotNull(facturas, "La lista de facturas no debería ser null");
	        assertFalse(facturas.isEmpty(), "Debería generarse al menos una factura");
	        
	        Factura factura = facturas.get(0);
	        assertTrue(factura.getInvoiceIdentifier().startsWith(aerolinea.getCodigo()), 
	            "El código de factura debería comenzar con '" + aerolinea.getCodigo() + "' pero fue '" + 
	            factura.getInvoiceIdentifier() + "'");
		} catch (FormatoDniIncorrectoException e) {
			fail();
		}
    }

    /**
     * GenerarCodigoFactura
     */
    @Test
    void testGenerarCodigoFactura() {
        
        String codigoAA = sistemaFacturacion.generarCodigoFactura(aerolinea);    // Ej: "AA000001"
        String codigoIBE = sistemaFacturacion.generarCodigoFactura(aerolinea1);  // Ej: "IBE1000002"
        
        System.out.println("Código AA generado: " + codigoAA);
        System.out.println("Código IBE generado: " + codigoIBE);
        
       
        assertNotNull(codigoAA, "El código no debería ser null");
        assertNotNull(codigoIBE, "El código no debería ser null");
        
    
        assertTrue(codigoAA.startsWith(aerolinea.getCodigo()), 
            "El código AA debería empezar con '" + aerolinea.getCodigo() + "'");
        assertTrue(codigoIBE.startsWith(aerolinea1.getCodigo()), 
            "El código IBE debería empezar con '" + aerolinea1.getCodigo() + "'");
        
   
        assertTrue(codigoAA.substring(aerolinea.getCodigo().length()).matches("\\d{6}"),
            "La parte numérica del código AA debe tener 6 dígitos");
        assertTrue(codigoIBE.substring(aerolinea1.getCodigo().length()).matches("\\d{6}"),
            "La parte numérica del código IBE debe tener 6 dígitos");
        
        
        int numAA = Integer.parseInt(codigoAA.substring(aerolinea.getCodigo().length()));
        int numIBE = Integer.parseInt(codigoIBE.substring(aerolinea1.getCodigo().length()));
        
        assertEquals(1, numIBE - numAA, "Los números deberían ser secuenciales");
        

        assertEquals(aerolinea.getCodigo().length() + 6, codigoAA.length(),
            "Longitud incorrecta para código AA");
        assertEquals(aerolinea1.getCodigo().length() + 6, codigoIBE.length(),
            "Longitud incorrecta para código IBE");
    }
    
}

    