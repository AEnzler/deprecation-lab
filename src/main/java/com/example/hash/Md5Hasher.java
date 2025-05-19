package com.example.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Legacy MD5 hasher.
 *
 * @deprecated since 1.0, for removal.
 *  Use {@link Sha256Hasher} instead.
 */
@Deprecated(forRemoval = true, since="1.0")
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
