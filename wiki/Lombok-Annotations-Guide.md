# Lombok Annotations Guide

Complete reference for every Lombok annotation used in this project.

---

## @Data

**What it generates:** `@Getter` + `@Setter` + `@ToString` + `@EqualsAndHashCode` + `@RequiredArgsConstructor`

**Use when:** You want a standard mutable data class with full boilerplate.

```java
@Data
public class User {
    private Long id;
    private String name;
    private String email;
}

// Generated (conceptually):
// - getId(), setId(), getName(), setName(), getEmail(), setEmail()
// - toString()  → "User(id=1, name=Alice, email=alice@example.com)"
// - equals() + hashCode()  → based on all fields
// - User(String name) constructor  → @RequiredArgsConstructor (no @NonNull fields here)
```

> **Tip:** Combine with `@Builder` and `@NoArgsConstructor` for maximum flexibility.

---

## @Value

**What it generates:** Immutable version of `@Data` — all fields become `private final`, no setters generated.

**Use when:** You need an immutable value object (DTO, configuration, record-like class).

```java
@Value
@Builder
@With
public class Address {
    String street;
    String city;
    String zipCode;
}

Address a = Address.builder().street("123 Main").city("Chicago").zipCode("60601").build();
// a.setCity("NYC");  ← compile error: no setter exists
```

---

## @Builder

**What it generates:** A static inner `Builder` class with a fluent API.

```java
@Builder
public class User { ... }

User user = User.builder()
    .id(1L)
    .firstName("Alice")
    .lastName("Smith")
    .build();
```

### @Builder.Default

Sets a default value that applies when the builder does NOT call that setter:

```java
@Builder
public class User {
    @Builder.Default
    private boolean active = true;  // true unless .active(false) is called
}
```

> **Warning:** `@Builder.Default` does NOT apply when using `new User()` or `@AllArgsConstructor`. It only applies through the builder.

### @Singular

For `List`, `Set`, or `Map` fields — generates per-element add methods:

```java
@Builder
public class Order {
    @Singular
    private List<OrderItem> items;
}

Order o = Order.builder()
    .item(item1)      // add one
    .item(item2)      // add another
    .build();         // items is an unmodifiable list
```

---

## @With

**What it generates:** `withXxx()` methods that return a **new instance** with one field changed.

**Use when:** Working with `@Value` (immutable) objects that need to be "updated".

```java
@Value @Builder @With
public class Address {
    String city;
    String zipCode;
}

Address original = Address.builder().city("Springfield").zipCode("62701").build();
Address moved    = original.withCity("Chicago").withZipCode("60601");
// original is UNCHANGED; moved is a new instance
```

---

## @Getter / @Setter

Generate getters and/or setters at class level (all fields) or field level (individual).

```java
@Getter
@Setter
public class Product {
    private String name;       // getName(), setName()

    @Setter(AccessLevel.NONE)
    private String sku;        // getSku() only — no setter
}
```

### @Getter(lazy = true)

Computes the value on first access and caches it (thread-safe via double-checked locking).

```java
@Getter(lazy = true)
private final List<String> tags = computeExpensiveTags();

private List<String> computeExpensiveTags() { /* runs only once */ }
```

---

## @ToString

Generates `toString()`. Use `exclude` or `of` to control which fields appear.

```java
@ToString(exclude = "description")    // omit one verbose field
@ToString(of = {"id", "name"})        // only include these fields
```

---

## @EqualsAndHashCode

Generates `equals()` and `hashCode()`. Use `of` to base equality on identity fields only.

```java
@EqualsAndHashCode(of = {"id", "sku"})  // two Products are equal if id AND sku match
public class Product { ... }
```

> **Best practice:** Always specify `of` or `exclude` to avoid accidental equality based on mutable or transient fields.

---

## Constructor Annotations

