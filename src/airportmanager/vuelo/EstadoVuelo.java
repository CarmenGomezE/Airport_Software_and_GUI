package src.airportmanager.vuelo;

/**
 * La enumeración EstadoVuelo define en que estado se encuentra un vuelo.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public enum EstadoVuelo {
    /** El vuelo se encuentra aceptado */
    ACEPTADO,
    /** El vuelo se encuentra en preparación para despegar */
    EN_PREPARACION_PARA_DESPEGAR,
    /** El vuelo se encuentra en inicio del embarque */
    INICIO_DEL_EMBARQUE,
    /** El vuelo se encuentra en fin del embarque */
    FIN_DEL_EMBARQUE,
    /** El vuelo se encuentra en inicio del desembarque */
    INICIO_DEL_DESEMBARQUE,
    /** El vuelo se encuentra en fin del desembarque */
    FIN_DEL_DESEMBARQUE,
    /** El vuelo se encuentra esperando el despegue */
    ESPERANDO_DESPEGUE,
    /** El vuelo se encuentra esperando pista de despegue */
    ESPERANDO_PISTA_DE_DESPEGUE,
    /** El vuelo se encuentra esperando pista de aterrizaje */
    ESPERANDO_PISTA_DE_ATERRIZAJE,
    /** El vuelo se encuentra esperando el aterrizaje */
    ESPERANDO_ATERRIZAJE,
    /** El vuelo se encuentra en aterrizaje */
    ATERRIZAJE,
    /** El vuelo se va a dirigir a hangar */
    AVION_A_HANGAR,
    /** El vuelo se encuentra operativo */
    OPERATIVO,
    /** El vuelo está a tiempo */
    A_TIEMPO,
    /** El vuelo está retrasado */
    RETRASADO,
    /** El vuelo se encuentra en vuelo */
    VUELO;

}