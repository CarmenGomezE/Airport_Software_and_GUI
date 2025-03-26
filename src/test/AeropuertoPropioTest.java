package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.AirportManager;
import src.airportmanager.aeropuerto.*;
import src.airportmanager.terminal.*;
import src.airportmanager.hangar.*;
import src.airportmanager.pista.Pista;
import src.airportmanager.pista.TipoPista;
import src.airportmanager.usuario.*;
import src.airportmanager.vuelo.*;
import src.airportmanager.avion.*;
import src.airportmanager.exceptions.TemporadasSolapadasException;


class AeropuertoPropioTest {

	private AeropuertoPropio aeropuertoPropio;
	private Finger finger1, finger2;
	private HangarPasajeros hangar1, hangar2;
	private ControladorAereo controlador;
	private Operador operador;
	private Gestor gestor;
	private TerminalPasajeros terminal1, terminal2;
	private Aerolinea aerolinea1, aerolinea2;
	private Vuelo vuelo1, vuelo2;
	private AeropuertoDestino aeropuertoDestino1, aeropuertoDestino2;
	private Set<PuertaEmbarque> puertas, puertas2;
	private PuertaEmbarque p1, p2, p3, p4;
	private List<Finger> fingers;
	private List<Hangar> hangares;
	private ZonaAparcamiento zona1, zona2;
	private List<ZonaAparcamiento> zonas;
	private List<Terminal> terminales;
	private AirportManager sistema;
	private List<Aerolinea> aerolineas;
	private List<Usuario> usuarios;
	private Avion avion;
	private List<Vuelo> vuelos;
	private List<AeropuertoDestino> aeropuertosDestino;
	private Pista pista1, pista2;
	private List<Pista> pistas;
	private List<PuertaEmbarque> puertasList, puertasList2;
	private Temporada temporada1, temporada2;
	private List<Temporada> temporadas;
	private Exception excepcion;
	
