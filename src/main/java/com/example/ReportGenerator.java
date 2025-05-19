package com.example;

import java.time.LocalDate;

/**
 * Builds a simple report and Base64-encodes it.
 */
public class ReportGenerator {
    private final UserManager users = new UserManager();

    /**
     * Generate and encode an XML snippet containing user+year.
     */
    public String generate(String u, String p) throws Exception {
        if (!users.authenticate(u, p)) return "Unauthorized";
        String xml = "<report user='" + u + "' date='" + LocalDate.now().getYear() + "'/>";
        return XmlService.encode(xml);
    }
}
