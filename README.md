# Deprecation Lab

## 🧑‍🏫 Task Overview

1. **Deprecate** the legacy `Md5Hasher` 
   - Add `@Deprecated(forRemoval=true, since="1.0")` to `Md5Hasher` 
2. **Swap** `AuthService` to use `Sha256Hasher` instead of `Md5Hasher`  
3. **Fix** the year in `ReportGenerator`
    - Replace the deprecated `new Date().getYear()` (returns year-1900)
    - Use the modern `java.time.LocalDate` API
4. **Migrate** `XmlService` import from `javax.xml.bind` → `jakarta.xml.bind`

---

## 🚀 Quick Start

1. **Fork** the repository on GitHub into your own account.
2. **Clone** *your* fork (use the URL shown on your fork page):
   ```bash
   git clone https://github.zhaw.ch/<your-username>/deprecation-lab.git
   cd deprecation-lab
   ```
3. **Run** the (failing) tests:
   ```bash
   ./mvnw clean test
   ```
4. **Edit** ONLY the files marked with TODO, then re-run:
   ```bash
   ./mvnw clean test
   ```
5. **Try** the CLI hints:
   ```bash
   ./mvnw compile exec:java
   ```
6. **Commit & Push** your code to your **fork**
---

## 🛠️ GitHub Actions & CI

* We use **Github Actions** to run:
1. `./mvnw clean test --fail-at-end`
2. Annotate any test failures in your push
3. Fail the build if there are errors
* Every push triggers the CI.
* Goal: green checks ✅ before merging.

---

## 📦 Additional setup

To get your environment ready before diving into the tasks, follow these steps:

- **Maven wrapper:** The project includes mvnw (Unix) and mvnw.cmd (Windows) scripts so you don’t need a local Maven installation. Use ./mvnw or mvnw.cmd to run Maven commands with the exact version specified in .mvn/wrapper/maven-wrapper.properties.

- **IDE configuration:** Open the project as a Maven project in your favorite IDE (IntelliJ IDEA, Eclipse, VS Code). The IDE will automatically download dependencies, configure JUnit 5 support, and index source files so you can navigate and refactor easily.

- **CI annotations:**:
   * **Tests** fail if functionality or deprecation rules are not met.
   * **TODO warnings** appear as yellow annotations — fix those TODO comments to clean up the exercise.
