package src.test;

import org.junit.jupiter.api.BeforeEach;

import src.airportmanager.terminal.*;

public class TerminalPasajerosTest {

    TerminalPasajeros terminal;
    PuertaEmbarque puerta;

    @BeforeEach
    void setUp() {

        puerta = new PuertaEmbarque();
        terminal = new TerminalPasajeros("T1", puerta, 100);
    }
    
}
