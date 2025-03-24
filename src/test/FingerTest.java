package aeropuerto.src.claudia;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aeropuerto.src.carmen.*;
import aeropuerto.src.claudia.*;
import aeropuerto.src.maria.*;
import aeropuerto.src.carmen.vuelo.*;
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
        })

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
        otroFinger = new HashSet<>("F456", 60.0, 4.0, new HashSet<>(Arrays.asList(new PuertaEmbarque())));
        assertNotEquals(finger.getId(), otroFinger.getId(), "Error, los id de los fingers deberían ser diferentes");
    }

    /**AQUI TENGO QUE CAMBIAR COSAS */
   @Test
    void testComprobarTamano() {
        Avion avionPequeno;
        Vuelo vueloPequeno;
        LocalDate fechaVuelo = LocalDate.of(2024, 3, 22);
        LocalTime horaVuelo = LocalTime.of(10, 15);

        /**Crear fecha de última revisión del avión*/
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 1); 
        Date fechaRevision = calendar.getTime();

        /**Crear el avión pequeño */
        avionPequeno = new AvionPasajeros("Airbus", "A320", 4.5, 35.8, 37.6, 6100.0, 2018, fechaRevision, "EC-XYZ", 180);

        /**Crear aeropuertos */
        AeropuertoPropio aeropuertoSalida = new AeropuertoPropio("Aeropuerto Madrid", "MAD", "Madrid", "España", 12.5, ZoneOffset.of("+1"), 2, DireccionRelativaCiudad.SUR);
        AeropuertoDestino aeropuertoDestino = new AeropuertoDestino("Aeropuerto París", "CDG", "París", "Francia", 18.0, ZoneOffset.of("+1"), 2, DireccionRelativaCiudad.NORTE);

        /**Crear operador, necesita aerolinea y gestor */
        Aerolinea aerolinea = new Aerolinea("Iberia", "IB123");
        Gestor gestor = new Gestor("Carlos López", "admin123", aeropuertoSalida);
        Operador operador = new Operador("Juan Pérez", "12345678A", aerolinea, aeropuertoSalida, gestor);

       
        vueloPequeno = new Vuelo("V123", fechaVuelo, null, null, horaVuelo, null, 
            TipoCarga.PASAJEROS, TipoModoEmbarque.FINGER, Periodicidad.PUNTUAL, TipoVuelo.SALIDA, 
            avionPequeno, aeropuertoDestino, aeropuertoSalida, null, operador);

        assertTrue(finger.comprobarTamano(vueloPequeno), "ERROR: El avión pequeño debería caber en el Finger.");
}


}