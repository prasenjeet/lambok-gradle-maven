package com.example.lombok;

import com.example.lombok.model.Address;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AddressTest {

    private static final Address BASE = Address.builder()
            .street("123 Main St")
            .city("Springfield")
            .state("IL")
            .zipCode("62701")
            .country("USA")
            .build();

    @Test
    void valueFieldsAreAccessibleViaGetters() {
        // @Value generates getters; there are no setters (fields are final)
        assertThat(BASE.getStreet()).isEqualTo("123 Main St");
        assertThat(BASE.getCity()).isEqualTo("Springfield");
        assertThat(BASE.getState()).isEqualTo("IL");
        assertThat(BASE.getZipCode()).isEqualTo("62701");
        assertThat(BASE.getCountry()).isEqualTo("USA");
    }

    @Test
    void withCreatesModifiedCopyAndLeavesOriginalUnchanged() {
        // @With: returns a new instance with that field changed
        Address updated = BASE.withCity("Chicago").withZipCode("60601");

        // Original is immutable and unchanged
        assertThat(BASE.getCity()).isEqualTo("Springfield");
        assertThat(BASE.getZipCode()).isEqualTo("62701");

        // New instance has the updated fields
        assertThat(updated.getCity()).isEqualTo("Chicago");
        assertThat(updated.getZipCode()).isEqualTo("60601");

        // Unchanged fields are copied over
        assertThat(updated.getStreet()).isEqualTo(BASE.getStreet());
        assertThat(updated.getState()).isEqualTo(BASE.getState());
        assertThat(updated.getCountry()).isEqualTo(BASE.getCountry());
    }

    @Test
    void valueGeneratesEqualsAndHashCode() {
        Address a1 = Address.builder().street("1 A St").city("X").state("Y").zipCode("00001").country("Z").build();
        Address a2 = Address.builder().street("1 A St").city("X").state("Y").zipCode("00001").country("Z").build();

        assertThat(a1).isEqualTo(a2);
        assertThat(a1.hashCode()).isEqualTo(a2.hashCode());
    }

    @Test
    void valueGeneratesToString() {
        assertThat(BASE.toString()).contains("123 Main St", "Springfield", "IL", "62701");
    }

    @Test
    void withReturnsDifferentInstance() {
        Address updated = BASE.withCity("Chicago");

        assertThat(updated).isNotSameAs(BASE);
        assertThat(updated).isNotEqualTo(BASE);
    }
}
