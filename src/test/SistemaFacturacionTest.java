package src.test;

import static org.junit.jupiter.api.Assertions.*;

import src.airportmanager.factura.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SistemaFacturacionTest {
	
	SistemaFacturacion sistemaFacturacion;
	Factura factura;

	@BeforeEach
	void setUp() {
		
		sistemaFacturacion = new SistemaFacturacion(1.6, 20.7, 43.9);
	}

	@Test
	void testGenerarFactura() {
		fail("Not yet implemented");
	}

	@Test
	void testGenerarCodigoFactura() {
		fail("Not yet implemented");
	}

}
