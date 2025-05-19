package com.example;

import java.nio.charset.StandardCharsets;
import jakarta.xml.bind.DatatypeConverter;

/**
 * Utility for XML Base64 encoding.
 */
public class XmlService {

    /**
     * Encode the given text into Base64.
     */
    public static String encode(String xml) {
        return DatatypeConverter.printBase64Binary(xml.getBytes(StandardCharsets.UTF_8));
    }
}