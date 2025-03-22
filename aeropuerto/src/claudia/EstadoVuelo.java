package aeropuerto.src.claudia;
import java.util.*;

/**
 * La enumeración EstadoVuelo define en que estado se encuentra un vuelo.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public enum EstadoVuelo{
    /**El vuelo se encuentra en preparación para despegar */
    EN_PREPARACION_PARA_DESPEGAR(null),
    /**El vuelo se encuentra en inicio del embarque */
    INICIO_DEL_EMBARQUE(null),
    /**El vuelo se encuentra en fin del embarque */
    FIN_DEL_EMBARQUE(null),
    /**El vuelo se encuentra en inicio del desembarque */
    INICIO_DEL_DESEMBARQUE(null),
    /**El vuelo se encuentra en fin del desembarque */
    FIN_DEL_DESEMBARQUE(null),
    /**El vuelo se encuentra esperando el despegue */
    ESPERANDO_DESPEGUE(null),
    /**El vuelo se encuentra esperando pista de despegue */
    ESPERANDO_PISTA_DE_DESPEGUE(null),
    /**El vuelo se encuentra esperando pista de aterrizaje */
    ESPERANDO_PISTA_DE_ATERRIZAJE(null),
    /**El vuelo se encuentra esperando el aterrizaje */
    ESPERANDO_ATERRIZAJE(null),
    /**El vuelo se encuentra en aterrizaje */
    ATERRIZAJE(null),
    /**El vuelo se va a dirigir a hangar */
    AVION_A_HANGAR(null),
    /**El vuelo se encuentra operativo */
    OPERATIVO(null),
    /**El vuelo está a tiempo*/
    A_TIEMPO(null),
    /**El vuelo está retrasado */
    RETRASADO(null),
    /**El vuelo se encuentra en vuelo */
    VUELO(null);

    /**La hora de cambio de estado */
    private LocalTime horaCambio;

    /**
     * Constructor de la enumeración EstadoVuelo.
     * @param horaCambio        Hora de cambio de estado.
     */
    public EstadoVuelo(LocalTime horaCambio) {
        this.horaCambio = horaCambio;
    }

    /**
     * Método que devuelve la hora de cambio de estado de un vuelo.
     * @return                  Hora de cambio de estado de un vuelo.
     */
    public LocalTime getHoraCambio() {
        return this.horaCambio;
    }
}