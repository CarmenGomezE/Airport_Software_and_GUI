package aeropuerto.src.maria;
/**
 * La enumeración CriterioBusqueda representa los distintos criterios para la búsqueda de vuelos.
 * 
 * @author María Pozo, Carmen Gómez, Claudia Municio.
 */
public enum CriterioBusqueda {
    /** Por el código del vuelo*/
    CODIGO,
    /** Por el origen del vuelo*/
    ORIGEN,
    /** Por el destino del vuelo*/
    DESTINO,
    /** Por la fecha del vuelo*/
    FECHA,
    /** Por la hora de salida del vuelo*/
    HORA_SALIDA,
    /** Por la hora de llegada del vuelo*/
    HORA_LLEGADA,
    /** Por la terminal del vuelo*/
    TERMINAL;
}