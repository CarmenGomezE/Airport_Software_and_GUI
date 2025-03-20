package aeropuerto.src.carmen.test;

import aeropuerto.src.carmen.factura.*;
import aeropuerto.src.carmen.*;

public class MainGeneradorFacturas {

    public static void main(String[] args) {
        SistemaFacturacion sistema = new SistemaFacturacion(100.0, 200.0, 150.0);
        Aerolinea aerolinea = new Aerolinea("Iberia", "IB", "logo.jpg");
        Factura factura = sistema.generarFactura(aerolinea);

        if (factura != null) {
            try {
                InvoiceSystem.createInvoice(factura, "./facturas/Factura_" + factura.getCodigo() + ".pdf");
                System.out.println("Factura generada correctamente.");
            } catch (NonExistentFileException | UnsupportedImageTypeException e) {
                System.err.println("Error al generar el PDF: " + e.getMessage());
            }
        } else {
            System.out.println("No se generaron vuelos para la factura.");
        }
    }
}