package com.example.lombok.model;

import lombok.Builder;
import lombok.Value;
import lombok.With;

/**
 * Demonstrates: @Value, @Builder, @With
 *
 * @Value = immutable version of @Data:
 *   - all fields become private final
 *   - generates getters (no setters)
 *   - generates toString, equals, hashCode
 *   - generates an all-args constructor
 *
 * @With generates withXxx() methods that return a NEW instance with that field changed,
 *   enabling safe "updates" on immutable objects.
 */
@Value
@Builder
@With
public class Address {

    String street;
    String city;
    String state;
    String zipCode;
    String country;
}
