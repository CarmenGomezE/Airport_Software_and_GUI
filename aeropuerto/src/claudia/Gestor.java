package aeropuerto.claudia;
import aeropuerto.maria;

public class Gestor extends Usuario{

    public void generarFacturaYNotificar(Aerolinea aerolinea, SistemaFacturacion sistemaFacturacion) {
         Factura factura = sistemaFacturacion.generarFactura(aerolinea);
        
        if (factura != null) {
            try {
                InvoiceSystem.createInvoice(factura, "./facturas/Factura_" + factura.getInvoiceIdentifier() + ".pdf");
                System.out.println("Factura generada correctamente.");
            } catch (NonExistentFileException | UnsupportedImageTypeException e) {
                System.err.println("Error al generar el PDF: " + e.getMessage());
            }
        } else {
            System.out.println("No se generaron vuelos para la factura.");
        }
    }

    public EstadoFactura verEstadoFactura(Factura factura) {
        return factura.getEstado();
    }

}

