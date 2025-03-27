package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.aeropuerto.Temporada;
/**
 * TemporadaTest
 */
class TemporadaTest {
	
	Temporada temporada, temporada2, temporada3;

	/**
	 * Crea objetos
	 */
	@BeforeEach
	void setUp() {
		
		temporada = new Temporada("01/06", "08:00", "22:00", "30/09");
		temporada2 = new Temporada("01/10", "08:00", "23:00", "30/12"); 
		temporada3 = new Temporada("07/01", "09:00", "14:00", "30/10");
	}

	/**
	 * Fecha inicio
	 */
	@Test
	void testGetFechaInicio() {
		assertEquals(LocalDate.of(2025, 6, 1), temporada.getFechaInicio(), "Error la fecha de inicio no es correcta");
	}

	/**
	 * GetFechaFin
	 */
	@Test
	void testGetFechaFin() {
		assertEquals(LocalDate.of(2025, 9, 30), temporada.getFechaFin(), "Error la fecha de fin no es correcta");
	}

	/**
	 * GethorarioApertura
	 */
	@Test
	void testGetHorarioApertura() {
		assertEquals(LocalTime.of(8, 0), temporada.getHorarioApertura(), "Error el horario apertura no es correcto");
	}

	/**
	 * GetHorarioCierre
	 */
	@Test
	void testGetHorarioCierre() {
		assertEquals(LocalTime.of(22, 0), temporada.getHorarioCierre(), "Error el horario cierre no es correcto");
	}

	/**
	 *ComprobarTemporada
	 */
	@Test
	void testComprobarTemporada() {
		assertFalse(temporada.comprobarTemporada(temporada2), "Error, las temporadas no deberían solaparse");
		assertTrue(temporada.comprobarTemporada(temporada3), "Error las temporadas deberían solaparse");
	}

	/**
	 * EstaEnTemporada
	 */
	@Test
	void testEstaEnTemporada() {
		assertTrue(temporada.estaEnTemporada(LocalDate.of(2025, 7, 2)), "Error, esa fecha debería estar en temporada");
		assertFalse(temporada.estaEnTemporada(LocalDate.of(2025, 4, 3)), "Error, esa fecha no debería estar en temporada");
	}

}
