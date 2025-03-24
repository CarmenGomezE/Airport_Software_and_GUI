package src.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.airportmanager.*;
import src.airportmanager.avion.*;
import src.airportmanager.terminal.*;
import src.airportmanager.vuelo.*;
import src.airportmanager.aeropuerto.*;
import src.airportmanager.usuario.*;
import java.time.*;
import java.util.*;



class FingerTest{

    Finger finger;
    PuertaEmbarque puerta1, puerta2;
    Set puertas;

    @BeforeEach
    void setUp() {
        
        puerta1 = new PuertaEmbarque();
        puerta2 = new PuertaEmbarque();

        puertas = new HashSet<>();
        puertas.add(puerta1);
        puertas.add(puerta2);

        finger = new Finger("F123", 50.0, 5.0, puertas);

    }

    @Test
    void testGetDimAlto() {
        assertEquals(5.0, finger.getDimAlto(), "Error, el método getDimAlto() no devuelve el valor esperado.");
    }

    @Test
    void testGetPuertasEmbarque() {

        HashSet puertasEsperadas = new HashSet<>();
        PuertaEmbarque p1, p2;

        p1 = new PuertaEmbarque();
        p2 = new PuertaEmbarque();
        puertasEsperadas.add(p1);
        puertasEsperadas.add(p2);

        assertEquals(puertasEsperadas, finger.getPuertasEmbarque(), "Error, las puertas de embarque no coinciden.");
        
    }

    @Test
    void testGetHorasUso() {
        assertEquals(0.0, finger.getHorasUso(), "Error, al principio el finger debería tener 0 horas de uso");
    }

    @Test
    void testAddHorasUso(){
        finger.addHorasUso(3.5);
        assertEquals(3.5, finger.getHorasUso(), "Error, las horas de uso no se actualizaron correctamente");

        finger.addHorasUso(2.0);
        assertEquals(5.5, finger.getHorasUso(), "Error, la suma de horas de uso no es correcta");
    }

    @Test
    void testAddHorasUsoNegativo() {
        Exception excepcion = assertThrows(IllegalArgumentException.class, ()->{
            finger.addHorasUso(-2.0);
        });

        assertEquals("Las horas deben ser positivas", excepcion.getMessage(), "Error, no se lanzó la excepción esperada");
    }

    @Test
    void testHorasUsoReset() {
        finger.addHorasUso(5.0);
        finger.horasUsoReset();
        assertEquals(0, finger.getHorasUso(), "Error, las horas de uso del finger no se resetearon correctamente");
    }

    @Test
    void testGetId() {
        Finger otroFinger;
        PuertaEmbarque p1, p2;
        Set p;
        p1 = new PuertaEmbarque();
        p2 = new PuertaEmbarque();

        p = new HashSet<>();
        p.add(p1);
        p.add(p2);

        otroFinger = new Finger("F124", 40.0, 4.0, p);
        assertNotEquals(finger.getId(), otroFinger.getId(), "Error, los id de los fingers deberían ser diferentes");
    }

    /**AQUI TENGO QUE CAMBIAR COSAS */
   @Test
    void testComprobarTamano() {

        Vuelo vueloPequeno;
        LocalDate fecha;
        LocalTime hora;
        Date revision;
        Calendar calendar;
        Avion avion;
        AeropuertoDestino aeropuertoDestino;
        AeropuertoPropio aeropuertoPropio;
        ZoneOffset zone;

        fecha = LocalDate.of(2024, 03, 22);
        hora = LocalTime.of(9,30, 0);
        calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 22);
        revision = calendar.getTime();
        zone = ZoneOffset.of("+1");

        aeropuertoPropio = new AeropuertoPropio("barajas", , null, null, 0, zone, 0, null)
        aeropuertoDestino = new AeropuertoDestino("Fuimicino", "FCO", "Roma", "Italia", 30.0, zone, 2, DireccionRelativaCiudad.NORTE);
        avion = new Avion("AIRBUS", "C42", 50.00, 50.0, 50.0, 60.000, 2020, revision, "JLN2000");
        vueloPequeno = new Vuelo("V123", fecha, null, null, hora, null, TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, avion, aeropuertoDestino, null, null, null);

    

        
}


}