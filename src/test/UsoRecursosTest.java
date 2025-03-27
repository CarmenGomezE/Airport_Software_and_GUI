package src.test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.factura.*;

class UsoRecursosTest {
	private UsoRecursos uso;

	@BeforeEach
	void setUp()  {
		
		uso = new UsoRecursos("RAM", 6.5, 30);
	}

	@Test
	void testGetResourceDescription() {
		assertEquals("RAM", uso.getResourceDescription(), "Error la descripción no es correcta");
	}

	@Test
	void testGetHourlyPrice() {
		assertEquals(6.5, uso.getHourlyPrice(), "Error las horas de uso no son correctas");
	}

	@Test
	void testGetUsageTime() {
		assertEquals(String.valueOf(30), uso.getUsageTime(), "Error el tiempo de uso no es correcto");
	}

	@Test
	void testGetPrice() {
		assertEquals(30*6.5, uso.getPrice(), "Error el precio no es correcto");
	}

}
