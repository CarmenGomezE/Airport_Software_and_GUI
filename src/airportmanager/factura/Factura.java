package src.airportmanager.factura;

import java.util.*;
import java.io.File;
import java.io.Serializable;
import java.time.*;
import java.time.format.*;

import es.uam.eps.padsof.invoices.IInvoiceInfo;
import es.uam.eps.padsof.invoices.IResourceUsageInfo;
import src.airportmanager.avion.Aerolinea;

/**
 * La clase Factura implementa la interfaz de las facturas y representa una
 * factura que el gestor emite
 * para una aerolinea por sus vuelos mensuales.
 * 
 * @author Carmen Gómez, María Pozo, Claudia Municio.
 */
public class Factura implements IInvoiceInfo , Serializable {

	/** Número de versión de la clase */
	private static final long serialVersionUID = 1L;
    /** Aerolinea de la factura */
    private Aerolinea aerolinea;
    /** Fecha de la factura */
    private LocalDate fecha;
    /** Perico base de la factura */
    private double precioBase;
    /** Recargo de la factura */
    private double recargo;
    /** Código de la factura */
    private String codigo;
    /** Lista de recursos de la factura */
    private List<IResourceUsageInfo> recursos;
    /** Estado de la factura */
    private EstadoFactura estado;
    /** Logo de la compañia */
    private static String companyLogo=  System.getProperty("user.dir") + File.separator + "resources" +File.separator + "logo.png";

    /**
     * Constructor de la clase Factura.
     * 
     * @param aerolinea  Aerolinea de la factura.
     * @param fecha      Fecha de la facturad.
     * @param precioBase Perico base de la factura.
     * @param recargo    Recargo de la factura.
     * @param codigo     Código de la factura .
     * @param recursos   Lista de recursos de la factura.
     * @param estado     Estado de la factura .
     */
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

    /**
     * Método de la interfaz.
     * 
     * @return Nombre de la aerolinea de la factura.
     */
    public String getCompanyName() {
        return this.aerolinea.getNombre();
    }

    /**
     * Método de la interfaz.
     * 
     * @return Logo de la aerolinea de la factura.
     */
    public String getCompanyLogo() {
        return companyLogo;
    }

    /**
     * Método de la interfaz.
     * 
     * @return Identificador de la factura.
     */
    public String getInvoiceIdentifier() {
        return this.codigo;
    }

    /**
     * Método de la interfaz.
     * 
     * @return Identificador de la aerolinea de la factura.
     */
    public String getAirline() {
        return this.aerolinea.getCodigo();
    }

    /**
     * Método de la interfaz.
     * 
     * @return Fecha de la factura.
     */
    public String getInvoiceDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.fecha.format(formatter);
    }

    /**
     * Método de la interfaz.
     * 
     * @return Precio base de la factura.
     */
    public double getBasePrice() {
        return this.precioBase;
    }

    /**
     * Método de la interfaz.
     * 
     * @return Recargo de la factura.
     */
    public double getSurcharge() {
        return this.recargo;
    }

    /**
     * Método de la interfaz.
     * 
     * @return Recursos de la factura.
     */
    public List<IResourceUsageInfo> getResourcePrices() {
        return recursos;
    }

    /**
     * Método que devuelve el estado de la factura.
     * 
     * @return Estado de la factura.
     */
    public EstadoFactura getEstado() {
        return this.estado;
    }

    /**
     * Método que marca como pagada una factura.
     *
     */
    public void marcarComoPagada() {
        this.estado = EstadoFactura.PAGADA;
    }

    /**
     * Método de la interfaz.
     * 
     * @return Precio de la factura.
     */
    public double getPrice() {
        double precioTotal = 0;
        for (IResourceUsageInfo r : recursos) {
            precioTotal += r.getPrice();
        }
        return precioTotal;
    }
}
