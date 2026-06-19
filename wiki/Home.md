# Lombok + Gradle & Maven — Sample Project

Welcome to the **Lombok Gradle/Maven Sample Project** wiki.

This project provides two fully working Java modules that demonstrate [Project Lombok](https://projectlombok.org/) annotations using both **Gradle** and **Maven** build systems. The Java source is identical in both modules — only the build configuration differs.

---

## Quick Links

| Page | Description |
|---|---|
| [Getting Started](Getting-Started) | Clone, build, and run in 5 minutes |
| [Lombok Annotations Guide](Lombok-Annotations-Guide) | All 18+ annotations with examples |
| [Gradle Setup](Gradle-Setup) | `build.gradle` configuration explained |
| [Maven Setup](Maven-Setup) | `pom.xml` configuration explained |
| [Code Examples](Code-Examples) | Practical before/after comparisons |
| [IDE Setup](IDE-Setup) | IntelliJ, Eclipse, VS Code configuration |

---

## What Is Lombok?

Project Lombok is a Java annotation processor that eliminates boilerplate code at compile time. Instead of writing hundreds of lines of getters, setters, constructors, `equals`, `hashCode`, and `toString` by hand, you annotate your class and Lombok generates the bytecode for you.

**Without Lombok** — a simple data class:
```java
public class User {
    private String firstName;
    private String lastName;
    private String email;

    public User() {}

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() { return "User(" + firstName + ", " + lastName + ")"; }

    @Override
    public boolean equals(Object o) { /* ~15 lines */ }

    @Override
    public int hashCode() { /* ~5 lines */ }
}
```

**With Lombok** — the same class:
```java
@Data
public class User {
    private String firstName;
    private String lastName;
    private String email;
}
```

---

## Project Structure

```
lambok-gradle-maven/
├── README.md
├── wiki/                        ← these wiki pages
├── lombok-gradle-demo/          ← Gradle module
│   ├── build.gradle
│   ├── settings.gradle
│   ├── gradle/wrapper/
│   └── src/
│       ├── main/java/com/example/lombok/
│       │   ├── model/           User, Address, Product, Order, Employee
│       │   ├── demo/            BuilderDemo, LoggingDemo, UtilityDemo
│       │   └── Main.java
│       └── test/java/com/example/lombok/
│           ├── UserTest.java
│           └── AddressTest.java
└── lombok-maven-demo/           ← Maven module (same source)
    ├── pom.xml
    └── src/ ...
```

---

## Annotations at a Glance

| Category | Annotations |
|---|---|
| **Data classes** | `@Data`, `@Value`, `@Getter`, `@Setter` |
| **Constructors** | `@NoArgsConstructor`, `@AllArgsConstructor`, `@RequiredArgsConstructor` |
| **Builder** | `@Builder`, `@Builder.Default`, `@Singular`, `@With` |
| **Logging** | `@Slf4j`, `@Log`, `@Log4j2`, `@CommonsLog` |
| **Utility** | `@NonNull`, `@Cleanup`, `@SneakyThrows`, `@Synchronized` |
| **Advanced** | `@Getter(lazy=true)`, `@Accessors`, `@ToString`, `@EqualsAndHashCode` |

---

## Technology Stack

| Library | Version |
|---|---|
| Java | 17 |
| Lombok | 1.18.30 |
| SLF4J | 2.0.9 |
| Logback | 1.4.11 |
| JUnit Jupiter | 5.10.0 |
| AssertJ | 3.24.2 |
| Gradle | 8.5 |
| Maven Compiler Plugin | 3.11.0 |
