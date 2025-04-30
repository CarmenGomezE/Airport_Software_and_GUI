package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.app.AirportManager;
import src.airportmanager.app.aeropuerto.*;
import src.airportmanager.app.avion.*;
import src.airportmanager.app.exceptions.*;
import src.airportmanager.terminal.*;
import src.airportmanager.hangar.*;
import src.airportmanager.notificacion.Notificacion;
import src.airportmanager.notificacion.TipoNotificacion;
import src.airportmanager.pista.Pista;
import src.airportmanager.pista.TipoPista;
import src.airportmanager.usuario.*;
import src.airportmanager.vuelo.*;

class OperadorTest {

    private AeropuertoPropio aeropuertoPropio;
    private HangarPasajeros hangar1, hangar2;
    private ControladorAereo controlador;
    private Operador operador, operadorPrincipal;
    private Gestor gestor;
    private TerminalPasajeros terminal1, terminal2;
    private Aerolinea aerolinea1, aerolinea2;
    private VueloProgramado vueloProgramado1;
    private Vuelo vuelo1, vuelo2;
    private AeropuertoDestino aeropuertoDestino1, aeropuertoDestino2;
    private PuertaEmbarque p1, p2, p3, p4;
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
    private Notificacion notificacion;
    private AvionPasajeros avionPasajeros;

    @BeforeEach
    void setUp() throws FormatoDniIncorrectoException {
        aeropuertoPropio = new AeropuertoPropio("Barajas", "MAD", "Madrid", "España", 64.9, ZoneOffset.UTC, 2,
                DireccionRelativaCiudad.ESTE);

        puertasList = new ArrayList<>();
        puertasList2 = new ArrayList<>();
        p1 = new PuertaEmbarque();
        p2 = new PuertaEmbarque();
        p3 = new PuertaEmbarque();
        p4 = new PuertaEmbarque();

        puertasList.add(p1);
        puertasList.add(p3);
        puertasList2.add(p2);
        puertasList2.add(p4);

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
        controlador = new ControladorAereo("Juan Pedro", "56872372J", "Juanpe44", aeropuertoPropio, gestor, terminal1,
                sistema);
        operador = new Operador("Miguel", "56098976M", aerolinea1, aeropuertoPropio, gestor, sistema);
        aerolinea1.setOperador(operador);
        operadorPrincipal = new Operador("Principal", "12345678A", aerolinea1, aeropuertoPropio, gestor, sistema);
        usuarios = new ArrayList<>();
        usuarios.add(gestor);
        usuarios.add(operador);
        usuarios.add(controlador);

        avion = new AvionPasajeros("AIRBUS", "AIR7", 87.0, 67.9, 54.2, 900000, 2021, new Date(), "PSJWU78", 0);
        aerolinea1.agregarAvion(avion);
        aeropuertoDestino1 = new AeropuertoDestino("Hearthrow", "LHR", "Londres", "Reino Unido", 56.9,
                ZoneOffset.of("+1"), 3, DireccionRelativaCiudad.SUR);

        vueloProgramado1 = new VueloProgramado("V123", LocalDate.of(2023, 5, 12), null, null, LocalTime.of(7, 30),
                null, TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO, Periodicidad.PUNTUAL,
                TipoVuelo.SALIDA, avion, aeropuertoDestino1, aeropuertoPropio, null);

        vuelo1 = new Vuelo("V123", LocalDate.of(2023, 5, 12), null, null, LocalTime.of(7, 30),
                null, TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO, Periodicidad.PUNTUAL,
                TipoVuelo.SALIDA, avion, aeropuertoDestino1, aeropuertoPropio, null, operadorPrincipal);

        vuelo2 = new Vuelo("V132", LocalDate.of(2024, 5, 12), null, null, LocalTime.of(10, 30),
                null, TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO, Periodicidad.PUNTUAL,
                TipoVuelo.LLEGADA, avion, aeropuertoDestino1, aeropuertoPropio, null, operadorPrincipal);

        vuelos = new ArrayList<>();
        vuelos.add(vuelo1);
        vuelos.add(vuelo2);

        aeropuertoDestino2 = new AeropuertoDestino("Fuiminio", "FMR", "Roma", "Italia", 56.9, ZoneOffset.of("+1"), 3,
                DireccionRelativaCiudad.OESTE);
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
        notificacion = new Notificacion("Cambio estado de vuelo", TipoNotificacion.CAMBIO_ESTADO_VUELO);
        avionPasajeros = new AvionPasajeros("AIRBUS", "AIR7", 87.0, 67.9, 54.2, 900000, 2021, new Date(), "PSJWU78",
                150);
    }

