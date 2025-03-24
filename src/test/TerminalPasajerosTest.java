package src.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.ZoneOffset;
import java.util.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.AirportManager;
import src.airportmanager.aeropuerto.AeropuertoPropio;
import src.airportmanager.aeropuerto.DireccionRelativaCiudad;
import src.airportmanager.terminal.*;
import src.airportmanager.usuario.*;

public class TerminalPasajerosTest {

    TerminalPasajeros terminal;
    PuertaEmbarque puerta, puerta2;
    List<PuertaEmbarque> puertas;
    List<ControladorAereo> controladores;
    ControladorAereo controlador;
    AeropuertoPropio aeropuertoPropio;
    ZoneOffset zone;
    Gestor gestor;
    AirportManager sistema;

    @BeforeEach
    void setUp() {

        zone = ZoneOffset.ofHours((+1));
        puerta = new PuertaEmbarque();
        terminal = new TerminalPasajeros("T1", puerta, 100);
        aeropuertoPropio = new AeropuertoPropio("Barajas", "MAD", "Madrid", "España", 35.0, zone, 3, DireccionRelativaCiudad.ESTE);
        sistema = new AirportManager();
        gestor = new Gestor("Pedro", "Pedrito10", aeropuertoPropio, sistema);
        controlador = new ControladorAereo("Juan", "5638746T", "juanito87", aeropuertoPropio, gestor, terminal, sistema);
    }

    @Test 
    void testGetNumeroMaxAviones() {
        assertEquals(100, terminal.getNumeroMaxAviones(), "Error, el número máximo de pasajeros no es correcto");
    }

    @Test 
    void testGetCodigo() {
        assertEquals("T1", terminal.getCodigo(), "Error el código de la terminal no es correcto");
    }

    @Test
    void testGetPuertasEmbarque() {
        assertTrue(terminal.getPuertasEmbarque().contains(puerta), "Error, la terminal debería contener la puerta de embarque asignada.");
    }

    @Test
    void testAddPuertasEmbarque() {

        puerta2 = new PuertaEmbarque();
        terminal.addPuertaEmbarque(puerta2);
        puertas = new ArrayList<>();
        puertas.add(puerta);
        puertas.add(puerta2);

        assertTrue(terminal.getPuertasEmbarque().containsAll(puertas), "Error, la terminal debería contener ambas puertas de embarque.");
    }

    @Test
    void testGetControladores() {
        assertTrue(terminal.getControladores().isEmpty(), "Error: al principio la lista de controladores debería estar vacía.");
    }
    
    @Test
    void testAddControladores() {

        terminal.agregarControlador(controlador);
        controladores = new ArrayList<>();
        controladores.add(controlador);
        assertTrue(terminal.getControladores().contains(controlador), "Error, la terminal debería contener al controlador agregado.");
        
    }
}
