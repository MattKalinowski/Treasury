package treasury.data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

public class Security {
    
    //Available algorithms are: MD2, MD5, SHA-1, SHA-224, SHA-256, SHA-384, SHA-512
    
    public static String getHash(byte[] inputBytes, String algorithm) {
        
        String hashValue = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(inputBytes);
            byte[] digestedBytes = messageDigest.digest();
            hashValue = DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();
        } catch (NoSuchAlgorithmException ex) {
        }
        return hashValue;
    }
    
}
