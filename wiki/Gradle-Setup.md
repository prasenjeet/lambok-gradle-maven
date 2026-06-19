# Gradle Setup

How to configure Lombok in a Gradle project, explained line by line.

---

## build.gradle (full file)

```groovy
plugins {
    id 'java'
    id 'application'
}

group = 'com.example'
version = '1.0-SNAPSHOT'

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

ext {
    lombokVersion = '1.18.30'
}

dependencies {
    // Lombok: compile-only — the annotation processor runs at build time,
    // the lombok.jar is NOT needed at runtime.
    compileOnly "org.projectlombok:lombok:${lombokVersion}"
    annotationProcessor "org.projectlombok:lombok:${lombokVersion}"

    // Same setup for test sources
    testCompileOnly "org.projectlombok:lombok:${lombokVersion}"
    testAnnotationProcessor "org.projectlombok:lombok:${lombokVersion}"

    // SLF4J + Logback — needed at runtime for @Slf4j
    implementation 'org.slf4j:slf4j-api:2.0.9'
    implementation 'ch.qos.logback:logback-classic:1.4.11'

    // Testing
    testImplementation 'org.junit.jupiter:junit-jupiter:5.10.0'
    testImplementation 'org.assertj:assertj-core:3.24.2'
}

application {
    mainClass = 'com.example.lombok.Main'
}

test {
    useJUnitPlatform()   // required for JUnit 5
}
```

---

## Key Points

### `compileOnly` vs `implementation`

| Configuration | Lombok on classpath at... | Use |
|---|---|---|
| `compileOnly` | compile time only | Lombok itself (no runtime need) |
| `annotationProcessor` | annotation processing | Lombok's code generator |
| `implementation` | compile + runtime | SLF4J, Logback |

Lombok annotations are stripped from bytecode after compilation. The generated code (getters, setters, etc.) is inlined directly into your `.class` files. There is **no Lombok dependency at runtime**.

### Why two Lombok entries?

```groovy
compileOnly         "org.projectlombok:lombok:..."   // makes annotations visible to javac
annotationProcessor "org.projectlombok:lombok:..."   // runs Lombok's code generator
```

Both are required. `compileOnly` without `annotationProcessor` means javac sees `@Data` but doesn't know what to do with it. `annotationProcessor` without `compileOnly` means the processor runs but the annotation class isn't on the classpath.

### Test sources

Test code that uses Lombok (e.g., `@Builder` in test factories) needs the same two configurations with `test` prefix:

```groovy
testCompileOnly     "org.projectlombok:lombok:..."
testAnnotationProcessor "org.projectlombok:lombok:..."
```

---

## Gradle Wrapper

Generate the wrapper so teammates don't need Gradle installed:

```bash
gradle wrapper --gradle-version 8.5
```

This creates:
```
gradle/
  wrapper/
    gradle-wrapper.jar        ← bootstrap binary (commit this)
    gradle-wrapper.properties ← points to Gradle distribution URL
gradlew     ← Unix shell script
gradlew.bat ← Windows batch script
```

Commit all four files. Teammates run `./gradlew build` without installing Gradle.

---

## Kotlin DSL (build.gradle.kts)

If you prefer Kotlin DSL:

```kotlin
plugins {
    java
    application
}

val lombokVersion = "1.18.30"

dependencies {
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.11")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.assertj:assertj-core:3.24.2")
}

application {
    mainClass.set("com.example.lombok.Main")
}

tasks.test {
    useJUnitPlatform()
}
```

---

## Spring Boot + Gradle

Spring Boot's dependency management simplifies the Lombok version:

```groovy
plugins {
    id 'org.springframework.boot' version '3.2.0'
    id 'io.spring.dependency-management' version '1.1.4'
    id 'java'
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter'

    // Version managed by Spring Boot BOM — no version needed
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
}
```

---

## Useful Gradle Tasks

```bash
./gradlew run                          # run the application
./gradlew test                         # run all tests
./gradlew test --tests "*.UserTest"    # run one test class
./gradlew build                        # compile + test + package JAR
./gradlew clean build                  # clean then full build
./gradlew dependencies                 # show dependency tree
```