	@BeforeEach
	void setUp() { 
		aeropuertoPropio = new AeropuertoPropio("Barajas", "MAD", "Madrid", "España", 64.9, ZoneOffset.UTC, 2, DireccionRelativaCiudad.ESTE);
		
		puertas = new HashSet<>();
		puertas2 = new HashSet<>();
		puertasList = new ArrayList<>();
		puertasList2 = new ArrayList<>();
		p1 = new PuertaEmbarque();
		p2 = new PuertaEmbarque();
		p3= new PuertaEmbarque();
		p4= new PuertaEmbarque();
				
		puertas.add(p1);
		puertas.add(p2);
		puertas2.add(p3);
		puertas2.add(p4);
		puertasList.add(p1);
		puertasList.add(p3);
		puertasList2.add(p2);
		puertasList2.add(p4);
		
		
		finger1 = new Finger(11.2,45.8, puertas);
		finger2 = new Finger(13.7, 23.7, puertas2);
		fingers = new ArrayList<>();
		fingers.add(finger1);
		fingers.add(finger2);
		
		hangar1 = new HangarPasajeros(20, 34.5, 54.8, 60.9, 4.7);
		hangar2 = new HangarPasajeros(25, 54.5, 59.8, 90.9, 4.2);
		hangares = new ArrayList<>();
		hangares.add(hangar1);
		hangares.add(hangar2);
		
		zona1 = new ZonaAparcamiento(2.5, 7.9, 6.5);
		zona2 = new ZonaAparcamiento(3.5, 6.9, 8.5);
		zonas = new ArrayList<>();
		zonas.add(zona1);
		zonas.add(zona2);
		
		terminal1 = new TerminalPasajeros("T1", puertasList, 100);
		terminal2 = new TerminalPasajeros("T2", puertasList2, 150);
		terminales = new ArrayList<>();
		terminales.add(terminal1);
		terminales.add(terminal2);
		
		aerolinea1 = new Aerolinea("IBERIA", "IBE1");
		aerolinea2 = new Aerolinea("RYANAIR", "RYN1");
		aerolineas = new ArrayList<>();
		aerolineas.add(aerolinea1);
		aerolineas.add(aerolinea2);
		
		sistema = new AirportManager();
		gestor = new Gestor("Lorena", "Lore76", aeropuertoPropio, sistema);
		controlador = new ControladorAereo("Juan Pedro", "56872372J", "Juanpe44", aeropuertoPropio, gestor, terminal1, sistema);
		operador = new Operador("Miguel", "56098976M", aerolinea1, aeropuertoPropio, gestor, sistema);
		usuarios = new ArrayList<>();
		usuarios.add(gestor);
		usuarios.add(operador);
		usuarios.add(controlador);
		
		avion = new Avion("AIRBUS", "AIR7", 87.0, 67.9, 54.2, 900000, 2021, new Date(), "PSJWU78");
		vuelo1 = new Vuelo("V123", LocalDate.of(2023, 5, 12), null, null, LocalTime.of(7, 30), null, TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avion, aeropuertoDestino1, aeropuertoPropio, null, operador);
		vuelo2 = new Vuelo("V132", LocalDate.of(2024, 5, 12), null, null, LocalTime.of(10, 30), null, TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO, Periodicidad.PUNTUAL, TipoVuelo.LLEGADA, avion, aeropuertoDestino1, aeropuertoPropio, null, operador);
		vuelos = new ArrayList<>();
		vuelos.add(vuelo1);
		vuelos.add(vuelo2);
		
		aeropuertoDestino1 = new AeropuertoDestino("Hearthrow", "LHR", "Londres", "Reino Unido", 56.9, ZoneOffset.of("+1"), 3, DireccionRelativaCiudad.SUR);
		aeropuertoDestino2 = new AeropuertoDestino("Fuiminio", "FMR", "Roma", "Italia", 56.9, ZoneOffset.of("+1"), 3, DireccionRelativaCiudad.OESTE);
		aeropuertosDestino = new ArrayList<>();
		aeropuertosDestino.add(aeropuertoDestino1);
		aeropuertosDestino.add(aeropuertoDestino2);
		
		pista1 = new Pista("P2", 10, TipoPista.ATERRIZAJE);
		pista2 = new Pista("P1", 20, TipoPista.DESPEGUE);
		pistas = new ArrayList<>();
		pistas.add(pista1);
		pistas.add(pista2);
		
		temporada1 = new Temporada("06/01", "08:00", "22:00", "30/09"); 
		temporada2 = new Temporada("30/09", "08:00", "23:00", "30/12"); 

		temporadas = new ArrayList<>();
		temporadas.add(temporada1);
		temporadas.add(temporada2);
	}

	@Test

	void testGetFingers() {
		assertTrue(aeropuertoPropio.getFingers().isEmpty(), "Error la lista de fingers al principio debería estar vacía");
	}

	@Test
	void testGetHangares() {
		assertTrue(aeropuertoPropio.getHangares().isEmpty(), "Error la lista de hangares al principio debería estar vacía");
	}

	@Test
	void testGetZonasAparcamiento() {
		assertTrue(aeropuertoPropio.getZonasAparcamiento().isEmpty(), "Error la lista de zonas de aparcamiento al principio debería estar vacía");
	}

	@Test
	void testGetTerminales() {
		assertTrue(aeropuertoPropio.getTerminales().isEmpty(), "Error la lista de terminales al principio debería estar vacía");
	}

	@Test
	void testGetUsuarios() {
		assertTrue(aeropuertoPropio.getUsuarios().isEmpty(), "Error la lista de usuarios al principio debería estar vacía");
	}

	@Test
	void testGetPistas() {
		assertTrue(aeropuertoPropio.getPistas().isEmpty(), "Error la lista de pistas al principio debería estar vacía");
	}

	@Test
	void testGetAerolineas() {
		assertTrue(aeropuertoPropio.getAerolineas().isEmpty(), "Error la lista de aerolineas al principio debería estar vacía");
	}

