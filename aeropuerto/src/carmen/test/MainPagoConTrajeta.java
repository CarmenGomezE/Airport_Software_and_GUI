public class MainPagoConTarjeta {
    public static void main(String[] args) {
        try {
            System.out.println("Número válido: " + TeleChargeAndPaySystem.isValidCardNumber("1234567890123456"));
            System.out.println("Número inválido: " + TeleChargeAndPaySystem.isValidCardNumber("123456789012345"));

            TeleChargeAndPaySystem.charge("1234567890123456", "Annual pass", 350, true);
            factura.marcarComoPagada();
            System.out.println("Pago realizado con éxito.");

        } catch (InvalidCardNumberException e) {
            System.err.println("Error: Número de tarjeta inválido.");
        } catch (FailedInternetConnectionException e) {
            System.err.println("Error: Conexión a Internet fallida.");
        } catch (OrderRejectedException e) {
            System.err.println("Error: El pago fue rechazado.");
        } catch (Exception e) {
            System.err.println("Error desconocido: " + e.getMessage());
        }
    }
}