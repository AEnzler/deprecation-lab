package com.example.hash;

/** Interface for hashing passwords. */
public interface PasswordHasher {
    String hash(String password) throws Exception;
}
