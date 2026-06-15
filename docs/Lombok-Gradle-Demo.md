# Lombok Gradle Demo

A Java project demonstrating [Project Lombok](https://projectlombok.org/) annotations with **Gradle** as the build tool.

## Prerequisites

- Java 11+
- Gradle 7+ (or use the included `gradlew` wrapper)

## Project Structure

```
lombok-gradle-demo/
├── build.gradle
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src/
    └── main/
        └── java/
```

## Build & Run

```bash
cd lombok-gradle-demo

# Build the project
./gradlew build

# Run the application
./gradlew run

# Run tests
./gradlew test
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

## Gradle Configuration

Lombok is added as `annotationProcessor` and `compileOnly` in `build.gradle`:

```groovy
dependencies {
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
}
```

## Related Pages

- [Lombok Maven Demo](Lombok-Maven-Demo.md) — same features, Maven build
- [Home](Home.md)
