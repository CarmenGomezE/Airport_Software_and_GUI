package src.airportmanager.factura;

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


class FacturaTest{

	private Factura factura;
	private Aerolinea aerolinea;
	private List<IResourceUsageInfo> recursos;
	private UsoRecursos recurso1, recurso2;
	
	@BeforeEach
	void setUp() {
		
		recursos = new ArrayList<>();
		recurso1 = new UsoRecursos("CPU", 10.0, 20);  
        recurso2 = new UsoRecursos("RAM", 5.0, 30);  
        recursos.add(recurso1);
        recursos.add(recurso2);
       
		aerolinea = new Aerolinea("IBERIA", "IB");
		factura = new Factura(aerolinea, LocalDate.of(2020,14 , 3), 5.0, 2.0, "FACT1", recursos, EstadoFactura.PAGADA);
	}

	@Test
	void testGetCompanyName() {
		assertEquals("IBERIA", factura.getCompanyName(), "Error el nombre de la compañía no es correcto");
	}

	@Test
	void testGetCompanyLogo() {
		assertEquals("./resources/logo.jpg", factura.getCompanyLogo(), "Error el logo de la compañía no es correcto");
	}

	@Test
	void testGetInvoiceIdentifier() {
		assertEquals("FACT1", factura.getInvoiceIdentifier(), "Error el invoice identifier no es correcto");
	}

	@Test
	void testGetAirline() {
		assertEquals(aerolinea, factura.getAirline(), "Error la aerolínea no es correcta");
	}

	@Test
	void testGetInvoiceDate() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBasePrice() {
		fail("Not yet implemented");
	}

	@Test
	void testGetSurcharge() {
		fail("Not yet implemented");
	}

	@Test
	void testGetResourcePrices() {
		fail("Not yet implemented");
	}

	@Test
	void testGetEstado() {
		fail("Not yet implemented");
	}

	@Test
	void testMarcarComoPagada() {
		fail("Not yet implemented");
	}

	@Test
	void testGetPrice() {
		fail("Not yet implemented");
	}

}
