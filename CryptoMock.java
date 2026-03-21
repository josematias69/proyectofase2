public class CryptoMock {

    public static String hash160(String input) {
        return "HASH_" + input.toUpperCase();
    }

    public static boolean checkSig(String signature, String pubKey) {
        return signature.startsWith("SIG_") && pubKey.startsWith("PUBKEY_");
    }
}