import java.util.ArrayDeque;
import java.util.Deque;

public class ScriptStack implements Stack {

    private Deque<String> stack;

    public ScriptStack() {
        this.stack = new ArrayDeque<>();
    }

    @Override
    public void push(String value) {
        stack.push(value);
    }

    @Override
    public String pop() {
        if (stack.isEmpty()) {
            throw new ScriptException("Stack vacío");
        }
        return stack.pop();
    }

    @Override
    public String peek() {
        if (stack.isEmpty()) {
            throw new ScriptException("Stack vacío");
        }
        return stack.peek();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}


