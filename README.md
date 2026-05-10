# Lombok — Gradle & Maven Sample Project

Demonstrates [Project Lombok](https://projectlombok.org/) annotations using two independent
build systems, so you can use whichever fits your workflow.

```
lambok-gradle-maven/
├── lombok-gradle-demo/   ← Gradle build
└── lombok-maven-demo/    ← Maven build
```

Both projects contain identical Java source code covering the most useful Lombok annotations.

---

## Lombok Annotations Covered

| Annotation | What it generates |
|---|---|
| `@Data` | `@Getter` + `@Setter` + `@ToString` + `@EqualsAndHashCode` + `@RequiredArgsConstructor` |
| `@Value` | Immutable `@Data`: all fields `final`, no setters |
| `@Builder` | Static inner `Builder` class with fluent `.field(value)` methods |
| `@With` | `withXxx()` methods that return a modified copy (works on immutable types) |
| `@Singular` | Per-element `.item()` builder method for collection fields |
| `@Builder.Default` | Default value in a builder that is NOT overridden unless explicitly set |
| `@Getter` / `@Setter` | Individual getter/setter generation (field or class level) |
| `@ToString` | `toString()` with optional `exclude` / `of` filtering |
| `@EqualsAndHashCode` | `equals()` + `hashCode()` with optional field selection via `of` |
| `@NoArgsConstructor` | Zero-argument constructor |
| `@AllArgsConstructor` | Constructor with all fields as parameters |
| `@RequiredArgsConstructor` | Constructor for `@NonNull` and `final` fields only |
| `@NonNull` | Null check inserted at the top of method / constructor |
| `@Slf4j` | Injects `private static final Logger log` (SLF4J) |
| `@Cleanup` | Calls `close()` automatically — equivalent to try-with-resources |
| `@SneakyThrows` | Rethrows checked exceptions without declaring them |
| `@Synchronized` | Synchronizes on a private lock object instead of `this` |
| `@Getter(lazy=true)` | Thread-safe lazy initializer — computed once, then cached |
| `@Accessors(chain=true)` | Setters return `this`, enabling fluent chaining |

---

## Project Structure (per module)

```
src/
├── main/
│   ├── java/com/example/lombok/
│   │   ├── model/
│   │   │   ├── User.java       @Data, @Builder, @NonNull, @Builder.Default, @Slf4j
│   │   │   ├── Address.java    @Value, @Builder, @With
│   │   │   ├── Product.java    @Getter, @Setter, @ToString, @EqualsAndHashCode, @Getter(lazy)
│   │   │   ├── Order.java      @Data, @Builder, @Singular, nested static class
│   │   │   └── Employee.java   @Accessors(chain=true)
│   │   ├── demo/
│   │   │   ├── BuilderDemo.java
│   │   │   ├── LoggingDemo.java
│   │   │   └── UtilityDemo.java
│   │   └── Main.java
│   └── resources/
│       └── logback.xml
└── test/
    └── java/com/example/lombok/
        ├── UserTest.java
        └── AddressTest.java
```

---

## Running — Gradle

Requires **Java 17+** and **Gradle 8+** (or generate the wrapper first).

```bash
cd lombok-gradle-demo

# Generate the Gradle wrapper (one-time, if not already present)
gradle wrapper

# Compile and run
./gradlew run

# Run tests
./gradlew test

# Build a JAR
./gradlew build
```

---

## Running — Maven

Requires **Java 17+** and **Maven 3.6+**.

```bash
cd lombok-maven-demo

# Compile and run
mvn compile exec:java

# Run tests
mvn test

# Package a JAR
mvn package
```

---

## IDE Setup

### IntelliJ IDEA
1. Install the **Lombok** plugin: `Settings → Plugins → Marketplace → Lombok`
2. Enable annotation processing: `Settings → Build → Compiler → Annotation Processors → Enable`

### Eclipse / Spring Tool Suite
1. Run the Lombok installer: `java -jar lombok.jar`
2. Follow the installer to patch your Eclipse installation.

### VS Code
1. Install the **Extension Pack for Java** (includes annotation processing support).

---

## Key Dependency Versions

| Library | Version |
|---|---|
| Lombok | 1.18.30 |
| SLF4J | 2.0.9 |
| Logback | 1.4.11 |
| JUnit Jupiter | 5.10.0 |
| AssertJ | 3.24.2 |
| Java | 17 |
