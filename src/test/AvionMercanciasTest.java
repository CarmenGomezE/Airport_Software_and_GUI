package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.avion.AvionMercancias;

class AvionMercanciasTest {
	
	private AvionMercancias avionMercancias;

	@BeforeEach
	void setUp(){
		avionMercancias = new AvionMercancias("AIRBUS", "AIR7", 87.0, 67.9, 54.2, 900000, 2021, new Date(), "PSJWU78", 250.60, false, true);
		
	}

	@Test
	void testGetCapacidadMaxCarga() {
		assertEquals(250.60, avionMercancias.getCapacidadMaxCarga(), "Error el campo de capacidad máxima de carga no es correcto");
	}

	@Test
	void testGetControlTemperatura() {
		assertEquals(true, avionMercancias.getControlTemperatura(), "Error el campo de control de temperatura no es correcto");
	}

	@Test
	void testGetMercaPeligrosa() {
		assertEquals(false, avionMercancias.getMercaPeligrosa(), "Error el campo de mercancías peligrosas");
	}

}
