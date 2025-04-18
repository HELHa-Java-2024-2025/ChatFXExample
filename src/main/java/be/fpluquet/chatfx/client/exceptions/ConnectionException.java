package be.fpluquet.chatfx.client.exceptions;

public class ConnectionException extends Throwable {
    public ConnectionException(String message) {
        this(message, null);
    }
    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
