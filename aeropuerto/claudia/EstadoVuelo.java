package aeropuerto.claudia;

/**
 * La enumeración EstadoVuelo define en que estado se encuentra un vuelo.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public enum EstadoVuelo{
    EN_PREPARACION_PARA_DESPEGAR,
    INICIO_DEL_EMBARQUE,
    FIN_DEL_EMBARQUE,
    INICIO_DEL_DESEMBARQUE,
    FIN_DEL_DESEMBARQUE,
    ESPERANDO_DESPEGUE,
    ESPERANDO_PISTA_DE_DESPEGUE,
    ESPERANDO_PISTA_DE_ATERRIZAJE,
    ATERRIZAJE,
    AVION_A_HANGAR,
    OPERATIVO,
    A_TIEMPO,
    RETRASADO,
    VUELO;

    private LocalTime horaCambio;
}