    @Test
    void testRecibirNotificacion() {
        operador.recibirNotificacion(notificacion);
        assertTrue(operador.getNotificaciones().contains(notificacion), "La notificación no se recibió correctamente");
    }

    @Test
    void testGetGestor() {
        assertEquals(gestor, operador.getGestor(), "El gestor no es correcto");
    }

    @Test
    void testCambiarEstadoVuelo()
            throws EstadoEquivocadoException, VueloNoDelUsuarioException, EstadoNoDelUsuarioException {
        avion.setAerolinea(aerolinea1);
        vuelo1.setTerminal(terminal1);
        vuelo1.cambiarEstado(EstadoVuelo.OPERATIVO);

        assertDoesNotThrow(
                () -> operadorPrincipal.cambiarEstadoVuelo(vuelo1, EstadoVuelo.EN_PREPARACION_PARA_DESPEGAR));
        assertEquals(EstadoVuelo.EN_PREPARACION_PARA_DESPEGAR, vuelo1.getEstado());
    }

    @Test
    void testGetAerolinea() {
        assertEquals(aerolinea1, operador.getAerolinea(), "La aerolinea no es correcta");
    }

    @Test
    void testDarDeAltaAvionPasajeros() {
        operador.darDeAltaAvionPasajeros("AIRBUS", "AIR7", 87.0, 67.9, 54.2, 900000, 2021, new Date(), "PSJWU78", 150);

        Avion avionRegistrado = aerolinea1.getAviones()
                .stream()
                .filter(a -> a.getMatricula().equals("PSJWU78"))
                .findFirst()
                .orElse(null);

        assertNotNull(avionRegistrado, "No se ha dado de alta correctamente el avión");
    }

    @Test
    void testDarDeAltaAvionMercancias() {
        operador.darDeAltaAvionMercancias("AIRBUS", "AIR7", 87.0, 67.9, 54.2, 900000, 2021, new Date(), "PSJWU78",
                250.60, false, true);
        Avion avionRegistrado = aerolinea1.getAviones()
                .stream()
                .filter(a -> a.getMatricula().equals("PSJWU78"))
                .findFirst()
                .orElse(null);
        assertNotNull(avionRegistrado, "No se ha dado de alta correctamente el avión");
    }

    @Test
    void testProponerVuelo() throws AvionNoDisponibleException, AvionNoValidoException, EscalaInvalidaException,
            AntelacionInvalidaException {
        LocalDate fechaFutura = LocalDate.now().plusDays(60);
        LocalTime hora = LocalTime.of(12, 30);

        Avion avionLibre = avionPasajeros;
        avionLibre.setAerolinea(aerolinea1);

        assertDoesNotThrow(() -> {
            operador.proponerVuelo(
                    "V456", fechaFutura, null, null, hora,
                    null, TipoCarga.PASAJEROS, TipoModoEmbarque.ZONA_DE_APARCAMIENTO,
                    Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avionLibre,
                    aeropuertoDestino1, aeropuertoPropio, null);
        });
    }

    @Test
    void testGetAlternativasAgrupadas() {
        assertNotNull(operador.getAlternativasAgrupadas());
        assertTrue(operador.getAlternativasAgrupadas().isEmpty());
    }

    @Test
    void testRecibirAlternativas() {
        List<Vuelo> alternativas = new ArrayList<>();
        alternativas.add(vuelo1);
        alternativas.add(vuelo2);

        operador.recibirAlternativas(vueloProgramado1, alternativas);

        assertTrue(operador.getAlternativasAgrupadas().containsKey(vueloProgramado1));
        assertEquals(2, operador.getAlternativas(vueloProgramado1).size());
    }

