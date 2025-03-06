/**
 * La enumeración EstadoFactura define si está pagada o pendiente.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class Factura {
    /** Codigo identificador de la factura */
    private String codigo;
    /** Fecha de emisión de la factura */
    private Date date;

    /** Estado de la factura */
    private EstadoFactura estadoFactura;
    /** Aerolinea  a la que pertenece la factura */
    private Aerolinea aerolinea;


}