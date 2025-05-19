package com.example;

import java.nio.charset.StandardCharsets;
import javax.xml.bind.DatatypeConverter;

/**
 * Utility for XML Base64 encoding.
 *
 * <p>In Java 8 and below, {@code javax.xml.bind.DatatypeConverter}
 * was bundled in the JDK. In Java 11+, it’s no longer included—you must
 * depend on the standalone JAXB API, which now lives under
 * {@code jakarta.xml.bind.DatatypeConverter}.</p>
 *
 * TODO:
 *   - remove old javax dependency and add Jakarta dependency in POM
 *   - Update the import to jakarta.xml.bind.DatatypeConverter;
 */
public class XmlService {

    /**
     * Encode the given text into Base64.
     */
    public static String encode(String xml) {
        // Base64 encode via the Jakarta API after imported
        return DatatypeConverter.printBase64Binary(xml.getBytes(StandardCharsets.UTF_8));
    }
}