package com.example;

import java.util.Date;

/**
 * Builds a simple report and Base64-encodes it.
 * Demonstrates:
 *   Deprecated API usage (Date.getYear())
 * <p><strong>Context:</strong>
 * The ReportGenerator currently uses Date.getYear() (deprecated since JDK 1.1,
 *  returns year-1900).</p>
 *
 * TODO:
 *   - Replace `new Date().getYear()` with `java.time.LocalDate` API
 */
public class ReportGenerator {
    private final UserManager users = new UserManager();

    /**
     * Generate and encode an XML snippet containing user+year.
     */
    public String generate(String u, String p) throws Exception {
        if (!users.authenticate(u, p)) return "Unauthorized";
        String xml = "<report user='" + u + "' date='" + (new Date().getYear() + 1900) + "'/>";
        return XmlService.encode(xml);
    }
}
