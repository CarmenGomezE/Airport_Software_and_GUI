/**
 * 
 */
package src.test;


import static org.junit.jupiter.api.Assertions.*;


import java.time.*;
import java.util.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.vuelo.Escala;
import src.airportmanager.vuelo.EstadoVuelo;
import src.airportmanager.vuelo.Periodicidad;
import src.airportmanager.vuelo.TipoCarga;
import src.airportmanager.vuelo.TipoModoEmbarque;
import src.airportmanager.vuelo.TipoVuelo;
import src.airportmanager.vuelo.Vuelo;

import src.airportmanager.AirportManager;
import src.airportmanager.aeropuerto.*;
import src.airportmanager.avion.*;
import src.airportmanager.pista.Pista;
import src.airportmanager.pista.TipoPista;
import src.airportmanager.terminal.Finger;

import src.airportmanager.terminal.PuertaEmbarque;
import src.airportmanager.terminal.TerminalPasajeros;
import src.airportmanager.usuario.ControladorAereo;
import src.airportmanager.usuario.Gestor;
import src.airportmanager.usuario.Operador;


/**
 * 
 */
class VueloTest {

	private Vuelo vuelo, vuelo2;
	private Avion avion, avion2;
	private Calendar calendar;
	private AeropuertoPropio aeropuertoPropio;
	private AeropuertoDestino aeropuertoDestino;
	private Operador operador, operadorSecundario;
	private Aerolinea aerolinea, aerolinea2;
	private Gestor gestor;
	private AirportManager sistema;
	private Finger finger;
	private Set<PuertaEmbarque> puertas;
	private PuertaEmbarque p1, p2;
	private TerminalPasajeros terminal;
	private ControladorAereo controlador;
	private Pista pista;
	private Map<Operador, Double> operadoresEsperados;
	private List<Operador> operadores;
	private Escala escala;
	private List<DayOfWeek> dias;
	private List<PuertaEmbarque> puertasList;
	
	@BeforeEach
	void setUp() {
		
		calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2021);
		calendar.set(Calendar.MONTH, Calendar.MAY);  
		calendar.set(Calendar.DAY_OF_MONTH, 12);
		puertas = new HashSet<>();
		p1 = new PuertaEmbarque();
		p2 = new PuertaEmbarque();
				
		puertas.add(p1);
		puertas.add(p2);
		puertasList = new ArrayList<>();
		puertasList.add(p1);
		puertasList.add(p2);
		
		terminal = new TerminalPasajeros("T1", puertasList, 200);
		
