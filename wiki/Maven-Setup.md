# Maven Setup

How to configure Lombok in a Maven project, explained section by section.

---

## pom.xml (full file)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
             http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>lombok-maven-demo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <lombok.version>1.18.30</lombok.version>
    </properties>

    <dependencies>
        <!-- Lombok: provided scope — not included in the final JAR -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
            <scope>provided</scope>
        </dependency>

        <!-- SLF4J + Logback for @Slf4j -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>2.0.9</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.4.11</version>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.10.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.assertj</groupId>
            <artifactId>assertj-core</artifactId>
            <version>3.24.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                    <!--
                        annotationProcessorPaths: explicit annotation processor list.
                        Required when using maven-compiler-plugin 3.5+.
                        Without this, Lombok may be skipped during compilation.
                    -->
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                            <version>${lombok.version}</version>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>

            <!-- JUnit 5 support -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.1.2</version>
            </plugin>

            <!-- Run with: mvn exec:java -->
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <mainClass>com.example.lombok.Main</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## Key Points

### `<scope>provided</scope>`

Lombok is only needed during compilation. The `provided` scope tells Maven:
- Include Lombok on the **compile classpath**
- Do **not** include it in the final packaged JAR

This is equivalent to Gradle's `compileOnly`.

### `<annotationProcessorPaths>`

This is the **critical** configuration most tutorials miss. Without it, `maven-compiler-plugin 3.5+` may not discover the Lombok annotation processor even though Lombok is on the classpath.

```xml
<annotationProcessorPaths>
    <path>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>${lombok.version}</version>
    </path>
</annotationProcessorPaths>
```

If you use multiple annotation processors (e.g., MapStruct + Lombok), list all of them here:

```xml
<annotationProcessorPaths>
    <path>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>${lombok.version}</version>
    </path>
    <path>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct-processor</artifactId>
        <version>1.5.5.Final</version>
    </path>
</annotationProcessorPaths>
```

> **Order matters with MapStruct:** Lombok must come **before** MapStruct so that getter/setter methods exist when MapStruct generates mapper implementations.

---

## Spring Boot + Maven

Spring Boot's parent POM manages Lombok's version:

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.0</version>
</parent>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>

    <!-- No version needed — managed by parent POM -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

Note: Spring Boot uses `<optional>true</optional>` instead of `<scope>provided</scope>`. Both prevent Lombok from being included in the final JAR, but `optional` also signals to downstream projects that they shouldn't inherit this dependency.

---

## Useful Maven Commands

```bash
mvn compile              # compile only
mvn compile exec:java    # compile and run Main
mvn test                 # run all tests
mvn test -Dtest=UserTest # run one test class
mvn package              # compile + test + create JAR
mvn clean package        # clean then full build
mvn dependency:tree      # show dependency tree
mvn versions:display-dependency-updates  # check for newer versions
```

---

## Multi-Module Maven Project

If you have a parent POM:

```xml
<!-- parent/pom.xml -->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.30</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Child modules only need:
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <!-- version and scope inherited from parent -->
</dependency>
```
