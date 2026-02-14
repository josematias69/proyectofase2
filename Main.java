public class Main {

    public static void main(String[] args) {

        ScriptEngine engine = new ScriptEngine(true);

        String script = "SIG_OK PUBKEY_OK OP_CHECKSIG";

        boolean result = engine.execute(script);

        System.out.println("Resultado final: " + result);
    }
}
