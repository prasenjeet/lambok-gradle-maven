# IDE Setup

Lombok requires annotation processing to be enabled in your IDE. Without this, the IDE shows "cannot resolve symbol" errors for generated methods (getters, setters, builders, etc.) even though the project compiles and runs fine.

---

## IntelliJ IDEA

### 1. Install the Lombok Plugin

`File → Settings → Plugins → Marketplace` → search **"Lombok"** → Install → Restart IDE

> In IntelliJ 2020.3+, the Lombok plugin is **bundled** — no installation needed.

### 2. Enable Annotation Processing

`File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors`

- Check **Enable annotation processing**
- Processor path: **Obtain processors from project classpath** (default)

### 3. Verify

Open `User.java` and check that:
- `user.getFirstName()` is recognized (no red underline)
- `User.builder()` is recognized
- The **Structure** panel (`Alt+7`) shows generated methods

### IntelliJ with Gradle

IntelliJ auto-detects Lombok from `build.gradle` when you import the project as a Gradle project. If errors persist, run:

```
File → Invalidate Caches / Restart
```

then reimport the Gradle project.

### IntelliJ with Maven

Same steps. Make sure the `<annotationProcessorPaths>` section is present in `pom.xml` (see [Maven Setup](Maven-Setup)). IntelliJ respects this config when delegating builds to Maven.

---

## Eclipse / Spring Tool Suite (STS)

### 1. Run the Lombok Installer

Download `lombok.jar` from [projectlombok.org/download](https://projectlombok.org/download):

```bash
java -jar lombok.jar
```

The GUI installer:
1. Detects your Eclipse installations
2. Select the target Eclipse
3. Click **Install / Update**
4. Restart Eclipse

### 2. Enable Annotation Processing (per project)

Right-click the project → **Properties → Java Compiler → Annotation Processing**

- Check **Enable annotation processing**
- Check **Enable processing in editor**

### 3. Configure the Factory Path (Maven projects)

Right-click the project → **Properties → Java Compiler → Annotation Processing → Factory Path**

- Check **Enable project specific settings**
- Add `lombok-1.18.30.jar` from your `.m2` cache

### Verify

The **Outline** view should show generated methods. No more red errors on `@Data` classes.

---

## VS Code

### 1. Install the Extension Pack for Java

Open Extensions (`Ctrl+Shift+X`) → search **"Extension Pack for Java"** (Microsoft) → Install

This includes the Language Server for Java which supports annotation processing.

### 2. Configure settings.json

Add to `.vscode/settings.json`:

```json
{
    "java.compile.nullAnalysis.mode": "automatic",
    "java.configuration.updateBuildConfiguration": "automatic"
}
```

### 3. For Maven projects

VS Code reads the `<annotationProcessorPaths>` from `pom.xml` automatically when the Java Language Server indexes the project.

### 4. For Gradle projects

VS Code reads `build.gradle` and picks up `annotationProcessor` configurations automatically.

### Known Limitation

VS Code's Java support for Lombok is functional but less mature than IntelliJ. Complex Lombok features like `@Builder` with `@Singular` may occasionally show false errors in the editor even when the code compiles correctly. Run the build to verify.

---

## NetBeans

1. Open **Tools → Options → Java → Annotation Processors**
2. Add `lombok.jar` as an annotation processor
3. Enable annotation processing for the project

---

## Checking That Lombok Works

Create a simple test class and verify the IDE recognizes generated methods:

```java
@Data
@Builder
public class LombokCheck {
    private String message;

    public static void main(String[] args) {
        LombokCheck c = LombokCheck.builder()
            .message("Lombok is working!")
            .build();
        System.out.println(c.getMessage());  // should NOT show red underline
        System.out.println(c);               // toString() works
    }
}
```

If `getMessage()` and `builder()` are recognized without errors, Lombok is configured correctly.

---

## lombok.config

You can control Lombok's behavior project-wide by placing a `lombok.config` file in the project root or a source directory:

```properties
# lombok.config

# Prevent Lombok from generating @ConstructorProperties on constructors
# (required for Jackson deserialization in some setups)
lombok.anyConstructor.addConstructorProperties = false

# Log @Builder calls (useful for debugging)
# lombok.log.fieldName = LOGGER   ← rename 'log' to 'LOGGER'

# Suppress specific annotations globally
# lombok.Data.flagUsage = warning
```

Place `lombok.config` alongside your source root. Lombok walks up the directory tree looking for this file.
