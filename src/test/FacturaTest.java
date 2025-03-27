package src.test;

import src.airportmanager.factura.*;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import es.uam.eps.padsof.invoices.IInvoiceInfo;
import es.uam.eps.padsof.invoices.IResourceUsageInfo;
import src.airportmanager.avion.*;
import src.airportmanager.factura.*;

/**
 * Clase FacturaTest
 */
class FacturaTest{

	private Factura factura;
	private Aerolinea aerolinea;
	private List<IResourceUsageInfo> recursos;
	private UsoRecursos recurso1, recurso2;
	
	/**
	 * Constructor
	 */
	public FacturaTest() {
		
	}
	
	/**
	 * Crea los objetos necesarios
	 */
	@BeforeEach
	void setUp() {
		
		recursos = new ArrayList<>();
		recurso1 = new UsoRecursos("CPU", 10.0, 20);  
        recurso2 = new UsoRecursos("RAM", 5.0, 30);  
        recursos.add(recurso1);
        recursos.add(recurso2);
       
		aerolinea = new Aerolinea("IBERIA", "IB");
		factura = new Factura(aerolinea, LocalDate.of(2020,3 , 14), 5.0, 2.0, "FACT1", recursos, EstadoFactura.PENDIENTE);
	}

	/**
	 * TestGetCompanyName
	 */
	@Test
	void testGetCompanyName() {
		assertEquals("IBERIA", factura.getCompanyName(), "Error el nombre de la compañía no es correcto");
	}

	/**
	 * TestGetCompanyLogo
	 */
	@Test
	void testGetCompanyLogo() {
		assertEquals("./resources/logo.jpg", factura.getCompanyLogo(), "Error el logo de la compañía no es correcto");
	}

	/**
	 * TestGetCompanyIdentifier
	 */
	@Test
	void testGetInvoiceIdentifier() {
		assertEquals("FACT1", factura.getInvoiceIdentifier(), "Error el invoice identifier no es correcto");
	}

	/**
	 * TestGetCompanyAirline
	 */
	@Test
	void testGetAirline() {
		assertEquals("IB", factura.getAirline(), "Error la aerolínea no es correcta");
	}

	/**
	 * TestGetInvoiceDate
	 */
	@Test
	void testGetInvoiceDate() {
		assertEquals("2020-03-14", factura.getInvoiceDate(), "Error la fecha no es correcta");
	}

	/**
	 * 
	 * TestGetBasePrice
	 */
	 
	@Test
	void testGetBasePrice() {
		assertEquals(5.0, factura.getBasePrice(), "Error el precio base no es correcto");
	}

	/**
	 * TestGetSurcharge
	 */
	@Test
	void testGetSurcharge() {
		assertEquals(2.0, factura.getSurcharge(), "Error el recargo no es correcto");
	}

	/**
	 * TestGetResourcePrices
	 */
	@Test
	void testGetResourcePrices() {
		assertEquals(recursos, factura.getResourcePrices(), "Error la lista de recursos no es correcta");
	}

	/**
	 * TestGetEstado
	 */
	@Test
	void testGetEstado() {
		assertEquals(EstadoFactura.PENDIENTE, factura.getEstado(), "Error el estado de la factura no es correcto");
	}

	/**
	 * TestMarcarComoPagada
	 */
	@Test
	void testMarcarComoPagada() {
		factura.marcarComoPagada();
		assertEquals(EstadoFactura.PAGADA, factura.getEstado(), "Error no se ha marcado como pagada");
	}

	/**
	 * Testgetprice
	 */
	@Test
	void testGetPrice() {
		assertEquals(10*20+5*30, factura.getPrice(), "Error el precio no se calculó correctamente");
	}

}
