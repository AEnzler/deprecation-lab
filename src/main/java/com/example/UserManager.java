package com.example;

/**
 * UserManager wraps AuthService for higher‐level user operations.
 *
 * <p>In a real app, this might handle user profiles, sessions, etc.
 * Here it simply delegates to AuthService.</p>
 */
public class UserManager {
    private final AuthService auth = new AuthService();

    /**
     * Authenticate a user/password pair.
     */
    public boolean authenticate(String u, String p) throws Exception {
        return auth.login(u, p);
    }
}
