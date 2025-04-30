package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.avion.AvionPasajeros;

/**
 * Clase de test de avion de pasajeros.
 */
class AvionPasajerosTest {

	private AvionPasajeros avionPasajeros;

	/**
	 * Constructor
	 */
	public AvionPasajerosTest() {

	}

	@BeforeEach
	void setUp() {

		avionPasajeros = new AvionPasajeros("AIRBUS", "AIR7", 87.0, 67.9, 54.2, 900000, 2021, new Date(), "PSJWU78",
				150);
	}

	@Test
	void testGetCapacidadMaxPasajeros() {
		assertEquals(150, avionPasajeros.getCapacidadMaxPasajeros(),
				"Error la capacidad máxima de pasajeros no coinicide");
	}

}
