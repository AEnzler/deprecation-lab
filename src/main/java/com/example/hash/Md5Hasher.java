package com.example.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Legacy MD5 hasher.
 *
 * <p><strong>Context:</strong>
 * MD5 was once used widely, including inside older JDK utilities, but is now
 * considered insecure for passwords. This class shows an existing “bad” API
 * you must deprecate and replace.</p>
 *
 *  TODO: Add deprecation annotation and documentation.
 *      1) Annotate the class with @Deprecated(forRemoval=true, since="1.0")
 *      2) Add a {@deprecated} Javadoc tag pointing to Sha256Hasher.
 */
public class Md5Hasher implements PasswordHasher {

    /**
     * Compute the MD5 hash of the given password.
     */
    @Override
    public String hash(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] d = md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder(d.length*2);
        for (byte x : d) sb.append(String.format("%02x", x));
        return sb.toString();
    }
}
