# Code Examples

Side-by-side comparisons: what Lombok generates vs. what you'd write by hand.

---

## @Data

**With Lombok:**
```java
@Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
```

**Equivalent without Lombok (~70 lines):**
```java
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    // @RequiredArgsConstructor (no required fields here, so empty)
    public User() {}

    // Getters
    public Long getId()          { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public String getEmail()     { return email; }

    // Setters
    public void setId(Long id)                { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName)   { this.lastName = lastName; }
    public void setEmail(String email)         { this.email = email; }

    @Override
    public String toString() {
        return "User(id=" + id + ", firstName=" + firstName
             + ", lastName=" + lastName + ", email=" + email + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User u = (User) o;
        return Objects.equals(id, u.id) && Objects.equals(firstName, u.firstName)
            && Objects.equals(lastName, u.lastName) && Objects.equals(email, u.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email);
    }
}
```

---

## @Builder

**With Lombok:**
```java
@Builder
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}

// Usage:
User user = User.builder()
    .id(1L)
    .firstName("Alice")
    .email("alice@example.com")
    .build();
```

**Equivalent without Lombok (~40 lines):**
```java
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private User(Builder b) {
        this.id = b.id;
        this.firstName = b.firstName;
        this.lastName = b.lastName;
        this.email = b.email;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;

        public Builder id(Long id)                { this.id = id; return this; }
        public Builder firstName(String firstName) { this.firstName = firstName; return this; }
        public Builder lastName(String lastName)   { this.lastName = lastName; return this; }
        public Builder email(String email)         { this.email = email; return this; }

        public User build() { return new User(this); }
    }
}
```

---

## @Value + @With

**With Lombok:**
```java
@Value
@Builder
@With
public class Address {
    String street;
    String city;
    String zipCode;
}

// Usage:
Address a = Address.builder().street("123 Main").city("Springfield").zipCode("62701").build();
Address b = a.withCity("Chicago");   // new instance; 'a' unchanged
```

**Equivalent without Lombok (~60 lines):**
```java
public final class Address {
    private final String street;
    private final String city;
    private final String zipCode;

    // All-args constructor (no setters — immutable)
    public Address(String street, String city, String zipCode) {
        this.street  = street;
        this.city    = city;
        this.zipCode = zipCode;
    }

    public String getStreet()  { return street; }
    public String getCity()    { return city; }
    public String getZipCode() { return zipCode; }

    // @With methods — return a new instance
    public Address withStreet(String street)   { return new Address(street, city, zipCode); }
    public Address withCity(String city)       { return new Address(street, city, zipCode); }
    public Address withZipCode(String zipCode) { return new Address(street, city, zipCode); }

    @Override public String toString() { ... }
    @Override public boolean equals(Object o) { ... }
    @Override public int hashCode() { ... }

    // Builder (another ~20 lines)
    public static Builder builder() { ... }
    public static class Builder { ... }
}
```

---

## @NonNull

**With Lombok:**
```java
public void process(@NonNull String name) {
    System.out.println("Processing: " + name);
}
```

**Equivalent without Lombok:**
```java
public void process(String name) {
    if (name == null) throw new NullPointerException("name is marked @NonNull but is null");
    System.out.println("Processing: " + name);
}
```

---

## @Slf4j

**With Lombok:**
```java
@Slf4j
public class UserService {
    public void createUser(String name) {
        log.info("Creating user: {}", name);
    }
}
```

**Equivalent without Lombok:**
```java
public class UserService {
    private static final org.slf4j.Logger log =
        org.slf4j.LoggerFactory.getLogger(UserService.class);

    public void createUser(String name) {
        log.info("Creating user: {}", name);
    }
}
```

---

## @Cleanup

**With Lombok:**
```java
@SneakyThrows
public void copyFile(String src, String dst) {
    @Cleanup InputStream  in  = new FileInputStream(src);
    @Cleanup OutputStream out = new FileOutputStream(dst);
    in.transferTo(out);
}
```

**Equivalent without Lombok:**
```java
public void copyFile(String src, String dst) throws IOException {
    try (InputStream in   = new FileInputStream(src);
         OutputStream out = new FileOutputStream(dst)) {
        in.transferTo(out);
    }
}
```

> `@Cleanup` predates try-with-resources (Java 7). Prefer try-with-resources for new code; use `@Cleanup` when you need to defer close to a later point in the method.

---

## @Synchronized

**With Lombok:**
```java
public class Counter {
    private int count = 0;

    @Synchronized
    public void increment() {
        count++;
    }
}
```

**Equivalent without Lombok:**
```java
public class Counter {
    private final Object $lock = new Object[0];
    private int count = 0;

    public void increment() {
        synchronized ($lock) {
            count++;
        }
    }
}
```

---

## @Builder with @Singular

**With Lombok:**
```java
@Builder
public class Order {
    private User customer;

    @Singular
    private List<OrderItem> items;
}

// Usage:
Order order = Order.builder()
    .customer(user)
    .item(item1)
    .item(item2)
    .build();
// order.getItems() returns an unmodifiable list
```

**Equivalent without Lombok (~30 extra lines in the builder):**
```java
// Inside the Builder class:
private List<OrderItem> items = new ArrayList<>();

public Builder item(OrderItem item) {
    this.items.add(item);
    return this;
}

public Builder items(Collection<? extends OrderItem> items) {
    this.items.addAll(items);
    return this;
}

public Builder clearItems() {
    this.items.clear();
    return this;
}

public Order build() {
    return new Order(customer, Collections.unmodifiableList(new ArrayList<>(items)));
}
```

---

## @Accessors(chain = true)

**With Lombok:**
```java
@Setter @Getter @Accessors(chain = true)
public class Employee {
    private String name;
    private String department;
    private double salary;
}

// Usage:
Employee e = new Employee()
    .setName("Alice")
    .setDepartment("Engineering")
    .setSalary(90000);
```

**Equivalent without Lombok:**
```java
public Employee setName(String name) {
    this.name = name;
    return this;  // return 'this' instead of void
}
// ... same for every setter
```
