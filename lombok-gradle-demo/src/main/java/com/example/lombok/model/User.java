package com.example.lombok.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Demonstrates: @Data, @Builder, @NoArgsConstructor, @AllArgsConstructor,
 *               @NonNull, @Builder.Default, @Slf4j
 *
 * @Data = @Getter + @Setter + @ToString + @EqualsAndHashCode + @RequiredArgsConstructor
 * @Builder.Default sets default values that survive the builder pattern
 */
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    private String email;

    private int age;

    // Default value applied when .active() is NOT called on the builder
    @Builder.Default
    private boolean active = true;

    private Address address;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void logUserInfo() {
        // @Slf4j injects: private static final Logger log = LoggerFactory.getLogger(User.class);
        log.info("User [{} - {}]: {} <{}>", id, isActive() ? "active" : "inactive",
                getFullName(), email);
    }
}
