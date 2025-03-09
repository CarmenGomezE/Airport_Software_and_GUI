package aeropuerto;
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

    /**
     * Constructor de la clase Factura.
     * 
     * @param codigo            Código de la factura.
     * @param date              Fecha de emisión de la factura.
     * @param estadoFactura     Estado de la factura.
     * @param aerolinea         Aerolinea que ha recibido la factura.
     */
    public Factura(String codigo, Date date, EstadoFactura estadoFactura, Aerolinea aerolinea){
        this.codigo=codigo;
        this.date=date;
        this.estadoFactura=estadoFactura;
        this.aerolinea=aerolinea;
    }

     /**
     * Método que devuelve el código de la factura.
     * 
     * @return Código de la factura.
     */
    public String getCodigo(){
        return this.codigo;
    }

     /**
     * Método que devuelve la fecha de la factura.
     * 
     * @return Fecha de la factura.
     */
    public Date getDate(){
        return this.date;
    }

     /**
     * Método que devuelve el estado de la factura.
     * 
     * @return Estado de la factura.
     */
    public EstadoFactura getEstadoFactura(){
        return this.estadoFactura;
    }

     /**
     * Método que devuelve la aerolinea que ha recibido la factura.
     * 
     * @return Aerolinea que ha recibido la factura.
     */
    public Aerolinea getAerolinea(){
        return this.aerolinea;
    }

}