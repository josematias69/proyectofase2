
public class ScriptEngine {

    private Stack stack;
    private boolean trace;

    public ScriptEngine(boolean trace) {
        this.stack = new ScriptStack();
        this.trace = trace;
    }

    public boolean execute(String script) {

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

        return !stack.isEmpty() && !stack.pop().equals("0");
    }

    private void executeOpcode(String opcode) {

        // Manejo automático OP_0 a OP_16
        if (opcode.matches("OP_\\d+")) {
            int value = Integer.parseInt(opcode.substring(3));
            if (value >= 0 && value <= 16) {
                stack.push(String.valueOf(value));
                return;
            }
        }

        switch (opcode) {

            case "OP_DUP":
                String top = stack.peek();
                stack.push(top);
                break;

            case "OP_ADD":
                int bAdd = Integer.parseInt(stack.pop());
                int aAdd = Integer.parseInt(stack.pop());
                stack.push(String.valueOf(aAdd + bAdd));
                break;

            case "OP_DROP":
                stack.pop();
                break;

            case "OP_EQUAL":
                String b1 = stack.pop();
                String a1 = stack.pop();
                if (a1.equals(b1)) {
                    stack.push("1");
                } else {
                    stack.push("0");
                }
                break;

            case "OP_EQUALVERIFY":
                String b2 = stack.pop();
                String a2 = stack.pop();
                if (!a2.equals(b2)) {
                    throw new ScriptException("OP_EQUALVERIFY falló");
                }
                break;

            case "OP_HASH160":
                String value = stack.pop();
                stack.push(CryptoMock.hash160(value));
                break;

            case "OP_CHECKSIG":
                String pubKey = stack.pop();
                String sig = stack.pop();
                boolean valid = CryptoMock.checkSig(sig, pubKey);
                if (valid) {
                    stack.push("1");
                } else {
                    stack.push("0");
                }
                break;

            default:
                throw new ScriptException("Opcode desconocido: " + opcode);
        }
    }

    private boolean isData(String token) {
        return !token.startsWith("OP_");
    }
}
