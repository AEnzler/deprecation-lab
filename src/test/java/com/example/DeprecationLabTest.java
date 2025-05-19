package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Deprecation Lab Requirements")
class DeprecationLabTest {

    private Path src(String f) {
        return Path.of("src","main","java","com","example", f + ".java");
    }

    private String stripComments(String s) {
        return s.replaceAll("(?s)/\\*.*?\\*/","").replaceAll("//.*","");
    }

    // ----- Deprecation checks -----

    @Test
    @DisplayName("1) Md5Hasher must be annotated @Deprecated")
    void md5Deprecated() throws Exception {
        Class<?> md5 = Class.forName("com.example.hash.Md5Hasher");
        assertTrue(
                md5.isAnnotationPresent(Deprecated.class),
                "ERROR: Md5Hasher is not annotated @Deprecated; please add @Deprecated(forRemoval=true, since=\"1.0\")."
        );
    }

    @Test
    @DisplayName("2) AuthService must use Sha256Hasher, not Md5Hasher")
    void authUsesSha256() throws IOException {
        String code = Files.readString(src("AuthService"));
        assertFalse(
                code.contains("new Md5Hasher()"),
                "ERROR: AuthService still uses Md5Hasher; please replace with Sha256Hasher."
        );
        assertTrue(
                code.contains("new Sha256Hasher()"),
                "ERROR: AuthService must use Sha256Hasher after deprecating Md5Hasher."
        );
    }

    @Test
    @DisplayName("3) ReportGenerator must not call deprecated Date.getYear()")
    void forbidDateGetYear() throws IOException {
        String rg = stripComments(Files.readString(src("ReportGenerator")));
        assertFalse(
                rg.contains("Date.getYear("),
                "ERROR: ReportGenerator uses deprecated Date.getYear(); please replace with LocalDate-based year extraction."
        );
    }

    @Test
    @DisplayName("4) XmlService must no longer import javax.xml.bind")
    void forbidOldJaxb() throws IOException {
        String xs = Files.readString(src("XmlService"));
        assertFalse(
                xs.contains("import javax.xml.bind.DatatypeConverter"),
                "ERROR: XmlService still imports javax.xml.bind.DatatypeConverter; please remove that import."
        );
    }

    @Test
    @DisplayName("5) XmlService must import jakarta.xml.bind.DatatypeConverter")
    void requireJakartaImport() throws IOException {
        String xs = Files.readString(src("XmlService"));
        assertTrue(
                xs.contains("import jakarta.xml.bind.DatatypeConverter"),
                "ERROR: XmlService must import jakarta.xml.bind.DatatypeConverter after updating the POM."
        );
    }

    // ----- Functional checks -----

    @Test @DisplayName("6) XmlService.encode() must produce correct Base64")
    void xmlServiceFunctional() {
        String input = "<foo>bar</foo>";
        String expected = Base64.getEncoder()
                .encodeToString(input.getBytes(StandardCharsets.UTF_8));
        assertEquals(
                expected,
                XmlService.encode(input),
                "ERROR: XmlService.encode did not return the expected Base64 string."
        );
    }

    @Test @DisplayName("7) AuthService.login() must succeed/fail correctly")
    void authServiceFunctional() throws Exception {
        AuthService auth = new AuthService();
        assertTrue(
                auth.login("user","password123"),
                "ERROR: AuthService.login should return true for the correct password 'password123'."
        );
        assertFalse(
                auth.login("user","wrong"),
                "ERROR: AuthService.login should return false for an incorrect password."
        );
    }

    @Test @DisplayName("8) ReportGenerator.generate() must embed correct year and encode properly")
    void reportGeneratorFunctional() throws Exception {
        String user = "Alice";
        String password = "password123";
        // Construct expected XML with current year
        int year = LocalDate.now().getYear();
        String xml = "<report user='" + user + "' date='" + year + "'/>";
        String expected = Base64.getEncoder()
                .encodeToString(xml.getBytes(StandardCharsets.UTF_8));

        String actual = new ReportGenerator().generate(user, password);
        assertEquals(
                expected,
                actual,
                "ERROR: ReportGenerator.generate() did not produce the expected Base64-encoded report."
        );
    }
}
