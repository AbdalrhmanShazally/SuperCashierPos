package security;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtil {
	//Static Encryption attributes
	private final static String algorithm = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";  //  Fix padding issue
	private final static String secret_key = "4086395267221119";
	
	
	//Encrypt:
	public static String encrypt(String data)throws Exception { 
		
		SecretKeySpec secretKeySpec = new SecretKeySpec(secret_key.getBytes("UTF-8"), algorithm);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		byte[] encryptBytse = cipher.doFinal(data.getBytes("UTF-8"));
		
		
		return Base64.getEncoder().encodeToString(encryptBytse);
	}
	
	//Decrypt :
	public static String decrypt(String encryptedData)throws Exception{
		
		SecretKeySpec secretKeySpec = new SecretKeySpec(secret_key.getBytes("UTF-8"), algorithm);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
		byte[] decodeBytes = Base64.getDecoder().decode(encryptedData);
		byte[] decryptedBytes = cipher.doFinal(decodeBytes);
		
		
		
		return new String(decryptedBytes,"UTF-8");
	}

}
