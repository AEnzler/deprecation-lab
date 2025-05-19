package com.example.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Simple SHA-256 hasher.
 *
 * <p><strong>Context:</strong>
 * SHA-256 offers strong, collision-resistant hashing, suitable for
 * password storage when combined with salt and work factors.</p>
 *
 * <p>This class replaces {@link Md5Hasher} in the lab.</p>
 */
public class Sha256Hasher implements PasswordHasher {

    /**
     * Compute the SHA-256 hash of the given password.
     */
    @Override
    public String hash(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] d = md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder(d.length*2);
        for (byte x : d) sb.append(String.format("%02x", x));
        return sb.toString();
    }
}
