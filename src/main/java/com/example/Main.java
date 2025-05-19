package com.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    private static final Path RG   = Path.of("src","main","java","com","example","ReportGenerator.java");
    private static final Path XS   = Path.of("src","main","java","com","example","XmlService.java");

    public static void main(String[] args) throws Exception {
        boolean allGood = true;

        try {
            Class<?> md5 = Class.forName("com.example.hash.Md5Hasher");
            if (!md5.isAnnotationPresent(Deprecated.class)) {
                System.err.println("[STEP 1] WARNING: Md5Hasher is not annotated @Deprecated; add @Deprecated(forRemoval=true, since=\"1.0\").");
                allGood = false;
            } else {
                System.out.println("[STEP 1] OK: Md5Hasher is annotated @Deprecated.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("[STEP 1] WARNING: Md5Hasher class not found.");
            allGood = false;
        }

        // STEP 1: Hasher check
        AuthService auth = new AuthService();
        String hasher = auth.getHasherName();
        if ("Md5Hasher".equals(hasher)) {
            System.err.println("[STEP 2] WARNING: AuthService is still using Md5Hasher. Please switch to Sha256Hasher.");
            allGood = false;
        } else {
            System.out.println("[STEP 2] OK: AuthService uses " + hasher + ".");
        }

        // STEP 2: ReportGenerator checks
        String rgSrc = Files.readString(RG);
        String rgClean = rgSrc.replaceAll("(?s)/\\*.*?\\*/", "").replaceAll("//.*", "");

        if (!rgClean.contains("LocalDate.now().getYear()")) {
            System.err.println("[STEP 3] WARNING: ReportGenerator must use LocalDate.now().getYear().");
            allGood = false;
        } else {
            System.out.println("[STEP 3] OK: ReportGenerator uses LocalDate.");
        }

        // STEP 3: XmlService import check
        String xsSrc = Files.readString(XS);
        if (xsSrc.contains("import javax.xml.bind")) {
            System.err.println("[STEP 4] WARNING: XmlService still imports javax.xml.bind.DatatypeConverter. Please migrate to jakarta.xml.bind.DatatypeConverter.");
            allGood = false;
        } else if (xsSrc.contains("import jakarta.xml.bind.DatatypeConverter")) {
            System.out.println("[STEP 4] OK: XmlService imports jakarta.xml.bind.DatatypeConverter.");
        } else {
            System.err.println("[STEP 4] WARNING: XmlService missing import for jakarta.xml.bind.DatatypeConverter.");
            allGood = false;
        }

        if (!allGood) {
            System.err.println();
            System.err.println("One or more steps failed. Fix the TODOs and re-run.");
            System.exit(2);
        }

        // Interactive demo
        Scanner in = new Scanner(System.in);
        System.out.print("Enter username: ");
        String user = in.nextLine().trim();
        System.out.print("Enter password: ");
        String pass = in.nextLine().trim();

        if (!auth.login(user, pass)) {
            System.out.println("Authentication failed for user '" + user + "'.");
            return;
        }

        System.out.print("Generate report? (y/N): ");
        String resp = in.nextLine().trim().toLowerCase();
        if ("y".equals(resp)) {
            String report = new ReportGenerator().generate(user, pass);
            System.out.println("Report (Base64):");
            System.out.println(report);
        } else {
            System.out.println("Done.");
        }
    }
}
