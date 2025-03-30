package security;

public class EncryptDatabaseConfig {
	
	public static void main(String[] args) {
		
		try {
            String encryptedUsername = EncryptionUtil.encrypt("supercashier");
            String encryptedPassword = EncryptionUtil.encrypt("super_cashier");
            String encryptedHost = EncryptionUtil.encrypt("localhost");
            String encryptedDbName = EncryptionUtil.encrypt("ucsmpos");
            String encryptedPort = EncryptionUtil.encrypt("3306");

            System.out.println("Encrypted Username: " + encryptedUsername);
            System.out.println("Encrypted Password: " + encryptedPassword);
            System.out.println("Encrypted Hostname: " + encryptedHost);
            System.out.println("Encrypted Database Name: " + encryptedDbName);
            System.out.println("Encrypted Port: " + encryptedPort);
            
            String decryptedUsername = EncryptionUtil.decrypt(encryptedUsername);
            String decryptedPassword = EncryptionUtil.decrypt(encryptedPassword);
            String decryptedHost = EncryptionUtil.decrypt(encryptedHost);
            String decryptedDbName = EncryptionUtil.decrypt(encryptedDbName);
            String decryptedPort = EncryptionUtil.decrypt(encryptedPort);
            
            System.out.println("decrypted Username: " + decryptedUsername);
            System.out.println("decrypted Password: " + decryptedPassword);
            System.out.println("decrypted Hostname: " + decryptedHost);
            System.out.println("decrypted Database Name: " + decryptedDbName);
            System.out.println("decrypted Port: " + decryptedPort);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