	@Test
	void testGetVuelosAceptados() {
		assertTrue(aeropuertoPropio.getVuelosAceptados().isEmpty(), "Error la lista de vuelos aceptados al principio debería estar vacía");
	}

	@Test
	void testGetAeropuertosDestino() {
		assertTrue(aeropuertoPropio.getAeropuertosDestino().isEmpty(), "Error la lista de aeropuertos de destino al principio debería estar vacía");
	}

	@Test
	void testGetPuertasEmbarque() {
		assertTrue(aeropuertoPropio.getPuertasEmbarque().isEmpty(), "Error la lista de puertas de embarque al principio debería estar vacía");
	}

	@Test
	void testAddFinger() {
		aeropuertoPropio.addFinger(finger1);
		aeropuertoPropio.addFinger(finger2);
		assertEquals(fingers, aeropuertoPropio.getFingers(), "Error la lista de fingers no es correcta");
		
	}

	@Test
	void testAddHangar() {
		aeropuertoPropio.addHangar(hangar1);
		aeropuertoPropio.addHangar(hangar2);
		assertEquals(hangares, aeropuertoPropio.getHangares(), "Error la lista de hangares no es correcta");
	}

	@Test
	void testAddZonaAparcamiento() {
		aeropuertoPropio.addZonaAparcamiento(zona1);
		aeropuertoPropio.addZonaAparcamiento(zona2);
		assertEquals(zonas, aeropuertoPropio.getZonasAparcamiento(), "Error la lista de zonas de aparcamiento no es correcta");
	}

	@Test
	void testAddTerminal() {
		aeropuertoPropio.addTerminal(terminal1);
		aeropuertoPropio.addTerminal(terminal2);
		assertEquals(terminales, aeropuertoPropio.getTerminales(), "Error la lista de terminales no es correcta");
	}

	@Test
	void testAddUsuario() {
		aeropuertoPropio.addUsuario(gestor);
		aeropuertoPropio.addUsuario(operador);
		aeropuertoPropio.addUsuario(controlador);
		assertEquals(usuarios, aeropuertoPropio.getUsuarios(), "Error la lista de usuarios no es correcta");
	}

	@Test
	void testAddPista() {
		aeropuertoPropio.addPista(pista1);
		aeropuertoPropio.addPista(pista2);
		assertEquals(pistas, aeropuertoPropio.getPistas(), "Error la lista de pistas no es correcta");
	}

	@Test
	void testAddAerolinea() {
		aeropuertoPropio.addAerolinea(aerolinea1);
		aeropuertoPropio.addAerolinea(aerolinea2);
		assertEquals(aerolineas, aeropuertoPropio.getAerolineas(), "Error la lista de aerolineas no es correcta");
	}

	@Test
	void testAddVueloAceptado() {
		aeropuertoPropio.addVueloAceptado(vuelo1);
		aeropuertoPropio.addVueloAceptado(vuelo2);
		assertEquals(vuelos, aeropuertoPropio.getVuelosAceptados(), "Error la lista de vuelos no es correcta");
	}

	@Test
	void testAddAeropuertoDestino() {
		aeropuertoPropio.addAeropuertoDestino(aeropuertoDestino1);
		aeropuertoPropio.addAeropuertoDestino(aeropuertoDestino2);
		assertEquals(aeropuertosDestino, aeropuertoPropio.getAeropuertosDestino(), "Error la lista de aeropuertos de destino no es correcta");
	}

	@Test
	void testGetHorasOperacion() {
	}

	@Test
	void testCalcularHorasEntre() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNombre() {
		assertEquals("Barajas", aeropuertoPropio.getNombre(), "Error el nombre no es correcto");
	}

	@Test
	void testSetNombre() {
		aeropuertoPropio.setNombre("Holaquetal");
		assertEquals("Holaquetal", aeropuertoPropio.getNombre(), "Error el nombre no se asignó correctamente");
	}

	@Test
	void testGetCodigo() {
		assertEquals("MAD", aeropuertoPropio.getCodigo(), "Error el código no es correcto");
	}

