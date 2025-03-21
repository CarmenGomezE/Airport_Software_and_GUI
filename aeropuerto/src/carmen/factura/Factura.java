package aeropuerto.src.carmen.factura;

import aeropuerto.src.carmen.*;
import java.util.*;
import java.time.*;
import java.time.format.*;

import es.uam.eps.padsof.invoices.IInvoiceInfo;
import es.uam.eps.padsof.invoices.IResourceUsageInfo;

public class Factura implements IInvoiceInfo {
    private Aerolinea aerolinea;
    private LocalDate fecha;
    private double precioBase;
    private double recargo;
    private String codigo;
    private List<IResourceUsageInfo> recursos;
    private EstadoFactura estado;

    public Factura(Aerolinea aerolinea, LocalDate fecha, double precioBase, double recargo, String codigo,
            List<IResourceUsageInfo> recursos, EstadoFactura estado) {
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
        return this.aerolinea.getNombre();
    }

    /** Método de la interfaz */
    public String getInvoiceIdentifier() {
        return this.codigo;
    }

    /** Método de la interfaz */
    public String getInvoiceDate() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.fecha.format(formatter);
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
        this.estado = EstadoFactura.PAGADA;
    }

    public double getPrice() {
        double precioTotal=0;
        for (IResourceUsageInfo r : recursos) {
            precioTotal += r.getPrice();
        }
        return precioTotal;
    }
}
