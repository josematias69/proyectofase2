public class CryptoMock {

    public static String hash160(String input) {
        return "HASH_" + input;
    }

    public static boolean checkSig(String signature, String pubKey) {
        return signature.equals("SIG_OK") && pubKey.equals("PUBKEY_OK");
    }
}
