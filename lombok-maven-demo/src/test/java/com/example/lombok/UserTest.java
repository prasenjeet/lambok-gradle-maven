package com.example.lombok;

import com.example.lombok.model.Address;
import com.example.lombok.model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserTest {

    @Test
    void builderSetsExplicitFields() {
        User user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .age(30)
                .build();

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getEmail()).isEqualTo("john@example.com");
        assertThat(user.getAge()).isEqualTo(30);
    }

    @Test
    void builderDefaultActivatesWhenNotSet() {
        // @Builder.Default: active = true unless .active(false) is explicitly called
        User user = User.builder().firstName("Jane").lastName("Smith").build();

        assertThat(user.isActive()).isTrue();
    }

    @Test
    void builderDefaultCanBeOverridden() {
        User user = User.builder().firstName("Jane").lastName("Smith").active(false).build();

        assertThat(user.isActive()).isFalse();
    }

    @Test
    void dataGeneratesGettersAndSetters() {
        User user = User.builder().firstName("Bob").lastName("Brown").build();

        // @Data generates setters for non-final, non-static fields
        user.setEmail("bob@example.com");
        user.setAge(25);

        assertThat(user.getEmail()).isEqualTo("bob@example.com");
        assertThat(user.getAge()).isEqualTo(25);
    }

    @Test
    void dataGeneratesToString() {
        User user = User.builder().firstName("Alice").lastName("A").build();

        assertThat(user.toString()).contains("firstName=Alice", "lastName=A");
    }

    @Test
    void dataGeneratesEqualsAndHashCode() {
        User u1 = User.builder().id(10L).firstName("Alice").lastName("A").build();
        User u2 = User.builder().id(10L).firstName("Alice").lastName("A").build();
        User u3 = User.builder().id(99L).firstName("Alice").lastName("A").build();

        assertThat(u1).isEqualTo(u2);
        assertThat(u1.hashCode()).isEqualTo(u2.hashCode());
        assertThat(u1).isNotEqualTo(u3);
    }

    @Test
    void nonNullThrowsOnNullFirstName() {
        assertThatThrownBy(() -> User.builder()
                .firstName(null)
                .lastName("Doe")
                .build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("firstName");
    }

    @Test
    void nonNullThrowsOnNullLastName() {
        assertThatThrownBy(() -> User.builder()
                .firstName("John")
                .lastName(null)
                .build())
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("lastName");
    }

    @Test
    void getFullNameCombinesNames() {
        User user = User.builder().firstName("John").lastName("Doe").build();

        assertThat(user.getFullName()).isEqualTo("John Doe");
    }

    @Test
    void noArgsConstructorCreatesEmptyUser() {
        // @NoArgsConstructor generates a no-arg constructor (needed for frameworks)
        User user = new User();

        assertThat(user.getFirstName()).isNull();
        assertThat(user.isActive()).isFalse(); // Java primitive default, not @Builder.Default
    }
}
