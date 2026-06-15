# Lombok Maven Demo

A Java project demonstrating [Project Lombok](https://projectlombok.org/) annotations with **Maven** as the build tool.

## Prerequisites

- Java 11+
- Maven 3.6+

## Project Structure

```
lombok-maven-demo/
├── pom.xml
└── src/
    └── main/
        └── java/
```

## Build & Run

```bash
cd lombok-maven-demo

# Build the project
mvn clean install

# Run the application
mvn exec:java

# Run tests
mvn test
```

## Key Lombok Annotations Used

| Annotation | Purpose |
|------------|---------|
| `@Data` | Generates getters, setters, `equals`, `hashCode`, `toString` |
| `@Builder` | Implements the Builder pattern |
| `@NoArgsConstructor` | Generates a no-argument constructor |
| `@AllArgsConstructor` | Generates an all-arguments constructor |
| `@Slf4j` | Injects a SLF4J logger field |
| `@Value` | Creates an immutable value class |

## Maven Configuration

Lombok is declared in `pom.xml` with `provided` scope — available at compile time but not bundled in the final artifact:

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.30</version>
    <scope>provided</scope>
</dependency>
```

## Related Pages

- [Lombok Gradle Demo](Lombok-Gradle-Demo.md) — same features, Gradle build
- [Home](Home.md)
