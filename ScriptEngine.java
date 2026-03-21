public class ScriptEngine {

    private Stack stack;
    private boolean trace;

    public ScriptEngine(boolean trace) {
        this.stack = new ScriptStack();
        this.trace = trace;
    }

    public boolean execute(String script) {

        // Fase 2
        if (script == null || script.trim().isEmpty()) {
            throw new ScriptException("Script vacío");
        }

        String[] tokens = script.split(" ");

        for (String token : tokens) {

            if (isData(token)) {
                stack.push(token);
            } else {
                executeOpcode(token);
            }

            if (trace) {
                System.out.println("[" + token + "] Stack: " + stack);
            }
        }

        // Fase 2
        if (stack.isEmpty()) {
            throw new ScriptException("Stack vacío al finalizar");
        }

        return !stack.pop().equals("0");
    }

    private void executeOpcode(String opcode) {

        if (opcode.matches("OP_\\d+")) {
            int value = Integer.parseInt(opcode.substring(3));
            if (value >= 0 && value <= 16) {
                stack.push(String.valueOf(value));
                return;
            }
        }

        switch (opcode) {

            case "OP_DUP":
                // Fase 2
                if (stack.size() < 1)
                    throw new ScriptException("OP_DUP requiere 1 elemento");
                stack.push(stack.peek());
                break;

            case "OP_DROP":
                if (stack.size() < 1)
                    throw new ScriptException("OP_DROP requiere 1 elemento");
                stack.pop();
                break;

            case "OP_EQUAL":
                if (stack.size() < 2)
                    throw new ScriptException("OP_EQUAL requiere 2 elementos");
                String bEq = stack.pop();
                String aEq = stack.pop();
                stack.push(aEq.equals(bEq) ? "1" : "0");
                break;

            case "OP_EQUALVERIFY":
                if (stack.size() < 2)
                    throw new ScriptException("OP_EQUALVERIFY requiere 2 elementos");
                String bEqV = stack.pop();
                String aEqV = stack.pop();
                if (!aEqV.equals(bEqV)) {
                    throw new ScriptException("OP_EQUALVERIFY falló");
                }
                break;

            // Fase 2
            case "OP_VERIFY":
                if (stack.size() < 1)
                    throw new ScriptException("OP_VERIFY requiere 1 elemento");
                String value = stack.pop();
                if (value.equals("0")) {
                    throw new ScriptException("OP_VERIFY falló");
                }
                break;

            case "OP_HASH160":
                if (stack.size() < 1)
                    throw new ScriptException("OP_HASH160 requiere 1 elemento");
                stack.push(CryptoMock.hash160(stack.pop()));
                break;

            case "OP_CHECKSIG":
                if (stack.size() < 2)
                    throw new ScriptException("OP_CHECKSIG requiere 2 elementos");
                String pubKey = stack.pop();
                String sig = stack.pop();
                boolean valid = CryptoMock.checkSig(sig, pubKey);
                stack.push(valid ? "1" : "0");
                break;

            // Fase 2
            case "OP_ADD":
                if (stack.size() < 2)
                    throw new ScriptException("OP_ADD requiere 2 elementos");

                String bAddStr = stack.pop();
                String aAddStr = stack.pop();

                if (!isNumeric(aAddStr) || !isNumeric(bAddStr)) {
                    throw new ScriptException("OP_ADD requiere números");
                }

                int resultAdd = Integer.parseInt(aAddStr) + Integer.parseInt(bAddStr);
                stack.push(String.valueOf(resultAdd));
                break;

            // Fase 2
            case "OP_GREATERTHAN":
                if (stack.size() < 2)
                    throw new ScriptException("OP_GREATERTHAN requiere 2 elementos");

                String bGtStr = stack.pop();
                String aGtStr = stack.pop();

                if (!isNumeric(aGtStr) || !isNumeric(bGtStr)) {
                    throw new ScriptException("OP_GREATERTHAN requiere números");
                }

                int aGt = Integer.parseInt(aGtStr);
                int bGt = Integer.parseInt(bGtStr);

                stack.push(aGt > bGt ? "1" : "0");
                break;

            default:
                throw new ScriptException("Opcode desconocido: " + opcode);
        }
    }

    private boolean isData(String token) {
        return !token.startsWith("OP_");
    }

    // Fase 2
    private boolean isNumeric(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}