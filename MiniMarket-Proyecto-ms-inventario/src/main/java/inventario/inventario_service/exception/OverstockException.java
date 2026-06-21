package inventario.inventario_service.exception;

public class OverstockException extends RuntimeException {

    public OverstockException(String message) {
        super(message);
    }
}
