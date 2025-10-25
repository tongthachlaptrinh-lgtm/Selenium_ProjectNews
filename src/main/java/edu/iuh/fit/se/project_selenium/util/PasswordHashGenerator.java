package edu.iuh.fit.se.project_selenium.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Test với password "123456"
        String password1 = "123456";
        String hash1 = encoder.encode(password1);
        System.out.println("Password: " + password1);
        System.out.println("BCrypt Hash: " + hash1);
        System.out.println("Verify: " + encoder.matches(password1, hash1));
        System.out.println();
        
        // Test với password "password"
        String password2 = "password";
        String hash2 = encoder.encode(password2);
        System.out.println("Password: " + password2);
        System.out.println("BCrypt Hash: " + hash2);
        System.out.println("Verify: " + encoder.matches(password2, hash2));
        System.out.println();
        
        // Test với password "admin"
        String password3 = "admin";
        String hash3 = encoder.encode(password3);
        System.out.println("Password: " + password3);
        System.out.println("BCrypt Hash: " + hash3);
        System.out.println("Verify: " + encoder.matches(password3, hash3));
    }
}
