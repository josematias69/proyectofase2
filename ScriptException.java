public class ScriptException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ScriptException() {
        super("Error en la ejecución del Script.");
    }

    public ScriptException(String message) {
        super(message);
    }

    public ScriptException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScriptException(Throwable cause) {
        super(cause);
    }
}