package aeropuerto.src.carmen.factura;

import java.util.Arrays;
import java.util.List;

import aeropuerto.libs.es.uam.eps.padsof.invoices.IInvoiceInfo;
import aeropuerto.libs.es.uam.eps.padsof.invoices.InvoiceSystem;
import aeropuerto.libs.es.uam.eps.padsof.invoices.NonExistentFileException;
import aeropuerto.libs.es.uam.eps.padsof.invoices.UnsupportedImageTypeException;

public class Factura implements IInvoiceInfo {
    private Aerolinea aerolinea;
    private Date fecha;
    private double precioBase;
    private double recargo;
    private String codigo;
    private List<IResourceUsageInfo> recursos;
    private EstadoFactura estado;

    public Factura(Aerolinea aerolinea, Date fecha, double precioBase, double recargo, String codigo,
            List<IResourceUsageInfo> recursos, EstadoFcatura estado) {
        this.aerolinea = aerolinea;
        this.fecha = fecha;
        this.codigo = codigo;
        this.precioBase = precioBase;
        this.recargo = recargo;
        this.recursos = recursos;
        this.estado = estado;
    }

    /** Método de la interfaz */
    public String getCompanyName() {
        return this.aerolinea.getNombre();
    }

    /** Método de la interfaz */
    public String getCompanyLogo() {
        return "./resources/logo.jpg";
    }

    /** Método de la interfaz */
    public String getAirline() {
        return this.aerolinea;
    }

    /** Método de la interfaz */
    public String getInvoiceIdentifier() {
        return this.codigo;
    }

    /** Método de la interfaz */
    public String getInvoiceDate() {
        return this.fecha;
    }

    public double getBasePrice() {
        return this.precioBase;
    }

    public double getSurcharge() {
        return this.recargo;
    }

    public List<IResourceUsageInfo> getResourcePrices() {
        return recursos;
    }

    public EstadoFactura getEstado() {
        return this.estado;
    }

    public void marcarComoPagada() {
        this.estado = EstadoFcatura.PAGADA;
    }

    public double getPrice() {
        double precioTotal;
        for (IResourceUsageInfo r : recursos) {
            precioTotal += r.getPrice();
        }
        return precioTotal;
    }
}
