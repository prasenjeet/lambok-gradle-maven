# Getting Started

## Prerequisites

| Tool | Minimum Version |
|---|---|
| JDK | 17 |
| Gradle | 8.x (or use the wrapper) |
| Maven | 3.6+ |
| Git | any recent version |

---

## Clone the Repository

```bash
git clone https://github.com/prasenjeet/lambok-gradle-maven.git
cd lambok-gradle-maven
```

---

## Gradle Module

### 1. Generate the wrapper (one-time)
```bash
cd lombok-gradle-demo
gradle wrapper
```

### 2. Run the demo
```bash
./gradlew run
```

**Expected output:**
```
12:00:00.123 [INFO ] c.e.lombok.Main - ================================================
12:00:00.124 [INFO ] c.e.lombok.Main -   Lombok Features Demo — Gradle Project
12:00:00.124 [INFO ] c.e.lombok.Main - ================================================
12:00:00.130 [INFO ] c.e.l.demo.BuilderDemo - === @Builder / @With / @Singular / @Accessors Demo ===
12:00:00.135 [INFO ] c.e.l.demo.BuilderDemo - Address: Address(street=123 Main St, city=Springfield, ...)
...
```

### 3. Run tests
```bash
./gradlew test
```

### 4. Build a JAR
```bash
./gradlew build
# JAR is at: build/libs/lombok-gradle-demo-1.0-SNAPSHOT.jar
```

---

## Maven Module

```bash
cd lombok-maven-demo
```

### Run the demo
```bash
mvn compile exec:java
```

### Run tests
```bash
mvn test
```

### Package a JAR
```bash
mvn package
# JAR is at: target/lombok-maven-demo-1.0-SNAPSHOT.jar
```

---

## What Each Demo Prints

| Section | Annotations shown |
|---|---|
| `BuilderDemo` | `@Builder`, `@With`, `@Singular`, `@Builder.Default`, `@Accessors` |
| `LoggingDemo` | `@Slf4j` — all 5 log levels, parameterized logging, exception logging |
| `UtilityDemo` | `@NonNull`, `@Cleanup`, `@SneakyThrows`, `@Synchronized` |

---

## Run a Single Test Class

**Gradle:**
```bash
./gradlew test --tests "com.example.lombok.UserTest"
./gradlew test --tests "com.example.lombok.AddressTest"
```

**Maven:**
```bash
mvn test -Dtest=UserTest
mvn test -Dtest=AddressTest
```

---

## Troubleshooting

### "cannot find symbol" compile errors
Your IDE or build tool is not running the annotation processor. See [IDE Setup](IDE-Setup).

### `@Builder.Default` field has wrong default
When using `@NoArgsConstructor` alongside `@Builder`, the no-args constructor does NOT apply `@Builder.Default` values — those only apply when the object is created via the builder. This is expected Lombok behavior.

### Null fields after `new User()`
Same reason: `@Builder.Default` only applies through the builder. Use the builder (`User.builder().firstName("...").build()`) to get defaults.
