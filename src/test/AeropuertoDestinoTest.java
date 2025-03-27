package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.aeropuerto.AeropuertoDestino;
import src.airportmanager.aeropuerto.DireccionRelativaCiudad;
import src.airportmanager.aeropuerto.Temporada;
import src.airportmanager.exceptions.TemporadasSolapadasException;
/**
 * AeropuertoDestinoTest
 */
class AeropuertoDestinoTest {
	
	private AeropuertoDestino aeropuertoDestino;
	private Temporada temporada1, temporada2;
	private List<Temporada> temporadas;
	
	/**
	 * Constructor
	 */
	public AeropuertoDestinoTest() {
		
	}
	/**
	 * Crea objetos
	 */
	@BeforeEach
	void setUp() {
		
		aeropuertoDestino = new AeropuertoDestino("Hearthrow", "LHR", "Londres", "Reino Unido", 56.9, ZoneOffset.of("+1"), 3, DireccionRelativaCiudad.SUR);
		temporada1 = new Temporada("06/01", "08:00", "22:00", "30/09"); 
		temporada2 = new Temporada("30/09", "08:00", "23:00", "30/12"); 

		temporadas = new ArrayList<>();
		temporadas.add(temporada1);
		temporadas.add(temporada2);
	}

	/**
	 * GetNombre
	 */
	@Test
	void testGetNombre() {
		assertEquals("Hearthrow", aeropuertoDestino.getNombre(), "Error el nombre no es correcto");
	}

	/**
	 * SetNOMBRE
	 */
	@Test
	void testSetNombre() {
		aeropuertoDestino.setNombre("Holaquetal");
		assertEquals("Holaquetal", aeropuertoDestino.getNombre(), "Error el nombre no se asignó correctamente");
	}

	/**
	 * GetCodigo
	 */
	@Test
	void testGetCodigo() {
		assertEquals("LHR", aeropuertoDestino.getCodigo(), "Error el código no es correcto");
	}

	/**
	 * SetCodigo
	 */
	@Test
	void testSetCodigo() {
		aeropuertoDestino.setCodigo("PTR");
		assertEquals("PTR", aeropuertoDestino.getCodigo(), "Error el código no se asignó correctamente");
	}

	/**
	 * GetCiudad
	 */
	@Test
	void testGetCiudad() {
		assertEquals("Londres", aeropuertoDestino.getCiudad(), "Error la ciudad no es correcta");
	}

	/**
	 * SetCiudad
	 */
	@Test
	void testSetCiudad() {
		aeropuertoDestino.setCiudad("París");
		assertEquals("París", aeropuertoDestino.getCiudad(), "Error la ciudad no se asignó correctamente");
	}

	/**
	 * GetPais
	 */
	@Test
	void testGetPais() {
		assertEquals("Reino Unido", aeropuertoDestino.getPais(), "Error el país no es correcta");
	}

	/**
	 * SetPais
	 */
	@Test
	void testSetPais() {
		aeropuertoDestino.setPais("Francia");
		assertEquals("Francia", aeropuertoDestino.getPais(), "Error el país no se asignó correctamente");
	}

	/**
	 * GetDistanciaCiudad
	 */
	@Test
	void testGetDistanciaCiudad() {
		assertEquals(56.9, aeropuertoDestino.getDistanciaCiudad(), "Error la distancia a la ciudad no es correcta");
	}

	/**
	 * SetDistanciacIUDAD
	 */
	@Test
	void testSetDistanciaCiudad() {
		aeropuertoDestino.setDistanciaCiudad(25.9);
		assertEquals(25.9, aeropuertoDestino.getDistanciaCiudad(), "Error la distancia a la ciudad no se asignó correctamente");
	}

	
	/**
	 * GetDiferenciaHoraria
	 */
	@Test
	void testGetDiferenciaHoraria() {
		assertEquals(ZoneOffset.of("+1"), aeropuertoDestino.getDiferenciaHoraria(), "Error la diferencia horaria no es correcta");
	}

	/**
	 * SetDiferenciaHoraria
	 */
	@Test
	void testSetDiferenciaHoraria() {
		aeropuertoDestino.setDiferenciaHoraria(ZoneOffset.of("+2"));
		assertEquals(ZoneOffset.of("+2"), aeropuertoDestino.getDiferenciaHoraria(), "Error la diferencia horaria no se asignó correctamente");
	}

	/**
	 * GetNumTemporadas
	 */
	@Test
	void testGetNumTemporadas() {
		assertEquals(3, aeropuertoDestino.getNumTemporadas(), "Error el número de temporadas no es correcto");
	}

	/**
	 * SetNumTemporadas
	 */
	@Test
	void testSetNumTemporadas() {
		aeropuertoDestino.setNumTemporadas(2);
		assertEquals(2, aeropuertoDestino.getNumTemporadas(), "Error el número de temporadas no se asignó correctamente");
	}

	/**
	 * GetDireccionCiudad
	 */
	@Test
	void testGetDireccionCiudad() {
		assertEquals(DireccionRelativaCiudad.SUR, aeropuertoDestino.getDireccionCiudad(), "Error la direccion hacia la ciudad no es correcta");
	}

	/**
	 * SetDireccionCiudad
	 */
	@Test
	void testSetDireccionCiudad() {
		aeropuertoDestino.setDireccionCiudad(DireccionRelativaCiudad.SUR);
		assertEquals(DireccionRelativaCiudad.SUR, aeropuertoDestino.getDireccionCiudad(), "Error la dirección hacia la ciudad no se asignó correctamente");
	}

	/**
	 * GetTemporadas
	 */
	@Test
	void testGetTemporadas() {
		assertTrue(aeropuertoDestino.getTemporadas().isEmpty(), "Error, al principio la lista de temporadas debería estar vacía");
	}

	/**
	 * SetTemporadas
	 */
	@Test
	void testSetTemporadas() {
		aeropuertoDestino.setTemporadas(temporadas);
		assertEquals(temporadas, aeropuertoDestino.getTemporadas(), "Error, la lista de temporadas no coincide");
	}

	/**
	 * Agregartemporada
	 * @throws TemporadasSolapadasException
	 */
	@Test
	void testAgregarTemporada() throws TemporadasSolapadasException {
	    
	    aeropuertoDestino.agregarTemporada(temporada1);
	    assertTrue(aeropuertoDestino.getTemporadas().contains(temporada1),
	               "Error: la lista de temporadas debería contener la temporada añadida");

	    TemporadasSolapadasException excepcion = assertThrows(TemporadasSolapadasException.class, () -> {
	        aeropuertoDestino.agregarTemporada(temporada1); 
	    });

	    assertEquals("La nueva temporada se solapa con otra existente", 
	                 excepcion.getMessage(), 
	                 "Error: No se lanzó la excepción esperada.");
	}

	
}