	@Test
	void testSetCodigo() {
		aeropuertoPropio.setCodigo("PTR");
		assertEquals("PTR", aeropuertoPropio.getCodigo(), "Error el código no se asignó correctamente");
	}

	@Test
	void testGetCiudad() {
		assertEquals("Madrid", aeropuertoPropio.getCiudad(), "Error la ciudad no es correcta");
	}

	@Test
	void testSetCiudad() {
		aeropuertoPropio.setCiudad("París");
		assertEquals("París", aeropuertoPropio.getCiudad(), "Error la ciudad no se asignó correctamente");
	}

	@Test
	void testGetPais() {
		assertEquals("España", aeropuertoPropio.getPais(), "Error el país no es correcta");
	}

	@Test
	void testSetPais() {
		aeropuertoPropio.setPais("Francia");
		assertEquals("Francia", aeropuertoPropio.getPais(), "Error el país no se asignó correctamente");
	}

	@Test
	void testGetDistanciaCiudad() {
		assertEquals(64.9, aeropuertoPropio.getDistanciaCiudad(), "Error la distancia a la ciudad no es correcta");
	}

	@Test
	void testSetDistanciaCiudad() {
		aeropuertoPropio.setDistanciaCiudad(25.9);
		assertEquals(25.9, aeropuertoPropio.getDistanciaCiudad(), "Error la distancia a la ciudad no se asignó correctamente");
	}

	@Test
	void testGetDiferenciaHoraria() {
		assertEquals(ZoneOffset.UTC, aeropuertoPropio.getDiferenciaHoraria(), "Error la diferencia horaria no es correcta");
	}

	@Test
	void testSetDiferenciaHoraria() {
		aeropuertoPropio.setDiferenciaHoraria(ZoneOffset.of("+1"));
		assertEquals(ZoneOffset.of("+1"), aeropuertoPropio.getDiferenciaHoraria(), "Error la diferencia horaria no se asignó correctamente");
	}

	@Test
	void testGetNumTemporadas() {
		assertEquals(2, aeropuertoPropio.getNumTemporadas(), "Error el número de temporadas no es correcto");
	}

	@Test
	void testSetNumTemporadas() {
		aeropuertoPropio.setNumTemporadas(3);
		assertEquals(3, aeropuertoPropio.getNumTemporadas(), "Error el número de temporadas no se asignó correctamente");
	}

	@Test
	void testGetDireccionCiudad() {
		assertEquals(DireccionRelativaCiudad.ESTE, aeropuertoPropio.getDireccionCiudad(), "Error la direccion hacia la ciudad no es correcta");
	}

	@Test
	void testSetDireccionCiudad() {
		aeropuertoPropio.setDireccionCiudad(DireccionRelativaCiudad.SUR);
		assertEquals(DireccionRelativaCiudad.SUR, aeropuertoPropio.getDireccionCiudad(), "Error la dirección hacia la ciudad no se asignó correctamente");
	}

	@Test
	void testGetTemporadas() {
		assertTrue(aeropuertoPropio.getTemporadas().isEmpty(), "Error, al principio la lista de temporadas debería estar vacía");
	}

	@Test
	void testSetTemporadas() {
		aeropuertoPropio.setTemporadas(temporadas);
		assertEquals(temporadas, aeropuertoPropio.getTemporadas(), "Error, la lista de temporadas no coincide");
	}

	@Test
	void testAgregarTemporada() throws TemporadasSolapadasException {
	    
	    aeropuertoPropio.agregarTemporada(temporada1);
	    assertTrue(aeropuertoPropio.getTemporadas().contains(temporada1),
	               "Error: la lista de temporadas debería contener la temporada añadida");

	    TemporadasSolapadasException excepcion = assertThrows(TemporadasSolapadasException.class, () -> {
	        aeropuertoPropio.agregarTemporada(temporada1); 
	    });

	    assertEquals("La nueva temporada se solapa con otra existente", 
	                 excepcion.getMessage(), 
	                 "Error: No se lanzó la excepción esperada.");
	}

	
}
