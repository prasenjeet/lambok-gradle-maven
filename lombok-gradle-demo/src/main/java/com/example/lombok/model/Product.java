package com.example.lombok.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates individual Lombok annotations instead of the all-in-one @Data:
 *   @Getter              — generates getters for all fields
 *   @Setter              — generates setters for non-final fields
 *   @ToString(exclude=)  — excludes verbose fields from toString
 *   @EqualsAndHashCode(of=) — bases equality only on identity fields
 *   @RequiredArgsConstructor — constructor for @NonNull and final fields only
 *   @Getter(lazy=true)   — computed once on first call, then cached (thread-safe)
 */
@Getter
@Setter
@ToString(exclude = "description")
@EqualsAndHashCode(of = {"id", "sku"})
@RequiredArgsConstructor
public class Product {

    private Long id;

    @NonNull
    private final String sku;   // required: NonNull + final → included in @RequiredArgsConstructor

    @NonNull
    private final String name;  // required

    private String description;
    private BigDecimal price;
    private int stockQuantity;

    // Lazy getter: the expensive computation runs only on first access, result is cached.
    // Lombok wraps this in double-checked locking for thread safety.
    @Getter(lazy = true)
    private final List<String> tags = computeTags();

    private List<String> computeTags() {
        return Arrays.asList(name.toLowerCase().split("\\s+"));
    }
}