| Annotation | Constructor generated |
|---|---|
| `@NoArgsConstructor` | `User()` — needed for JPA, Jackson, frameworks |
| `@AllArgsConstructor` | `User(Long id, String firstName, ...)` — all fields |
| `@RequiredArgsConstructor` | Only `@NonNull` and `final` fields (included in `@Data`) |

```java
@RequiredArgsConstructor
public class Product {
    @NonNull private final String sku;   // required
    @NonNull private final String name;  // required
    private String description;          // NOT in constructor
}

new Product("SKU-1", "Laptop");  // valid
new Product();                   // compile error — no no-arg constructor
```

---

## @NonNull

Inserts a null check at the top of the annotated method or constructor parameter.

```java
public void process(@NonNull String name) {
    // Lombok inserts:
    // if (name == null) throw new NullPointerException("name is marked @NonNull but is null");
    ...
}
```

> Applies to method parameters, constructor parameters, and fields (field-level triggers checks in setters and constructors).

---

## @Slf4j

Injects a `private static final Logger log` field using SLF4J.

```java
@Slf4j
public class MyService {
    public void doWork() {
        log.info("Starting work for user={}", userId);
        log.debug("Detail: {}", detail);
        log.error("Failed: {}", e.getMessage(), e);
    }
}
```

**Other logging annotations:**

| Annotation | Framework |
|---|---|
| `@Slf4j` | SLF4J (recommended — works with Logback, Log4j2, etc.) |
| `@Log` | `java.util.logging` (JDK built-in) |
| `@Log4j2` | Apache Log4j 2 |
| `@CommonsLog` | Apache Commons Logging |
| `@Flogger` | Google Flogger |

---

## @Cleanup

Ensures `close()` is called on a resource — equivalent to try-with-resources, but works on any local variable.

```java
@SneakyThrows
public void readFile() {
    @Cleanup InputStream in  = new FileInputStream("data.bin");
    @Cleanup OutputStream out = new FileOutputStream("out.bin");
    // ... read and write ...
    // 'in' and 'out' are closed here, in reverse order, even on exception
}
```

---

## @SneakyThrows

Rethrows checked exceptions without declaring them in the method signature. Uses Java's type-erasure to bypass the compiler check — the exception is **not wrapped**.

```java
@SneakyThrows(IOException.class)
public String readFile(Path path) {
    return Files.readString(path);  // throws IOException, but no 'throws' needed
}
```

> **Use sparingly.** It hides the fact that a checked exception can propagate. Good for implementing interfaces that don't allow checked exceptions (e.g., `Runnable`).

---

## @Synchronized

Synchronizes on a private lock object (`$lock`) instead of `this`.

```java
@Synchronized
public void increment() {
    counter++;
    // Generates: synchronized($lock) { counter++; }
}
```

**Why not just `synchronized` on the method?** Synchronized methods lock on `this`, which external code can also lock on, creating deadlock risk. `@Synchronized` uses a hidden private field.

---

## @Accessors

Changes how getters and setters are named or how they behave.

```java
// chain = true: setters return 'this' for method chaining
@Accessors(chain = true)
@Setter @Getter
public class Employee {
    private String name;
    private String department;
}

employee.setName("Alice").setDepartment("Engineering");  // chaining works

// fluent = true: getter/setter names match field names (no get/set prefix)
@Accessors(fluent = true)
public class Config {
    private int timeout;
}

config.timeout(30);   // setter
config.timeout();     // getter
```

---

## Annotation Combinations

Common patterns used in real applications:

### JPA Entity
```java
@Data
@NoArgsConstructor        // JPA requires no-arg
@AllArgsConstructor       // convenient for tests
@Builder                  // builder for test data factories
@Entity
public class UserEntity { ... }
```

### Immutable DTO
```java
@Value
@Builder
@With
public class UserDto { ... }
```

### Service with logging
```java
@Slf4j
@RequiredArgsConstructor  // inject final dependencies
public class UserService {
    private final UserRepository repo;
    // log field auto-injected by @Slf4j
}
```