    @Test
    void testAceptarVuelo() {
        avion.setAerolinea(aerolinea1);
        vuelo1.setTerminal(terminal1);
        vuelo1.setControlador(controlador);

        operador.recibirAlternativas(vueloProgramado1, List.of(vuelo1));

        assertDoesNotThrow(() -> operador.aceptarVuelo(vuelo1));
        assertTrue(operador.getDecisionTomada().contains(vueloProgramado1));
    }

    @Test
    void testRechazarVuelo() {
        operador.recibirAlternativas(vueloProgramado1, List.of(vuelo1));
        operador.rechazarVuelo(vueloProgramado1);

        assertTrue(operador.getDecisionTomada().contains(vueloProgramado1));
    }

    @Test
    void testSolicitarCompartirVuelo() throws OperacionInvalidaException, SolicitudInvalidaExcepcion {
        Operador operadorReceptor = operadorPrincipal;
        Operador operadorSolicitante = this.operador;

        vuelo1.getOperadoresAsignados().clear();

        assertDoesNotThrow(() -> {
            operadorSolicitante.solicitarCompartirVuelo(vuelo1, operadorReceptor, 30.0);
        });

        assertEquals(1, operadorReceptor.getSolicitudesRecibidas().size());
    }

    @Test
    void testRecibirSolicitud() {
        Operador operadorReceptor;
        try {
            operadorReceptor = new Operador("Receptor", "11223344B", aerolinea1, aeropuertoPropio, gestor, sistema);

            SolicitudVueloCompartido solicitud = new SolicitudVueloCompartido(vuelo1, operador, operadorReceptor, 30.0);

            operadorReceptor.recibirSolicitud(solicitud);

            assertTrue(operadorReceptor.getSolicitudesRecibidas().contains(solicitud));
        } catch (FormatoDniIncorrectoException e) {
            fail();
        }
    }

    @Test
    void testAceptarSolicitud() {
        Operador operadorPrincipal = this.operadorPrincipal;
        Operador operadorSolicitante = this.operador;

        SolicitudVueloCompartido solicitud = new SolicitudVueloCompartido(vuelo1, operadorSolicitante,
                operadorPrincipal, 30.0);

        operadorPrincipal.recibirSolicitud(solicitud);

        assertDoesNotThrow(() -> operadorPrincipal.aceptarSolicitud(solicitud));
        assertEquals(EstadoSolicitud.ACEPTADA, solicitud.getEstadoSolicitud());
    }

    @Test
    void testRechazarSolicitud() {
        Operador operadorPrincipal = this.operadorPrincipal;
        SolicitudVueloCompartido solicitud = new SolicitudVueloCompartido(vuelo1, operador, operadorPrincipal, 30.0);

        operadorPrincipal.recibirSolicitud(solicitud);
        operadorPrincipal.rechazarSolicitud(solicitud);

        assertEquals(EstadoSolicitud.RECHAZADA, solicitud.getEstadoSolicitud());
    }

    @Test
    void testGetDni() {
        assertEquals("56098976M", operador.getDni());
    }

    @Test
    void testGetEstadisticasOperador() {
        assertNotNull(operador.getEstadisticasOperador());
    }

    @Test
    void testGetSolicitudesRecibidas() {
        assertNotNull(operador.getSolicitudesRecibidas());
        assertTrue(operador.getSolicitudesRecibidas().isEmpty());
    }

    @Test
    void testBuscarSolicitudPorCodigo() {
        SolicitudVueloCompartido solicitud = new SolicitudVueloCompartido(vuelo1, operador, operadorPrincipal, 30.0);
        operador.recibirSolicitud(solicitud);

        assertEquals(solicitud, operador.buscarSolicitudPorCodigo("V123"));
        assertNull(operador.buscarSolicitudPorCodigo("INVALIDO"));
    }

    @Test
    void testRecibirNotificacionCambioEstado() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        vuelo1.getOperadores().add(operador);
        operadorPrincipal.recibirNotificacionCambioEstado(vuelo1);

        assertTrue(outContent.toString().contains("Nueva notificación:"));
    }
}