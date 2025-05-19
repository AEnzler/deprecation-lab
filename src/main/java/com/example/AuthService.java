package com.example;

import com.example.hash.Md5Hasher;
import com.example.hash.Sha256Hasher;
import com.example.hash.PasswordHasher;

/**
 * Authentication service.
 *
 * <p>This example uses a hard‐coded “password123” hash to simulate
 * user lookup in a database. In real code, you’d fetch the stored
 * hash+salt from a secure store.</p>
 *
 */
public class AuthService {
    private final PasswordHasher hasher = new Sha256Hasher();

    /**
     * Validate the given password against the stored hash.
     */
    public boolean login(String user, String pass) throws Exception {
        // stored hash is always hash("password123")
        String stored = hasher.hash("password123");
        return hasher.hash(pass).equalsIgnoreCase(stored);
    }

    /**
     * Expose current hasher for CLI feedback
     */
    public String getHasherName() {
        return hasher.getClass().getSimpleName();
    }
}
