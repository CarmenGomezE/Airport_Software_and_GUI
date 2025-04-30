/**

 * 
 */
package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.*;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.app.aeropuerto.*;
import src.airportmanager.app.avion.*;
import src.airportmanager.vuelo.*;
import src.airportmanager.usuario.*;

/**
 * VueloProgramado test
 */
class VueloProgramadoTest {
	private Aeropuerto aeropuertoDestino, aeropuertoOrigen;
	private Avion avion, avion2;
	private VueloProgramado vuelo1, vuelo2;
	private List<DayOfWeek> diasRecurrentes;
	private Escala escala;

	/**
	 * Constructor
	 */
	public VueloProgramadoTest() {

	}

	/**
	 * Crea objetos necesarios
	 */
	@BeforeEach
	void setUp() {
		aeropuertoDestino = new AeropuertoDestino("John F. Kennedy International Airport", "JF", "Nueva York",
				"Estados Unidos", 20.0, ZoneOffset.ofHours(-5), 4, DireccionRelativaCiudad.SUR);
		diasRecurrentes = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
		aeropuertoOrigen = new AeropuertoPropio("Barajas", "MAD", "Madrid", "España", 64.9, ZoneOffset.UTC, 2,
				DireccionRelativaCiudad.ESTE);
		escala = new Escala(aeropuertoOrigen, aeropuertoDestino, LocalTime.of(7, 30), LocalTime.of(9, 30));
		avion = new AvionPasajeros("AIRBUS", "AIR7", 87.0, 67.9, 54.2, 900000, 2021, new Date(), "PSJWU78", 0);
		vuelo1 = new VueloProgramado("V123", LocalDate.of(2023, 5, 12), LocalDate.of(2023, 5, 12),
				LocalDate.of(2023, 5, 12), LocalTime.of(7, 30), null, TipoCarga.PASAJEROS,
				TipoModoEmbarque.ZONA_DE_APARCAMIENTO, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avion, aeropuertoDestino,
				aeropuertoOrigen, null);
		vuelo2 = new VueloProgramado("K234", LocalDate.of(2023, 5, 12), LocalDate.of(2023, 5, 12),
				LocalDate.of(2023, 7, 12), LocalTime.of(7, 30), diasRecurrentes, TipoCarga.PASAJEROS,
				TipoModoEmbarque.ZONA_DE_APARCAMIENTO, Periodicidad.RECURRENTE, TipoVuelo.SALIDA, avion,
				aeropuertoDestino, aeropuertoOrigen, escala);
	}

	/**
	 * GetAeropuertoDestino
	 */
	@Test
	void testGetAeropuertoDestino() {
		assertEquals(aeropuertoDestino, vuelo1.getAeropuertoDestino(),
				"Error, el vuelo debería devolver el aeropuerto de destino inicializado");
	}

	/**
	 * GetAeropuertoSalida
	 */
	@Test
	void testGetAeropuertoSalida() {
		assertEquals(aeropuertoOrigen, vuelo1.getAeropuertoSalida(),
				"Error, el vuelo debería devolver el aeropuerto de salida inicializado");
	}

	/**
	 * GetFechaInicio
	 */
	@Test
	void testGetFechaInicio() {
		assertNull(vuelo1.getFechaInicio(), "Error, el vuelo debería devolver la fecha inicio a null");
		assertEquals(LocalDate.of(2023, 5, 12), vuelo2.getFechaInicio(),
				"Error, el vuelo debería devolver la fecha inicio inicializada");
	}

	/**
	 * GetFechaFinal
	 */
	@Test
	void testGetFechaFinal() {
		assertNull(vuelo1.getFechaFinal(), "Error, el vuelo debería devolver la fecha final a null");
		assertEquals(LocalDate.of(2023, 7, 12), vuelo2.getFechaFinal(),
				"Error, el vuelo debería devolver la fecha final inicializada");
	}

	/**
	 * GetCodigo
	 */
	@Test
	void testGetCodigo() {
		assertEquals("V123", vuelo1.getCodigo(), "Error, el vuelo debería devolver el código inicializado");
	}

	/**
	 * GetFecha
	 */
	@Test
	void testGetFecha() {
		assertEquals(LocalDate.of(2023, 5, 12), vuelo1.getFecha(),
				"Error, el vuelo debería devolver la fecha inicializada");
	}

	/**
	 * GetHora
	 */
	@Test
	void testGetHora() {
		assertEquals(LocalTime.of(7, 30), vuelo1.getHora(), "Error, el vuelo debería devolver la hora inicializada");
	}

	/**
	 * GetDiasRecuurente
	 */
	@Test
	void testGetDiasRecurrente() {
		assertNull(vuelo1.getDiasRecurrente(), "Error, el vuelo debería devolver los dias recurrentes a null");
		assertEquals(diasRecurrentes, vuelo2.getDiasRecurrente(),
				"Error, el vuelo debería devolver los días recurrentes inicializados");
	}

	/**
	 * GetTipoCarga
	 */
	@Test
	void testGetTipoCarga() {
		assertEquals(TipoCarga.PASAJEROS, vuelo1.getTipoCarga(),
				"Error, el vuelo debería devolver el tipo de carga inicializado");
	}

	/**
	 * GetTipoModoEmbarque
	 */
	@Test
	void testGetTipoModoEmbarque() {
		assertEquals(TipoModoEmbarque.ZONA_DE_APARCAMIENTO, vuelo1.getTipoModoEmbarque(),
				"Error, el vuelo debería devolver el tipo de modo de embarque inicializado");
	}

	/**
	 * GetPeriodicidad
	 */
	@Test
	void testGetPeriodicidad() {
		assertEquals(Periodicidad.PUNTUAL, vuelo1.getPeriodicidad(),
				"Error, el vuelo debería devolver la periodicidad inicializada");
	}

	/**
	 * GetTipoVuelo
	 */
	@Test
	void testGetTipoVuelo() {
		assertEquals(TipoVuelo.SALIDA, vuelo1.getTipoVuelo(),
				"Error, el vuelo debería devolver el tipo de vuelo inicializado");
	}

	/**
	 * GetAvion
	 */
	@Test
	void testGetAvion() {
		assertEquals(avion, vuelo1.getAvion(), "Error, el vuelo debería devolver el avion inicializado");
	}

	/**
	 * SetHora
	 */
	@Test
	void testSetHora() {
		vuelo1.setHora(LocalTime.of(8, 30));
		assertEquals(LocalTime.of(8, 30), vuelo1.getHora(), "Error, el vuelo debría poner la hora inicializada");
	}

	/**
	 * SetAvion
	 */
	@Test
	void testSetAvion() {
		avion2 = new AvionPasajeros("AIRBAG", "AIR7", 87.0, 67.9, 54.2, 900000, 2021, new Date(), "PSJWU78", 0);
		vuelo1.setAvion(avion2);
		assertEquals(avion2, vuelo1.getAvion(), "Error, el vuelo debería poner el avion inicializado");
	}

	/**
	 * SetFecha
	 */
	@Test
	void testSetFecha() {
		vuelo1.setFecha(LocalDate.of(2026, 4, 5));
		assertEquals(LocalDate.of(2026, 4, 5), vuelo1.getFecha(),
				"Error, el vuelo debería poner la fecha inicializada");
	}

	/**
	 * GetEscala
	 */
	@Test
	void testGetEscala() {
		assertNull(vuelo1.getEscala(), "Error, el vuelo debería devolver la escala null");
		assertEquals(escala, vuelo2.getEscala(), "Error, el vuelo debería devolver la escala inicializada");
	}

}