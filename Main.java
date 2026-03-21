import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ScriptEngine engine = new ScriptEngine(true);

        boolean running = true;

        System.out.println("=================================");
        System.out.println("   SIMULADOR DE BITCOIN SCRIPT   ");
        System.out.println("=================================");

        System.out.println("\nOpcodes disponibles:");
        System.out.println("- OP_0 a OP_16");
        System.out.println("- OP_DUP");
        System.out.println("- OP_DROP");
        System.out.println("- OP_EQUAL");
        System.out.println("- OP_EQUALVERIFY");
        System.out.println("- OP_VERIFY");
        System.out.println("- OP_HASH160");
        System.out.println("- OP_CHECKSIG");
        System.out.println("- OP_ADD");
        System.out.println("- OP_GREATERTHAN");

        System.out.println("\nEjemplos:");
        System.out.println("1) 1 2 OP_ADD");
        System.out.println("2) 5 5 OP_EQUAL");
        System.out.println("3) SIG_A PUBKEY_A OP_DUP OP_HASH160 HASH_PUBKEY_A OP_EQUALVERIFY OP_CHECKSIG");

        while (running) {

            System.out.println("\n1. Ejecutar Script");
            System.out.println("2. Salir");
            System.out.print("Opción: ");

            String option = scanner.nextLine();

            switch (option) {

                case "1":
                    System.out.println("\nIngrese el script:");
                    System.out.print("> ");
                    String script = scanner.nextLine();

                    try {
                        boolean result = engine.execute(script);
                        System.out.println("Resultado: " + result);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "2":
                    running = false;
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida");
            }
        }

        scanner.close();
    }
}