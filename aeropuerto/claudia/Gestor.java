package aeropuerto.claudia;
import aeropuerto.maria;

public class Gestor extends Usuario{

    public void emitirOrdenDePago(Factura factura, Operador operador) {
        operador.recibirNotificacionFacturaEmitida(factura);
    }

    public EstadoFactura verEstadoFactura(Factura factura) {
        return factura.getEstadoFactura();
    }
}