		pista = new Pista("P2", 50, TipoPista.DESPEGUE);
		dias = new ArrayList<DayOfWeek>();
		dias.add(DayOfWeek.MONDAY);
		dias.add(DayOfWeek.WEDNESDAY);
		finger = new Finger(11.2,45.8, puertas);
		avion = new Avion("AIRBUS", "AIR7", 87.0, 67.9, 54.2, 900000, 2021, new Date(), "PSJWU78");
		avion2 = new Avion("BOEING", "BTE3", 65.003, 56.3, 87.4, 800000, 2022, new Date(), "9387438JK");
		aeropuertoPropio = new AeropuertoPropio("Barajas", "MAD", "Madrid", "España", 64.9, ZoneOffset.UTC, 2, DireccionRelativaCiudad.ESTE);
		aeropuertoDestino = new AeropuertoDestino("Hearthrow", "LHR", "Londres", "Reino Unido", 56.9, ZoneOffset.of("+1"), 3, DireccionRelativaCiudad.SUR);
		aerolinea = new Aerolinea("IBERIA", "IB2");
		aerolinea2 = new Aerolinea ("RYANAIR", "RY1");
		sistema = new AirportManager();
		gestor = new Gestor("Juan Pablo", "JuanPa89", aeropuertoPropio, sistema);
		controlador = new ControladorAereo("Santiago", "56537209L", "Santi67", aeropuertoPropio, gestor, terminal, sistema);
		operador = new Operador("Juan Carlos", "567086H", aerolinea, aeropuertoPropio, gestor, sistema);
		operadorSecundario = new Operador("Pablo", "673292Ñ", aerolinea2, aeropuertoPropio, gestor, sistema);
		escala = new Escala(aeropuertoDestino, aeropuertoDestino, LocalTime.of(12, 30), LocalTime.of(13, 30));
		vuelo = new Vuelo("V123", LocalDate.of(2023, 5, 12), null, null, LocalTime.of(7, 30), null, TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avion, aeropuertoDestino, aeropuertoPropio, null, operador);
		vuelo2 = new Vuelo("V231", LocalDate.of(2024, 4, 17), LocalDate.of(2024, 4, 17), LocalDate.of(2024, 9, 17), LocalTime.of(11, 30),dias, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER, Periodicidad.RECURRENTE, TipoVuelo.LLEGADA, avion, aeropuertoDestino, aeropuertoPropio, escala, operador);
		
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#cambiarEstado(src.airportmanager.vuelo.EstadoVuelo)}.
	 */
	@Test
	void testCambiarEstado() {
		
		vuelo.cambiarEstado(EstadoVuelo.AVION_A_HANGAR);
		assertEquals(EstadoVuelo.AVION_A_HANGAR, vuelo.getEstado(), "Error, el vuelo no ha cambiado su estado correctamente");
	}

	
	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#getEstado()}.
	 */
	@Test
	void testGetEstado() {
		assertEquals(EstadoVuelo.ACEPTADO, vuelo.getEstado(), "Error, el vuelo al principio debería tener estado ACEPTADO");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#getRetraso()}.
	 */
	@Test
	void testGetRetraso() {
		assertEquals(0, vuelo.getRetraso(), "Error, el vuelo debería tener retraso de 0");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#addRetraso(int)}.
	 */
	@Test
	void testAddRetraso() {
		vuelo.addRetraso(7);
		assertEquals(7, vuelo.getRetraso(), "Error, el vuelo no ha actualizado el retraso correctamente");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#getHistorialEstadoVuelo()}.
	 */
	@Test
	void testGetHistorialEstadoVuelo() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#getModoEmbarque()}.
	 */
	@Test
	void testGetModoEmbarque() {
		assertEquals(null, vuelo.getModoEmbarque(), "Error al principio no hay modo embarque");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#setModoEmbarque(src.airportmanager.terminal.ModoEmbarque)}.
	 */
	@Test
	void testSetModoEmbarque() {
		vuelo.setModoEmbarque(finger);
		assertEquals(finger, vuelo.getModoEmbarque(), "Error, el modo embarque no se cambio correctamente");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#getPuertaEmbarque()}.
	 */
	@Test
	void testGetPuertaEmbarque() {
		assertEquals(null, vuelo.getPuertaEmbarque(), "Error al principio no hay puertas de embarque asignadas al vuelo");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#setPuertaEmbarque(src.airportmanager.terminal.PuertaEmbarque)}.
	 */
	@Test
	void testSetPuertaEmbarque() {
		vuelo.setPuertaEmbarque(p1);
		assertEquals(p1, vuelo.getPuertaEmbarque(), "Error, la puerta de embarque no se asignó correctamente");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#setTerminal(src.airportmanager.terminal.Terminal)}.
	 */
	@Test
	void testSetTerminal() {
		vuelo.setTerminal(terminal);
		assertEquals(terminal, vuelo.getTerminal(), "Error, la terminal no se asignó correctamente");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#setControlador(src.airportmanager.usuario.ControladorAereo)}.
	 */
	@Test
	void testSetControlador() {
		vuelo.setControlador(controlador);
		assertEquals(controlador, vuelo.getControlador(), "Error, el controlador no se asignó correctamente");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#getTerminal()}.
	 */
	@Test
	void testGetTerminal() {
		assertEquals(null, vuelo.getTerminal(), "Error, al principio no hay terminal asignada");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#getControlador()}.
	 */
	@Test
	void testGetControlador() {
		assertEquals(null, vuelo.getControlador(), "Error, al principio no hay controlador asignado");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#getPista()}.
	 */
	@Test
	void testGetPista() {
		assertEquals(null, vuelo.getPista(), "Error, al principio no hay pista asignada");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#setPista(src.airportmanager.pista.Pista)}.
	 */
	@Test
	void testSetPista() {
		vuelo.setPista(pista);
		assertEquals(pista, vuelo.getPista(), "Error la pista no se asignó correctamente");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#agregarOperadorSecundario(src.airportmanager.usuario.Operador, double)}.
	 */
	@Test
	void testAgregarOperadorSecundario() {
		vuelo.agregarOperadorSecundario(operadorSecundario, 34);
		assertTrue(vuelo.getOperadores().contains(operadorSecundario), "Error, el operador secundario no se asignó correctamente");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#getCargaAsignada(src.airportmanager.usuario.Operador)}.
	 */
	@Test
	void testGetCargaAsignada() {
		 assertEquals(100.0, vuelo.getCargaAsignada(operador), "Error: Debería devolver 100.0.");

		  vuelo.agregarOperadorSecundario(operadorSecundario, 30.5);
		  assertEquals(30.5, vuelo.getCargaAsignada(operadorSecundario), "Error: La carga asignada al operador no es la esperada.");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#getCargaTotalAsignada()}.
	 */
	@Test
	void testGetCargaTotalAsignada() {
		assertEquals(100.0, vuelo.getCargaTotalAsignada(), "Error: Al inicio, la carga total debería ser 0.0.");

	    
	    vuelo.agregarOperadorSecundario(operadorSecundario, 45.0);

	    assertEquals(100.0, vuelo.getCargaTotalAsignada(), "Error: La carga total asignada no es correcta.");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#getOperadorPrincipal()}.
	 */
	@Test
	void testGetOperadorPrincipal() {
		assertEquals(operador, vuelo.getOperadorPrincipal(), "Error, el operador no es correcto");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#getOperadoresAsignados()}.
	 */
	@Test
	void testGetOperadoresAsignados() {
		
		vuelo.agregarOperadorSecundario(operadorSecundario, 23.2);
		
		operadoresEsperados = new HashMap<>();
		
		operadoresEsperados.put(operador, 100.0 - 23.2);
		operadoresEsperados.put(operadorSecundario, 23.2);
		assertEquals(operadoresEsperados, vuelo.getOperadoresAsignados(), "Error: El mapa de operadores asignados no coincide con el esperado.");
		
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#getOperadores()}.
	 */
	@Test
	void testGetOperadores() {
		
		vuelo.agregarOperadorSecundario(operadorSecundario, 23.2);
		operadores = new ArrayList<>();
		operadores.add(operador);
		operadores.add(operadorSecundario);
		assertEquals(operadores, vuelo.getOperadores(), "Error: La lista de operadores asignados no coincide con la esperada.");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.Vuelo#getTiempoEspera()}.
	 */
	@Test
	void testGetTiempoEspera() {
	    
	    assertEquals(EstadoVuelo.ACEPTADO, vuelo.getEstado(), "Error: El estado inicial no es ACEPTADO.");

	    
	    vuelo.cambiarEstado(EstadoVuelo.ESPERANDO_DESPEGUE);
	    assertEquals(EstadoVuelo.ESPERANDO_DESPEGUE, vuelo.getEstado(), "Error: El estado no se cambió correctamente a ESPERANDO_DESPEGUE.");

	    
	    assertEquals(0, vuelo.getTiempoEspera(), "Error: Un vuelo en estado ESPERANDO_DESPEGUE sin hora de cambio debería tener 0 minutos de espera.");

	    
	    LocalTime horaCambio = LocalTime.now().minusMinutes(30);
	    vuelo.getHistorialEstadoVuelo().agregarCambioEstado(EstadoVuelo.ESPERANDO_DESPEGUE, horaCambio);

	    
	    int tiempoEspera = vuelo.getTiempoEspera();
	    System.out.println("Tiempo de espera calculado: " + tiempoEspera); 
	    assertTrue(tiempoEspera >= 29 && tiempoEspera <= 31, 
	        "Error: El tiempo de espera debería ser aproximadamente 30 minutos, pero se obtuvo: " + tiempoEspera);
	}


	/**
	 * Test method for {@link src.airportmanager.vuelo.VueloProgramado#getAeropuertoDestino()}.
	 */
	@Test
	void testGetAeropuertoDestino() {
		assertEquals(aeropuertoDestino, vuelo.getAeropuertoDestino(), "Error el aeropuerto de destino no es correcto");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.VueloProgramado#getAeropuertoSalida()}.
	 */
	@Test
	void testGetAeropuertoSalida() {
		assertEquals(aeropuertoPropio, vuelo.getAeropuertoSalida(), "Error el aeropuerto de salida no es correcto");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.VueloProgramado#getFechaInicio()}.
	 */
	@Test
	void testGetFechaInicio() {
		assertEquals(LocalDate.of(2024, 4, 17), vuelo2.getFechaInicio(), "Error, la fecha inicial no es correcta");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.VueloProgramado#getFechaFinal()}.
	 */
	@Test
	void testGetFechaFinal() {
		assertEquals(LocalDate.of(2024, 9, 17), vuelo2.getFechaFinal(), "Error la fecha final no es correcta");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.VueloProgramado#getCodigo()}.
	 */
	@Test
	void testGetCodigo() {
		assertEquals("V123", vuelo.getCodigo(), "Error, el código no es correcto");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.VueloProgramado#getFecha()}.
	 */
	@Test
	void testGetFecha() {
		assertEquals(LocalDate.of(2023, 5, 12), vuelo.getFecha(), "Error, la fecha no es correcta");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.VueloProgramado#getHora()}.
	 */
	@Test
	void testGetHora() {
		assertEquals(LocalTime.of(7, 30), vuelo.getHora(), "Error, la hora no es correcta");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.VueloProgramado#getDiasRecurrente()}.
	 */
	@Test
	void testGetDiasRecurrente() {
		assertNotNull(vuelo2.getDiasRecurrente(), "Error: La lista de días recurrentes es null");
		assertEquals(dias, vuelo2.getDiasRecurrente(), "Error la lista de días recurrente no es correcta");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.VueloProgramado#getTipoCarga()}.
	 */
	@Test
	void testGetTipoCarga() {
		assertEquals(TipoCarga.PASAJEROS, vuelo.getTipoCarga(), "Error, el tipo de carga no es correcta");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.VueloProgramado#getTipoModoEmbarque()}.
	 */
	@Test
	void testGetTipoModoEmbarque() {
		assertEquals(TipoModoEmbarque.ZONA_DE_APARCAMIENTO, vuelo.getTipoModoEmbarque(), "Error, el tipo de modo de embarque no es correcto");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.VueloProgramado#getPeriodicidad()}.
	 */
	@Test
	void testGetPeriodicidad() {
		assertEquals(Periodicidad.PUNTUAL, vuelo.getPeriodicidad(), "Error, la periodicidad no es correcta");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.VueloProgramado#getTipoVuelo()}.
	 */
	@Test
	void testGetTipoVuelo() {
		assertEquals(TipoVuelo.SALIDA, vuelo.getTipoVuelo(), "Error, el tipo de vuelo no es correcto");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.VueloProgramado#getAvion()}.
	 */
	@Test
	void testGetAvion() {
		assertEquals(avion, vuelo.getAvion(), "Error, el avión no coincide");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.VueloProgramado#setHora(java.time.LocalTime)}.
	 */
	@Test
	void testSetHora() {
		vuelo.setHora(LocalTime.of(12, 45));
		assertEquals(LocalTime.of(12, 45), vuelo.getHora(), "Error la hora no se asignó correctamente");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.VueloProgramado#setAvion(src.airportmanager.avion.Avion)}.
	 */
	@Test
	void testSetAvion() {
		vuelo.setAvion(avion2);
		assertEquals(avion2, vuelo.getAvion(), "Error, el avión no se asignó correctamente");
		
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.VueloProgramado#setFecha(java.time.LocalDate)}.
	 */
	@Test
	void testSetFecha() {
		vuelo.setFecha(LocalDate.of(2024, 4, 17));
		assertEquals(LocalDate.of(2024, 4, 17), vuelo.getFecha(), "Error la fecha no se asignó correctamente");
	}

	/**
	 * Test method for {@link src.airportmanager.vuelo.VueloProgramado#getEscala()}.
	 */
	@Test
	void testGetEscala() {
		assertEquals(escala, vuelo2.getEscala(), "Error, la escala no coincide");
	}

}
