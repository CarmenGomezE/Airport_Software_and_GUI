/**
 * 
 */
package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.app.aeropuerto.AeropuertoDestino;
import src.airportmanager.app.aeropuerto.AeropuertoPropio;
import src.airportmanager.app.aeropuerto.DireccionRelativaCiudad;
import src.airportmanager.vuelo.Escala;

/**
 * EscalaTest
 */
class EscalaTest {
	private LocalTime horaLlegada, horaSalida;
	Escala escala;
	AeropuertoPropio aeropuertoPropio;
	AeropuertoDestino aeropuertoDestino;
	ZoneOffset zone;

	/**
	 * Constructor
	 */
	public EscalaTest() {

	}

	/**
	 * Crea objetos necesarios
	 */
	@BeforeEach
	void setUp() {
		zone = ZoneOffset.ofHours(+1);
		horaLlegada = LocalTime.of(7, 30, 0);
		horaSalida = LocalTime.of(11, 30, 0);
		aeropuertoDestino = new AeropuertoDestino("Heathrow", "LHR", "Londres", "Reino Unido", 34.0, zone, 2,
				DireccionRelativaCiudad.OESTE);
		aeropuertoPropio = new AeropuertoPropio("Barajas", "MAD", "Madrid", "España", 23.0, zone, 4,
				DireccionRelativaCiudad.ESTE);
		escala = new Escala(aeropuertoPropio, aeropuertoDestino, horaLlegada, horaSalida);
	}

	/**
	 * Test method for
	 * {@link src.airportmanager.vuelo.Escala#getAeropuertoDestino()}.
	 */
	@Test
	void testGetAeropuertoDestino() {
		assertEquals(aeropuertoDestino, escala.getAeropuertoDestino(),
				"Error. El aeropuerto de destino no es correcto");
	}

	/**
	 * Test method for
	 * {@link src.airportmanager.vuelo.Escala#getAeropuertoOrigen()}.
	 */
	@Test
	void testGetAeropuertoOrigen() {
		assertEquals(aeropuertoPropio, escala.getAeropuertoOrigen(), "Error. El aeropuerto de origen no es correcto");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Escala#getHoraLlegada()}.
	 */
	@Test
	void testGetHoraLlegada() {
		assertEquals(horaLlegada, escala.getHoraLlegada(), "Error la hora de llegada no es correcta");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Escala#getHoraSalida()}.
	 */
	@Test
	void testGetHoraSalida() {
		assertEquals(horaSalida, escala.getHoraSalida(), "Error la hora de salida no es correcta");
	}

}
