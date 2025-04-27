package src.airportmanager.vuelo;

/**
 * La enumeración EstadoVuelo define en que estado se encuentra un vuelo.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public enum EstadoVuelo {
    /** El vuelo se encuentra aceptado */
    ACEPTADO(true),
    /** El vuelo se encuentra en preparación para despegar */
    EN_PREPARACION_PARA_DESPEGAR(false),
    /** El vuelo se encuentra en inicio del embarque */
    INICIO_DEL_EMBARQUE(false),
    /** El vuelo se encuentra en fin del embarque */
    FIN_DEL_EMBARQUE(false),
    /** El vuelo se encuentra en inicio del desembarque */
    INICIO_DEL_DESEMBARQUE(false),
    /** El vuelo se encuentra en fin del desembarque */
    FIN_DEL_DESEMBARQUE(false),
    /** El vuelo se encuentra esperando el despegue */
    ESPERANDO_DESPEGUE(false),
    /** El vuelo se encuentra esperando pista de despegue */
    ESPERANDO_PISTA_DE_DESPEGUE(false),
    /** El vuelo se encuentra esperando pista de aterrizaje */
    ESPERANDO_PISTA_DE_ATERRIZAJE(false),
    /** El vuelo se encuentra esperando el aterrizaje */
    ESPERANDO_ATERRIZAJE(false),
    /** El vuelo se encuentra en aterrizaje */
    ATERRIZAJE(false),
    /** El vuelo se va a dirigir a hangar */
    AVION_A_HANGAR(false),
    /** El vuelo se encuentra operativo */
    OPERATIVO(false),
    /** El vuelo está a tiempo */
    A_TIEMPO(true),
    /** El vuelo está retrasado */
    RETRASADO(true),
    /** El vuelo se encuentra en vuelo */
    VUELO(true);
	
	/** Bandera que indica si el estado es automático*/
	private final boolean automatico;
    
	/** 
	 * Constructor de la enumeración EstadoVuelo.
	 * 
	 * @param automatico	Boolean si es automático o no.
	 * 
	 */
    private EstadoVuelo(boolean automatico) {
        this.automatico = automatico;
    }
    
    /** 
     * Método que devuelve si un estado es aitomático o no.
     * 
     * @return True si es automático, false si no.
     */
    public boolean esAutomatico() {
        return automatico;
    }
}