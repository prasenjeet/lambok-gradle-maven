package com.example.lombok.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Demonstrates: @Singular (for collection fields in builders), nested static class with Lombok
 *
 * @Singular on a List field generates three builder methods:
 *   .item(OrderItem)                    — add one item
 *   .items(Collection<OrderItem>)       — add many items
 *   .clearItems()                       — clear all items
 * The resulting list is unmodifiable after build().
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;

    @NonNull
    private User customer;

    @Singular
    private List<OrderItem> items;

    @Builder.Default
    private LocalDateTime orderDate = LocalDateTime.now();

    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public enum OrderStatus {
        PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    }

    /**
     * Nested static class — Lombok annotations work the same inside a nested class.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItem {
        @NonNull
        private Product product;
        private int quantity;
        @NonNull
        private BigDecimal unitPrice;
    }
}
